package com.wiktorski.mybudget.model.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Setter
@Getter
public class CategorySummaryDTO implements Serializable {
    String name;
    float summaryAmount = 0;
    String color;
}
