package com.wiktorski.mybudget.controller;

import com.wiktorski.mybudget.model.DTO.CategoryDTO;
import com.wiktorski.mybudget.model.DTO.PaymentDTO;
import com.wiktorski.mybudget.model.MBResponse;
import com.wiktorski.mybudget.model.entity.Payment;
import com.wiktorski.mybudget.model.entity.User;
import com.wiktorski.mybudget.service.PaymentService;
import com.wiktorski.mybudget.service.security.SecurityService;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@org.springframework.web.bind.annotation.RestController
@RequestMapping("/api")
public class RestController {

    private final PaymentService paymentService;
    private final SecurityService securityService;

    @GetMapping
    public String henlo() {
        return "Henlo there";
    }


/*    //TODO no usage
    @GetMapping("/paymentt")
    public ResponseEntity<List<PaymentDTO>> getPaymentss() {
        return new ResponseEntity<>(
                paymentService.getUserPaymentsDesc().stream()
                        .map(paymentService::paymentToPaymentDTO).collect(Collectors.toList())
                , HttpStatus.OK);
    }*/

    /**
     * without params map for DT
     */
    @GetMapping("/payments")
    public MBResponse<List<PaymentDTO>> getPayments() {
        MBResponse<List<PaymentDTO>> response = new MBResponse<>();
        response.setData(
                paymentService.getUserPaymentsDesc().stream()
                        .map(paymentService::paymentToPaymentDTO).collect(Collectors.toList())
        );
        return response;
    }

    @GetMapping("/paymentsDT")
    public MBResponse<List<PaymentDTO>> getPaymentsForDataTables(Map<String, String> params) {
        MBResponse<List<PaymentDTO>> response = new MBResponse<>();
        response.setData(
                paymentService.getUserPaymentsDesc().stream()
                        .map(paymentService::paymentToPaymentDTO).collect(Collectors.toList())
        );
        return response;
    }

    @Transactional
    @PostMapping("/payment/add")
    public ResponseEntity addPayment(@RequestBody PaymentDTO paymentDTO) {
        if (paymentService.addPayment(paymentDTO))
            return ResponseEntity.ok(HttpStatus.OK);
        else
            return ResponseEntity.badRequest().body("Bad request");
    }

    @PostMapping("/payment/update")
    public ResponseEntity updatePayment(@RequestBody PaymentDTO paymentDTO) {
        if (paymentService.updatePayment(paymentDTO))
            return ResponseEntity.ok(HttpStatus.OK);
        else
            return ResponseEntity.badRequest().body("Bad request");

    }

    @GetMapping("/payment/delete/{paymentId}")
    public ResponseEntity deletePayment(@PathVariable int paymentId) {
        if (paymentService.deletePaymentById(paymentId))
            return ResponseEntity.ok(HttpStatus.OK);
        else
            return ResponseEntity.badRequest().body("Bad request");
    }

    @GetMapping("/futurePaymentsDT")
    public MBResponse<List<PaymentDTO>> getFuturePaymentsDT(Map<String, String> params) {
        MBResponse<List<PaymentDTO>> response = new MBResponse<>();
        response.setData(
                paymentService.getFuturePaymentsDesc()
        );
        return response;
    }

    @Transactional
    @PostMapping("/futurePayment/add")
    public ResponseEntity addFuturePayment(@RequestBody PaymentDTO paymentDTO) {
        if (paymentService.addFuturePayment(paymentDTO))
            return ResponseEntity.ok(HttpStatus.OK);
        else
            return ResponseEntity.badRequest().body("Bad request");
    }

    @GetMapping("/futurePayment/delete/{id}")
    public ResponseEntity deteleFuturePayment(@PathVariable int id) {
        paymentService.deleteFuturePaymentById(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PostMapping("/futurePayment/update")
    public ResponseEntity updateFuturePayment(@RequestBody PaymentDTO paymentDTO) {
        paymentService.updateFuturePayment(paymentDTO);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PostMapping("/futurePayment/move")
    public ResponseEntity moveFuturePayment(@RequestBody PaymentDTO paymentDTO){
        paymentService.moveFuturePayment(paymentDTO);
        return ResponseEntity.ok(HttpStatus.OK);
    }


    @GetMapping("/categoryDT/{name}")
    public MBResponse<List<PaymentDTO>> getPaymentsInCategoryDT(@PathVariable String name, Map<String, String> params) {
        List<Payment> returnPayments = new ArrayList<>();
        //TODO should be already fetched from db in this form && or use stream filter
        paymentService.getUserPaymentsDesc().forEach(payment -> {
            if (payment.getCategory() != null && payment.getCategory().getName().equals(name))
                returnPayments.add(payment);
        });
        MBResponse<List<PaymentDTO>> response = new MBResponse<>();
        response.setData(returnPayments.stream().map(paymentService::paymentToPaymentDTO).collect(Collectors.toList()));
        return response;
    }

    @GetMapping("/categoryDT")
    public MBResponse<List<CategoryDTO>> getCategories(Map<String, String> params) {
        MBResponse<List<CategoryDTO>> response = new MBResponse<>();
        response.setData(paymentService.getUserCategories().stream().map(CategoryDTO::of).collect(Collectors.toList()));
        return response;
    }

    @GetMapping("/category/delete/{categoryId}")
    public ResponseEntity deleteCategory(@PathVariable int categoryId) {
        if (paymentService.deleteCategory(categoryId))
            return ResponseEntity.ok(HttpStatus.OK);
        else
            return ResponseEntity.badRequest().body("Bad Request");
    }

    @Transactional
    @PostMapping("/category/update")
    public ResponseEntity updateCategory(@RequestBody CategoryDTO categoryDTO) {
        if (paymentService.updateCategory(categoryDTO))
            return ResponseEntity.ok(HttpStatus.OK);
        else
            return ResponseEntity.badRequest().body("Bad request");
    }

    @GetMapping("/budget")
    public Map<String, String> getBudget() {
        User user = securityService.getLoggedInUser();
        Map<String, String> data = new HashMap<>();
        data.put("budget", String.valueOf(user.getBudget()));
        return data;
    }
}
