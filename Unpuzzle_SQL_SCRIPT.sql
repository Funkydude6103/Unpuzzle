CREATE DATABASE UNPUZZLE;

USE UNPUZZLE
-- Creating the Player table
CREATE TABLE Player (
                        id INT AUTO_INCREMENT PRIMARY KEY,
                        username VARCHAR(255) NOT NULL,
                        password VARCHAR(255) NOT NULL,
                        age INT,
                        level INT,
                        diamonds INT
);
USE UNPUZZLE
-- Creating the Level table
CREATE TABLE Level (
                       id INT AUTO_INCREMENT PRIMARY KEY,
                       gameboard BLOB -- Here BLOB is used to store serialized objects
);