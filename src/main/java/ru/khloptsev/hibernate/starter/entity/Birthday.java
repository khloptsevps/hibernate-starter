package ru.khloptsev.hibernate.starter.entity;

import java.time.LocalDate;
import java.time.Period;

public record Birthday(LocalDate birthDate) {

    public int getAge() {
        return Period.between(birthDate, LocalDate.now()).getYears();
    }
}
