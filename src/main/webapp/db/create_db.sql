CREATE DATABASE IF NOT EXISTS Admissions_committee;
USE Admissions_committee;
CREATE TABLE users
(
id      SERIAL      NOT NULL,
login   VARCHAR(50) NOT NULL,
email   VARCHAR(50) NOT NULL,
password VARCHAR(50) NOT NULL,
first_name   VARCHAR(50) NOT NULL,
second_name   VARCHAR(50) NOT NULL,
city   VARCHAR(50) NOT NULL,
region   VARCHAR(50) NOT NULL,
institution   VARCHAR(50) NOT NULL
);

CREATE TABLE faculties
(
id SERIAL NOT NULL,
name   VARCHAR(50) NOT NULL,
budget_places   INT NOT NULL,
all_places   INT NOT NULL
);

CREATE TABLE roles
(
id SERIAL PRIMARY KEY NOT NULL,
name   VARCHAR(50) NOT NULL,
description   VARCHAR(50)
);

CREATE TABLE subjects
(
id SERIAL NOT NULL,
name VARCHAR(50) NOT NULL
)