package com.wiktorski.mybudget.repository;

import com.wiktorski.mybudget.model.entity.Payment;
import com.wiktorski.mybudget.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.List;

public interface PaymentRepository extends JpaRepository<Payment, Integer>, QuerydslPredicateExecutor {

    List<Payment> findByCategoryId(int id);

    List<Payment> findAllByUserOrderByDateDesc(User user);

}
