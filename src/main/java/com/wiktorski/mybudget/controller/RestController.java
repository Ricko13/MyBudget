package com.wiktorski.mybudget.controller;

import com.wiktorski.mybudget.model.entity.Payment;
import com.wiktorski.mybudget.model.entity.PaymentDTO;
import com.wiktorski.mybudget.repository.PaymentRepository;
import com.wiktorski.mybudget.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequiredArgsConstructor
@org.springframework.web.bind.annotation.RestController
@RequestMapping("/api")
public class RestController {

    private final PaymentRepository paymentRepository;
    private final PaymentService paymentService;

    public String henlo(){
        return "henlo there";
    }

    @PostMapping
    public String updatePayment(@ModelAttribute PaymentDTO paymentDTO){
        paymentService.updatePayment(paymentDTO);
        return "update request";
    }

    @GetMapping("/payment/edit/{id}")
    public Payment getPaymentDataToEdit(@PathVariable int id){
        return paymentRepository.findById(id).orElse(null);
    }

    @PostMapping("/payment/edit/{id}")
    public PaymentDTO editPayment(@RequestBody PaymentDTO request){
        return request;
    }
}
