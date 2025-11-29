package ru.khloptsev.hibernate.starter.entity.user;

import jakarta.persistence.*;
import lombok.*;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(exclude = "company")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "users", schema = "hibernate")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private int id;
    @Column(unique = true, name = "email", nullable = false)
    private String email;
    PersonalInfo personalInfo;
    @Enumerated(value = EnumType.STRING)
    private Role role;
    /*
        При таком раскладе cascade = CascadeType.ALL при удалении юзера из компании
        пойдет запрос на удаление компании из таблицы.
     */
    /*
        optional = false требует чтобы поле было не null.
        То есть связь обязательная.
     */
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id")
    private Company company;

    @PrePersist
    @PreUpdate
    private void calcAgeBeforeSave() {
        if (personalInfo != null) {
            personalInfo.calculateAgeBeforeSave();
        };
    }

    @PostLoad
    private void calcAgeAfterLoad() {
        if (personalInfo != null) {
            personalInfo.calculateAgeAfterLoad();
        }
    }
}
