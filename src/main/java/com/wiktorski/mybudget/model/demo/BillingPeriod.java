package com.wiktorski.mybudget.model.demo;

import com.wiktorski.mybudget.model.entity.Payment;
import com.wiktorski.mybudget.model.entity.User;

import javax.persistence.*;
import java.util.List;


//TODO to delete
//@Entity
public class BillingPeriod {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private float budget;
    private float savings;
    //@OneToMany(mappedBy = "billingPeriod")
    private List<Payment> payments;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public BillingPeriod() {
    }

    public BillingPeriod(float budget, float savings) {
        this.budget = budget;
        this.savings = savings;
    }

    public float getBudget() {
        return budget;
    }

    public void setBudget(float budget) {
        this.budget = budget;
    }

    public float getSavings() {
        return savings;
    }

    public void setSavings(float savings) {
        this.savings = savings;
    }

    public List<Payment> getPayments() {
        return payments;
    }

    public void setPayments(List<Payment> payments) {
        this.payments = payments;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }


}
