package com.wiktorski.mybudget.service;
import com.wiktorski.mybudget.model.Category;
import com.wiktorski.mybudget.model.Payment;
import com.wiktorski.mybudget.repository.CategoryRepository;
import com.wiktorski.mybudget.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

@Service
public class PaymentService {

    private PaymentRepository paymentRepo;
    private SecurityService securityService;
    private CategoryRepository categoryRepo;

    public PaymentService(PaymentRepository paymentRepo, SecurityService securityService, CategoryRepository categoryRepo) {
        this.paymentRepo = paymentRepo;
        this.securityService = securityService;
        this.categoryRepo = categoryRepo;
    }

    public void addPayment(String name, float price, String idCat, @Nullable Date date, String description) {
        int idCategory = Integer.parseInt(idCat);
        if (date == null) {
            date = new Date();
        }

        Payment payment = new Payment(name, price, securityService.getLoggedInUser(), date);

        if (!description.equals("")) {
            payment.setDescription(description);
        }

        if (idCategory != -1) {
            Optional cat = categoryRepo.findById(idCategory);
            cat.ifPresent(
                    catg -> {
                        payment.setCategory((Category) catg);
                        //paymentRepo.save(payment);
                    }
            );
        }
        paymentRepo.save(payment);

    }

    public void addPayment(String name, float price, String idCat, String date, String time, String description) throws ParseException {
        addPayment(name, price, idCat, parseStringDate(date, time), description);
    }


    //    form always sends data but data can be empty like ""
    private Date parseStringDate(String date, String time) throws ParseException {
        Date dt;
        //Calendar calendar;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-ddHH:mm");

        if (date.equals("") && time.equals("")) {
            return new Date();
        } else if (!date.equals("") && time.equals("")) {
            dt = sdf.parse(date + "00:00");
        } else if (date.equals("")) {
            String[] timeArr = time.split(":");
            dt = new Date();
            dt.setHours(Integer.parseInt(timeArr[0]));  /*should use Calendar*/
            dt.setMinutes(Integer.parseInt(timeArr[1]));
        } else dt = sdf.parse(date + time);

        return dt;
    }

}
