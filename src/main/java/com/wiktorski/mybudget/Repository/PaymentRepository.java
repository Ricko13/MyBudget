package com.wiktorski.mybudget.Repository;

import com.wiktorski.mybudget.Model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PaymentRepository extends JpaRepository<Payment,Integer> {

//    List<Payment> findByCategory(String name);

}
