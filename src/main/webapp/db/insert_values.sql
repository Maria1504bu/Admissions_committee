
INSERT INTO roles (name, description) VALUE ('user', 'description');
INSERT INTO users (email, password, role_id) VALUE ('candidate@gmail', '11111', 1);
INSERT INTO candidates VALUES (1, 'Sofia', 'Dudar', 'Stepanivna', 'Ternopil', 'Berezhany',
 'school 23');
INSERT INTO exams VALUES (1, 'Math'), (2, 'English'), (3, 'Biology');
INSERT INTO candidates_exams VALUES (1, 1, 145), (1, 2, 157), (1, 3, 124);