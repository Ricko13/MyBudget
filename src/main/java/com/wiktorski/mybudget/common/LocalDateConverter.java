package com.wiktorski.mybudget.common;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.sql.Date;
import java.time.LocalDate;
import java.util.Optional;

/**
 * JPA 2.1 was released before Java 8 and therefore
 * doesn’t support the new Date and Time API. If you want to use the
 * new classes (in the right way), you need to define the conversion to
 * java.sql.Date and java.sql.Timestamp yourself.*/
@Converter(autoApply = true)
public class LocalDateConverter implements AttributeConverter<LocalDate, Date> {

    @Override
    public Date convertToDatabaseColumn(LocalDate localDate) {
        return Optional.ofNullable(localDate)
                .map(Date::valueOf)
                .orElse(null);
    }

    @Override
    public LocalDate convertToEntityAttribute(Date date) {
        return Optional.ofNullable(date)
                .map(Date::toLocalDate)
                .orElse(null);
    }
}