package com.wiktorski.mybudget.Model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String name;
    private float amount;
    private Date date;
    @ManyToOne
    @JoinColumn(name="category_id")
    private Category category;

    @ManyToOne
    @JoinColumn(name="billingPeriod_id")
    private BillingPeriod billingPeriod;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;


    public Payment() {
    }

    public Payment(String name, float amount) {
        this.name = name;
        this.amount = amount;
    }

    public Payment(String name, float amount, User user) {
        this.name = name;
        this.amount = amount;
        this.user=user;
    }

    public Payment(String name, float amount, Category category, User user) {
        this.name = name;
        this.amount = amount;
        this.category = category;
        this.user=user;
    }

    public User getUser() { return user;}
    public void setUser(User user) {  this.user = user;}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public Date getDate() { return date;}

    public void setDate(Date date) { this.date = date; }

    @JsonManagedReference
    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    @JsonIgnore
    public BillingPeriod getBillingPeriod() {
        return billingPeriod;
    }

    public void setBillingPeriod(BillingPeriod billingPeriod) {
        this.billingPeriod = billingPeriod;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Payment{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", amount=" + amount +
                ", category=" + category +
                '}';
    }
}
