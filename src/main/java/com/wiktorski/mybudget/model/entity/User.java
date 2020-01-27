package com.wiktorski.mybudget.model.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@NoArgsConstructor
@Setter
@Getter
@Entity
public class User {

    @Id
    @GeneratedValue/*(strategy = GenerationType.AUTO)*/
    private int id;
    private String name;
    private String surname;
    private String email;
    private String username;
    private String password;
    private String confirmationToken;
    private boolean enabled;

    //TODO Powinno być w oddzielnym entity w relacji @OneToOne*/
    private float budget;
    private float savings;
    private float toSave;
    /*@OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY, optional = false)
    private Budget budget;*/
    /*****************************************************/

    @Transient
    private String passwordConfirm;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Payment> payments;
    public void addPayment(Payment payment) {
        this.payments.add(payment);
    }       /*???*/

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Category> categories;

    @ManyToMany
    private Set<Role> roles;

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
