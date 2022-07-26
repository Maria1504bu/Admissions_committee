DROP DATABASE IF EXISTS Admissions_committee;

CREATE DATABASE IF NOT EXISTS Admissions_committee;
USE Admissions_committee;


# CREATE TABLE roles
# (
#     id   INT AUTO_INCREMENT PRIMARY KEY          NOT NULL,
#     name VARCHAR(50) CHARACTER SET 'utf8' UNIQUE NOT NULL
# );

CREATE TABLE logins
(
    `id`       INT                               NOT NULL AUTO_INCREMENT,
    `email`    VARCHAR(45) CHARACTER SET 'utf8'  NOT NULL,
    `password` VARCHAR(200) CHARACTER SET 'utf8' NOT NULL,
    `role`  ENUM('ADMIN', 'CANDIDATE'),
    PRIMARY KEY (`id`),
    UNIQUE INDEX `email_UNIQUE` (`email` ASC) VISIBLE
#     INDEX `fk_logins_role_idx` (`role_id` ASC) VISIBLE,
#     CONSTRAINT `fk_logins_roles`
#         FOREIGN KEY (`role_id`)
#             REFERENCES roles (`id`)
#             ON DELETE CASCADE
#             ON UPDATE CASCADE
);

CREATE TABLE cities
(
    `id`   INT                              NOT NULL AUTO_INCREMENT,
    `name` VARCHAR(45) CHARACTER SET 'utf8' NOT NULL,
    PRIMARY KEY (`id`)
);

CREATE TABLE candidates
(
    `login_id`    INT                               NOT NULL,
    `lastname` VARCHAR(50) CHARACTER SET 'utf8'  NOT NULL,
    `firstname`  VARCHAR(50) CHARACTER SET 'utf8'  NOT NULL,
    `fathername` VARCHAR(50) CHARACTER SET 'utf8'  NOT NULL,
    `certificate` VARCHAR(80) CHARACTER SET 'utf8',
    `city_id`     INT                               NOT NULL,
    `school_name` VARCHAR(150) CHARACTER SET 'utf8' NOT NULL,
    `is_blocked`  BOOLEAN                           NOT NULL,
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
    id            INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
    budget_places INT                            NOT NULL,
    total_places  INT                            NOT NULL
);

CREATE TABLE languages
(
    `id`        INT PRIMARY KEY                  NOT NULL AUTO_INCREMENT,
    `name`      VARCHAR(20) CHARACTER SET 'utf8' NOT NULL,
    `lang_code` VARCHAR(5) CHARACTER SET 'utf8'  NOT NULL
);

CREATE TABLE IF NOT EXISTS faculties_languages
(
    `faculty_id`  INT                                      NOT NULL,
    `language_id` INT                                      NOT NULL,
    `name`        VARCHAR(250) CHARACTER SET 'utf8' UNIQUE NOT NULL,
    PRIMARY KEY (`faculty_id`, `language_id`),
    INDEX `fk_faculties_languages_faculties1_idx` (`faculty_id` ASC) VISIBLE,
    INDEX `fk_faculties_languages_languages1_idx` (`language_id` ASC) VISIBLE,
    CONSTRAINT `fk_faculties_languages_faculties1`
        FOREIGN KEY (`faculty_id`)
            REFERENCES faculties (`id`)
            ON DELETE CASCADE
            ON UPDATE CASCADE,
    CONSTRAINT `fk_faculties_languages_languages1`
        FOREIGN KEY (`language_id`)
            REFERENCES languages (`id`)
            ON DELETE CASCADE
            ON UPDATE CASCADE,
    CONSTRAINT UNIQUE (faculty_id, language_id)
);

CREATE TABLE subjects
(
    `id`       INT NOT NULL AUTO_INCREMENT,
    `maxGrade` INT NOT NULL,
    PRIMARY KEY (`id`)
);

