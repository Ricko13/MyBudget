package com.wiktorski.mybudget.bootstrap;

import com.wiktorski.mybudget.model.Budget;
import com.wiktorski.mybudget.model.Category;
import com.wiktorski.mybudget.model.Payment;
import com.wiktorski.mybudget.model.User;
import com.wiktorski.mybudget.repository.CategoryRepository;
import com.wiktorski.mybudget.repository.PaymentRepository;
import com.wiktorski.mybudget.repository.UserRepository;
import com.wiktorski.mybudget.service.SecurityService;
import com.wiktorski.mybudget.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

@Component
@AllArgsConstructor
public class DevBootstrap implements ApplicationListener<ContextRefreshedEvent> {

    private UserService userService;
    private PaymentRepository paymentRepo;
    private CategoryRepository catRepo;
    private SecurityService securityService;


    //    NIE DZIALA
    //@Value("@{custom.devMode}") //wyrzuca IllegalArgumentExc
    /*@Value("#{new Boolean('$custom.devMode')}")
    private boolean isDevMode;*/

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        //if (isDevMode)
            init();
    }

    private void init() {
        if(userService.findByUsername("a")==null){
        User user = new User("a", "a", "dwiktorski13@gmail.com", "a", "a");
        user.setEnabled(true);
        Budget budget=new Budget();

        user.setBudget(budget);
        userService.save(user);

        Payment pmnt = new Payment("nocat", (float) 25.60, user,new Date());

        paymentRepo.save(pmnt);

        Category cat = new Category("przykladowa kategoria", user);
        Payment pmnt2 = new Payment("cat", (float) 89.34, user, new Date());
        pmnt2.setCategory(cat);
        catRepo.save(cat);
        paymentRepo.save(pmnt2);
        }

//        nie działą
        securityService.autoLogin("a","a");
    }
}
