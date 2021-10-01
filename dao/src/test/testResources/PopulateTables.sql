INSERT INTO accounts (surname, name, email, password, dateOfRegistration, role, picAttached) VALUES
('SurnameOne', 'NameOne', 'name@gmail.com', 'one', '20210313', 'ADMIN', '0'),
('SurnameTwo', 'NameTwo', 'surnameTwo@gmail.com', 'two', '20210308', 'USER', '0'),
('SurnameThee', 'NameThree', 'surnameThree@gmail.com', 'three', '20210308', 'USER', '0');
-- ('SurnameFour', 'NameFour', 'surnameFour@gmail.com', 'four', '20210308', 'USER', '0');
-- ('SurnameFive', 'NameFive', 'surnameFive@gmail.com', 'five', '20210308', '0'),
-- ('SurnameSix', 'NameSix', 'surnameSix@gmail.com', 'six', '20210308', '0'),
-- ('SurnameSeven', 'NameSeven', 'surnameSeven@gmail.com', 'seven', '20210308', '0'),
-- ('SurnameEight', 'NameEight', 'surnameEight@gmail.com', 'eight', '20210308', '0'),
-- ('SurnameNine', 'NameNine', 'surnameNine@gmail.com', 'nine', '20210308', '0');

INSERT INTO communities (commName, dateOfRegistration, picAttached) VALUES
('CommunityOne', '20210308', '0'),
('CommunityTwo', '20210308', '0'),
('CommunityThree', '20210308', '0');

INSERT INTO chat_rooms (interlocutorOneID, interlocutorTwoID) VALUES
('1', '2'),
('1', '3');

INSERT INTO members (accountID, commID, memberStatus) VALUES
('1', '1', '0'),
('2', '1', '2'),
('3', '2', '2'),
('2', '2', '1');

INSERT INTO messages (chatRoomID, sourceID, targetID, content, publicationDate) VALUES
('1', '1', '2', 'Text1', '2021-03-26 17:33:40'),
('1', '2', '1', 'Text2', '2021-03-26 17:33:40'),
('2', '3', '1', 'Text3', '2021-03-26 17:33:40'),
('2', '1', '3', 'Text4', '2021-03-26 17:33:40');

INSERT INTO phones (accountID, phoneNumber, phoneType) VALUES
('1', '12345', 'home'),
('1', '23456', 'work'),
('3', '34567', 'work'),
('2', '45678', 'work');

INSERT INTO posts (sourceID, accTargetID, commTargetID, postType, content, publicationDate) VALUES
('1', '2', null, 'account', 'Text1', '2021-03-26 17:33:40'),
('2', '3', null, 'account', 'Text2', '2021-03-26 17:33:40'),
('1', null, '2', 'comm', 'Text3', '2021-03-26 17:33:40'),
('1', null, '3', 'comm', 'Text4', '2021-03-26 17:33:40');

INSERT INTO requests (sourceID, accTargetID, commTargetID, requestType, requestStatus) VALUES
('1', '2', null, 'account', 0),
('2', '3', null, 'account', 1),
('3', '1', null, 'account', 0),
('1', null, '1', 'comm', 0),
('2', null, '1', 'comm', 0);

INSERT INTO relations (accountOneID, accountTwoID, relationStatus, actionAccountID) VALUES
('1', '2', '1', '2'),
('3', '1', '1', '1'),
('3', '2', '3', '3');

INSERT INTO acc_pictures (accountID, content) VALUES
('1', x'C9CBBBCCCEB9C8CABCCCCEB9C9CBBB');

INSERT INTO comm_pictures (commID, content) VALUES
('1', x'C9CBBBCCCEB9C8CABCCCCEB9C9CBBB');