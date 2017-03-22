DROP TABLE IF EXISTS Rating CASCADE;
DROP TABLE IF EXISTS UserRole CASCADE;
DROP TABLE IF EXISTS Film CASCADE;
DROP TABLE IF EXISTS Users CASCADE;
DROP TABLE IF EXISTS Role CASCADE;

--

CREATE TABLE IF NOT EXISTS Role (
idRole INT AUTO_INCREMENT NOT NULL,
role VARCHAR(100) NOT NULL,
PRIMARY KEY (idRole)
) ENGINE=InnoDB CHARACTER SET=UTF8;

CREATE TABLE IF NOT EXISTS Users (
idUsers INT AUTO_INCREMENT NOT NULL,
name VARCHAR(100) NOT NULL,
password VARCHAR(32) NOT NULL,
PRIMARY KEY (idUsers)
) ENGINE=InnoDB CHARACTER SET=UTF8;

CREATE TABLE IF NOT EXISTS Film (
idFilm INT AUTO_INCREMENT NOT NULL,
title VARCHAR(100) NOT NULL,
yr INT NOT NULL,
poster BLOB NOT NULL,
PRIMARY KEY (idFilm)
) ENGINE=InnoDB CHARACTER SET=UTF8;

CREATE TABLE IF NOT EXISTS UserRole (
idUserRole INT AUTO_INCREMENT NOT NULL,
userId INT NOT NULL,
roleId INT NOT NULL,
PRIMARY KEY (idUserRole),
FOREIGN KEY (userId) REFERENCES Users(idUsers)
	ON UPDATE CASCADE
	ON DELETE RESTRICT,
FOREIGN KEY (roleId) REFERENCES Role(idRole)
	ON UPDATE CASCADE
	ON DELETE RESTRICT
) ENGINE=InnoDB CHARACTER SET=UTF8;

CREATE TABLE IF NOT EXISTS Rating (
idRating INT AUTO_INCREMENT NOT NULL,
userId INT NOT NULL,
filmId INT NOT NULL,
rating DOUBLE NOT NULL,
PRIMARY KEY (idRating),
FOREIGN KEY (userId) REFERENCES Users(idUsers)
	ON UPDATE CASCADE
	ON DELETE RESTRICT,
FOREIGN KEY (filmId) REFERENCES Film(idFilm)
	ON UPDATE CASCADE
	ON DELETE RESTRICT
) ENGINE=InnoDB CHARACTER SET=UTF8;