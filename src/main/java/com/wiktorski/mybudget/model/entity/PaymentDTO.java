package com.wiktorski.mybudget.model.entity;

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
    private String date;
    private String description;
    private int categoryId;

    public PaymentDTO of(Payment payment){
        return PaymentDTO.builder()
                .id(payment.getId())
                .categoryId(payment.getId())
                .date(payment.getJustDate())
                .description(payment.getDescription())
                .name(payment.getName())
                .price(payment.getPrice())
                .build();
    }
}
