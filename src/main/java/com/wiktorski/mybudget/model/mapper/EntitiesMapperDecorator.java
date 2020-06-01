package com.wiktorski.mybudget.model.mapper;

import com.wiktorski.mybudget.model.DTO.PaymentDTO;
import com.wiktorski.mybudget.model.entity.Payment;
import com.wiktorski.mybudget.model.entity.User;
import com.wiktorski.mybudget.repository.CategoryRepository;
import com.wiktorski.mybudget.service.security.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.time.LocalDate;
import java.util.Optional;

public abstract class EntitiesMapperDecorator implements EntitiesMapper {

    @Autowired
    @Qualifier("delegate")
    private  EntitiesMapper delegate;

    @Autowired
    private  CategoryRepository categoryRepo;
    @Autowired
    private  SecurityService securityService;

    @Override
    public Payment toPaymentEntity(PaymentDTO dto) {
        Payment payment = delegate.toPaymentEntity(dto);
        if(dto.getDate()==null)
            payment.setDate(LocalDate.now());
        payment.setCategory(categoryRepo.findById(dto.getCategoryId()).orElse(null));
        payment.setUser(getUser());
        return payment;
    }

    @Override
    public PaymentDTO toDTO(Payment payment){
        PaymentDTO dto = delegate.toDTO(payment);
        Optional.ofNullable(payment.getCategory()).ifPresent(category -> {
            dto.setCategoryId(category.getId());
            dto.setCategoryName(category.getName());
        });
        return dto;
    }


    private User getUser(){
        return securityService.getLoggedInUser();
    }

}
