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
import java.text.SimpleDateFormat;

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
    private String categoryName;
    private int categoryId;


    //TODO may throw nullpointer exception
    public static PaymentDTO of(Payment payment) {
        return PaymentDTO.builder()
                .id(payment.getId())
                .categoryId(payment.getId())
                .date(payment.getJustDate())
                .description(payment.getDescription())
                .name(payment.getName())
                .price(payment.getPrice())
                .build();
    }


    public static PaymentDTO of(FuturePayment futurePayment) {
        Category cat = futurePayment.getCategory();
        return PaymentDTO.builder()
                .date(futurePayment.getDate() == null ? null : futurePayment.getDate().toString().split(" ")[0])
//                .date(futurePayment.getDate() == null ? null : futurePayment.getDate().toString())
                .description(futurePayment.getDescription() == null ? "" : futurePayment.getDescription())
                .id(futurePayment.getId())
                .name(futurePayment.getName())
                .price(futurePayment.getPrice())
                .categoryId(cat == null ? -1 : cat.getId())
                .categoryName(cat == null ? "" : cat.getName())
                .build();
    }
}
