package com.wiktorski.mybudget.service;

import com.wiktorski.mybudget.model.Category;
import com.wiktorski.mybudget.model.Payment;
import com.wiktorski.mybudget.repository.CategoryRepository;
import com.wiktorski.mybudget.repository.PaymentRepository;
import com.wiktorski.mybudget.service.security.SecurityService;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepo;
    private final SecurityService securityService;
    private final CategoryRepository categoryRepo;
    private final UserService userService;

    public void addPayment(String name, float price, String idCat, @Nullable Date date, String description) {
        int idCategory = Integer.parseInt(idCat);
        if (date == null) {
            date = new Date();
        }

        userService.addPaymentToBudget(price);

        Payment payment = new Payment(name, price, securityService.getLoggedInUser(), date);


        if (!description.equals("")) {
            payment.setDescription(description);
        }

        if (idCategory != -1) {
            Optional cat = categoryRepo.findById(idCategory);
            cat.ifPresent(
                    catg -> {
                        payment.setCategory((Category) catg);
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

    public void deleteCategory(int categoryId) {
        final List<Payment> payments = paymentRepo.findByCategoryId(categoryId);
        payments.forEach(payment -> {
            payment.setCategory(null);
        });
        categoryRepo.deleteById(categoryId);
    }
}
