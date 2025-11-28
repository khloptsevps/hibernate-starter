package ru.khloptsev.hibernate.starter.utils;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import ru.khloptsev.hibernate.starter.converter.BirthdayConverter;

public final class HibernateUtils {
    private HibernateUtils() {}

    public static SessionFactory buildSessionFactory() {
        Configuration configuration = new Configuration();
//        configuration.addAttributeConverter(BirthdayConverter.class, true);
        configuration.configure();

        return configuration.buildSessionFactory();
    }
}
