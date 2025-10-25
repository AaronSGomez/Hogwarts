CREATE DATABASE if not exists hp;

USE hp;

CREATE TABLE house (
                       id INT AUTO_INCREMENT PRIMARY KEY,
                       name VARCHAR(50) NOT NULL,
                       founder VARCHAR(50) NOT NULL
);

CREATE TABLE wand (
                      id INT AUTO_INCREMENT PRIMARY KEY,
                      wood VARCHAR(50),
                      core VARCHAR(50),
                      length DOUBLE
);

CREATE TABLE wizard (
                        id INT AUTO_INCREMENT PRIMARY KEY,
                        name VARCHAR(50),
                        age INT,
                        house_id INT,
                        wand_id INT,
                        FOREIGN KEY (house_id) REFERENCES house(id),
                        FOREIGN KEY (wand_id) REFERENCES wand(id)
);
crh-rgqb-wvy