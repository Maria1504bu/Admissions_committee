DROP DATABASE IF EXISTS Admissions_committee;

CREATE DATABASE IF NOT EXISTS Admissions_committee;
USE Admissions_committee;


CREATE TABLE roles
(
    id   INT AUTO_INCREMENT PRIMARY KEY          NOT NULL,
    name VARCHAR(50) CHARACTER SET 'utf8' UNIQUE NOT NULL
);

CREATE TABLE logins
(
    `id`            INT                               NOT NULL AUTO_INCREMENT,
    `email`         VARCHAR(45) CHARACTER SET 'utf8'  NOT NULL,
    `password`      VARCHAR(200) CHARACTER SET 'utf8' NOT NULL,
    `roles_id` INT                               NOT NULL,
    PRIMARY KEY (`id`),
    UNIQUE INDEX `email_UNIQUE` (`email` ASC) VISIBLE,
    INDEX `fk_logins_roles_idx` (`roles_id` ASC) VISIBLE,
    CONSTRAINT `fk_logins_roles`
        FOREIGN KEY (`roles_id`)
            REFERENCES roles (`id`)
            ON DELETE CASCADE
            ON UPDATE CASCADE
);

CREATE TABLE cities
(
    `id`   INT                              NOT NULL AUTO_INCREMENT,
    `name` VARCHAR(45) CHARACTER SET 'utf8' NOT NULL,
    PRIMARY KEY (`id`)
);

CREATE TABLE candidates
(
    `login_id` INT                               NOT NULL,
    `first_name`     VARCHAR(50) CHARACTER SET 'utf8'  NOT NULL,
    `father_name`    VARCHAR(50) CHARACTER SET 'utf8'  NOT NULL,
    `second_name`    VARCHAR(50) CHARACTER SET 'utf8'  NOT NULL,
    `certificate_url` VARCHAR(80) CHARACTER SET 'utf8' NOT NULL,
    `city_id`        INT                               NOT NULL,
    `school_name`    VARCHAR(150) CHARACTER SET 'utf8' NOT NULL,
    `is_blocked`     BOOLEAN                           NOT NULL,
    `appl_date`      DATE                              NOT NULL,
    INDEX `fk_candidates_cities1_idx` (`city_id` ASC) VISIBLE,
    INDEX `fk_candidates_logins1_idx` (`login_id` ASC) VISIBLE,
    PRIMARY KEY (`login_id`),
    CONSTRAINT `fk_candidates_cities1`
        FOREIGN KEY (`city_id`)
            REFERENCES cities (`id`)
            ON DELETE CASCADE
            ON UPDATE CASCADE,
    CONSTRAINT `fk_candidates_logins`
        FOREIGN KEY (`login_id`)
            REFERENCES logins (`id`)
            ON DELETE CASCADE
            ON UPDATE CASCADE
);

CREATE TABLE faculties
(
    id            INT PRIMARY KEY NOT NULL,
    budget_places INT             NOT NULL,
    all_places    INT             NOT NULL
);

CREATE TABLE languages
(
    `id`        INT PRIMARY KEY                  NOT NULL AUTO_INCREMENT,
    `name`      VARCHAR(20) CHARACTER SET 'utf8' NOT NULL,
    `lang_code` VARCHAR(5) CHARACTER SET 'utf8'  NOT NULL
);

CREATE TABLE IF NOT EXISTS faculties_languages
(
    `faculties_id` INT                               NOT NULL,
    `languages_id` INT                               NOT NULL,
    `name`         VARCHAR(250) CHARACTER SET 'utf8' NOT NULL,
    PRIMARY KEY (`faculties_id`, `languages_id`),
    INDEX `fk_faculties_languages_faculties1_idx` (`faculties_id` ASC) VISIBLE,
    INDEX `fk_faculties_languages_languages1_idx` (`languages_id` ASC) VISIBLE,
    CONSTRAINT `fk_faculties_languages_faculties1`
        FOREIGN KEY (`faculties_id`)
            REFERENCES faculties (`id`)
            ON DELETE CASCADE
            ON UPDATE CASCADE,
    CONSTRAINT `fk_faculties_languages_languages1`
        FOREIGN KEY (`languages_id`)
            REFERENCES languages (`id`)
            ON DELETE CASCADE
            ON UPDATE CASCADE
);

