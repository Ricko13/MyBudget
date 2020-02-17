package com.wiktorski.mybudget.repository;

import com.wiktorski.mybudget.model.entity.FuturePayment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FuturePaymentRepository extends JpaRepository<FuturePayment, Integer> {
}
