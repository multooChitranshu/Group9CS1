CREATE DATABASE if not exists MetroDataBase;
USE  MetroDataBase;

CREATE TABLE if not exists Card (
	cardId VARCHAR(15),
    aadharId VARCHAR(15),
    balance VARCHAR(6),
    PRIMARY KEY (cardId)
    );
 
CREATE TABLE if not exists Station (
	stationId VARCHAR(5),
    stationName VARCHAR(15),
    previousStationId VARCHAR(5),
    nextStationId VARCHAR(5),
    PRIMARY KEY (stationId)
);
drop table Transaction;
CREATE TABLE if not exists Transaction (
	dateAndTimeOfBoarding DATETIME,
    dateAndTimeOfExit DATETIME,
    sourceStationId varchar(5),
    destinationStationId varchar(5),
    fare double,
    cardId VARCHAR(15),
    FOREIGN KEY (cardId) REFERENCES Card(cardId),
    FOREIGN KEY (sourceStationId) REFERENCES Station(stationId),
    FOREIGN KEY (destinationStationId) REFERENCES Station(stationId)
    
    
    );
    
Insert into Station values('1','bhopal',null,'2');
Insert into Station values('2','MP Nagar','1','3');
Insert into Station values('3','Indrapuri','2','4');
Insert into Station values('4','New Market','3','5');
Insert into Station values('5','Board Office','4',null);
