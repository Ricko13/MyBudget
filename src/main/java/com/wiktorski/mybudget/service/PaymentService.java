package com.wiktorski.mybudget.service;

import com.wiktorski.mybudget.model.entity.Payment;
import com.wiktorski.mybudget.model.entity.PaymentDTO;
import com.wiktorski.mybudget.repository.CategoryRepository;
import com.wiktorski.mybudget.repository.PaymentRepository;
import com.wiktorski.mybudget.service.security.SecurityService;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepo;
    private final SecurityService securityService;
    private final CategoryRepository categoryRepo;
    private final UserService userService;

    public boolean addPayment(String name, float price, int idCat, @Nullable Date date, String description) {
            //TODO trycatch i w catchu false
            userService.decreaseBudget(price);
            //if (date == null) { date = new Date(); }  //TODO dlaczego data sama się tworzy? coś z @Nullable?
            Payment payment = new Payment(name, price, securityService.getLoggedInUser(), date);
            if (!description.equals(""))
                payment.setDescription(description);
            if (idCat != -1)
                categoryRepo.findById(idCat).ifPresent(payment::setCategory);
            paymentRepo.save(payment);
            return true;
    }

    public boolean addPayment(String name, float price, int idCat, String date, String time, String description) throws ParseException {
        return addPayment(name, price, idCat, parseStringDate(date, time), description);
    }

    public boolean addPayment(PaymentDTO paymentDTO) {
        try {
            return addPayment(paymentDTO.getName(), paymentDTO.getPrice(), paymentDTO.getCategoryId(),
                   parseStringDate(paymentDTO.getDate(),""),paymentDTO.getDescription());
        } catch (ParseException e) {
            return false;
        }
    }

    /**    form always sends data but data can be empty like "" */
    private Date parseStringDate(String requestDate, String time) throws ParseException {
        Date date;
        SimpleDateFormat simpleDate = new SimpleDateFormat("yyyy-MM-ddHH:mm");

        if (requestDate.equals("") && time.equals("")) {
            return new Date();
        } else if (!requestDate.equals("") && time.equals("")) {
            date = simpleDate.parse(requestDate + "00:00");
        } else if (requestDate.equals("")) {
            String[] timeArr = time.split(":");
            date = new Date();
            date.setHours(Integer.parseInt(timeArr[0]));  /*should use Calendar*/
            date.setMinutes(Integer.parseInt(timeArr[1]));
        } else date = simpleDate.parse(requestDate + time);
        return date;
    }

    public void deleteCategory(int categoryId) {
        paymentRepo.findByCategoryId(categoryId).forEach(payment -> payment.setCategory(null));
        categoryRepo.deleteById(categoryId);
    }

    public boolean updatePayment(PaymentDTO paymentDTO) {
        Payment payment = paymentRepo.findById(paymentDTO.getId()).orElse(null);
        if (payment == null) return false;
        try {
            payment.setCategory(categoryRepo.findById(paymentDTO.getCategoryId()).orElse(null));
            payment.setDescription(paymentDTO.getDescription());
            payment.setDate(parseStringDate(paymentDTO.getDate(), ""));
            payment.setName(paymentDTO.getName());
            payment.setPrice(paymentDTO.getPrice());
            paymentRepo.save(payment);
        } catch (Exception e) {
            System.out.println("test");
            //TODO exception logging
            return false;
        }
        return true;
    }

    public Payment paymentDTOtoPayment(PaymentDTO paymentDTO) throws ParseException {
        Payment payment = Payment.builder()
                .id(paymentDTO.getId())
                .name(paymentDTO.getName())
                .price(paymentDTO.getPrice())
                .description(paymentDTO.getDescription())
                .category(categoryRepo.findById(paymentDTO.getCategoryId()).orElse(null))
                .build();
        if (!paymentDTO.getDate().equals(""))
            payment.setDate(new SimpleDateFormat("dd-MM-yyyy").parse(paymentDTO.getDate()));

        return payment;
    }

    public PaymentDTO paymentToPaymentDTO(Payment payment) {
        PaymentDTO dto = PaymentDTO.builder()
                .id(payment.getId())
                .date(payment.getJustDate())
                .description(payment.getDescription())
                .name(payment.getName())
                .price(payment.getPrice())
                .build();
        if (payment.getCategory() != null) {
            dto.setCategoryId(payment.getCategory().getId());
            dto.setCategoryName(payment.getCategory().getName());
        }
        return dto;
    }
}


