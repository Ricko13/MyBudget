package com.wiktorski.mybudget.controller;

import com.wiktorski.mybudget.model.Category;
import com.wiktorski.mybudget.model.Payment;
import com.wiktorski.mybudget.model.PaymentDTO;
import com.wiktorski.mybudget.model.User;
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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.ParseException;
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

    @GetMapping("/payment/edit/{id}")
    public String editPayment(@PathVariable int id, Model model){
        model.addAttribute("payment", paymentRepository.findById(id).orElse(null));
        model.addAttribute("categories", userService.getUserCategories());
        return "/payment/editPayment";
    }

    @ResponseBody
    @PostMapping("/payment/update")
    public String updatePayment(@ModelAttribute PaymentDTO paymentDTO){
        paymentService.updatePayment(paymentDTO);
        return paymentDTO.toString();
        //return "redirect:/history";
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

    @GetMapping("/myAccount")
    public String account(){
        return "/user/account";
    }

}
