CREATE TABLE Accounts (
account_ID INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
surname VARCHAR(45) NOT NULL,
middlename VARCHAR(45),
name VARCHAR(30) NOT NULL,
email VARCHAR(50) NOT NULL UNIQUE,
password VARCHAR(30) NOT NULL,
dateOfBirth DATE,
homePhone VARCHAR(15),
workPhone VARCHAR(15),
skype VARCHAR(30) UNIQUE,
icq VARCHAR(15) UNIQUE,
homeAddress VARCHAR(45),
workAddress VARCHAR(45),
addInfo VARCHAR(100));

CREATE TABLE Groups (
group_ID INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
groupName VARCHAR(45) NOT NULL,
groupDescription VARCHAR(300));

CREATE TABLE Relationship (
accountOne_ID INT NOT NULL,
accountTwo_ID INT NOT NULL,
status TINYINT(3) NOT NULL DEFAULT '0',
actionAccount_ID INT NOT NULL,

CONSTRAINT relationship_pr PRIMARY KEY (accountOne_ID, accountTwo_ID),
FOREIGN KEY (accountOne_ID) REFERENCES Accounts(account_ID) ON DELETE CASCADE,
FOREIGN KEY (accountTwo_ID) REFERENCES Accounts(account_ID) ON DELETE CASCADE,
FOREIGN KEY (actionAccount_ID) REFERENCES Accounts(account_ID));
