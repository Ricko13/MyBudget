package com.wiktorski.mybudget.repository;

import com.wiktorski.mybudget.model.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PaymentRepository extends JpaRepository<Payment, Integer> {
    List<Payment> findByCategoryId(int id);
}
