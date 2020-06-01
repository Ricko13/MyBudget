package com.wiktorski.mybudget.service;

import com.wiktorski.mybudget.model.DTO.ReportDTO;
import com.wiktorski.mybudget.model.DTO.CategorySummaryDTO;
import com.wiktorski.mybudget.model.entity.Category;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class ReportingService {

    private final PaymentService paymentService;
    private final BudgetService budgetService;
    public static final String NO_CATEGORY = "No category";


    //TODO czy kolejność kolorów na pewno pokrywa się z kolejnością wartości
    public ReportDTO getPaymentsReport(ReportDTO dto) {
        Map<String, Float> valuesInCategories = new HashMap<>();
        Map<String, String> colorsInCategories = new HashMap<>();
        //List<CategorySummaryDTO> categoriesSummary = new LinkedList<>()
        final float[] totalOutcome = {0};
        final int[] paymentsAmount = {0};
        paymentService.getPayments(dto).forEach(payment -> {
            totalOutcome[0] += payment.getPrice();
            paymentsAmount[0]++;
            Category cat = payment.getCategory();
            if (cat != null) {
                valuesInCategories.merge(cat.getName(), payment.getPrice(), Float::sum);
                if(!colorsInCategories.containsKey(cat.getName()))
                    colorsInCategories.put(cat.getName(), cat.getColor());
            }else {
                valuesInCategories.merge(NO_CATEGORY, payment.getPrice(), Float::sum);
                if(!colorsInCategories.containsKey(NO_CATEGORY))
                    colorsInCategories.put(NO_CATEGORY, "#000000");
            }
        });
        dto.setTotalOutcomeAmount(totalOutcome[0]);
        dto.setSumInCategories(summaryDtoListFromMap(valuesInCategories, colorsInCategories));
        dto.setPaymentsAmount(paymentsAmount[0]);

        final float[] totalIncome = {0};
        budgetService.getIncome(dto).forEach(income -> totalIncome[0] += income.getValue());
        dto.setBudget(budgetService.getBudgetResponse().getBudget());
        dto.setTotalIncomeAmount(totalIncome[0]);
        dto.setAverageDailyOutcome(totalOutcome[0] / paymentsAmount[0]);
        dto.setIncomeMinusOutcome(totalIncome[0] - totalOutcome[0]);
       return  dto;
    }

    private List<CategorySummaryDTO> summaryDtoListFromMap(Map<String, Float> values, Map<String, String> colors) {
        List<CategorySummaryDTO> toReturn = new LinkedList<>();
        values.forEach((key, value) -> {
            toReturn.add(CategorySummaryDTO.builder()
                    .name(key)
                    .summaryAmount(value)
                    .color(colors.get(key))
                    .build());
        });
        sortCategorySummaryByAmountAsc(toReturn);
        return toReturn;
    }

    private void sortCategorySummaryByAmountAsc(List<CategorySummaryDTO> list){
//        list.sort((cat1, cat2) -> cat1.getSummaryAmount() > cat2.getSummaryAmount() ? 1 : 0);
        list.sort(Comparator.comparingInt(cat -> (int) cat.getSummaryAmount()));
    }


    private ReportDTO processOutcome(ReportDTO dto) {
        return null;
    }

    private ReportDTO processIncome(ReportDTO dto) {
        return null;
    }

}
