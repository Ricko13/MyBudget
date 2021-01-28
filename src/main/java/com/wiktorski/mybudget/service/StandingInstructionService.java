package com.wiktorski.mybudget.service;

import com.wiktorski.mybudget.common.exception.ExceptionType;
import com.wiktorski.mybudget.common.exception.MBException;
import com.wiktorski.mybudget.model.DTO.StandingInstructionDTO;
import com.wiktorski.mybudget.model.entity.StandingInstructionEntity;
import com.wiktorski.mybudget.model.mapper.EntitiesMapper;
import com.wiktorski.mybudget.repository.StandingInstructionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class StandingInstructionService extends AbstractBasicService {

    private final StandingInstructionRepository repository;
    private final EntitiesMapper mapper;
    private final PaymentService paymentService;

    public void addStandingInstruction(StandingInstructionDTO dto) {
        StandingInstructionEntity entity = mapper.toStandingInstruction(dto);
        entity.setUser(getCurrentUser());
        repository.save(entity);
    }

    public void updateStandingInstruction(StandingInstructionDTO dto) {
        StandingInstructionEntity entity = repository.findById(dto.getId()).orElseThrow(() -> new MBException(ExceptionType.ENTITY_NOT_FOUND));
        mapper.updateStandingInstruction(dto, entity);
        repository.save(entity);
    }

    public void deleteStandingInstruction(int id) {
        repository.deleteById(id);
    }

    public void processStandingInstruction(StandingInstructionEntity entity) {
        paymentService.addPaymentWithoutLoggedUser(mapper.standingToPayment(entity));
        entity.setLatestExecution(LocalDate.now());
        repository.save(entity);
    }

    public List<StandingInstructionDTO> getStandingInstructions() {
        if(getCurrentUser() == null)
            throw new MBException(ExceptionType.NO_LOGGED_USER);
        return repository.findAllByUser_Id(getCurrentUser().getId())
                .stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }
}
