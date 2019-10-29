package com.wiktorski.mybudget.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Null;
import java.util.List;
import java.util.Set;

@NoArgsConstructor
@Setter
@Getter
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

    @Null
    @OneToMany(mappedBy = "user")
    private List<Payment> payments;
    public void addPayment(Payment payment) {
        this.payments.add(payment);
    }       /*???*/

    @OneToMany(mappedBy = "user")
    private List<Category> categories;

    @ManyToMany
    private Set<Role> roles;

    @OneToOne(mappedBy = "user")
    private Budget budget;


    /*
    @OneToMany(mappedBy = "user")
    private List<BillingPeriod> billingPeriods;
    private int currentBillingPeriodId;
    */


    public User(String name, String surname, String email, String username, String password) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.username = username;
        this.password = password;
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
