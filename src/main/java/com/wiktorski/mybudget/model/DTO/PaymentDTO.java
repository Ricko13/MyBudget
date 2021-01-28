package com.wiktorski.mybudget.model.DTO;

import com.wiktorski.mybudget.model.entity.Category;
import com.wiktorski.mybudget.model.entity.FuturePayment;
import com.wiktorski.mybudget.model.entity.Payment;
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
public class PaymentDTO implements Serializable {
    private int id;
    private String name;
    private float price;
    private LocalDate date;
    private String description;
    private String categoryName;
    private String categoryColor;
    private int categoryId;


    //TODO may throw nullpointer exception cause of category
    public static PaymentDTO of(Payment payment) {
        return PaymentDTO.builder()
                .id(payment.getId())
                .categoryId(payment.getCategory().getId())
                .date(payment.getDate())
                .description(payment.getDescription())
                .name(payment.getName())
                .price(payment.getPrice())
                .build();
    }

    //Optional.ofNullable dla category
    public static PaymentDTO of(FuturePayment futurePayment) {
        Category cat = futurePayment.getCategory();
        return PaymentDTO.builder()
                .date(futurePayment.getDate())
                .description(futurePayment.getDescription())
                .id(futurePayment.getId())
                .name(futurePayment.getName())
                .price(futurePayment.getPrice())
                .categoryId(cat == null ? -1 : cat.getId())
                .categoryName(cat == null ? "" : cat.getName())
                .build();
    }
}
