INSERT INTO roles (name)
VALUES ('ADMIN'),
       ('CANDIDATE');
INSERT INTO languages (name, lang_code)
VALUES ('English', 'en'),
       ('Українська', 'uk');
INSERT INTO logins (email, password, role_id)
VALUES ('admin@gmail.com', 'AAB27D62AA18F1D428D488788A0B1BEC', 1),
       ('candidate@gmail.com', 'AAB27D62AA18F1D428D488788A0B1BEC', 2),
       ('vova27@gmail.com', 'AAB27D62AA18F1D428D488788A0B1BEC', 2),
       ('grabovych@gmail.com', 'AAB27D62AA18F1D428D488788A0B1BEC', 2),
       ('kozarDiana@gmail.com', 'AAB27D62AA18F1D428D488788A0B1BEC', 2),
       ('kravchukDmytro@gmail.com', 'AAB27D62AA18F1D428D488788A0B1BEC', 2),
       ('sapushakRoman@gmail.com', 'AAB27D62AA18F1D428D488788A0B1BEC', 2);
INSERT
INTO cities (`name`)
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
INSERT INTO candidates
VALUES (2, 'Дудар', 'Софія', 'Степанівна', 'havenot', 3,
        'school 23', FALSE, '2022-08-14'),
       (3, 'Футка', 'Володимир', 'Миколайович', 'havenot', 6,
        'school 17', FALSE, '2022-08-29'),
       (4, 'Грабович', 'Петро', 'Богданович', 'havenot', 10,
        'school 22', FALSE, '2022-08-29'),
       (5, 'Козар', 'Діана', 'Петрівна', 'havenot', 12,
        'school 22', FALSE, '2022-09-21'),
       (6, 'Кравчук', 'Дмитро', 'Остапович', 'havenot', 2,
        'school 2', FALSE, '2022-09-21'),
       (7, 'Сапужак', 'Роман', 'Олександрович', 'havenot', 19,
        'school 5', FALSE, '2022-09-21');
INSERT INTO faculties (budget_places, total_places)
VALUES (4, 10),
       (2, 7),
       (6, 8);
INSERT INTO faculties_languages (faculty_id, language_id, name)
VALUES (1, 1, 'Management'),
       (1, 2, 'Менеджмент'),
       (2, 1, 'Audit'),
       (2, 2, 'Аудит'),
       (3, 1, 'Engineering'),
       (3, 2, 'Машинобудування');
INSERT INTO subjects (maxGrade)
VALUES (150),
       (140),
       (45),
       (40);
INSERT INTO faculties_subjects
VALUES (1, 1, 40),
       (1, 4, 60),
       (2, 1, 70),
       (2, 2, 30),
       (3, 2, 20),
       (3, 3, 30),
       (3, 4, 50);
INSERT INTO grades (candidate_id, subject_id, grade)
VALUES (2, 1, 123),
       (2, 2, 120),
       (2, 3, 45),
       (2, 4, 40),
       (3, 1, 150),
       (3, 2, 140),
       (3, 4, 35),
       (4, 1, 111),
       (4, 4, 34),
       (5, 1, 74),
       (5, 2, 77),
       (5, 3, 39),
       (5, 4, 36),
       (6, 1, 88),
       (6, 2, 134),
       (6, 3, 33),
       (6, 4, 40),
       (7, 1, 77),
       (7, 2, 60),
       (7, 3, 34),
       (7, 4, 27);
INSERT INTO applications (login_id, faculty_id, status)
VALUES (2, 1, 'NOT_PROCEED'),
       (2, 2, 'NOT_PROCEED'),
       (2, 3, 'NOT_PROCEED'),
       (3, 1, 'NOT_PROCEED'),
       (3, 2, 'NOT_PROCEED'),
       (4, 1, 'NOT_PROCEED'),
       (5, 1, 'NOT_PROCEED'),
       (5, 2, 'NOT_PROCEED'),
       (5, 3, 'NOT_PROCEED'),
       (6, 1, 'NOT_PROCEED'),
       (6, 3, 'NOT_PROCEED'),
       (7, 2, 'NOT_PROCEED'),
       (7, 3, 'NOT_PROCEED');
INSERT INTO applications (login_id, faculty_id, status)
VALUES (3, 3,  'NOT_PROCEED');
INSERT INTO subjects_languages
VALUES (1, 1, 'Math'),
       (1, 2, 'Математика'),
       (2, 1, 'Chemistry'),
       (2, 2, 'Хімія'),
       (3, 1, 'Physic'),
       (3, 2, 'Фізика'),
       (4, 1, 'English'),
       (4, 2, 'Англійська мова');