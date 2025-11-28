package ru.khloptsev.hibernate.starter.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "users", schema = "hibernate")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String email;
    PersonalInfo personalInfo;
    @Enumerated(value = EnumType.STRING)
    private Role role;

    @PrePersist
    @PreUpdate
    private void calcAgeBeforeSave() {
        personalInfo.calculateAgeBeforeSave();
    }

    @PostLoad
    private void calcAgeAfterLoad() {
        personalInfo.calculateAgeAfterLoad();
    }
}
