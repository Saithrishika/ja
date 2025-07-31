CREATE DATABASE exam_db;
USE exam_db;

CREATE TABLE users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50),
    password VARCHAR(50)
);

INSERT INTO users (username, password) VALUES ('student1', 'pass123');

CREATE TABLE questions (
    id INT AUTO_INCREMENT PRIMARY KEY,
    question TEXT,
    option_a VARCHAR(100),
    option_b VARCHAR(100),
    option_c VARCHAR(100),
    option_d VARCHAR(100),
    answer VARCHAR(1)
);

INSERT INTO questions (question, option_a, option_b, option_c, option_d, answer) VALUES
('What is the capital of France?', 'Berlin', 'Madrid', 'Paris', 'Rome', 'C'),
('What is 2 + 2?', '3', '4', '5', '6', 'B'),
('Java is developed by?', 'Microsoft', 'Apple', 'Sun Microsystems', 'Google', 'C');