CREATE TABLE faculties_subjects
(
    faculty_id          INT NOT NULL,
    subject_id          INT NOT NULL,
    subject_coefficient INT NOT NULL,
    INDEX `fk_subjects_has_faculties_faculties1_idx` (faculty_id ASC) VISIBLE,
    INDEX `fk_subjects_has_faculties_subjects1_idx` (`subject_id` ASC) VISIBLE,
    CONSTRAINT `fk_subjects_has_faculties_faculties1`
        FOREIGN KEY (faculty_id)
            REFERENCES faculties (`id`)
            ON DELETE CASCADE
            ON UPDATE CASCADE,
    CONSTRAINT `fk_subjects_has_faculties_subjects1`
        FOREIGN KEY (`subject_id`)
            REFERENCES subjects (`id`)
            ON DELETE CASCADE
            ON UPDATE CASCADE,
    CONSTRAINT `subject_coefficient_range`
        CHECK ( subject_coefficient > 0 && subject_coefficient <= 100 ),
    CONSTRAINT UNIQUE (subject_id, faculty_id)
);

-- MySQL don`t support statement-level triggers, so I`m able to check only if sum is greater 100
CREATE TRIGGER i_sum_of_subject_coefficient_by_faculty
    AFTER INSERT
    ON faculties_subjects
    FOR EACH ROW
BEGIN
    DECLARE coef_sum INT;
    DECLARE error_message VARCHAR(255);
    SELECT SUM(subject_coefficient)
    FROM faculties_subjects
    WHERE faculty_id = NEW.faculty_id
    INTO coef_sum;

    SET error_message = CONCAT('Sum of subject_coefficients must can`t be more than 100  but is ', coef_sum);
    IF coef_sum > 100 THEN
        SIGNAL SQLSTATE '45000'
            SET MESSAGE_TEXT = error_message;
    end if;
END;

CREATE TABLE subjects_languages
(
    subject_id  INT                                      NOT NULL,
    language_id INT                                      NOT NULL,
    name        VARCHAR(100) CHARACTER SET 'utf8' UNIQUE NOT NULL,
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
            ON UPDATE CASCADE,
    CONSTRAINT UNIQUE (subject_id, language_id)
);

CREATE TABLE grades
(
    candidate_id         INT NOT NULL,
    subject_id INT NOT NULL,
    grade      INT NOT NULL,
    PRIMARY KEY (candidate_id, subject_id),
    INDEX `fk_grades_candidates1_idx` (candidate_id ASC) VISIBLE,
    INDEX `fk_grades_subjects1_idx` (subject_id ASC) VISIBLE,
    CONSTRAINT `fk_grades_subjects1`
        FOREIGN KEY (subject_id)
            REFERENCES subjects (id)
            ON DELETE CASCADE
            ON UPDATE CASCADE,
    CONSTRAINT `fk_grades_candidates1`
        FOREIGN KEY (candidate_id)
            REFERENCES candidates (login_id)
            ON DELETE CASCADE
            ON UPDATE CASCADE,
    CONSTRAINT UNIQUE (candidate_id, subject_id)
);

CREATE TABLE applications
(
    `id`           INT         NOT NULL AUTO_INCREMENT,
    `login_id`     INT         NOT NULL,
    `faculty_id`   INT         NOT NULL,
    `rating_score` INT, -- it will be calculating by trigger
    `status`       VARCHAR(30) NOT NULL DEFAULT 'NOT_PROCEED',
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
            ON UPDATE CASCADE,
    CONSTRAINT UNIQUE (login_id, faculty_id)
);

CREATE TRIGGER i_counting_rating_score
    BEFORE INSERT
    ON applications
    FOR EACH ROW
BEGIN
DECLARE cur_subj_id, sum_score INT DEFAULT 0;
DECLARE cursor_list_is_done BOOLEAN DEFAULT false;
DECLARE cursor_List CURSOR FOR
    SELECT subject_id FROM faculties_subjects WHERE faculties_subjects.faculty_id = NEW.faculty_id;

DECLARE CONTINUE HANDLER FOR NOT FOUND SET cursor_list_is_done = TRUE;

OPEN cursor_List;

loop_List:
    LOOP
        FETCH cursor_List INTO cur_subj_id;
IF cursor_list_is_done THEN
            LEAVE loop_List;
end if;
        -- multiply by 2, because max rating score must be 200 and subject_coefficient is in percents
        SET sum_score = sum_score + (2 * (SELECT grade FROM grades WHERE subject_id = cur_subj_id AND candidate_id = NEW.login_id) /
                         (SELECT maxGrade FROM subjects WHERE subjects.id = cur_subj_id) *
                         (SELECT subject_coefficient FROM faculties_subjects WHERE subject_id = cur_subj_id AND faculty_id=NEW.faculty_id));

END LOOP;
CLOSE cursor_List;
SET NEW.rating_score = sum_score;
END;



