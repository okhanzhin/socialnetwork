package com.getjavajob.training.okhanzhin.socialnetwork.dao;

import com.getjavajob.training.okhanzhin.socialnetwork.domain.Account;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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

    private ConnectionPool pool = ConnectionPool.getPool();

    public boolean createRelation(long accountOneID, long accountTwoID) {
        Connection connection = pool.getConnection();

        try (PreparedStatement statement = connection.prepareStatement(SEND_REQUEST)) {
            statement.setLong(3, accountOneID);

            if (accountOneID < accountTwoID) {
                statement.setLong(1, accountOneID);
                statement.setLong(2, accountTwoID);
            } else {
                statement.setLong(1, accountTwoID);
                statement.setLong(2, accountOneID);
            }
            statement.executeUpdate();
            connection.commit();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                connection.rollback();
                pool.returnConnection(connection);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return false;
    }

    public boolean acceptRelation(long acceptorID, long requesterID) {
        Connection connection = pool.getConnection();

        try (PreparedStatement statement = connection.prepareStatement(ACCEPT_REQUEST)) {
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
            statement.executeUpdate();
            connection.commit();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                connection.rollback();
                pool.returnConnection(connection);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return false;
    }

    public boolean declineRelation(long declinerID, long requesterID) {
        Connection connection = pool.getConnection();

        try (PreparedStatement statement = connection.prepareStatement(DECLINE_REQUEST)) {
            statement.setLong(1, declinerID);
            if (declinerID > requesterID) {
                statement.setLong(3, declinerID);
                statement.setLong(2, requesterID);
            } else {
                statement.setLong(3, requesterID);
                statement.setLong(2, declinerID);
            }
            statement.executeUpdate();
            connection.commit();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                connection.rollback();
                pool.returnConnection(connection);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return false;
    }

    public boolean breakRelation(long breakerID, long friendID) {
        Connection connection = pool.getConnection();

        try (PreparedStatement statement = connection.prepareStatement(BREAK_RELATIONSHIP)) {
            statement.setLong(1, breakerID);
            if (breakerID > friendID) {
                statement.setLong(2, friendID);
                statement.setLong(3, breakerID);
            } else {
                statement.setLong(2, breakerID);
                statement.setLong(3, friendID);
            }
            statement.executeUpdate();
            connection.commit();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                connection.rollback();
                pool.returnConnection(connection);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return false;
    }

    public boolean blockAccount(long blockerID, long friendID) {
        Connection connection = pool.getConnection();

        try (PreparedStatement statement = connection.prepareStatement(BLOCK_ACCOUNT)) {
            statement.setLong(1, blockerID);
            if (blockerID > friendID) {
                statement.setLong(2, friendID);
                statement.setLong(3, blockerID);
            } else {
                statement.setLong(2, blockerID);
                statement.setLong(3, friendID);
            }
            statement.executeUpdate();
            connection.commit();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                connection.rollback();
                pool.returnConnection(connection);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return false;
    }

    public List<Account> getFriendsList(Account account) {
        long accountID = account.getAccountID();
        List<Account> friendsList = new ArrayList<>();
        Connection connection = pool.getConnection();

        try (PreparedStatement statement = connection.prepareStatement(GET_FRIENDS_ID_LIST)) {
            statement.setLong(1, accountID);
            statement.setLong(2, accountID);
            try (ResultSet resultSet = statement.executeQuery()) {
                AbstractDao<Account> accountDao = new AccountDao();
                while (resultSet.next()) {
                    long accountOneID = resultSet.getLong(1);
                    if (accountOneID != accountID) {
                        friendsList.add(accountDao.getById(accountOneID));
                    }
                    int accountTwoID = resultSet.getInt(2);
                    if (accountTwoID != accountID) {
                        friendsList.add(accountDao.getById(accountTwoID));
                    }
                }

            }
        } catch (SQLException e) {
            throw new DaoException("Can't create friends list from ResultSet.", e);
        } finally {
            pool.returnConnection(connection);
        }

        return friendsList;
    }

    public String getRelationStringById(long accountID) {
        Connection connection = pool.getConnection();

        try (PreparedStatement statement = connection.prepareStatement(SELECT_BY_ID)) {
            StringBuilder stringBuilder = new StringBuilder();
            statement.setLong(1, accountID);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    for (int i = 1; i <= 4; i++) {
                        stringBuilder.append(resultSet.getInt(i));
                        if (i != 4) {
                            stringBuilder.append("_");
                        }
                    }
                }
            } catch (SQLException e) {
                throw new DaoException("Can't get relation from ResultSet.", e);
            }
            return stringBuilder.toString();
        } catch (SQLException e) {
            throw new DaoException("Can't return relation row as string by ID.", e);
        } finally {
            pool.returnConnection(connection);
        }
    }
}