package ru.khloptsev.hibernate.starter;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import ru.khloptsev.hibernate.starter.entity.user.Birthday;

import java.time.LocalDate;
import java.time.Period;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Вычисление возраста по заданным параметрам и текущей датой")
public class BirthdayTest {
    @ParameterizedTest
    @ValueSource(ints = {1990, 1983, 2000, 2010})
    public void getAge(int year) {
        LocalDate birthDayDate = LocalDate.of(year, 1, 1);;
        Birthday birthDay = new Birthday(birthDayDate);
        int actual = birthDay.getAge();
        int expected = Period.between(birthDayDate, LocalDate.now()).getYears();
        assertEquals(expected, actual);
    }
}
