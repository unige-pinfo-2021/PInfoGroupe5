DROP TABLE IF EXISTS UGroups;

CREATE TABLE IF NOT EXISTS UGroups(
    ID varchar(255) not null,
    username varchar(255),
    primary key (ID, username)
);


--INSERT INTO UGroups(ID, username) VALUES('goupINIT', 'user0');

