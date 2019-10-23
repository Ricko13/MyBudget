package com.wiktorski.mybudget.model;

import javax.persistence.*;
import javax.validation.constraints.Null;
import java.util.List;
import java.util.Set;


@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String name;
    private String surname;
    private String email;
    private String username;
    private String password;
    private String confirmationToken;
    private boolean enabled;
    @Transient
    private String passwordConfirm;
    //private String role;

    @Null
    @OneToMany(mappedBy = "user")
    private List<Payment> payments;

    public List<Payment> getPayments() {
        return payments;
    }

    public void addPayment(Payment payment) {
        this.payments.add(payment);
    }

    public void setPayments(List<Payment> payments) {
        this.payments = payments;
    }

    @OneToMany(mappedBy = "user")
    private List<Category> categories;

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    @ManyToMany
    private Set<Role> roles;
    /*
    @OneToMany(mappedBy = "user")
    private List<BillingPeriod> billingPeriods;
    private int currentBillingPeriodId;

    public List<BillingPeriod> getBillingPeriods() {
        return billingPeriods;
    }

    public void setBillingPeriods(List<BillingPeriod> billingPeriods) {
        this.billingPeriods = billingPeriods;
    }

    public int getCurrentBillingPeriodId() {
        return currentBillingPeriodId;
    }

    public void setCurrentBillingPeriodId(int currentBillingPeriodId) {
        this.currentBillingPeriodId = currentBillingPeriodId;
    }

    */

    public User() {
    }

    public User(String name, String surname, String email, String username, String password) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.username = username;
        this.password = password;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String login) {
        this.username = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPasswordConfirm() {
        return passwordConfirm;
    }

    public void setPasswordConfirm(String passwordConfirm) {
        this.passwordConfirm = passwordConfirm;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public String getConfirmationToken() {
        return confirmationToken;
    }

    public void setConfirmationToken(String confirmationToken) {
        this.confirmationToken = confirmationToken;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }


    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
