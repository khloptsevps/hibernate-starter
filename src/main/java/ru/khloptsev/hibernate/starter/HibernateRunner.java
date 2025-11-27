package ru.khloptsev.hibernate.starter;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.khloptsev.hibernate.starter.entity.Birthday;
import ru.khloptsev.hibernate.starter.entity.Role;
import ru.khloptsev.hibernate.starter.entity.User;
import ru.khloptsev.hibernate.starter.utils.HibernateUtils;

import java.time.LocalDate;

public class HibernateRunner {
    private static final Logger log = LoggerFactory.getLogger(HibernateRunner.class);

    static void main() {
        log.info("Приложение запущено");
        User user = User.builder()
                .email("testuser@example.ex")
                .firstname("test")
                .lastname("user")
                .birthDate(new Birthday(LocalDate.of(1986, 1, 1)))
                .role(Role.ADMIN)
                .build();
        log.info("Создана сущность user статус transient: {}", user);
        try (SessionFactory sessionFactory = HibernateUtils.buildSessionFactory()) {

            try (Session session = sessionFactory.openSession()) {
                log.info("Открыта сессия: {}", session);
                session.beginTransaction();
                user.setFirstname("Oleg");
                log.warn("User firstname изменено {}", user);
                System.out.println(session.isDirty());
                session.find(User.class, "testuser@example.ex");
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
