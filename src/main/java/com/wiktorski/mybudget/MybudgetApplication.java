package com.wiktorski.mybudget;

import com.wiktorski.mybudget.controller.TestController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class MybudgetApplication {

    public static void main(String[] args) {
//        SpringApplication.run(MybudgetApplication.class, args);
        ApplicationContext context=SpringApplication.run(MybudgetApplication.class, args);
        System.out.println(context.getBean(TestController.class).sayHenlo());
    }

}
