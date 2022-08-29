
INSERT INTO roles (name) VALUES ('ADMIN'), ('CANDIDATE');
INSERT INTO languages (name, lang_code) VALUES ('English', 'en'), ('Українська', 'uk');
INSERT INTO logins (email, password, role_id) VALUE ('admin@gmail', 'B0BAEE9D279D34FA1DFD71AADB908C3F', 1);
INSERT INTO logins (email, password, role_id) VALUE ('candidate@gmail', 'B0BAEE9D279D34FA1DFD71AADB908C3F', 2);
INSERT INTO cities (`name`)
VALUES ('Cherkasy'),
       ('Chernihiv'),
       ('Chernivtsi'),
       ('Dnipropetrovsk'),
       ('Donetsk'),
       ('Ivano-Frankivsk'),
       ('Kharkiv'),
       ('Kherson'),
       ('Khmelnytskyi'),
       ('Kiev'),
       ('Kirovohrad'),
       ('Luhansk'),
       ('Lviv'),
       ('Mykolaiv'),
       ('Odessa'),
       ('Poltava'),
       ('Rivne'),
       ('Sumy'),
       ('Ternopil'),
       ('Vinnytsia'),
       ('Volyn'),
       ('Zakarpattia'),
       ('Zaporizhia'),
       ('Zhytomyr');
INSERT INTO candidates VALUES (2, 'Sofia', 'Dudar', 'Stepanivna', 'havenot', 3,
                               'school 23', FALSE, '2022-08-14');
INSERT INTO faculties (budget_places, total_places) VALUES (4, 10), (5, 7), (6, 8);
INSERT INTO faculties_languages (faculty_id, language_id, name) VALUES (1, 1, 'Management'),
                                                                          (1, 2, 'Менеджмент'),
                                                                       (2, 1, 'Audit'),
                                                                       (2, 2, 'Аудит'),
                                                                       (3, 1, 'Engineering'),
                                                                       (3, 2, 'Машинобудування');
INSERT INTO applications (login_id, faculty_id, priority, status) VALUE (2, 1, 1, 'NOT_PROCEED');

INSERT INTO subjects (duration) VALUES (50), (40), (45), (40);
INSERT INTO subjects_languages VALUES (1, 1, 'Math'), (1, 2, 'Математика'),
                                      (2, 1, 'Chemistry'), (2, 2, 'Хімія'),
                                      (3, 1, 'Biology'), (3, 2, 'Біологія'),
                                      (4, 1, 'English'), (4, 2, 'Англійська мова');
INSERT INTO grades (subject_id, grade) VALUES (1, 123), (2, 147);
INSERT INTO applications_grades VALUES (1, 1), (1, 2);
INSERT INTO faculties (budget_places, total_places) VALUES (?, ?);
INSERT INTO faculties_languages (faculty_id, language_id, name) VALUES
    ((SELECT MAX(id) FROM faculties), (SELECT id FROM languages WHERE lang_code = 'en'), ?),
           ((SELECT MAX(id) FROM faculties), (SELECT id FROM languages WHERE lang_code = 'uk'), ?);
SELECT su.id, su.duration, GROUP_CONCAT(sl.name SEPARATOR '; ') as name FROM subjects su, subjects_languages sl
            GROUP BY su.id

