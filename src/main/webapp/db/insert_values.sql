
INSERT INTO roles (name, description) VALUE ('user', 'description');
INSERT INTO users (email, password, role_id) VALUE ('candidate@gmail', '11111', 1);
INSERT INTO candidates VALUES (1, 'Sofia', 'Dudar', 'Stepanivna', 'Ternopil', 'Berezhany',
 'school 23');
INSERT INTO exams VALUES (1, 'Math', 170), (2, 'English', 158), (3, 'Biology', 163);
INSERT INTO candidates_exams VALUES (1, 1), (1, 2), (1, 3);