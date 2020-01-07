package com.wiktorski.mybudget.controller;

import com.wiktorski.mybudget.model.Payment;
import com.wiktorski.mybudget.model.PaymentDTO;
import com.wiktorski.mybudget.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequiredArgsConstructor
@org.springframework.web.bind.annotation.RestController
@RequestMapping("/api")
public class RestController {

    private final PaymentRepository paymentRepository;

    public String henlo(){
        return "henlo there";
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
