package com.wiktorski.mybudget.repository;

import com.querydsl.core.types.Predicate;
import com.wiktorski.mybudget.model.entity.Payment;
import com.wiktorski.mybudget.model.entity.User;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Integer>, QuerydslPredicateExecutor {

    List<Payment> findByCategoryId(int id);

    List<Payment> findAllByUserOrderByDateDesc(User user);

    List<Payment> findAll(Predicate predicate);

    List<Payment> findAll(Predicate predicate, Sort sort);
}
