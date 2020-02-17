package com.wiktorski.mybudget.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.wiktorski.mybudget.model.DTO.PaymentDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
//@Inheritance(strategy = InheritanceType.JOINED)
public class FuturePayment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String name;
    private float price;
    @Deprecated //TODO change to LocalDate or LocalDateTime
    //@Temporal(TemporalType.DATE)
    private Date date;
    private String description;
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Transient
    private String justDate;
    @Transient
    private String justTime;
/*
    public FuturePayment(String name, float price, User user, Date date) {
        this.name = name;
        this.price = price;
        this.date = date;
        this.user = user;
    }*/

    public FuturePayment(User user, String name, float price, Date date, String description, Category category) {
        this.user = user;
        this.name = name;
        this.price = price;
        this.date = date;
        this.description = description;
        this.category = category;
    }

    @JsonManagedReference
    public Category getCategory() {
        return category;
    }

    @Override
    public String toString() {
        return "Payment{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                '}';
    }
}
