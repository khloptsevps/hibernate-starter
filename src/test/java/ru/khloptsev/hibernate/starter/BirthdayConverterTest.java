package ru.khloptsev.hibernate.starter;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import ru.khloptsev.hibernate.starter.converter.BirthdayConverter;
import ru.khloptsev.hibernate.starter.entity.user.Birthday;

import java.sql.Date;
import java.time.LocalDate;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Тест конвертора")
public class BirthdayConverterTest {

    static BirthdayConverter converter;

    @BeforeAll
    static void initConverter() {
        converter = new BirthdayConverter();
    }

    static Stream<LocalDate> initBirthdayDays() {
        return Stream.of(
                LocalDate.of(1990, 1,1),
                LocalDate.of(1995, 1,1),
                LocalDate.of(2000, 1,1),
                LocalDate.of(2007, 1,1)
        );
    }

    @ParameterizedTest
    @MethodSource("initBirthdayDays")
    @DisplayName("Конвертация типа Birthday в БД")
    public void convertToDatabaseColumn(LocalDate birthdayDay) {
        Birthday birthday = new Birthday(birthdayDay);
        Date actual = converter.convertToDatabaseColumn(birthday);
        Date expected = Date.valueOf(birthdayDay);

        assertEquals(expected, actual);
    }

    @ParameterizedTest
    @MethodSource("initBirthdayDays")
    @DisplayName("Конвертация типа Birthday из БД")
    public void convertToEntityAttribute(LocalDate birthdayDay) {
        Date birthdayDayFromDb = Date.valueOf(birthdayDay);
        Birthday actual = converter.convertToEntityAttribute(birthdayDayFromDb);
        Birthday expected = new Birthday(birthdayDay);

        assertEquals(expected, actual);
    }
}
