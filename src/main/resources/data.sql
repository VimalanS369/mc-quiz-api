-- Creating APP_USER table
CREATE TABLE IF NOT EXISTS APP_USER (
    ID BIGINT AUTO_INCREMENT PRIMARY KEY,
    USERNAME VARCHAR(255) NOT NULL
);

-- Creating QUESTION table
CREATE TABLE IF NOT EXISTS QUESTION (
    ID BIGINT AUTO_INCREMENT PRIMARY KEY,
    OPTIONA VARCHAR(255),
    OPTIONB VARCHAR(255),
    OPTIONC VARCHAR(255),
    OPTIOND VARCHAR(255),
    CORRECT_ANSWER CHAR(1),
    QUESTION_TEXT VARCHAR(255)
);

-- Inserting data into APP_USER table
INSERT INTO APP_USER (ID, USERNAME) VALUES (1, 'admin'), (2, 'user');

-- Inserting data into QUESTION table
INSERT INTO QUESTION (ID, OPTIONA, OPTIONB, OPTIONC, OPTIOND, CORRECT_ANSWER, QUESTION_TEXT) 
VALUES 
(1, 'Paris', 'Berlin', 'Rome', 'Madrid', 'A', 'What is the capital of France?'),
(2, '3', '4', '5', '6', 'B', 'What is 2 + 2?'),
(3, 'Java', 'Python', 'JavaScript', 'C++', 'A', 'Which programming language is used for Android development?'),
(4, 'Mount Everest', 'K2', 'Kangchenjunga', 'Lhotse', 'C', 'What is the tallest mountain in the world?');
