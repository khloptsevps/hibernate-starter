package ru.khloptsev.hibernate.starter;

import jakarta.persistence.Column;
import jakarta.persistence.Table;
import lombok.Cleanup;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.jupiter.api.*;

import ru.khloptsev.hibernate.starter.entity.user.*;
import ru.khloptsev.hibernate.starter.utils.HibernateUtils;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Собрать строку как будто это Hibernate")
class HibernateRunnerTest {

    @Test
    public void testHibernateApi() {
        Company company = Company.builder()
                .name("Mail")
                .build();
        User u1 = User.builder()
                .email("ivan1@gmail.com")
                .personalInfo(PersonalInfo.builder()
                        .firstname("Ivan")
                        .lastname("Ivanov")
                        .birthDate(new Birthday(LocalDate.of(1995, 5, 23)))
                        .build())
                .role(Role.USER)
                .build();

        String sqlTemplate = """
                insert
                into
                %s
                (%s)
                values
                (%s)
                """;

        String expected = """
                insert
                into
                hibernate.users
                (id, email, personalInfo, role, company)
                values
                (?, ?, ?, ?, ?)
                """;

        String table = Optional.ofNullable(u1.getClass().getAnnotation(Table.class))
                .map(t -> t.schema() + "." + t.name())
                .orElse(u1.getClass().getSimpleName());

        Field[] fields = u1.getClass().getDeclaredFields();

        String columnNames = Arrays.stream(fields)
                .map(field -> Optional.ofNullable(field.getAnnotation(Column.class))
                        .map(Column::name).orElse(field.getName()))
                .collect(Collectors.joining(", "));

        String columnValues = Arrays.stream(fields)
                .map(field -> "?")
                .collect(Collectors.joining(", "));

        String actual = sqlTemplate.formatted(table, columnNames, columnValues);

        assertEquals(expected, actual);
    }

    @Test
    public void testOneToMany() {
        @Cleanup
        Session session = HibernateUtils.getSessionFactory().openSession();
        Transaction tz = session.beginTransaction();
        var company = session.find(Company.class, 1);
        System.out.println(company.getUsers());
        tz.commit();
    }

    @Test
    public void checkAddUserAndCompany() {
        @Cleanup
        Session session = HibernateUtils.getSessionFactory().openSession();
        Transaction tz = session.beginTransaction();
        Company newCompany = Company.builder()
                .name("Yandex")
                .build();
        User newUser = User.builder()
                .email("ivan4@gmail.com")
                .role(Role.ADMIN)
                .build();
        newCompany.addUser(newUser);
        session.persist(newCompany);
        tz.commit();
    }

    @Test
    public void addUserInCompany() {
        @Cleanup
        Session session = HibernateUtils.getSessionFactory().openSession();
        Transaction tz = session.beginTransaction();
        Company company = session.find(Company.class, 7);
        User newUser = User.builder()
                .email("ivan5@gmail.com")
                .role(Role.ADMIN)
                .build();

        if (company != null) {
            company.addUser(newUser);
        }
        tz.commit();
    }

    @Test void checkOrphanRemoval() {
        @Cleanup
        Session session = HibernateUtils.getSessionFactory().openSession();
        Transaction tz = session.beginTransaction();
        Company company = session.find(Company.class, 7);
        company.getUsers().removeIf(user -> user.getId() == 3);
        tz.commit();
    }
}