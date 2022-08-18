
INSERT INTO roles (name) VALUES ('ADMIN'), ('CANDIDATE');
INSERT INTO logins (email, password, role_id) VALUE ('admin@gmail', 'B0BAEE9D279D34FA1DFD71AADB908C3F', 1);
INSERT INTO logins (email, password, role_id) VALUE ('candidate@gmail', 'B0BAEE9D279D34FA1DFD71AADB908C3F', 2);
INSERT INTO candidates VALUES (2, 'Sofia', 'Dudar', 'Stepanivna', 'certificate_url', 3,
 'school 23', FALSE, '2022-08-14');
# INSERT INTO languages (`name`, `lang_code`) VALUES ('English', 'en'), ('Українська', 'uk'), ('Русский', 'ru');
# INSERT INTO subjects (name) VALUES ('Math'), ('English'), ('Biology'), ('Chemistry');
# INSERT INTO candidates_exams VALUES (1, 1, 145), (1, 2, 157), (1, 3, 124);
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
