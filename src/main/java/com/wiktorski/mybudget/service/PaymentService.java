package com.wiktorski.mybudget.service;

import com.wiktorski.mybudget.model.DTO.CategoryDTO;
import com.wiktorski.mybudget.model.DTO.PaymentDTO;
import com.wiktorski.mybudget.model.entity.Category;
import com.wiktorski.mybudget.model.entity.FuturePayment;
import com.wiktorski.mybudget.model.entity.Payment;
import com.wiktorski.mybudget.repository.CategoryRepository;
import com.wiktorski.mybudget.repository.FuturePaymentRepository;
import com.wiktorski.mybudget.repository.PaymentRepository;
import com.wiktorski.mybudget.service.security.SecurityService;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepo;
    private final SecurityService securityService;
    private final CategoryRepository categoryRepo;
    private final UserService userService;
    private final FuturePaymentRepository futurePaymentRepo;

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
                    parseStringDate(paymentDTO.getDate(), ""), paymentDTO.getDescription());
        } catch (ParseException e) {
            return false;
        }
    }

    /**
     * form always sends data but data can be empty like ""
     */
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

    public boolean deleteById(int paymentId) {
        try {
            paymentRepo.deleteById(paymentId);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean deleteCategory(int categoryId) {
        try {
            paymentRepo.findByCategoryId(categoryId).forEach(payment -> payment.setCategory(null));
            categoryRepo.deleteById(categoryId);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean updateCategory(CategoryDTO categoryDTO) {
        Category cat = categoryRepo.findById(categoryDTO.getId()).orElse(null);
        if (cat != null) {
            try {
                cat.setName(categoryDTO.getName());
                categoryRepo.save(cat);
                return true;
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        } else
            return false;
    }


    public List<Category> getUserCategories() {
        return securityService.getLoggedInUser().getCategories();
    }

    private List<Payment> sortPayments(List<Payment> payments) {
        Comparator<Payment> comp = (o1, o2) -> {
            if (o1.getDate().before(o2.getDate())) {
                return 1;
            } else if (o1.getDate().after(o2.getDate())) {
                return -1;
            } else {
                return 0;
            }
        };
        payments.sort(comp);
        return payments;
    }

    public List<Payment> getUserPaymentsDesc() {
        List<Payment> payments = securityService.getLoggedInUser().getPayments();
        for (Payment p : payments) {
            Date date = p.getDate();
            p.setJustDate(new SimpleDateFormat("dd-MM-yyyy").format(date));
            p.setJustTime(new SimpleDateFormat("HH:mm").format(date));
        }
        return sortPayments(payments);  //TODO get from db sorted or leave it for comparator usage example?
    }

    public List<PaymentDTO> getFuturePayments() {
        List<FuturePayment> fut = securityService.getLoggedInUser().getFuturePayments();
        for (FuturePayment f : fut) {
            if (f.getDate() != null)
                f.setJustDate(new SimpleDateFormat("dd-MM-yyyyy").format(f.getDate()));
        }
        return fut.stream().map(PaymentDTO::of).collect(Collectors.toList());
    }

    public boolean addFuturePayment(PaymentDTO paymentDTO) {

        try {
            futurePaymentRepo.save(new FuturePayment(paymentDTO.getName(), paymentDTO.getPrice(), securityService.getLoggedInUser(), parseStringDate(paymentDTO.getDate(), "")));
            return true;
        } catch (ParseException e) {
            return false;
        }
    }

}


