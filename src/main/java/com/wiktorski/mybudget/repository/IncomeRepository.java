package com.wiktorski.mybudget.repository;

import com.querydsl.core.types.Predicate;
import com.wiktorski.mybudget.model.entity.Income;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IncomeRepository extends JpaRepository<Income, Integer>, QuerydslPredicateExecutor {

    List<Income> findAll(Predicate predicate);

}
