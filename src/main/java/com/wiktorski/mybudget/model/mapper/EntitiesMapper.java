package com.wiktorski.mybudget.model.mapper;

import com.wiktorski.mybudget.model.DTO.CategoryDTO;
import com.wiktorski.mybudget.model.DTO.PaymentDTO;
import com.wiktorski.mybudget.model.DTO.StandingInstructionDTO;
import com.wiktorski.mybudget.model.entity.Category;
import com.wiktorski.mybudget.model.entity.FuturePayment;
import com.wiktorski.mybudget.model.entity.Payment;
import com.wiktorski.mybudget.model.entity.StandingInstructionEntity;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;

@DecoratedWith(EntitiesMapperDecorator.class)
@Mapper(componentModel = "Spring")
public interface EntitiesMapper {

    /**
     * TO ENTITY
     */

    @Mapping(target = "category", ignore = true)
    @Mapping(target = "user", ignore = true)
    Payment toPaymentEntity(PaymentDTO dto);

    @Mapping(target = "category", ignore = true)
    @Mapping(target = "user", ignore = true)
    FuturePayment toFutureEntity(PaymentDTO dto);

    Category toCategory(CategoryDTO dto);

    @Mapping(target = "category", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "latestExecution", ignore = true)
    StandingInstructionEntity toStandingInstruction(StandingInstructionDTO dto);

    @Mapping(target = "id", ignore = true)
    Payment standingToPayment(StandingInstructionEntity standingInstructionEntity);

    /**
     * TO DTO
     */

    @Mappings({
            @Mapping(source = "category.id", target = "categoryId"),
            @Mapping(source = "category.name", target = "categoryName"),
            @Mapping(source = "category.color", target = "categoryColor")
    })
    PaymentDTO toDTO(Payment payment);

    @Mappings({
            @Mapping(source = "category.id", target = "categoryId"),
            @Mapping(source = "category.name", target = "categoryName"),
            @Mapping(source = "category.color", target = "categoryColor")
    })
    PaymentDTO toDTO(FuturePayment payment);

    CategoryDTO toDTO(Category category);

    @Mapping(source = "category.id", target = "categoryId")
    @Mapping(source = "category.name", target = "categoryName")
    @Mapping(source = "category.color", target = "categoryColor")
    StandingInstructionDTO toDTO(StandingInstructionEntity standingInstruction);


    /**
     * UPDATE
     */

    @Mapping(target = "category", ignore = true)
    void updatePayment(PaymentDTO paymentDTO, @MappingTarget Payment toUpdate);

    @Mapping(target = "category", ignore = true)
    void updateFuturePayment(PaymentDTO paymentDTO, @MappingTarget FuturePayment toUpdate);

    void updateCategory(CategoryDTO categoryDTO, @MappingTarget Category category);

    @Mapping(target = "category", ignore = true)
    void updateStandingInstruction(StandingInstructionDTO source, @MappingTarget StandingInstructionEntity toUpdate);

}

