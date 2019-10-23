package com.wiktorski.mybudget.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String name;
    private float price;
    @NotNull
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

   /* @ManyToOne
    @JoinColumn(name="billingPeriod_id")
    private BillingPeriod billingPeriod;*/

    public Payment() { }

    public Payment(String name, float price, User user) {
        this.name = name;
        this.price = price;
        this.user = user;
    }

    public Payment(String name, float price, User user, Category category) {
        this.name = name;
        this.price = price;
        this.category = category;
        this.user = user;
    }

    public Payment(String name, float price, User user, Date date) {
        this.name = name;
        this.price = price;
        this.date = date;
        this.description = description;
        this.category = category;
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @JsonManagedReference
    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    /*
        @JsonIgnore
        public BillingPeriod getBillingPeriod() {
            return billingPeriod;
        }
        public void setBillingPeriod(BillingPeriod billingPeriod) {
            this.billingPeriod = billingPeriod;
        }
    */
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getJustDate() {
        return justDate;
    }

    public void setJustDate(String justDate) {
        this.justDate = justDate;
    }

    public String getJustTime() {
        return justTime;
    }

    public void setJustTime(String justTime) {
        this.justTime = justTime;
    }

    @Override
    public String toString() {
        return "Payment{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", category=" + category +
                '}';
    }
}
