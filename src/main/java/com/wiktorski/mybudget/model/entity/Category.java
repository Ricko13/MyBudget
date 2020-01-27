package com.wiktorski.mybudget.model.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;


@Entity
@NoArgsConstructor
@Getter
@Setter
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String name;
    @OneToMany(mappedBy = "category")
    private List<Payment> payments;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;

    public Category(String name, User user) {
        this.name = name;
        this.user = user;
    }

    public Category(String name) {
        this.name = name;
    }

    @JsonIgnore
    @JsonBackReference
    public List<Payment> getPayments(){
        return payments;
    }
    public void setPayments(List<Payment> payments) {
        this.payments = payments;
    }

    @Override
    public String toString() {
        return "Category{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", payments=" + payments +
                '}';
    }
}
