package com.wiktorski.mybudget.service;

import com.wiktorski.mybudget.model.DTO.BudgetResponse;
import com.wiktorski.mybudget.model.DTO.IncomeDTO;
import com.wiktorski.mybudget.model.entity.Income;
import com.wiktorski.mybudget.repository.IncomeRepository;
import com.wiktorski.mybudget.service.security.SecurityService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BudgetService {

    private final SecurityService securityService;
    private final IncomeRepository incomeRepo;
    private final UserService userService;

    public BudgetResponse getBudgetResponse() {
        return BudgetResponse.builder().budget(securityService.getLoggedInUser().getBudget()).build();
    }

    public List<IncomeDTO> getIncome() {
        return securityService.getLoggedInUser()
                .getIncomes().stream().map(IncomeDTO::of).collect(Collectors.toList());
    }

    public void addIncome(IncomeDTO request) {
        userService.addToBudget(request.getValue());
        incomeRepo.save(
                Income.builder()
                        .title(request.getTitle())
                        .value(request.getValue())
                        .user(securityService.getLoggedInUser())
                        .build()
        );
    }
}
