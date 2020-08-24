package com.wiktorski.mybudget.service;

import com.wiktorski.mybudget.model.DTO.PaymentDTO;
import com.wiktorski.mybudget.model.entity.Payment;
import com.wiktorski.mybudget.repository.PaymentRepository;
import com.wiktorski.mybudget.service.security.SecurityService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

//@RequiredArgsConstructor
@Component
public class StandingInstructionJob {

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private PaymentRepository repo;

    @Autowired
    private SecurityService securityService;

    @Scheduled(fixedRate = 10000)
    public void processStandingInstructions() {


        /*PaymentDTO dto = PaymentDTO.builder()
                .date(LocalDate.now())
                .description("SCHEDULED TEST")
                .name("SCHEDULED")
                .price(13)
                .build();
        paymentService.addPayment(dto);*/
        System.out.println("****************************** SCHEDULED TASK ******************************");

    }

}
