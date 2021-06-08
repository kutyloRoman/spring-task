CREATE TABLE IF NOT EXISTS User
(
    id   INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(32) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL
);
INSERT INTO User(username,password)
VALUES ('testuser','password'),
       ('roman', 'pass');