package ru.khloptsev.hibernate.starter;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import ru.khloptsev.hibernate.starter.entity.Birthday;
import ru.khloptsev.hibernate.starter.entity.PersonalInfo;
import ru.khloptsev.hibernate.starter.entity.Role;
import ru.khloptsev.hibernate.starter.entity.User;
import ru.khloptsev.hibernate.starter.utils.HibernateUtils;

import java.time.LocalDate;

@Slf4j
public class HibernateRunner {
    static void main() {
        log.info("Приложение запущено");
        User user = User.builder()
                .email("oleg1@mail.ru")
                .personalInfo(PersonalInfo.builder()
                        .firstname("Oleg")
                        .lastname("Zhukov")
                        .birthDate(new Birthday(LocalDate.of(1995, 5, 23)))
                        .build())
                .role(Role.USER)
                .build();
        log.info("Создана сущность user статус transient: {}", user);
        try (SessionFactory sessionFactory = HibernateUtils.buildSessionFactory()) {

            try (Session session = sessionFactory.openSession()) {
                log.info("Открыта сессия: {}", session);
                session.beginTransaction();
//                log.warn("User firstname изменено {}", user);


//                session.persist(user);


                log.debug("User: {}, Session: {}", user, session);
                session.beginTransaction().commit();
            }
        } catch (Exception e) {
            log.error("Произошла ошибка: ", e);
            throw e;
        }
        log.info("Приложение завершило работу\n----------------");
    }
}
