DROP TABLE IF EXISTS Invits;

CREATE TABLE IF NOT EXISTS Invits (
    ID varchar(255) not null,
    film varchar(255) not null,
    score INT,
    totalscore INT, -- nb of members, votes
    primary key (ID, film)
);


/*INSERT INTO Invits(ID, film, score, totalscore) VALUES('INITINV','init', 0,7);
INSERT INTO Invits(ID, film, score, totalscore) VALUES('double','init', 0,5);
INSERT INTO Invits(ID, film, score, totalscore) VALUES('double','second', 0,2);*/


