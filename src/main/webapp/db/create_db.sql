DROP DATABASE IF EXISTS Admissions_committee;

CREATE DATABASE IF NOT EXISTS Admissions_committee;
USE Admissions_committee;


CREATE TABLE roles
(
    id          INT AUTO_INCREMENT PRIMARY KEY NOT NULL,
    name        VARCHAR(50)                    NOT NULL,
    description VARCHAR(50)
);

CREATE TABLE users
(
    id          INTEGER PRIMARY KEY  AUTO_INCREMENT NOT NULL,
    email       VARCHAR(50)        NOT NULL,
    password    VARCHAR(50)        NOT NULL,
    role_id     INT                NOT NULL,
    CONSTRAINT fkUserRole
        FOREIGN KEY (role_id)
            REFERENCES roles (id)
            ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE candidates
(
    id INTEGER PRIMARY KEY,
    first_name  VARCHAR(50)        NOT NULL,
    last_name   VARCHAR(50)        NOT NULL,
    father_name VARCHAR(50)        NOT NULL,
    city        VARCHAR(50)        NOT NULL,
    region      VARCHAR(50)        NOT NULL,
    institution VARCHAR(50)        NOT NULL,
    CONSTRAINT
        FOREIGN KEY (id)
            REFERENCES users (id)
            ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE subjects
(
    id   INT PRIMARY KEY NOT NULL,
    name VARCHAR(50)     NOT NULL,
    mark INT             NOT NULL
);

CREATE TABLE faculties
(
    id            INT PRIMARY KEY NOT NULL,
    name          VARCHAR(50)     NOT NULL,
    budget_places INT             NOT NULL,
    all_places    INT             NOT NULL
);

CREATE TABLE subjects_for_faculties
(
    id         INT AUTO_INCREMENT PRIMARY KEY,
    subject_id INT NOT NULL,
    faculty_id INT NOT NULL,
    INDEX fkSubjectIdIndex (subject_id ASC) VISIBLE,
    INDEX fkFacultyIdIndex (faculty_id ASC) VISIBLE,
    CONSTRAINT
        FOREIGN KEY (subject_id)
            REFERENCES subjects (id)
            ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT
        FOREIGN KEY (faculty_id)
            REFERENCES faculties (id)
            ON DELETE CASCADE ON UPDATE CASCADE
);

INSERT INTO roles (name, description) VALUE ('user', 'description');
INSERT INTO users (email, password, role_id) VALUE ('qqq', 'qqq', 1)
