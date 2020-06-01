package com.wiktorski.mybudget.controller;

import com.wiktorski.mybudget.model.DTO.BudgetResponse;
import com.wiktorski.mybudget.model.DTO.CategoryDTO;
import com.wiktorski.mybudget.model.DTO.IncomeDTO;
import com.wiktorski.mybudget.model.DTO.MBResponse;
import com.wiktorski.mybudget.model.DTO.PaymentDTO;
import com.wiktorski.mybudget.model.DTO.ReportDTO;
import com.wiktorski.mybudget.model.entity.Payment;
import com.wiktorski.mybudget.model.mapper.EntitiesMapper;
import com.wiktorski.mybudget.service.BudgetService;
import com.wiktorski.mybudget.service.PaymentService;
import com.wiktorski.mybudget.service.ReportingService;
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
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@org.springframework.web.bind.annotation.RestController
@RequestMapping("/api")
public class RestController {

    private final PaymentService paymentService;
    private final SecurityService securityService;
    private final BudgetService budgetService;
    private final EntitiesMapper mapper;
    private final ReportingService reportingService;

    @GetMapping
    public String henlo() {
        return "Henlo there";
    }

    /**
     * without params map for DT
     */
    @GetMapping("/payments")
    public MBResponse<List<PaymentDTO>> getPayments() {
        MBResponse<List<PaymentDTO>> response = new MBResponse<>();
        response.setData(
                paymentService.getUserPaymentsDesc().stream()
                        .map(mapper::toDTO).collect(Collectors.toList())
        );
        return response;
    }

    @GetMapping("/paymentsDT")
    public MBResponse<List<PaymentDTO>> getPaymentsForDataTables(Map<String, String> params) {
        MBResponse<List<PaymentDTO>> response = new MBResponse<>();
        response.setData(
                paymentService.getUserPaymentsDesc().stream()
                        .map(mapper::toDTO).collect(Collectors.toList())
        );
        return response;
        //TODO wykorzystaj builer jak niżej, i przerzuć mapowanie do serwisu
        /*return MBResponse.<List<PaymentDTO>>builder().data(
                paymentService.getUserPaymentsDesc().stream()
                        .map(mapper::toDTO).collect(Collectors.toList())
        ).build();*/
    }


    @PostMapping("/payment/add")
    public ResponseEntity addPayment(@RequestBody PaymentDTO paymentDTO) {
        paymentService.addPayment(paymentDTO);
        return ResponseEntity.ok("Added payment succesfully");

    }

    @PostMapping("/payment/update")
    public ResponseEntity updatePayment(@RequestBody PaymentDTO paymentDTO) {
        paymentService.updatePayment(paymentDTO);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @GetMapping("/payment/delete/{paymentId}")
    public ResponseEntity deletePayment(@PathVariable int paymentId) {
        paymentService.deletePaymentById(paymentId);
        return ResponseEntity.ok(HttpStatus.OK);
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
    public ResponseEntity deleteFuturePayment(@PathVariable int id) {
        paymentService.deleteFuturePaymentById(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PostMapping("/futurePayment/update")
    public ResponseEntity updateFuturePayment(@RequestBody PaymentDTO paymentDTO) {
        paymentService.updateFuturePayment(paymentDTO);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PostMapping("/futurePayment/move")
    public ResponseEntity moveFuturePayment(@RequestBody PaymentDTO paymentDTO) {
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
        response.setData(returnPayments.stream().map(mapper::toDTO).collect(Collectors.toList()));
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
        paymentService.deleteCategory(categoryId);
        return ResponseEntity.ok(HttpStatus.OK);

    }

    @Transactional
    @PostMapping("/category/update")
    public ResponseEntity updateCategory(@RequestBody CategoryDTO categoryDTO) {
        paymentService.updateCategory(categoryDTO);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @GetMapping("/category/chart") //TODO póki co takie samo jak /categoryDT
    public MBResponse<List<CategoryDTO>> getCategoryChartData() {
        return MBResponse.<List<CategoryDTO>>builder().data(
                paymentService.getUserCategories().stream().map(CategoryDTO::of).collect(Collectors.toList())
        ).build();
    }

    @GetMapping("/incomeDT")

    public MBResponse<List<IncomeDTO>> getIncome() {
        MBResponse<List<IncomeDTO>> response = new MBResponse<>();
        response.setData(securityService.getLoggedInUser().getIncomes().stream().map(IncomeDTO::of).collect(Collectors.toList()));
        return response;
    }

    @GetMapping("/budget")
    public BudgetResponse getBudgetData() {
        return budgetService.getBudgetResponse();
    }

    @PostMapping("/income")
    public void addIncome(@RequestBody IncomeDTO request) {
        budgetService.addIncome(request);
    }

    @PostMapping("/report")
    public ReportDTO getReportData(@RequestBody ReportDTO dto){
        return reportingService.getPaymentsReport(dto);
    }

    @GetMapping("/category")
    public List<CategoryDTO> getCategoried(){
        return securityService.getLoggedInUser().getCategories().stream().map(CategoryDTO::of).collect(Collectors.toList());
    }
}
