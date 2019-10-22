package com.wiktorski.mybudget.Repository;

import com.wiktorski.mybudget.Model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
}
