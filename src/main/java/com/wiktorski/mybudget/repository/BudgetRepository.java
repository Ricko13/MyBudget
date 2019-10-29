package com.wiktorski.mybudget.repository;

import com.wiktorski.mybudget.model.Budget;
import org.springframework.data.repository.CrudRepository;

public interface BudgetRepository extends CrudRepository<Budget, Integer> {
}
