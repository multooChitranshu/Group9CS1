CREATE DATABASE if not exists MetroDataBase;
USE  MetroDataBase;

CREATE TABLE if not exists Card (
	cardId bigint,
    aadharId bigint UNIQUE,
    balance double,
    PRIMARY KEY (cardId)
    );
 
CREATE TABLE if not exists Station (
	stationId int,
    stationName VARCHAR(25),
    previousStationId int,
    nextStationId int,
    PRIMARY KEY (stationId)
);
CREATE TABLE if not exists Transaction (
	cardId bigint,
	sourceStationId int default -1,
	dateAndTimeOfBoarding DATETIME default null,
	destinationStationId int default -1,
    dateAndTimeOfExit DATETIME default null,
    fare double default 0,
    FOREIGN KEY (cardId) REFERENCES Card(cardId),
    FOREIGN KEY (sourceStationId) REFERENCES Station(stationId),
    FOREIGN KEY (destinationStationId) REFERENCES Station(stationId)
    );
    
Insert into Station values(1,'Bhopal',null,2);
Insert into Station values(2,'MP Nagar',1,3);
Insert into Station values(3,'Indrapuri',2,4);
Insert into Station values(4,'New Market',3,5);
Insert into Station values(5,'Board Office',4,null);

