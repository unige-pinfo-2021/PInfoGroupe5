DROP TABLE IF EXISTS Users;

CREATE TABLE IF NOT EXISTS Users (
    username varchar(255) not null,
    primary key (username)
);

INSERT INTO Users(username) VALUES('User0');
INSERT INTO Users(username) VALUES('User1');
INSERT INTO Users(username) VALUES('User2');
INSERT INTO Users(username) VALUES('User3');
INSERT INTO Users(username) VALUES('User4');
INSERT INTO Users(username) VALUES('User5');
