package com.wiktorski.mybudget.controller;

import com.wiktorski.mybudget.model.entity.Category;
import com.wiktorski.mybudget.model.entity.Payment;
import com.wiktorski.mybudget.model.entity.PaymentDTO;
import com.wiktorski.mybudget.model.entity.User;
import com.wiktorski.mybudget.repository.CategoryRepository;
import com.wiktorski.mybudget.repository.PaymentRepository;
import com.wiktorski.mybudget.service.PaymentService;
import com.wiktorski.mybudget.service.security.SecurityService;
import com.wiktorski.mybudget.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Controller
@AllArgsConstructor
public class MainController {

    UserService userService;
    PaymentService paymentService;
    SecurityService securityService;
    PaymentRepository paymentRepository;
    CategoryRepository categoryRepository;

    @GetMapping("/")
    public String index(Model model){
        User user = securityService.getLoggedInUser();
        model.addAttribute("user", user);
        model.addAttribute("budgetSum", (user.getBudget()+user.getSavings()));
        model.addAttribute("payments", userService.getUserPaymentsDesc());
        model.addAttribute("categories", userService.getUserCategories());
        return "index";
    }

    @GetMapping("/budget")
    public String budget(Model model){
        User u = securityService.getLoggedInUser();
        model.addAttribute("user", u);
        model.addAttribute("budgetSum", u.getBudget()+u.getSavings());
        return "/user/budget";
    }

    @Transactional
    @PostMapping("/budget/add")
    public String addToBudget(@RequestParam float amount){
        try{
        userService.addToBudget(amount);
        }catch (Exception e){
            e.printStackTrace();
        }
        return "redirect:/budget";
    }

    @GetMapping("/history")
    public String history(Model model) {
        model.addAttribute("payments", userService.getUserPaymentsDesc());
        model.addAttribute("categories", userService.getUserCategories());
        return "/payment/payment";
    }

    @GetMapping("/payment/add")
    public String paymentAdd(Model model) {
        model.addAttribute("categories", userService.getUserCategories());
        return "/payment/newPayment";
    }

    @Transactional
    @PostMapping("/payment/add")
    public String paymentAddFinal(@RequestParam String name, @RequestParam float price,
                                  @RequestParam(name = "categories") String idCat,
                                  @RequestParam String date, @RequestParam String description) {
        try {
            paymentService.addPayment(name, price, idCat, date, "", description);
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

    @GetMapping("/payment/edit/{id}")
    public String editPayment(@PathVariable int id, Model model){
        Payment payment =  paymentRepository.findById(id).orElse(null);
        model.addAttribute("payment",payment);
        model.addAttribute("categories", userService.getUserCategories());
        assert payment != null;         //TODO co
        model.addAttribute("date", new SimpleDateFormat("yyyy-MM-dd").format(payment.getDate()));
        if(payment.getCategory()!=null)
            model.addAttribute("paymentCategoryId", payment.getCategory().getId());
        else
            model.addAttribute("paymentCategoryId", -1);
        return "/payment/editPayment";
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
        List<Payment> returnPayments = new ArrayList<>();
        userService.getUserPaymentsDesc().forEach(payment -> {
            if (payment.getCategory() != null && payment.getCategory().getName().equals(name))
                returnPayments.add(payment);
        });
        model.addAttribute("category", name);
        model.addAttribute("payments", returnPayments);
        return "/payment/paymentInCategory";
    }

    @GetMapping("/myAccount")
    public String account(){
        return "/user/account";
    }

}
