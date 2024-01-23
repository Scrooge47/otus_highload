CREATE TABLE IF NOT EXISTS users (
     id INT AUTO_INCREMENT PRIMARY KEY NOT NULL,
     username VARCHAR(50)  UNIQUE NOT NULL,
     password VARCHAR(255) NOT NULL,
     first_name VARCHAR(255),
     last_name VARCHAR(255),
     age  TINYINT UNSIGNED,
     gender  BIT(1),
     interest TEXT,
     city VARCHAR(100)
);