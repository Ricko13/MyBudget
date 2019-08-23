package com.wiktorski.mybudget.Controller;

import com.wiktorski.mybudget.Model.Category;
import com.wiktorski.mybudget.Model.Payment;
import com.wiktorski.mybudget.Model.User;
import com.wiktorski.mybudget.Repository.CategoryRepository;
import com.wiktorski.mybudget.Repository.PaymentRepository;
import com.wiktorski.mybudget.Service.SecurityService;
import com.wiktorski.mybudget.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class MainController {

    @Autowired
    UserService userService;
    @Autowired
    SecurityService securityService;
    @Autowired
    PaymentRepository paymentRepository;
    @Autowired
    CategoryRepository categoryRepository;

    @GetMapping("/history")
    public String history(Model model){
        model.addAttribute("payments",userService.getCurrentUserPayments());
        return "/payment/payment";
    }

    @GetMapping("/payment/add")
    public String paymentAdd(Model model){
        model.addAttribute("categories", userService.getCurrentUserCategories());
        return "/payment/newPayment";
    }

    //@Transactional
    @PostMapping("/payment/add")
    public String paymentAddFinal(@RequestParam String name, @RequestParam float amount, @RequestParam(required = false, name = "categories") String idCat){
        int idCategory=-1;
        /*Optional<Category> cat=categoryRepository.findById(idCategory);
       cat.ifPresentOrElse(category -> paymentRepository.save(new Payment(name,amount,category,securityService.getLoggedInUser())),
               ()->  );*/

       if(idCat.isEmpty())
           paymentRepository.save(new Payment(name,amount, securityService.getLoggedInUser()));
       else
          idCategory=Integer.parseInt(idCat);
        if(idCategory!=-1){
           Optional<Category> cat=categoryRepository.findById(idCategory);
            cat.ifPresent(category -> paymentRepository.save(new Payment(name,amount,category,securityService.getLoggedInUser())));
        }
        return "redirect:/history";
    }

    @GetMapping("/payment/delete/{id}")
    public String deletePayment(@PathVariable int id, Model model){
        paymentRepository.deleteById(id);
        return "redirect:/history";
    }

    @GetMapping("/category")
    public String category(Model model){
        model.addAttribute("categories",userService.getCurrentUserCategories());
        return "/category/category";
    }

    @GetMapping("/category/add")
    public String categoryAdd(){
        return "/category/newCategory";
    }

    @PostMapping("/category/add")
    public String categoryAdd(@RequestParam String name){
        categoryRepository.save(new Category(name,securityService.getLoggedInUser()));
        return "redirect:/category";
    }

    @GetMapping("/category/delete/{id}")
    public String deleteCategory(@PathVariable int id){
        categoryRepository.deleteById(id);
        return "redirect:/category";
    }

    @GetMapping("/category/{name}")
    public String showInCategory(@PathVariable String name, Model model){
        User user=securityService.getLoggedInUser();
        List<Payment> payments=userService.getCurrentUserPayments();
        List<Payment> returnPayments=new ArrayList<Payment>();
        for(Payment payment:payments){
            if(payment.getCategory()!=null){
                if(payment.getCategory().getName().equals(name))
                    returnPayments.add(payment);
            }
        }
        model.addAttribute("category",name);
        model.addAttribute("payments", returnPayments);
        return "/payment/paymentInCategory";
    }

}
