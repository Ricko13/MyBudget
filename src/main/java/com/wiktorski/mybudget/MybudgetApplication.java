package com.wiktorski.mybudget;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class MybudgetApplication {

    public static void main(String[] args) {
        SpringApplication.run(MybudgetApplication.class, args);
//        ApplicationContext context=SpringApplication.run(MybudgetApplication.class, args);
//        System.out.println(context.getBean(TestController.class).sayHenlo());
    }

}