CREATE TABLE subjects
(
    `id`       INT NOT NULL AUTO_INCREMENT,
    `duration` INT NOT NULL,
    PRIMARY KEY (`id`)
);

CREATE TABLE faculties_subjects
(
    `subjects_id`  INT NOT NULL,
    `faculties_id` INT NOT NULL,
    INDEX `fk_subjects_has_faculties_faculties1_idx` (`faculties_id` ASC) VISIBLE,
    INDEX `fk_subjects_has_faculties_subjects1_idx` (`subjects_id` ASC) VISIBLE,
    CONSTRAINT `fk_subjects_has_faculties_faculties1`
        FOREIGN KEY (`faculties_id`)
            REFERENCES faculties (`id`)
            ON DELETE CASCADE
            ON UPDATE CASCADE,
    CONSTRAINT `fk_subjects_has_faculties_subjects1`
        FOREIGN KEY (`subjects_id`)
            REFERENCES subjects (`id`)
            ON DELETE CASCADE
            ON UPDATE CASCADE
);

CREATE TABLE subjects_languages
(
    subject_id  INT                               NOT NULL,
    language_id INT                               NOT NULL,
    `name`         VARCHAR(100) CHARACTER SET 'utf8' NOT NULL,
    PRIMARY KEY (subject_id, language_id),
    INDEX `fk_subjects_languages_subjects1_idx` (subject_id ASC) VISIBLE,
    INDEX `fk_subjects_languages_languages1_idx` (language_id ASC) VISIBLE,
    CONSTRAINT `fk_subjects_languages_subjects1`
        FOREIGN KEY (subject_id)
            REFERENCES subjects (`id`)
            ON DELETE CASCADE
            ON UPDATE CASCADE,
    CONSTRAINT `fk_subjects_languages_languages1`
        FOREIGN KEY (language_id)
            REFERENCES languages (`id`)
            ON DELETE CASCADE
            ON UPDATE CASCADE
);

CREATE TABLE applications
(
    `id`             INT NOT NULL AUTO_INCREMENT,
    `login_id` INT NOT NULL,
    `faculty_id`   INT NOT NULL,
    `priority`       INT NOT NULL,
    `status`    VARCHAR(30) NOT NULL,
    PRIMARY KEY (`id`),
    INDEX `fk_statement_logins1_idx` (`login_id` ASC) VISIBLE,
    INDEX `fk_applications_faculties1_idx` (`faculty_id` ASC) VISIBLE,
    CONSTRAINT `fk_applications_faculties1`
        FOREIGN KEY (`faculty_id`)
            REFERENCES faculties (`id`)
            ON DELETE CASCADE
            ON UPDATE CASCADE,
    CONSTRAINT `fk_statement_logins1`
        FOREIGN KEY (`login_id`)
            REFERENCES logins (`id`)
            ON DELETE CASCADE
            ON UPDATE CASCADE
)
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8;

CREATE TABLE grades
(
    `id`          INT NOT NULL AUTO_INCREMENT,
    `subject_id` INT NOT NULL,
    `grade`       INT NOT NULL,
    PRIMARY KEY (`id`),
    INDEX `fk_grades_subjects1_idx` (`subject_id` ASC) VISIBLE,
    CONSTRAINT `fk_grades_subjects1`
        FOREIGN KEY (`subject_id`)
            REFERENCES subjects (`id`)
            ON DELETE CASCADE
            ON UPDATE CASCADE
);

CREATE TABLE applications_grades
(
    `application_id` INT NOT NULL,
    grade_id       INT NOT NULL,
    PRIMARY KEY (`application_id`, grade_id),
    INDEX `fk_applications_has_grades_grades1_idx` (grade_id ASC) VISIBLE,
    INDEX `fk_applications_has_grades_applications1_idx` (`application_id` ASC) VISIBLE,
    CONSTRAINT `fk_applications_has_grades_applications1`
        FOREIGN KEY (`application_id`)
            REFERENCES applications (`id`)
            ON DELETE CASCADE
            ON UPDATE CASCADE,
    CONSTRAINT `fk_applications_has_grades_grades1`
        FOREIGN KEY (grade_id)
            REFERENCES grades (`id`)
            ON DELETE CASCADE
            ON UPDATE CASCADE
);


