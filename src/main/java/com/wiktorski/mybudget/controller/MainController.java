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
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
@AllArgsConstructor
public class MainController {

    UserService userService;
    PaymentService paymentService;
    SecurityService securityService;
//    PaymentRepository paymentRepository;
//    CategoryRepository categoryRepository;

    @GetMapping("/")
    public String index(Model model){
       /* User user = securityService.getLoggedInUser();
        model.addAttribute("user", user);
        model.addAttribute("payments", paymentService.getUserPaymentsDesc());
        model.addAttribute("categories", paymentService.getUserCategories());*/
        return "index";
    }

    @GetMapping("/budget")
    public String budget(Model model){
      /*  User user = securityService.getLoggedInUser();
        model.addAttribute("user", user);*/
        return "/user/budget";
    }

    @Transactional //TODO ???
    @PostMapping("/budget/add")
    public String addToBudget(@RequestParam float amount){
        userService.addToBudget(amount);
        return "redirect:/budget";
    }

    @GetMapping("/history")
    public String history(Model model) {
        model.addAttribute("categories", paymentService.getUserCategories());
        model.addAttribute("dataURL", "/api/paymentsDT");
        model.addAttribute("isFuture", false);
        return "/payment/payment";
    }

    @GetMapping("/category/{name}")
    public String showInCategory(@PathVariable String name, Model model) {
        List<Payment> returnPayments = new ArrayList<>();
        //TODO should be already fetched from db in this form && or use stream filter
        paymentService.getUserPaymentsDesc().forEach(payment -> {
            if (payment.getCategory() != null && payment.getCategory().getName().equals(name))
                returnPayments.add(payment);
        });
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

  /*  @GetMapping("/payment/delete/{id}")
    public String deletePayment(@PathVariable int id) {
        paymentRepository.deleteById(id);
        return "redirect:/history";
    }*/

  /*  @GetMapping("/payment/edit/{id}")
    public String editPayment(@PathVariable int id, Model model){
        Payment payment =  paymentRepository.findById(id).orElse(null);
        model.addAttribute("payment",payment);
        model.addAttribute("categories", paymentService.getUserCategories());
        assert payment != null;         //TODO co
        model.addAttribute("date", new SimpleDateFormat("yyyy-MM-dd").format(payment.getDate()));
        if(payment.getCategory()!=null)
            model.addAttribute("paymentCategorycId", payment.getCategory().getId());
        else
            model.addAttribute("paymentCategoryId", -1);
        return "/payment/editPayment";
    }*/

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
