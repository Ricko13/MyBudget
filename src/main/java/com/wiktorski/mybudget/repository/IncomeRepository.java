package com.wiktorski.mybudget.repository;

import com.wiktorski.mybudget.model.entity.Income;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IncomeRepository extends JpaRepository<Income, Integer> {
}
