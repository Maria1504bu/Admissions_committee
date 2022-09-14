
INSERT INTO roles (name) VALUES ('ADMIN'), ('CANDIDATE');
INSERT INTO languages (name, lang_code) VALUES ('English', 'en'), ('Українська', 'uk');
INSERT INTO logins (email, password, role_id) VALUES ('admin@gmail', 'B0BAEE9D279D34FA1DFD71AADB908C3F', 1),
                                                     ('candidate@gmail', 'B0BAEE9D279D34FA1DFD71AADB908C3F', 2),
                                                     ('vova27@gmail', 'B0BAEE9D279D34FA1DFD71AADB908C3F', 2),
                                                     ('grabovych@gmail', 'B0BAEE9D279D34FA1DFD71AADB908C3F', 2);
INSERT INTO cities (`name`)
VALUES ('Cherkasy'),
       ('Chernihiv'),
       ('Chernivtsi'),
       ('Dnipropetrovsk'),
       ('Donetsk'),
       ('Ivano_Frankivsk'),
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
INSERT INTO candidates VALUES(2, 'Dudar', 'Sofia', 'Stepanivna', 'havenot', 3,
                              'school 23', FALSE, '2022-08-14'),
                           (3, 'Futka', 'Volodymyr', 'Mykolayovych', 'havenot', 6,
                            'school 17', FALSE, '2022-08-29'),
                           (4, 'Grabovych', 'Petro', 'Bogdanovych', 'havenot', 10,
                            'school 22', FALSE, '2022-08-29');
INSERT INTO faculties (budget_places, total_places) VALUES (4, 10), (5, 7), (6, 8);
INSERT INTO faculties_languages (faculty_id, language_id, name) VALUES (1, 1, 'Management'),
                                                                          (1, 2, 'Менеджмент'),
                                                                       (2, 1, 'Audit'),
                                                                       (2, 2, 'Аудит'),
                                                                       (3, 1, 'Engineering'),
                                                                       (3, 2, 'Машинобудування');
INSERT INTO applications (login_id, faculty_id, status) VALUES (2, 1, 'NOT_PROCEED'),
                                                                         (2, 2, 'NOT_PROCEED'),
                                                                         (2, 3, 'NOT_PROCEED'),
                                                                         (3, 1, 'NOT_PROCEED'),
                                                                         (3, 2, 'NOT_PROCEED'),
                                                                         (4, 1, 'NOT_PROCEED');
INSERT INTO subjects (maxGrade) VALUES (150), (140), (45), (40);

INSERT INTO subjects_languages VALUES (1, 1, 'Math'), (1, 2, 'Математика'),
                                      (2, 1, 'Chemistry'), (2, 2, 'Хімія'),
                                      (3, 1, 'Biology'), (3, 2, 'Біологія'),
                                      (4, 1, 'English'), (4, 2, 'Англійська мова');
INSERT INTO faculties_subjects VALUES (1, 1),
                                      (1, 4),
                                      (2, 1),
                                      (3, 1),
                                      (3, 2);
INSERT INTO grades (subject_id, grade) VALUES (1, 123), (2, 147);
INSERT INTO applications_grades VALUES (1, 1), (1, 2), (2, 2), (3, 1), (3, 2);