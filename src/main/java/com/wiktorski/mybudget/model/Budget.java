package com.wiktorski.mybudget.model;

import lombok.*;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Budget {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private float budget;
    private float savings;
    private float toSave;
    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Override
    public String toString() {
        return "Budget{" +
                "id=" + id +
                ", budget=" + budget +
                ", savings=" + savings +
                '}';
    }
}
