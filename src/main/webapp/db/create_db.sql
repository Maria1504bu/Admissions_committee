DROP DATABASE IF EXISTS Admissions_committee;

CREATE DATABASE IF NOT EXISTS Admissions_committee;
USE Admissions_committee;


CREATE TABLE user_roles
(
    id   INT AUTO_INCREMENT PRIMARY KEY          NOT NULL,
    name VARCHAR(50) CHARACTER SET 'utf8' UNIQUE NOT NULL
);

CREATE TABLE user_logins
(
    `id`            INT                               NOT NULL AUTO_INCREMENT,
    `email`         VARCHAR(45) CHARACTER SET 'utf8'  NOT NULL,
    `password`      VARCHAR(200) CHARACTER SET 'utf8' NOT NULL,
    `user_roles_id` INT                               NOT NULL,
    PRIMARY KEY (`id`),
    UNIQUE INDEX `email_UNIQUE` (`email` ASC) VISIBLE,
    INDEX `fk_user_logins_user_roles_idx` (`user_roles_id` ASC) VISIBLE,
    CONSTRAINT `fk_user_logins_user_roles`
        FOREIGN KEY (`user_roles_id`)
            REFERENCES user_roles (`id`)
            ON DELETE CASCADE
            ON UPDATE CASCADE
);

CREATE TABLE states
(
    `id`   INT                              NOT NULL AUTO_INCREMENT,
    `name` VARCHAR(45) CHARACTER SET 'utf8' NOT NULL,
    PRIMARY KEY (`id`)
);

CREATE TABLE candidates
(
    `user_logins_id` INT                               NOT NULL,
    `first_name`     VARCHAR(50) CHARACTER SET 'utf8'  NOT NULL,
    `father_name`    VARCHAR(50) CHARACTER SET 'utf8'  NOT NULL,
    `second_name`    VARCHAR(50) CHARACTER SET 'utf8'  NOT NULL,
    `city`           VARCHAR(50) CHARACTER SET 'utf8'  NOT NULL,
    `states_id`      INT                               NOT NULL,
    `school_name`    VARCHAR(150) CHARACTER SET 'utf8' NOT NULL,
    `is_blocked`     INT                               NOT NULL,
    `appl_date`      DATE                              NOT NULL,
    INDEX `fk_candidates_states1_idx` (`states_id` ASC) VISIBLE,
    INDEX `fk_candidates_user_logins1_idx` (`user_logins_id` ASC) VISIBLE,
    PRIMARY KEY (`user_logins_id`),
    CONSTRAINT `fk_candidates_states1`
        FOREIGN KEY (`states_id`)
            REFERENCES states (`id`)
            ON DELETE CASCADE
            ON UPDATE CASCADE,
    CONSTRAINT `fk_candidates_user_logins`
        FOREIGN KEY (`user_logins_id`)
            REFERENCES user_logins (`id`)
            ON DELETE CASCADE
            ON UPDATE CASCADE
);


CREATE TABLE certificates
(
    `enrollees_user_logins_id` INT        NOT NULL,
    `school_certificate`       MEDIUMBLOB NOT NULL,
    PRIMARY KEY (`enrollees_user_logins_id`),
    INDEX `fk_certificates_enrollees1_idx` (`enrollees_user_logins_id` ASC) VISIBLE,
    CONSTRAINT `fk_certificates_enrollees1`
        FOREIGN KEY (`enrollees_user_logins_id`)
            REFERENCES candidates (`user_logins_id`)
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
    `subjects_id`  INT                               NOT NULL,
    `languages_id` INT                               NOT NULL,
    `name`         VARCHAR(100) CHARACTER SET 'utf8' NOT NULL,
    PRIMARY KEY (`subjects_id`, `languages_id`),
    INDEX `fk_subjects_languages_subjects1_idx` (`subjects_id` ASC) VISIBLE,
    INDEX `fk_subjects_languages_languages1_idx` (`languages_id` ASC) VISIBLE,
    CONSTRAINT `fk_subjects_languages_subjects1`
        FOREIGN KEY (`subjects_id`)
            REFERENCES subjects (`id`)
            ON DELETE CASCADE
            ON UPDATE CASCADE,
    CONSTRAINT `fk_subjects_languages_languages1`
        FOREIGN KEY (`languages_id`)
            REFERENCES languages (`id`)
            ON DELETE CASCADE
            ON UPDATE CASCADE
);

CREATE TABLE applications
(
    `id`             INT NOT NULL AUTO_INCREMENT,
    `user_logins_id` INT NOT NULL,
    `faculties_id`   INT NOT NULL,
    `priority`       INT NOT NULL,
    `is_approved`    INT NOT NULL,
    PRIMARY KEY (`id`),
    INDEX `fk_statement_user_logins1_idx` (`user_logins_id` ASC) VISIBLE,
    INDEX `fk_applications_faculties1_idx` (`faculties_id` ASC) VISIBLE,
    CONSTRAINT `fk_applications_faculties1`
        FOREIGN KEY (`faculties_id`)
            REFERENCES faculties (`id`)
            ON DELETE CASCADE
            ON UPDATE CASCADE,
    CONSTRAINT `fk_statement_user_logins1`
        FOREIGN KEY (`user_logins_id`)
            REFERENCES user_logins (`id`)
            ON DELETE CASCADE
            ON UPDATE CASCADE
)
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8;

CREATE TABLE IF NOT EXISTS `university`.`grades`
(
    `id`          INT NOT NULL AUTO_INCREMENT,
    `subjects_id` INT NOT NULL,
    `grade`       INT NOT NULL,
    PRIMARY KEY (`id`),
    INDEX `fk_grades_subjects1_idx` (`subjects_id` ASC) VISIBLE,
    CONSTRAINT `fk_grades_subjects1`
        FOREIGN KEY (`subjects_id`)
            REFERENCES subjects (`id`)
            ON DELETE CASCADE
            ON UPDATE CASCADE
);

CREATE TABLE IF NOT EXISTS `university`.`applications_grades`
(
    `applications_id` INT NOT NULL,
    `grades_id`       INT NOT NULL,
    PRIMARY KEY (`applications_id`, `grades_id`),
    INDEX `fk_applications_has_grades_grades1_idx` (`grades_id` ASC) VISIBLE,
    INDEX `fk_applications_has_grades_applications1_idx` (`applications_id` ASC) VISIBLE,
    CONSTRAINT `fk_applications_has_grades_applications1`
        FOREIGN KEY (`applications_id`)
            REFERENCES applications (`id`)
            ON DELETE CASCADE
            ON UPDATE CASCADE,
    CONSTRAINT `fk_applications_has_grades_grades1`
        FOREIGN KEY (`grades_id`)
            REFERENCES grades (`id`)
            ON DELETE CASCADE
            ON UPDATE CASCADE
);


