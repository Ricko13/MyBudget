package com.wiktorski.mybudget.model.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class ReportDTO implements Serializable {

    private LocalDate startDate;
    private LocalDate endDate;

    private float budget;

    private int paymentsAmount;
    private float totalOutcomeAmount;
    private float totalIncomeAmount;
    private float averageDailyOutcome;
    private float incomeMinusOutcome;

    private List<CategorySummaryDTO> sumInCategories;
    private List<PaymentDTO> payments;

}
