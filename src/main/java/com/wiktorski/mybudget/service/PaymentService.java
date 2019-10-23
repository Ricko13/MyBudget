package com.wiktorski.mybudget.service;

import com.wiktorski.mybudget.model.Category;
import com.wiktorski.mybudget.model.Payment;
import com.wiktorski.mybudget.repository.CategoryRepository;
import com.wiktorski.mybudget.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import sun.java2d.pipe.SpanShapeRenderer;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

@Service
public class PaymentService {

    @Autowired
    private PaymentRepository paymentRepo;
    @Autowired
    private SecurityService securityService;
    @Autowired
    private CategoryRepository categoryRepo;

    public void addPayment(String name, float price, @Nullable String idCat, @Nullable Date date) {
        int idCategory = Integer.parseInt(idCat);
        Optional cat;
        if (date == null) date = new Date();

        if (idCategory != -1) {
            cat = categoryRepo.findById(idCategory);
            cat.ifPresent(
                    catg -> {
                        paymentRepo.save(new Payment(name, price, securityService.getLoggedInUser(), (Category) catg));
                    }
            );
        } else {
            paymentRepo.save(new Payment(name, price, securityService.getLoggedInUser(), date));
        }
    }

    public void addPayment(String name, float price, @Nullable String idCat, @Nullable String date) {
        addPayment(name, price, idCat, parseStringDate(date));
    }

    private Date parseStringDate(String date) {
        Date dt = null;
        try {
            dt = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm").parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return dt;
    }

}
