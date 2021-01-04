package com.getjavajob.training.okhanzhin.socialnetwork.dao;

import com.getjavajob.training.okhanzhin.socialnetwork.domain.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

@Repository
public class RelationshipDao {
    private static final String SEND_REQUEST =
            "INSERT INTO Relationship (accountOne_ID, accountTwo_ID, status, actionAccount_ID) VALUES (?, ?, 0, ?)";
    private static final String ACCEPT_REQUEST =
            "UPDATE Relationship SET status = 1, actionAccount_ID = ? WHERE accountOne_ID = ? AND accountTwo_ID = ?";
    private static final String DECLINE_REQUEST =
            "UPDATE Relationship SET status = 2, actionAccount_ID = ? WHERE accountOne_ID = ? AND accountTwo_ID = ?";
    private static final String BREAK_RELATIONSHIP =
            "UPDATE Relationship SET status = 0, actionAccount_ID = ? WHERE accountOne_ID = ? AND accountTwo_ID = ?";
    private static final String BLOCK_ACCOUNT =
            "UPDATE Relationship SET status = 3, actionAccount_ID = ? WHERE accountOne_ID = ? AND accountTwo_ID = ?";
    private static final String GET_FRIENDS_ID_LIST =
            "SELECT * FROM Relationship WHERE (accountOne_ID = ? OR accountTwo_ID = ?) AND status = 1";
    private static final String SELECT_BY_ID = "SELECT * FROM Relationship WHERE accountOne_ID = ?";

    private final JdbcTemplate jdbcTemplate;
    private AccountDao accountDao;

    @Autowired
    public RelationshipDao(JdbcTemplate jdbcTemplate, AccountDao accountDao) {
        this.jdbcTemplate = jdbcTemplate;
        this.accountDao = accountDao;
    }

    public void createRelation(long accountOneID, long accountTwoID) {
        this.jdbcTemplate.update(connection -> {
            PreparedStatement statement = connection.prepareStatement(SEND_REQUEST);
            statement.setLong(3, accountOneID);

            if (accountOneID < accountTwoID) {
                statement.setLong(1, accountOneID);
                statement.setLong(2, accountTwoID);
            } else {
                statement.setLong(1, accountTwoID);
                statement.setLong(2, accountOneID);
            }
            return statement;
        });
    }

    public void acceptRelation(long acceptorID, long requesterID) {
        this.jdbcTemplate.update(connection -> {
            PreparedStatement statement = connection.prepareStatement(ACCEPT_REQUEST);
            statement.setLong(1, acceptorID);
            if (acceptorID > requesterID) {
                statement.setLong(3, acceptorID);
                statement.setLong(2, requesterID);
                System.out.println("UPDATE Relationship SET status = 1, action_account_ID = " + acceptorID +
                        " WHERE account_one_ID = " + requesterID + " AND account_two_ID = " + acceptorID);
            } else {
                statement.setLong(2, acceptorID);
                statement.setLong(3, requesterID);
                System.out.println("UPDATE Relationship SET status = 1, action_account_ID = " + acceptorID +
                        " WHERE account_one_ID = " + acceptorID + " AND account_two_ID = " + requesterID);
            }
            return statement;
        });
    }

    public void declineRelation(long declinerID, long requesterID) {
        this.jdbcTemplate.update(connection -> {
            PreparedStatement statement = connection.prepareStatement(DECLINE_REQUEST);
            statement.setLong(1, declinerID);
            if (declinerID > requesterID) {
                statement.setLong(3, declinerID);
                statement.setLong(2, requesterID);
                System.out.println("UPDATE Relationship SET status = 1, action_account_ID = " + declinerID +
                        " WHERE account_one_ID = " + requesterID + " AND account_two_ID = " + declinerID);
            } else {
                statement.setLong(2, declinerID);
                statement.setLong(3, requesterID);
                System.out.println("UPDATE Relationship SET status = 1, action_account_ID = " + declinerID +
                        " WHERE account_one_ID = " + declinerID + " AND account_two_ID = " + requesterID);
            }
            return statement;
        });
    }

    public void breakRelation(long breakerID, long friendID) {
        this.jdbcTemplate.update(connection -> {
            PreparedStatement statement = connection.prepareStatement(BREAK_RELATIONSHIP);
            statement.setLong(1, breakerID);
            if (breakerID > friendID) {
                statement.setLong(3, breakerID);
                statement.setLong(2, friendID);
                System.out.println("UPDATE Relationship SET status = 1, action_account_ID = " + breakerID +
                        " WHERE account_one_ID = " + friendID + " AND account_two_ID = " + breakerID);
            } else {
                statement.setLong(2, breakerID);
                statement.setLong(3, friendID);
                System.out.println("UPDATE Relationship SET status = 1, action_account_ID = " + breakerID +
                        " WHERE account_one_ID = " + breakerID + " AND account_two_ID = " + friendID);
            }
            return statement;
        });
    }

    public void blockAccount(long blockerID, long friendID) {
        this.jdbcTemplate.update(connection -> {
            PreparedStatement statement = connection.prepareStatement(BLOCK_ACCOUNT);
            statement.setLong(1, blockerID);
            if (blockerID > friendID) {
                statement.setLong(3, blockerID);
                statement.setLong(2, friendID);
                System.out.println("UPDATE Relationship SET status = 1, action_account_ID = " + blockerID +
                        " WHERE account_one_ID = " + friendID + " AND account_two_ID = " + blockerID);
            } else {
                statement.setLong(2, blockerID);
                statement.setLong(3, friendID);
                System.out.println("UPDATE Relationship SET status = 1, action_account_ID = " + blockerID +
                        " WHERE account_one_ID = " + blockerID + " AND account_two_ID = " + friendID);
            }
            return statement;
        });
    }

    public List<Account> getFriendsList(Account account) {
        return this.jdbcTemplate.query(GET_FRIENDS_ID_LIST, new Object[]{account.getAccountID(), account.getAccountID()},
                                        getListExtractor(account));
    }

    private ResultSetExtractor<List<Account>> getListExtractor(Account account) {
        return resultSet -> {
            List<Account> accounts = new ArrayList<>();

            while (resultSet.next()) {
                long accountOneID = resultSet.getInt("accountOne_ID");
                System.out.println(accountOneID);
                if (accountOneID != account.getAccountID()) {
                    accounts.add(accountDao.getById(accountOneID));
                }
                int accountTwoID = resultSet.getInt("accountTwo_ID");
                if (accountTwoID != account.getAccountID()) {
                    accounts.add(accountDao.getById(accountTwoID));
                }
            }

            return accounts;
        };
    }

    public String getRelationStringById(long accountID) {
        return this.jdbcTemplate.query(SELECT_BY_ID, new Object[]{accountID}, resultSet -> {
            StringBuilder stringBuilder = new StringBuilder();
            while (resultSet.next()) {
                for (int i = 1; i <= 4; i++) {
                    stringBuilder.append(resultSet.getInt(i));
                    if (i != 4) {
                        stringBuilder.append("_");
                    }
                }
            }
            return stringBuilder.toString();
        });
    }
}