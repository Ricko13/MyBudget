package com.wiktorski.mybudget.repository;

import com.querydsl.core.types.Predicate;
import com.wiktorski.mybudget.model.entity.Income;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.List;

public interface IncomeRepository extends JpaRepository<Income, Integer>, QuerydslPredicateExecutor {
    List<Income> findAll(Predicate predicate);
}
