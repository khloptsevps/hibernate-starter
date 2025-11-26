package ru.khloptsev.hibernate.starter;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.model.naming.PhysicalNamingStrategySnakeCaseImpl;
import org.hibernate.cfg.Configuration;
import ru.khloptsev.hibernate.starter.converter.BirthdayConverter;
import ru.khloptsev.hibernate.starter.entity.Birthday;
import ru.khloptsev.hibernate.starter.entity.Role;
import ru.khloptsev.hibernate.starter.entity.User;

import java.time.LocalDate;

public class HibernateRunner {
    static void main() {
        Configuration configuration = new Configuration();
        configuration.configure();

        try (SessionFactory sessionFactory = configuration.buildSessionFactory();
             Session session = sessionFactory.openSession()) {
            User user = User.builder()
                    .email("testuser@example.ex")
                    .firstname("test")
                    .lastname("user")
                    .birthDate(new Birthday(LocalDate.of(1986, 1, 1)))
                    .role(Role.ADMIN)
                    .build();

            session.beginTransaction();

            session.persist(user);

            session.beginTransaction().commit();

        }
    }
}
