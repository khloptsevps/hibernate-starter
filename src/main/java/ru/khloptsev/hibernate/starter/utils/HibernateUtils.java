package ru.khloptsev.hibernate.starter.utils;

import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import ru.khloptsev.hibernate.starter.converter.BirthdayConverter;

public final class HibernateUtils {
    private HibernateUtils() {}
    private static SessionFactory instance;

    static {
        try {
            initSession();
        } catch (HibernateException e) {
            throw new RuntimeException("Не удалось инициализировать SessionFactory", e);
        }
    }

    private static void initSession() {
        Configuration configuration = new Configuration();
        configuration.addAttributeConverter(BirthdayConverter.class, true);
        configuration.configure();
        instance = configuration.buildSessionFactory();
    }

    public static SessionFactory getSessionFactory() {
        return instance;
    }
}
