package ru.khloptsev.hibernate.starter;

import jakarta.persistence.Column;
import jakarta.persistence.Table;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import ru.khloptsev.hibernate.starter.entity.Birthday;
import ru.khloptsev.hibernate.starter.entity.User;

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
        User u1 = User.builder()
                .email("mail@example.ex")
                .firstname("Test")
                .lastname("User")
                .birthDate(new Birthday(LocalDate.of(2000, 1, 1)))
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
                (email, firstname, lastname, birth_date, age, role)
                values
                (?, ?, ?, ?, ?, ?)
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
}