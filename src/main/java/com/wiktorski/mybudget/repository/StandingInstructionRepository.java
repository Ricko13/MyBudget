package com.wiktorski.mybudget.repository;

import com.querydsl.core.types.Predicate;
import com.wiktorski.mybudget.model.DTO.StandingInstructionDTO;
import com.wiktorski.mybudget.model.entity.StandingInstructionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StandingInstructionRepository extends JpaRepository<StandingInstructionEntity, Integer>, QuerydslPredicateExecutor<StandingInstructionEntity> {

    List<StandingInstructionEntity> findAll(Predicate predicate);

    List<StandingInstructionEntity> findAllByUser_Id(int id);
}
