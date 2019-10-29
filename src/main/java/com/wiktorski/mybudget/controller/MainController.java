package com.wiktorski.mybudget.controller;

import com.wiktorski.mybudget.model.Category;
import com.wiktorski.mybudget.model.Payment;
import com.wiktorski.mybudget.repository.CategoryRepository;
import com.wiktorski.mybudget.repository.PaymentRepository;
import com.wiktorski.mybudget.service.PaymentService;
import com.wiktorski.mybudget.service.SecurityService;
import com.wiktorski.mybudget.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@AllArgsConstructor
public class MainController {

    UserService userService;
    PaymentService paymentService;
    SecurityService securityService;
    PaymentRepository paymentRepository;
    CategoryRepository categoryRepository;

    @GetMapping("/history")
    public String history(Model model) {
        List<Payment> payments=userService.getUserPaymentsDesc();
            for(Payment p:payments){
                Date date=p.getDate();
                p.setJustDate(new SimpleDateFormat("dd-MM-yyyy").format(date));
                p.setJustTime(new SimpleDateFormat("HH:mm").format(date));
            }

        model.addAttribute("payments", userService.getUserPaymentsDesc());
        model.addAttribute("categories", userService.getUserCategories());
        return "/payment/payment";
    }

    @GetMapping("/payment/add")
    public String paymentAdd(Model model) {
        model.addAttribute("categories", userService.getUserCategories());
        return "/payment/newPayment";
    }

    //@Transactional
    @PostMapping("/payment/add")
    public String paymentAddFinal(@RequestParam String name, @RequestParam float price,
                                  @RequestParam(name = "categories") String idCat,
                                  @RequestParam String date, @RequestParam String time, @RequestParam String description) {
        try {
            paymentService.addPayment(name, price, idCat, date, time, description);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "redirect:/history";
    }

    @GetMapping("/payment/delete/{id}")
    public String deletePayment(@PathVariable int id, Model model) {
        paymentRepository.deleteById(id);
        return "redirect:/history";
    }

    @GetMapping("/category")
    public String category(Model model) {
        model.addAttribute("categories", userService.getUserCategories());
        return "/category/category";
    }

    @GetMapping("/category/add")
    public String categoryAdd() {
        return "/category/newCategory";
    }

    @PostMapping("/category/add")
    public String categoryAdd(@RequestParam String name) {
        categoryRepository.save(new Category(name, securityService.getLoggedInUser()));
        return "redirect:/category";
    }

    @GetMapping("/category/delete/{id}")
    public String deleteCategory(@PathVariable int id) {
        paymentService.deleteCategory(id);
        return "redirect:/category";
    }

    @GetMapping("/category/{name}")
    public String showInCategory(@PathVariable String name, Model model) {
        List<Payment> payments = userService.getUserPaymentsDesc();
        List<Payment> returnPayments = new ArrayList<>();
        for (Payment payment : payments) {
            if (payment.getCategory() != null) {
                if (payment.getCategory().getName().equals(name))
                    returnPayments.add(payment);
            }
        }
        model.addAttribute("category", name);
        model.addAttribute("payments", returnPayments);
        return "/payment/paymentInCategory";
    }

    @GetMapping("/budget")
    public String budget(Model model){
//        model.addAttribute("budget", );
        return "/budget/budget";
    }

    @GetMapping("/myAccount")
    public String account(){
        return "/user/account";
    }

}
