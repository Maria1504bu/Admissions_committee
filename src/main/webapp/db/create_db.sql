DROP DATABASE IF EXISTS Admissions_committee;

CREATE DATABASE IF NOT EXISTS Admissions_committee;
USE Admissions_committee;


CREATE TABLE roles
(
    id          INT AUTO_INCREMENT PRIMARY KEY NOT NULL,
    name        VARCHAR(50) UNIQUE             NOT NULL
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
    surname   VARCHAR(50)        NOT NULL,
    first_name  VARCHAR(50)        NOT NULL,
    father_name VARCHAR(50)        NOT NULL,
    city        VARCHAR(50)        NOT NULL,
    region      VARCHAR(50)        NOT NULL,
    institution VARCHAR(50)        NOT NULL,
    CONSTRAINT
        FOREIGN KEY (id)
            REFERENCES users (id)
            ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE exams
(
    id   INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
    name VARCHAR(50)     NOT NULL
);

CREATE TABLE faculties
(
    id            INT PRIMARY KEY NOT NULL,
    name          VARCHAR(50)     NOT NULL,
    budget_places INT             NOT NULL,
    all_places    INT             NOT NULL
);

CREATE TABLE candidates_exams
(
    candidate_id INT NOT NULL,
    exam_id INT NOT NULL,
    mark INT NOT NULL,
    INDEX fkExamIdIndex (candidate_id ASC) VISIBLE,
    INDEX fkFacultyIdIndex (exam_id ASC) VISIBLE,
    CONSTRAINT
        FOREIGN KEY (candidate_id)
            REFERENCES candidates (id)
            ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT
        FOREIGN KEY (exam_id)
            REFERENCES exams (id)
            ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT PRIMARY KEY PK_Candidates_Exams (candidate_id, exam_id)
);

CREATE TABLE faculties_exams
(
    faculty_id INT NOT NULL,
    exam_id INT NOT NULL,
    INDEX fkFacultyIdIndex (faculty_id ASC) VISIBLE,
    INDEX fkExamIdIndex (exam_id ASC) VISIBLE,
    CONSTRAINT
        FOREIGN KEY (faculty_id)
            REFERENCES faculties (id)
            ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT
        FOREIGN KEY (exam_id)
            REFERENCES exams (id)
            ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT PRIMARY KEY PK_Faculties_Exams (faculty_id, exam_id)
);