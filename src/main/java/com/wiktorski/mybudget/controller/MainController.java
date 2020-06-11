package com.wiktorski.mybudget.controller;

import com.wiktorski.mybudget.model.entity.Payment;
import com.wiktorski.mybudget.service.PaymentService;
import com.wiktorski.mybudget.service.UserService;
import com.wiktorski.mybudget.service.security.SecurityService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
@AllArgsConstructor
public class MainController {

    UserService userService;
    PaymentService paymentService;
    SecurityService securityService;

    //@GetMapping("/")
    public String index(){
        return "index";
    }

    @GetMapping("/budget")
    public String budget(){
        return "/user/budget";
    }

    @GetMapping("/history")
    public String history(Model model) {
        model.addAttribute("categories", paymentService.getUserCategories());
        model.addAttribute("dataURL", "/api/paymentsDT");
        model.addAttribute("isFuture", false);
        return "/payment/payment";
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/test")
    public String test(){
        return "";
    }

    @GetMapping("/category/{name}")
    public String showInCategory(@PathVariable String name, Model model) {
        model.addAttribute("category", name);
        model.addAttribute("dataURL", "/api/categoryDT/"+name);
        model.addAttribute("categories", paymentService.getUserCategories());
        model.addAttribute("isFuture", false);
        return "/payment/payment";
    }

    @GetMapping("/futurePayments")
    public String futurePayments(Model model){
        model.addAttribute("dataURL", "/api/futurePaymentsDT");
        model.addAttribute("isFuture", true);
        model.addAttribute("categories", paymentService.getUserCategories());
        return "/payment/payment";
    }

    @GetMapping("/payment/add")
    public String paymentAdd(Model model) {
        model.addAttribute("categories", paymentService.getUserCategories());
        return "/payment/newPayment";
    }

    @GetMapping("/category")
    public String category(Model model) {
        model.addAttribute("categories", paymentService.getUserCategories());
        return "/category/category";
    }

    @GetMapping("/category/add")
    public String categoryAdd() {
        return "/category/newCategory";
    }

  /*  @PostMapping("/category/add")
    public String categoryAdd(@RequestParam String name, @RequestParam String color) {
        categoryRepository.save(new Category(name, color, securityService.getLoggedInUser()));
        return "redirect:/category";
    }*/

    @GetMapping("/category/delete/{id}")
    public String deleteCategory(@PathVariable int id) {
        paymentService.deleteCategory(id);
        return "redirect:/category";
    }

    @GetMapping("/myAccount")
    public String account(){
        return "/user/account";
    }

}
