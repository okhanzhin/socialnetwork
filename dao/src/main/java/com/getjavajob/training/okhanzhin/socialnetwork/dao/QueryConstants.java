package com.getjavajob.training.okhanzhin.socialnetwork.dao;

public final class QueryConstants {
    public static final String GET_ACCOUNT_BY_ID = "SELECT a FROM Account a WHERE a.accountID = :id";
    public static final String GET_ACCOUNT_BY_EMAIL = "SELECT a FROM Account a WHERE a.email = :email";
    public static final String GET_ACCOUNTS_LIST_BY_COMMUNITY_ID = "SELECT a FROM Account a JOIN Member m " +
            "ON a.accountID = m.account.accountID WHERE m.community = :community";
    public static final String GET_ALL_EMAILS = "SELECT a.email FROM Account a";
    public static final String GET_COMMUNITY_LIST_BY_ACCOUNT_ID = "SELECT c FROM Community c JOIN Member m " +
            "ON c.commID = m.community.commID WHERE m.account = :account";
    public static final String GET_TODAY_BIRTHDAY_ACCOUNTS = "SELECT a FROM Account a WHERE a.dateOfBirth = :dateOfBirth";
    public static final String IS_ACCOUNT_AVAILABLE = "SELECT a.enabled FROM Account a WHERE a.email= :email";
    public static final String UPDATE_PASSWORD_BY_ACCOUNT_ID = "UPDATE Account a SET a.password = :password " +
            "WHERE a.accountID = :accountID";

    public static final String DELETE_MEMBER = "DELETE FROM Member m WHERE m.account = :account " +
            "AND m.community = :community";
    public static final String GET_OWNER_BY_COMMUNITY_ID = "SELECT m FROM Member m " +
            "WHERE m.community.commID = :commID AND m.memberStatus = 0";
    public static final String GET_MEMBERS_BY_COMMUNITY_ID = "SELECT m FROM Member m WHERE m.community.commID = :commID";
    public static final String GET_COMMUNITY_MODERATORS = "SELECT m FROM Member m WHERE m.community.commID= :commID " +
            "AND m.memberStatus = 1";

    public static final String GET_CHAT_ROOM_BY_ID = "SELECT c FROM ChatRoom c WHERE c.chatRoomID = :id";
    public static final String GET_CHAT_ROOM_BY_INTERLOCUTORS = "SELECT c FROM ChatRoom c " +
            "WHERE (c.interlocutorOne = :interlocutorOne AND c.interlocutorTwo = :interlocutorTwo) OR " +
            "(c.interlocutorOne = :interlocutorTwo AND c.interlocutorTwo = :interlocutorOne)";
    public static final String GET_CHAT_ROOMS_LIST_BY_ACCOUNT_ID = "SELECT DISTINCT c FROM ChatRoom c JOIN FETCH c.messages m " +
            "WHERE c.interlocutorOne = :account OR c.interlocutorTwo = :account";

    public static final String GET_MESSAGE_SEQUENCE =
            "SELECT m FROM Message m WHERE (m.source.accountID = :accountID1 AND m.target.accountID = :accountID2) " +
                    "OR (m.source.accountID = :accountID2 AND m.target.accountID = :accountID1)";
    public static final String GET_INTERLOCUTORS_IDENTIFIERS = "SELECT sourceID FROM messages " +
            "WHERE targetID = ?1 UNION SELECT targetID FROM messages " +
            "WHERE sourceID = ?2";

    public static final String GET_ACCOUNT_PICTURE = "SELECT p FROM AccountPicture p JOIN FETCH p.account a " +
            "WHERE p.account = :account";
    public static final String GET_COMMUNITY_PICTURE = "SELECT p FROM CommunityPicture p JOIN FETCH p.community c " +
            "WHERE p.community = :community";

    public static final String GET_ACCOUNT_POSTS =
            "SELECT p FROM AccountPost p JOIN FETCH p.source s " +
                    "WHERE p.accountTarget.accountID = :targetID ORDER BY p.publicationDate DESC";
    public static final String GET_COMMUNITY_POSTS =
            "SELECT p FROM CommunityPost p JOIN FETCH p.source s " +
                    "WHERE p.communityTarget.commID = :targetID ORDER BY p.publicationDate DESC";

    public static final String GET_RELATION_BY_ACCOUNTS = "SELECT r FROM Relationship r WHERE " +
            "(r.accountOne = :accountOne AND r.accountTwo = :accountTwo) OR " +
            "(r.accountOne = :accountTwo AND r.accountTwo = :accountOne)";
    public static final String GET_ACCEPTED_RELATIONS =
            "SELECT r FROM Relationship r JOIN FETCH r.accountOne a1 JOIN FETCH r.accountTwo a2 " +
                    "WHERE (r.accountOne = :account OR r.accountTwo = :account) AND r.relationStatus = 1";
    public static final String GET_BLOCKED_RELATIONS =
            "SELECT r FROM Relationship r JOIN FETCH r.accountOne a1 JOIN FETCH r.accountTwo a2 " +
                    "WHERE (r.accountOne = :account OR r.accountTwo = :account) AND r.relationStatus = 3";

    public static final String GET_ACCOUNT_REQUEST = "SELECT r FROM AccountRequest r WHERE r.source = :source " +
            "AND r.accountTarget = :accTarget";
    public static final String GET_COMMUNITY_REQUEST = "SELECT r FROM CommunityRequest r WHERE r.source = :source " +
            "AND r.communityTarget = :commTarget";
    public static final String GET_UNCONFIRMED_GROUP_REQUESTS =
            "SELECT r FROM CommunityRequest r JOIN FETCH r.source s JOIN FETCH r.communityTarget ct " +
                    "WHERE r.communityTarget = :community AND r.requestStatus = 0";
    public static final String GET_PENDING_REQUESTS_BY_ACCOUNT_ID =
            "SELECT r FROM AccountRequest r JOIN FETCH r.source s JOIN FETCH r.accountTarget at " +
                    "WHERE r.accountTarget = :account AND r.requestStatus = 0";
    public static final String GET_OUTGOING_REQUESTS_BY_ACCOUNT_ID =
            "SELECT r FROM AccountRequest r JOIN FETCH r.source s JOIN FETCH r.accountTarget at " +
                    "WHERE r.source = :account AND r.requestStatus = 0";
    public static final String GET_ACCEPTED_REQUESTS_BY_ACCOUNT_ID =
            "SELECT r FROM AccountRequest r JOIN FETCH r.source s JOIN FETCH r.accountTarget at " +
                    "WHERE (r.source = :account OR r.accountTarget = :account) AND r.requestStatus = 1";
}