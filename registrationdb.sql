CREATE DATABASE registrationdb;
USE registrationdb;

CREATE TABLE users (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100),
    mobile VARCHAR(15),
    gender VARCHAR(10),
    dob VARCHAR(50),
    address VARCHAR(200)
);

