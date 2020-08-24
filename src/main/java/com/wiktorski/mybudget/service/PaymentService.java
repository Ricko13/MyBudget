package com.wiktorski.mybudget.service;

import com.querydsl.core.BooleanBuilder;
import com.wiktorski.mybudget.common.Validator;
import com.wiktorski.mybudget.common.exception.ExceptionType;
import com.wiktorski.mybudget.common.exception.MBException;
import com.wiktorski.mybudget.model.DTO.CategoryDTO;
import com.wiktorski.mybudget.model.DTO.ChangeCategoryColorRequest;
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
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepo;
    private final SecurityService securityService;
    private final CategoryRepository categoryRepo;
    private final UserService userService;
    private final FuturePaymentRepository futurePaymentRepo;
    private final EntitiesMapper mapper;
    private final Validator validator;

    @Transactional
    public void addPayment(PaymentDTO dto) {
        //TODO Validator.validate
        paymentRepo.save(mapper.toPaymentEntity(dto));
        userService.decreaseBudget(dto.getPrice());
    }

    @Transactional
    public void updatePayment(PaymentDTO paymentDTO) {
        //TODO Validator.validatePaymentDTO(paymentDTO);
        Payment payment = paymentRepo.findById(paymentDTO.getId())
                .orElseThrow(() -> new MBException(ExceptionType.ENTITY_NOT_FOUND));
        userService.addToBudget(payment.getPrice());
        userService.decreaseBudget(paymentDTO.getPrice());
        mapper.updatePayment(paymentDTO, payment);
    }

    @Transactional
    public void deletePaymentById(int paymentId) {
        paymentRepo.findById(paymentId).ifPresent(payment -> userService.addToBudget(payment.getPrice()));
        paymentRepo.deleteById(paymentId);
    }

    public List<Payment> getUserPaymentsDesc() {
        return paymentRepo.findAllByUserOrderByDateDesc(securityService.getLoggedInUser());
    }


    public List<PaymentDTO> getUserPaymentsDtoDesc() {
        return paymentRepo.findAllByUserOrderByDateDesc(securityService.getLoggedInUser())
                .stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }

    public List<Payment> getPayments(ReportDTO dto) {
        QPayment qPayment = QPayment.payment;
        BooleanBuilder where = new BooleanBuilder();
        where.and(qPayment.user.id.eq(getUser().getId()))
                .and(qPayment.date.between(dto.getStartDate(), dto.getEndDate()));
        return paymentRepo.findAll(where, new Sort(Sort.Direction.DESC, "price"));
    }

    public void deleteCategory(int categoryId) {
        paymentRepo.findByCategoryId(categoryId).forEach(payment -> payment.setCategory(null));
        categoryRepo.deleteById(categoryId);
    }

    public void updateCategory(CategoryDTO categoryDTO) {
        Category cat = categoryRepo.findById(categoryDTO.getId())
                .orElseThrow(() -> new MBException(ExceptionType.ENTITY_NOT_FOUND));
        cat.setName(categoryDTO.getName());
        categoryRepo.save(cat);
    }

    public List<Category> getUserCategories() {
        return getUser().getCategories();
    }

    public List<PaymentDTO> getFuturePaymentsDesc() {
        return futurePaymentRepo.findAllByUserOrderByDateDesc(getUser()).stream().map(mapper::toDTO).collect(Collectors.toList());
    }

    public void addFuturePayment(PaymentDTO dto) {
//        FuturePayment fut = mapper.toFutureEntity(dto);

        Category cat = categoryRepo.findById(dto.getCategoryId()).orElse(null);
        FuturePayment fut = new FuturePayment(
                securityService.getLoggedInUser(),
                dto.getName(),
                dto.getPrice(),
                dto.getDate(),
                dto.getDescription(),
                cat
        );
        futurePaymentRepo.save(fut);
    }

    public void deleteFuturePaymentById(int id) {
        futurePaymentRepo.deleteById(id);
    }

    public void updateFuturePayment(PaymentDTO paymentDTO) {
        FuturePayment fut = futurePaymentRepo.findById(paymentDTO.getId())
                .orElseThrow(() -> new MBException(ExceptionType.ENTITY_NOT_FOUND));
        mapper.updateFuturePayment(paymentDTO, fut);


       /* if (fut != null) {
            fut.setDate(paymentDTO.getDate());
            fut.setCategory(categoryRepo.findById(paymentDTO.getCategoryId()).orElse(null));
            fut.setDescription(paymentDTO.getDescription());
            fut.setName(paymentDTO.getName());
            fut.setPrice(paymentDTO.getPrice());
        }
        futurePaymentRepo.save(fut);*/
    }

    public void moveFuturePayment(PaymentDTO paymentDTO) {
        addPayment(paymentDTO);
        deleteFuturePaymentById(paymentDTO.getId());
    }


    private User getUser() {
        return securityService.getLoggedInUser();
    }

    public void updateCategoryColor(ChangeCategoryColorRequest request) {
        Category cat = categoryRepo.findById(request.getId())
                .orElseThrow(() -> new MBException(ExceptionType.ENTITY_NOT_FOUND));
        cat.setColor(request.getColor());
        categoryRepo.save(cat);
    }
}


