package com.wiktorski.mybudget.model.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class StandingInstructionDTO implements Serializable {

    private int id;
    private String name;
    private float price;
    private String description;
    private int categoryId;
    private String categoryName;
    private String categoryColor;
    private LocalDate latestExecution;

}
