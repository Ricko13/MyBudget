package com.wiktorski.mybudget.Controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wiktorski.mybudget.Model.Category;
import com.wiktorski.mybudget.Model.Payment;
import com.wiktorski.mybudget.Repository.CategoryRepository;
import com.wiktorski.mybudget.Repository.PaymentRepository;
import com.wiktorski.mybudget.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/api")
public class TestController {

    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    CategoryRepository categoryRepo;
    @Autowired
    PaymentRepository paymentRepo;
    @Autowired
    UserService userService;

    @GetMapping("/category/add/{name}")
    public String addCategory(@PathVariable String name) throws JsonProcessingException {
        if(!name.isEmpty() && name!=null)
        categoryRepo.save(new Category(name));
       return  objectMapper.writeValueAsString(categoryRepo.findAll());
    }


    @GetMapping("/category")
    public String showCategories() throws JsonProcessingException {
        //return  objectMapper.writeValueAsString(categoryRepo.findAll());
        return objectMapper.writeValueAsString(userService.getCurrentUserCategories());
    }

    @GetMapping("/payment/test/{idCat}")
    public void addPaymentToCat(@PathVariable int idCat) throws Exception {
        Optional<Category> cat=categoryRepo.findById(idCat);
        if(cat.isPresent()){
        paymentRepo.save(new Payment("test",(float)13.40,cat.get(),null));
        }else{
            throw new Exception("Didnt find category with that id");
        }
    }


    @GetMapping("/payment")
    public String showPayments() throws JsonProcessingException {
       // return objectMapper.writeValueAsString(paymentRepo.findAll());
        return objectMapper.writeValueAsString(userService.getCurrentUserPayments());
    }

    @GetMapping("/showCat")
    public Category showCat(){
        Optional<Payment> pmt=paymentRepo.findById(6);
        return pmt.map(Payment::getCategory).orElse(null);

    }


}
