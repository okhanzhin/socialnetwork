CREATE TABLE IF NOT EXISTS accounts
(
    accountID          INT         NOT NULL PRIMARY KEY AUTO_INCREMENT,
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
    enabled            TINYINT(1)  NOT NULL DEFAULT '1',
    picAttached        TINYINT(1)  NOT NULL
);

CREATE TABLE IF NOT EXISTS acc_pictures
(
    picID     INT        NOT NULL PRIMARY KEY AUTO_INCREMENT,
    accountID INT        NOT NULL,
    content   MEDIUMBLOB NOT NULL,
    FOREIGN KEY (accountID) REFERENCES accounts (accountID) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS communities
(
    commID             INT         NOT NULL PRIMARY KEY AUTO_INCREMENT,
    commName           VARCHAR(45) NOT NULL UNIQUE,
    commDescription    VARCHAR(300),
    dateOfRegistration DATE,
    picAttached        TINYINT(1)  NOT NULL
);

CREATE TABLE IF NOT EXISTS comm_pictures
(
    picID   INT        NOT NULL PRIMARY KEY AUTO_INCREMENT,
    commID  INT        NOT NULL,
    content MEDIUMBLOB NOT NULL,
    FOREIGN KEY (commID) REFERENCES communities (commID) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS chat_rooms
(
    chatRoomID        INT        NOT NULL PRIMARY KEY AUTO_INCREMENT,
    interlocutorOneID INT        NOT NULL,
    interlocutorTwoID INT        NOT NULL,
    FOREIGN KEY (interlocutorOneID) REFERENCES accounts (accountID) ON DELETE CASCADE,
    FOREIGN KEY (interlocutorTwoID) REFERENCES accounts (accountID) ON DELETE CASCADE
);


CREATE TABLE IF NOT EXISTS relations
(
    relationID      INT        NOT NULL PRIMARY KEY AUTO_INCREMENT,
    accountOneID    INT        NOT NULL,
    accountTwoID    INT        NOT NULL,
    relationStatus  TINYINT(3) NOT NULL DEFAULT '0',
    actionAccountID INT        NOT NULL,
    FOREIGN KEY (accountOneID) REFERENCES accounts (accountID) ON DELETE CASCADE,
    FOREIGN KEY (accountTwoID) REFERENCES accounts (accountID) ON DELETE CASCADE,
    FOREIGN KEY (actionAccountID) REFERENCES accounts (accountID) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS phones
(
    phoneID     INT         NOT NULL PRIMARY KEY AUTO_INCREMENT,
    accountID   INT         NOT NULL,
    phoneNumber VARCHAR(15) NOT NULL,
    phoneType   VARCHAR(15) NOT NULL,
    FOREIGN KEY (accountID) REFERENCES accounts (accountID) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS members
(
    accountID    INT        NOT NULL,
    commID       INT        NOT NULL,
    memberStatus TINYINT(3) NOT NULL,
    CONSTRAINT members_pr PRIMARY KEY (accountID, commID),
    FOREIGN KEY (accountID) REFERENCES accounts (accountID) ON DELETE CASCADE,
    FOREIGN KEY (commID) REFERENCES communities (commID) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS requests
(
    requestID     INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    sourceID      INT NOT NULL,
    accTargetID   INT,
    commTargetID  INT,
    requestType   VARCHAR(7),
    requestStatus TINYINT(4),
    FOREIGN KEY (sourceID) REFERENCES accounts (accountID) ON DELETE CASCADE,
    FOREIGN KEY (accTargetID) REFERENCES accounts (accountID) ON DELETE CASCADE,
    FOREIGN KEY (commTargetID) REFERENCES communities (commID) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS messages
(
    messageID       INT  NOT NULL PRIMARY KEY AUTO_INCREMENT,
    chatRoomID      INT  NOT NULL,
    sourceID        INT  NOT NULL,
    targetID        INT  NOT NULL,
    content         TEXT NOT NULL,
    publicationDate TIMESTAMP,
    FOREIGN KEY (chatRoomID) REFERENCES chat_rooms (chatRoomID) ON DELETE CASCADE,
    FOREIGN KEY (sourceID) REFERENCES accounts (accountID) ON DELETE CASCADE,
    FOREIGN KEY (targetID) REFERENCES accounts (accountID) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS posts
(
    postID          INT       NOT NULL PRIMARY KEY AUTO_INCREMENT,
    sourceID        INT       NOT NULL,
    accTargetID     INT,
    commTargetID    INT,
    postType        VARCHAR(7),
    content         TEXT      NOT NULL,
    publicationDate TIMESTAMP NOT NULL,
    FOREIGN KEY (sourceID) REFERENCES accounts (accountID) ON DELETE CASCADE,
    FOREIGN KEY (accTargetID) REFERENCES accounts (accountID) ON DELETE CASCADE,
    FOREIGN KEY (commTargetID) REFERENCES communities (commID) ON DELETE CASCADE
);