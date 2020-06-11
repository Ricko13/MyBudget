package com.wiktorski.mybudget.repository;

import com.wiktorski.mybudget.model.entity.FuturePayment;
import com.wiktorski.mybudget.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FuturePaymentRepository extends JpaRepository<FuturePayment, Integer> {

    List<FuturePayment> findAllByUserOrderByDateDesc(User user);

}
