INSERT INTO addresses (zip_code, country, city, street, house, apartment)
VALUES ('602267', 'Россия', 'Муром', 'ул. Льва Толстого', 'д. 15', 'кв. 4');

INSERT INTO addresses (zip_code, country, city, street, house, apartment)
VALUES ('600000', 'Россия', 'Владимир', 'ул. Ленина', 'д. 102', NULL);

INSERT INTO enrollees (last_name, first_name, middle_name, gender, nationality, birth_date, passing_score, address_id)
VALUES ('Иванов', 'Иван', 'Иванович', 'Мужской', 'Русский', '2008-05-15', 240, 1);

INSERT INTO enrollees (last_name, first_name, middle_name, gender, nationality, birth_date, passing_score, address_id)
VALUES ('Петрова', 'Анна', 'Сергеевна', 'Женский', 'Русская', '2008-11-22', 250, 2);

INSERT INTO exam_scores (subject, score, enrollee_id)
VALUES ('Математика', 80, 1);

INSERT INTO exam_scores (subject, score, enrollee_id)
VALUES ('Русский язык', 75, 1);

INSERT INTO exam_scores (subject, score, enrollee_id)
VALUES ('Математика', 95, 2);

INSERT INTO exam_scores (subject, score, enrollee_id)
VALUES ('Физика', 90, 2);

INSERT INTO students (last_name, first_name, middle_name, city, gender, age, student_group, faculty)
VALUES ('Михайлов', 'Михаил', 'Петрович', 'Владимир', 'Мужской', 20, 'ПИН-123', 'Программная инженерия');

INSERT INTO students (last_name, first_name, middle_name, city, gender, age, student_group, faculty)
VALUES ('Козлова', 'Мария', 'Игоревна', 'Муром', 'Женский', 19, 'ИС-124', 'Информационные системы');
