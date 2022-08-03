
INSERT INTO roles (name) VALUES ('admin'), ('candidate');
INSERT INTO users (email, password, role_id) VALUE ('admin@gmail', '11111', 1);
INSERT INTO users (email, password, role_id) VALUE ('candidate@gmail', '11111', 2);
INSERT INTO candidates VALUES (1, 'Sofia', 'Dudar', 'Stepanivna', 'Ternopil', 'Berezhany',
 'school 23');
INSERT INTO exams (name) VALUES ('Math'), ('English'), ('Biology'), ('Chemistry');
INSERT INTO candidates_exams VALUES (1, 1, 145), (1, 2, 157), (1, 3, 124);