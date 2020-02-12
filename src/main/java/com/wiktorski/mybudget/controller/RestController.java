package com.wiktorski.mybudget.controller;

import com.wiktorski.mybudget.model.MBResponse;
import com.wiktorski.mybudget.model.entity.Payment;
import com.wiktorski.mybudget.model.entity.PaymentDTO;
import com.wiktorski.mybudget.service.PaymentService;
import com.wiktorski.mybudget.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@org.springframework.web.bind.annotation.RestController
@RequestMapping("/api")
public class RestController {

    private final PaymentService paymentService;
    private final UserService userService;

    @GetMapping
    public String henlo(){
        return "Henlo there";
    }


    //TODO no usage
    @GetMapping("/paymentt")
    public ResponseEntity<List<PaymentDTO>> getPaymentss(){
        return new ResponseEntity<>(
                userService.getUserPaymentsDesc().stream()
                        .map(paymentService::paymentToPaymentDTO).collect(Collectors.toList())
                , HttpStatus.OK);
    }

    @GetMapping("/paymentsDT")
    public MBResponse<List<PaymentDTO>> getPaymentsForDataTables(Map<String, String> params){
       MBResponse<List<PaymentDTO>> response = new MBResponse<>();
       response.setData(
               userService.getUserPaymentsDesc().stream()
                       .map(paymentService::paymentToPaymentDTO).collect(Collectors.toList())
       );
       return response;
    }

    @GetMapping("/payments")
    public MBResponse<List<PaymentDTO>> getPayments(){
        MBResponse<List<PaymentDTO>> response = new MBResponse<>();
        response.setData(
                userService.getUserPaymentsDesc().stream()
                        .map(paymentService::paymentToPaymentDTO).collect(Collectors.toList())
        );
        return response;
    }
    @Transactional
    @PostMapping("/payment/add")
    public ResponseEntity addPayment(@RequestBody PaymentDTO paymentDTO){
        if(paymentService.addPayment(paymentDTO))
            return ResponseEntity.ok(HttpStatus.OK);
        else
            return ResponseEntity.badRequest().body("Bad request");
    }

    @PostMapping("/payment/update")
    public ResponseEntity updatePayment(@RequestBody PaymentDTO paymentDTO){
       if( paymentService.updatePayment(paymentDTO) )
           return ResponseEntity.ok(HttpStatus.OK);
       else
           return ResponseEntity.badRequest().body("Bad request");

    }

    @GetMapping("/categoryDT/{name}")
    public MBResponse<List<PaymentDTO>> getPaymentsInCategoryDT(@PathVariable String name, Map<String, String> params){
        List<Payment> returnPayments = new ArrayList<>();
        //TODO should be already fetched from db in this form && or use stream filter
        userService.getUserPaymentsDesc().forEach(payment -> {
            if (payment.getCategory() != null && payment.getCategory().getName().equals(name))
                returnPayments.add(payment);
        });
        MBResponse<List<PaymentDTO>> response = new MBResponse<>();
        response.setData(returnPayments.stream().map(paymentService::paymentToPaymentDTO).collect(Collectors.toList()));
        return response;
    }

}
