package ru.khloptsev.hibernate.starter.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import ru.khloptsev.hibernate.starter.entity.Birthday;

import java.sql.Date;
import java.util.Optional;

@Converter(autoApply = true)
public class BirthdayConverter implements AttributeConverter<Birthday, Date> {

    @Override
    public Date convertToDatabaseColumn(Birthday birthDay) {
        return Optional.ofNullable(birthDay)
                .map(Birthday::birthDate)
                .map(Date::valueOf)
                .orElse(null);
    }

    @Override
    public Birthday convertToEntityAttribute(Date date) {
        return Optional.ofNullable(date)
                .map(Date::toLocalDate)
                .map(Birthday::new)
                .orElse(null);
    }
}
