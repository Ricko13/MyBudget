package com.wiktorski.mybudget.service;

import com.querydsl.core.BooleanBuilder;
import com.wiktorski.mybudget.common.Validator;
import com.wiktorski.mybudget.common.exception.ExceptionType;
import com.wiktorski.mybudget.common.exception.MBException;
import com.wiktorski.mybudget.model.DTO.CategoryDTO;
import com.wiktorski.mybudget.model.DTO.PaymentDTO;
import com.wiktorski.mybudget.model.DTO.ReportDTO;
import com.wiktorski.mybudget.model.entity.Category;
import com.wiktorski.mybudget.model.entity.FuturePayment;
import com.wiktorski.mybudget.model.entity.Payment;
import com.wiktorski.mybudget.model.entity.QPayment;
import com.wiktorski.mybudget.model.entity.User;
import com.wiktorski.mybudget.model.mapper.EntitiesMapper;
import com.wiktorski.mybudget.repository.CategoryRepository;
import com.wiktorski.mybudget.repository.FuturePaymentRepository;
import com.wiktorski.mybudget.repository.PaymentRepository;
import com.wiktorski.mybudget.service.security.SecurityService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PaymentServiceOLD {

    private final PaymentRepository paymentRepo;
    private final SecurityService securityService;
    private final CategoryRepository categoryRepo;
    private final UserService userService;
    private final FuturePaymentRepository futurePaymentRepo;
    private final EntitiesMapper mapper;
    private final Validator validator;

    /**
     * old add payment
     */
 /*   public boolean addPayment(String name, float price, int idCat, @Nullable Date date, String description) {
        //TODO trycatch i w catchu false
        userService.decreaseBudget(price);
        //if (date == null) { date = new Date(); }  //TODO dlaczego data sama się tworzy? coś z @Nullable?
        Payment payment = new Payment(name, price, securityService.getLoggedInUser(), date);
//        payment.setLocalDate(LocalDate.now());
        if (!description.equals(""))
            payment.setDescription(description);
        if (idCat != -1)
            categoryRepo.findById(idCat).ifPresent(payment::setCategory);
        paymentRepo.save(payment);
        return true;
    }

    public boolean addPayment(String name, float price, int idCat, String date, String time, String description) throws ParseException {
        return addPayment(name, price, idCat, parseStringDate(date, time), description);
    }*/
    @Transactional
    public void addPayment(PaymentDTO dto) {
/*        LocalDate date = Optional.ofNullable(dto.getDate()).orElse(LocalDate.now());
        Payment payment = new Payment(dto.getName(), dto.getPrice(), getUser(), date);
        payment.setDescription(dto.getDescription()); //TODO to constructor
        if (dto.getCategoryId() != -1)
            payment.setCategory(categoryRepo.findById(dto.getCategoryId()).orElseThrow(() -> new MBException(ExceptionType.ENTITY_NOT_FOUND)));
        */
        //TODO Validator.validate
        paymentRepo.save(mapper.toPaymentEntity(dto));
        userService.decreaseBudget(dto.getPrice());
    }

    @Transactional
    public void updatePayment(PaymentDTO paymentDTO) {
        //TODO Validator.validatePaymentDTO(paymentDTO);
        Payment payment = paymentRepo.findById(paymentDTO.getId()).orElseThrow(() -> new MBException(ExceptionType.ENTITY_NOT_FOUND));
        /*payment.setCategory(categoryRepo.findById(paymentDTO.getCategoryId()).orElse(null));
        payment.setDescription(paymentDTO.getDescription());
        payment.setDate(paymentDTO.getDate());
        payment.setName(paymentDTO.getName());
        payment.setPrice(paymentDTO.getPrice());
        paymentRepo.save(payment);*/


        mapper.updatePayment(paymentDTO, payment);
    }

    public void deletePaymentById(int paymentId) {
        //TODO check if exists and constraints and throw exception
        paymentRepo.deleteById(paymentId);

    }

    public List<Payment> getUserPaymentsDesc() {
        return paymentRepo.findAllByUserOrderByDateDesc(securityService.getLoggedInUser());
       /* QPayment qPayment = QPayment.payment;
        BooleanBuilder expr = new BooleanBuilder();
        expr.and(qPayment.user.id.eq(securityService.getLoggedInUser().getId())).and()*/

       /* List<Payment> payments = securityService.getLoggedInUser().getPayments();
        return sortPayments(payments);  //TODO get from db sorted or leave it for comparator usage example?*/
    }

    public List<Payment> getPayments(ReportDTO dto){
        QPayment qPayment =QPayment.payment;
        BooleanBuilder where = new BooleanBuilder();
        where.and(qPayment.user.id.eq(getUser().getId()))
            .and(qPayment.date.between(dto.getStartDate(), dto.getEndDate()));
        return paymentRepo.findAll(where);
    }

    public void deleteCategory(int categoryId) {
        paymentRepo.findByCategoryId(categoryId).forEach(payment -> payment.setCategory(null));
        categoryRepo.deleteById(categoryId);
    }

    public void updateCategory(CategoryDTO categoryDTO) {
        Category cat = categoryRepo.findById(categoryDTO.getId()).orElseThrow(() -> new MBException(ExceptionType.ENTITY_NOT_FOUND));
        cat.setName(categoryDTO.getName());
        categoryRepo.save(cat);
    }

    public List<Category> getUserCategories() {
        return getUser().getCategories();
    }

    public List<PaymentDTO> getFuturePaymentsDesc() {
       /* List<FuturePayment> payments = securityService.getLoggedInUser().getFuturePayments();
        return sortFuturePayments(payments).stream().map(PaymentDTO::of).collect(Collectors.toList());*/
        return futurePaymentRepo.findAllByUserOrderByDateDesc(getUser()).stream().map(mapper::toDTO).collect(Collectors.toList());
    }

    public boolean addFuturePayment(PaymentDTO dto) {
        try {
            Category cat = categoryRepo.findById(dto.getCategoryId()).orElse(null);
            futurePaymentRepo.save(new FuturePayment(securityService.getLoggedInUser(), dto.getName(), dto.getPrice()
                    , dto.getDate(), dto.getDescription(), cat));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public void deleteFuturePaymentById(int id) {
        futurePaymentRepo.deleteById(id);
    }

    public void updateFuturePayment(PaymentDTO paymentDTO) {
        FuturePayment fut = futurePaymentRepo.findById(paymentDTO.getId()).orElseThrow(() -> new MBException(ExceptionType.ENTITY_NOT_FOUND));
        if (fut != null) {
            fut.setDate(paymentDTO.getDate());
            fut.setCategory(categoryRepo.findById(paymentDTO.getCategoryId()).orElse(null));
            fut.setDescription(paymentDTO.getDescription());
            fut.setName(paymentDTO.getName());
            fut.setPrice(paymentDTO.getPrice());
        }
        futurePaymentRepo.save(fut);
    }

    public void moveFuturePayment(PaymentDTO paymentDTO) {
        addPayment(paymentDTO);
        deleteFuturePaymentById(paymentDTO.getId());
    }

   /* public Payment paymentDTOtoPayment(PaymentDTO paymentDTO) throws ParseException {
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
    }*/

    /*public PaymentDTO paymentToPaymentDTO(Payment payment) {
        PaymentDTO dto = PaymentDTO.builder()
                .id(payment.getId())
//                .localDate(payment.getJustDate().equals("") ? payment.getDate().toString() : payment.getJustDate())
                .date(payment.getDate())
                .description(payment.getDescription())
                .name(payment.getName())
                .price(payment.getPrice())
                .build();
        if (payment.getCategory() != null) {
            dto.setCategoryId(payment.getCategory().getId());
            dto.setCategoryName(payment.getCategory().getName());
        }
        return dto;
    }*/

    /**
     * form always sends data but data can be empty like ""
     */
     /* private Date parseStringDate(String requestDate, String time) throws ParseException {
      Date date;
        SimpleDateFormat simpleDate = new SimpleDateFormat("yyyy-MM-ddHH:mm");

        if (requestDate.equals("") && time.equals("")) {
            return new Date();
        } else if (!requestDate.equals("") && time.equals("")) {
            date = simpleDate.parse(requestDate + "00:00");
        } else if (requestDate.equals("")) {
            String[] timeArr = time.split(":");
            date = new Date();
            date.setHours(Integer.parseInt(timeArr[0]));  *//*should use Calendar*//*
            date.setMinutes(Integer.parseInt(timeArr[1]));
        } else date = simpleDate.parse(requestDate + time);
        return date;
    }*/

    //TODO 2 same comparators, merge it, or let futurePayment extends from payment
    private List<Payment> sortPayments(List<Payment> payments) {
        /*Comparator<Payment> comp = (o1, o2) -> {
            if (o1.getDate().before(o2.getDate())) {
                return 1;
            } else if (o1.getDate().after(o2.getDate())) {
                return -1;
            } else {
                return 0;
            }
        };
        payments.sort(comp);*/
        return payments;
    }

    //TODO przykład ułatwienia z query dsl/hibernate
    private List<FuturePayment> sortFuturePayments(List<FuturePayment> payments) {
        /*Comparator<FuturePayment> comp = (o1, o2) -> {
            if (o1.getDate().before(o2.getDate())) {
                return 1;
            } else if (o1.getDate().after(o2.getDate())) {
                return -1;
            } else {
                return 0;
            }
        };
        payments.sort(comp);*/
        return payments;
    }

    private User getUser() {
        return securityService.getLoggedInUser();
    }
}


