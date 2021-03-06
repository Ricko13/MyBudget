package com.wiktorski.mybudget.model.mapper;

import com.wiktorski.mybudget.model.DTO.PaymentDTO;
import com.wiktorski.mybudget.model.DTO.StandingInstructionDTO;
import com.wiktorski.mybudget.model.entity.FuturePayment;
import com.wiktorski.mybudget.model.entity.Payment;
import com.wiktorski.mybudget.model.entity.StandingInstructionEntity;
import com.wiktorski.mybudget.model.entity.User;
import com.wiktorski.mybudget.repository.CategoryRepository;
import com.wiktorski.mybudget.repository.FuturePaymentRepository;
import com.wiktorski.mybudget.repository.PaymentRepository;
import com.wiktorski.mybudget.repository.StandingInstructionRepository;
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

    @Autowired
    private PaymentRepository paymentRepo;

    @Autowired
    private FuturePaymentRepository futurePaymentRepo;

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
    public FuturePayment toFutureEntity(PaymentDTO dto) {
        FuturePayment fut = delegate.toFutureEntity(dto);
        fut.setCategory(categoryRepo.findById(dto.getCategoryId()).orElse(null));
        fut.setUser(getUser());
        return fut;
    }

    @Override
    public StandingInstructionEntity toStandingInstruction(StandingInstructionDTO dto) {
        StandingInstructionEntity entity = delegate.toStandingInstruction(dto);
        entity.setCategory(categoryRepo.findById(dto.getCategoryId()).orElse(null));
        return entity;
    }

    @Override
    public Payment standingToPayment(StandingInstructionEntity standingInstruction) {
        Payment payment = delegate.standingToPayment(standingInstruction);
        payment.setDate(LocalDate.now());
        return payment;
    }

    /*    @Override
    public PaymentDTO toDTO(Payment payment){
        PaymentDTO dto = delegate.toDTO(payment);
        Optional.ofNullable(payment.getCategory()).ifPresent(category -> {
            dto.setCategoryId(category.getId());
            dto.setCategoryName(category.getName());
        });
        return dto;
    }*/

    @Override
    public void updatePayment(PaymentDTO paymentDTO, Payment toUpdate) {
        delegate.updatePayment(paymentDTO, toUpdate);
        categoryRepo.findById(paymentDTO.getCategoryId())
                .ifPresent(toUpdate::setCategory);
        paymentRepo.save(toUpdate);

    }

    @Override
    public void updateFuturePayment(PaymentDTO paymentDTO, FuturePayment toUpdate) {
        delegate.updateFuturePayment(paymentDTO, toUpdate);
        categoryRepo.findById(paymentDTO.getCategoryId())
                .ifPresent(toUpdate::setCategory);
        futurePaymentRepo.save(toUpdate);
    }

    @Override
    public void updateStandingInstruction(StandingInstructionDTO source, StandingInstructionEntity toUpdate) {
        delegate.updateStandingInstruction(source, toUpdate);
        toUpdate.setCategory(categoryRepo.findById(source.getCategoryId()).orElse(null));
    }

    private User getUser(){
        return securityService.getLoggedInUser();
    }

}
