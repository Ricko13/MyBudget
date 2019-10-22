package com.wiktorski.mybudget.Model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.List;


@Entity
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


    public Category() {
    }

    public Category(String name, User user) {
        this.name = name;
        this.user = user;
    }

    public User getUser() { return user;}

    public void setUser(User user) { this.user = user;}

    public Category(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId(){ return id;}

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
