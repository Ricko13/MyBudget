package com.wiktorski.mybudget.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class PaymentDTO implements Serializable {
    private int id;
    private String name;
    private float price;
    private String date;    //nie może być Date?
    private String description;
    private int categoryId;
}
