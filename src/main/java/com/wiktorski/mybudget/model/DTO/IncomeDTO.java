package com.wiktorski.mybudget.model.DTO;

import com.wiktorski.mybudget.model.entity.Income;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class IncomeDTO implements Serializable {
    private int id;
    private int userId;
    private String title;
    private float value;
    private LocalDateTime timestamp;

    public static IncomeDTO of(Income income) {
        return IncomeDTO.builder()
                .id(income.getId())
                .userId(income.getUser().getId())
                .timestamp(income.getTimestamp())
                .title(income.getTitle())
                .value(income.getValue())
                .build();
    }
}
