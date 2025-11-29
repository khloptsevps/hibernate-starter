package ru.khloptsev.hibernate.starter;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.Transaction;
import ru.khloptsev.hibernate.starter.entity.user.*;
import ru.khloptsev.hibernate.starter.utils.HibernateUtils;

import java.time.LocalDate;

@Slf4j
public class HibernateRunner {
    static void main() {
        log.info("Приложение запущено");
        Company company = Company.builder()
                .name("Google")
                .build();
        User user = User.builder()
                .email("andrey@gmail.com")
                .personalInfo(PersonalInfo.builder()
                        .firstname("Andrey")
                        .lastname("Andreev")
                        .birthDate(new Birthday(LocalDate.of(1997, 12, 1)))
                        .build())
                .company(company)
                .role(Role.USER)
                .build();

            try (Session session = HibernateUtils.getSessionFactory().openSession()) {
                log.info("Открыта сессия: {}", session);
                Transaction tz = session.beginTransaction();
                tz.commit();

            } catch (Exception e) {
                log.error("Произошла ошибка: ", e);
                throw e;
            }
        log.info("Приложение завершило работу\n----------------");
    }
}
