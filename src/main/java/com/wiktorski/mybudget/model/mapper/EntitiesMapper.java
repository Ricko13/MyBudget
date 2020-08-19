package com.wiktorski.mybudget.model.mapper;

import com.wiktorski.mybudget.model.DTO.CategoryDTO;
import com.wiktorski.mybudget.model.DTO.PaymentDTO;
import com.wiktorski.mybudget.model.entity.Category;
import com.wiktorski.mybudget.model.entity.FuturePayment;
import com.wiktorski.mybudget.model.entity.Payment;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@DecoratedWith(EntitiesMapperDecorator.class)
@Mapper(componentModel = "Spring")
public interface EntitiesMapper {


    @Mapping(target = "category", ignore = true)
    @Mapping(target = "user", ignore = true)
    Payment toPaymentEntity(PaymentDTO dto);

    @Mapping(target = "category", ignore = true)
    @Mapping(target = "user", ignore = true)
    FuturePayment toFutureEntity(PaymentDTO dto);

    Category toCategory(CategoryDTO dto);


    /**
     * Można by dla dto @Mapping(source = "category.name", target = "categoryName"
     * ale w tym wypadku category może być nullem
     * */
    PaymentDTO toDTO(Payment payment);
    PaymentDTO toDTO(FuturePayment payment);
    CategoryDTO toDTO(Category category);



    @Mapping(target = "category", ignore = true)
    void updatePayment(PaymentDTO paymentDTO, @MappingTarget Payment toUpdate);

    @Mapping(target = "category", ignore = true)
    void updateFuturePayment(PaymentDTO paymentDTO, @MappingTarget FuturePayment toUpdate);

    void updateCategory(CategoryDTO categoryDTO, @MappingTarget Category category);
}

