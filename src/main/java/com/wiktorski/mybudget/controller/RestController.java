package com.wiktorski.mybudget.controller;

import com.wiktorski.mybudget.model.entity.Payment;
import com.wiktorski.mybudget.model.entity.PaymentDTO;
import com.wiktorski.mybudget.repository.PaymentRepository;
import com.wiktorski.mybudget.service.PaymentService;
import com.wiktorski.mybudget.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@org.springframework.web.bind.annotation.RestController
@RequestMapping("/api")
public class RestController {

    private final PaymentRepository paymentRepository;
    private final PaymentService paymentService;
    private final UserService userService;

    @GetMapping
    public String henlo(){
        return "Henlo there";
    }

    @GetMapping("/payment")
    public ResponseEntity<List<PaymentDTO>> getPayments(){
        return new ResponseEntity<>(
                userService.getUserPaymentsDesc().stream()
                        .map(paymentService::paymentToPaymentDTO).collect(Collectors.toList())
                , HttpStatus.OK);
    }

    @PostMapping("/payment/update")
    public ResponseEntity updatePayment(@RequestBody PaymentDTO paymentDTO){
       if( paymentService.updatePayment(paymentDTO) )
           return ResponseEntity.ok(HttpStatus.OK);
       else
           return ResponseEntity.badRequest().body("Bad request");

    }



}
