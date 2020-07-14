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
            "INSERT INTO Relationship (accountOne_ID, accountTwo_ID, status, actionAccount_ID) VALUES (?, ?, ?, ?)";
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

    public boolean sendRequest(int accountOneID, int accountTwoID) {
        Connection connection = pool.getConnection();

        try (PreparedStatement statement = connection.prepareStatement(SEND_REQUEST)) {
            statement.setInt(4, accountOneID);
            statement.setInt(3, 0);

            if (accountOneID < accountTwoID) {
                statement.setInt(1, accountOneID);
                statement.setInt(2, accountTwoID);
            } else {
                statement.setInt(1, accountTwoID);
                statement.setInt(2, accountOneID);
            }
            statement.executeUpdate();
            connection.commit();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                connection.rollback();
                pool.close(connection);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return false;
    }

    public boolean acceptRequest(int acceptorID, int requesterID) {
        Connection connection = pool.getConnection();

        try (PreparedStatement statement = connection.prepareStatement(ACCEPT_REQUEST)) {
            statement.setInt(1, acceptorID);
            if (acceptorID > requesterID) {
                statement.setInt(3, acceptorID);
                statement.setInt(2, requesterID);
                System.out.println("UPDATE Relationship SET status = 1, action_account_ID = " + acceptorID +
                        " WHERE account_one_ID = " + requesterID + " AND account_two_ID = " + acceptorID);
            } else {
                statement.setInt(2, acceptorID);
                statement.setInt(3, requesterID);
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
                pool.close(connection);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return false;
    }

    public boolean declineRequest(int declinerID, int requesterID) {
        Connection connection = pool.getConnection();

        try (PreparedStatement statement = connection.prepareStatement(DECLINE_REQUEST)) {
            statement.setInt(1, declinerID);
            if (declinerID > requesterID) {
                statement.setInt(3, declinerID);
                statement.setInt(2, requesterID);
            } else {
                statement.setInt(3, requesterID);
                statement.setInt(2, declinerID);
            }
            statement.executeUpdate();
            connection.commit();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                connection.rollback();
                pool.close(connection);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return false;
    }

    public boolean breakRelationship(int breakerID, int friendID) {
        Connection connection = pool.getConnection();

        try (PreparedStatement statement = connection.prepareStatement(BREAK_RELATIONSHIP)) {
            statement.setInt(1, breakerID);
            if (breakerID > friendID) {
                statement.setInt(2, friendID);
                statement.setInt(3, breakerID);
            } else {
                statement.setInt(2, breakerID);
                statement.setInt(3, friendID);
            }
            statement.executeUpdate();
            connection.commit();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                connection.rollback();
                pool.close(connection);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return false;
    }

    public boolean blockAccount(int blockerID, int friendID) {
        Connection connection = pool.getConnection();

        try (PreparedStatement statement = connection.prepareStatement(BLOCK_ACCOUNT)) {
            statement.setInt(1, blockerID);
            if (blockerID > friendID) {
                statement.setInt(2, friendID);
                statement.setInt(3, blockerID);
            } else {
                statement.setInt(2, blockerID);
                statement.setInt(3, friendID);
            }
            statement.executeUpdate();
            connection.commit();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                connection.rollback();
                pool.close(connection);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return false;
    }

    public List<Account> getFriendsList(Account account) {
        int accountID = account.getAccountID();
        List<Account> friendsList = new ArrayList<>();
        Connection connection = pool.getConnection();

        try (PreparedStatement statement = connection.prepareStatement(GET_FRIENDS_ID_LIST)) {
            statement.setInt(1, accountID);
            statement.setInt(2, accountID);
            try (ResultSet resultSet = statement.executeQuery()) {
                AbstractDao<Account> accountDao = new AccountDao();
                while (resultSet.next()) {
                    int accountOneID = resultSet.getInt(1);
                    if (accountOneID != accountID) {
                        friendsList.add(accountDao.getById(accountOneID));
                    }
                    int accountTwoID = resultSet.getInt(2);
                    if (accountTwoID != accountID) {
                        friendsList.add(accountDao.getById(accountTwoID));
                    }
                }
                return friendsList;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            pool.close(connection);
        }

        return null;
    }

    public String getRelationStringById(int accountID) {
        Connection connection = pool.getConnection();

        try (PreparedStatement statement = connection.prepareStatement(SELECT_BY_ID)) {
            StringBuilder stringBuilder = new StringBuilder();
            statement.setInt(1, accountID);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    for (int i = 1; i <= 4; i++) {
                        stringBuilder.append(resultSet.getInt(i));
                        if (i != 4) {
                            stringBuilder.append("_");
                        }
                    }
                }
            }
            return stringBuilder.toString();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return null;
    }
}