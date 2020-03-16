package com.wiktorski.mybudget.bootstrap;

import com.wiktorski.mybudget.model.entity.Category;
import com.wiktorski.mybudget.model.entity.Payment;
import com.wiktorski.mybudget.model.entity.User;
import com.wiktorski.mybudget.repository.CategoryRepository;
import com.wiktorski.mybudget.repository.PaymentRepository;
import com.wiktorski.mybudget.repository.UserRepository;
import com.wiktorski.mybudget.service.security.SecurityService;
import com.wiktorski.mybudget.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

//@Profile("dev")
@Component
@AllArgsConstructor
public class DevBootstrap implements ApplicationListener<ContextRefreshedEvent> {

    private UserService userService;
    private PaymentRepository paymentRepo;
    private CategoryRepository catRepo;
    private SecurityService securityService;
    private UserRepository userRepository;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        init();
    }

    private void setBudgetAmoung() {
        User user=securityService.getLoggedInUser();
        user.setBudget((float)108+(float)998+(float)36);
        userRepository.save(user);

    }

    private void init() {
        if (userService.findByUsername("a") == null) {
            User user = new User("a", "a", "dwiktorski13@gmail.com", "a", "a");
            user.setEnabled(true);
            userService.save(user);


            Payment pmnt = new Payment("nocat", (float) 25.60, user, LocalDate.now());
            paymentRepo.save(pmnt);

            Category cat = new Category("przykladowa kategoria", user);
            Payment pmnt2 = new Payment("cat", (float) 89.34, user, LocalDate.now());
            pmnt2.setCategory(cat);
            catRepo.save(cat);
            paymentRepo.save(pmnt2);
        }

//        nie działą
        securityService.autoLogin("a", "a");
    }
}
