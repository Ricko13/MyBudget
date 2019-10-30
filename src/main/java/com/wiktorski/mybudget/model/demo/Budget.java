package com.wiktorski.mybudget.model.demo;

import com.wiktorski.mybudget.model.User;
import lombok.*;

import javax.persistence.*;

/*@Entity*/
@NoArgsConstructor
@Setter
@Getter
public class Budget {

    //    @GeneratedValue(strategy = GenerationType.AUTO)   //używamy id Usera poprzez @MapsId
    @Id
    private int id;
    private float budget;
    private float savings;
    private float toSave;

    //@JoinColumn(name = "user_id")
    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    private User user;

    public Budget(float budget, float savings, float toSave, User user) {
        this.budget = budget;
        this.savings = savings;
        this.toSave = toSave;
        this.user = user;
    }

    @Override
    public String toString() {
        return "Budget{" +
                "id=" + id +
                ", budget=" + budget +
                ", savings=" + savings +
                '}';
    }
}
