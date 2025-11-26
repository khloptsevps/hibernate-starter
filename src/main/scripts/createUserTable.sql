drop table if exists users;

CREATE TABLE users
(
    email VARCHAR(128) PRIMARY KEY ,
    firstname VARCHAR(128),
    lastname VARCHAR(128),
    birth_date DATE,
    age INT,
    role VARCHAR(24)
);