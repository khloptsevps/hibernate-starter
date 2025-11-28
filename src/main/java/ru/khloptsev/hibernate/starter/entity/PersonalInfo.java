package ru.khloptsev.hibernate.starter.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.khloptsev.hibernate.starter.converter.BirthdayConverter;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Embeddable
public class PersonalInfo {
    private String firstname;
    private String lastname;
    @Column(name = "birth_date")
    @Convert(converter = BirthdayConverter.class)
    private Birthday birthDate;
    private int age;

    void calculateAgeBeforeSave() {
        if (birthDate == null) {
            age = 0;
        } else {
            age = birthDate.getAge();
        }
    }

    void calculateAgeAfterLoad() {
        if (birthDate == null) {
            age = 0;
        } else {
            age = birthDate.getAge();
        }
    }
}
