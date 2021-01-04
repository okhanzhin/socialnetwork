CREATE TABLE IF NOT EXISTS Accounts
(
    account_ID         INT         NOT NULL PRIMARY KEY AUTO_INCREMENT,
    surname            VARCHAR(45) NOT NULL,
    middlename         VARCHAR(45),
    name               VARCHAR(30) NOT NULL,
    email              VARCHAR(50) NOT NULL UNIQUE,
    password           VARCHAR(30) NOT NULL,
    dateOfBirth        DATE,
    skype              VARCHAR(30) UNIQUE,
    icq                VARCHAR(15) UNIQUE,
    homeAddress        VARCHAR(45),
    workAddress        VARCHAR(45),
    addInfo            VARCHAR(100),
    dateOfRegistration DATE,
    role               VARCHAR(5),
    picture            MEDIUMBLOB
);

CREATE TABLE IF NOT EXISTS Groups
(
    group_ID           INT         NOT NULL PRIMARY KEY AUTO_INCREMENT,
    groupName          VARCHAR(45) NOT NULL,
    groupDescription   VARCHAR(300),
    dateOfRegistration DATE,
    picture            MEDIUMBLOB
);

CREATE TABLE IF NOT EXISTS Relationship
(
    accountOne_ID    INT        NOT NULL,
    accountTwo_ID    INT        NOT NULL,
    status           TINYINT(3) NOT NULL DEFAULT '0',
    actionAccount_ID INT        NOT NULL,
    CONSTRAINT relationship_pr PRIMARY KEY (accountOne_ID, accountTwo_ID),
    FOREIGN KEY (accountOne_ID) REFERENCES Accounts (account_ID) ON DELETE CASCADE,
    FOREIGN KEY (accountTwo_ID) REFERENCES Accounts (account_ID) ON DELETE CASCADE,
    FOREIGN KEY (actionAccount_ID) REFERENCES Accounts (account_ID) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS Phones
(
    phone_ID    INT         NOT NULL PRIMARY KEY AUTO_INCREMENT,
    account_ID  INT         NOT NULL,
    phoneNumber VARCHAR(15) NOT NULL,
    phoneType   VARCHAR(15) NOT NULL,
    FOREIGN KEY (account_ID) REFERENCES Accounts (account_ID) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS Members
(
    account_ID   INT        NOT NULL,
    group_ID     INT        NOT NULL,
    memberStatus TINYINT(3) NOT NULL,
    CONSTRAINT members_pr PRIMARY KEY (account_ID, group_ID),
    FOREIGN KEY (account_ID) REFERENCES Accounts (account_ID) ON DELETE CASCADE,
    FOREIGN KEY (group_ID) REFERENCES Groups (group_ID) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS Requests
(
    request_ID    INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    creator_ID    INT NOT NULL,
    recipient_ID  INT NOT NULL,
    requestType   VARCHAR(5),
    requestStatus TINYINT(4)
);

