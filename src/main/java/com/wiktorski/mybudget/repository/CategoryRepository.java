package com.wiktorski.mybudget.repository;

import com.wiktorski.mybudget.model.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Integer> {

    Boolean existsById(int id);

}
