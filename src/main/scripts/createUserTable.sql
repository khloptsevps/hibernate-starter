drop table if exists users;
drop table if exists company;

CREATE TABLE users
(
    id SERIAL PRIMARY KEY,
    email VARCHAR(128) UNIQUE NOT NULL,
    firstname VARCHAR(128),
    lastname VARCHAR(128),
    birth_date DATE,
    age INT,
    role VARCHAR(24)
);

CREATE TABLE company
(
    id SERIAL PRIMARY KEY,
    name VARCHAR(128) UNIQUE NOT NULL
);