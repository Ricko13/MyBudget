package com.wiktorski.mybudget.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.LocalDate;
import java.time.LocalDateTime;

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
    @CreationTimestamp
    private LocalDateTime timestamp;

    private LocalDate date;

    private String description;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

//    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public FuturePayment(User user, String name, float price, LocalDate localDate, String description, Category category) {
        this.user = user;
        this.name = name;
        this.price = price;
        this.date = localDate;
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
