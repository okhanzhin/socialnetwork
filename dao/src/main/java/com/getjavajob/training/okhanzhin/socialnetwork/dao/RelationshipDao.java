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
            "INSERT INTO Relationship (account_one_ID, account_two_ID, status, action_account_ID) VALUES (?, ?, ?, ?)";
    private static final String ACCEPT_REQUEST =
            "UPDATE Relationship SET status = 1, action_account_ID = ? WHERE account_one_ID = ? AND account_two_ID = ?";
    private static final String DECLINE_REQUEST =
            "UPDATE Relationship SET status = 2, action_account_ID = ? WHERE account_one_ID = ? AND account_two_ID = ?";
    private static final String BREAK_RELATIONSHIP =
            "UPDATE Relationship SET status = 0, action_account_ID = ? WHERE account_one_ID = ? AND account_two_ID = ?";
    private static final String BLOCK_ACCOUNT =
            "UPDATE Relationship SET status = 3, action_account_ID = ? WHERE account_one_ID = ? AND account_two_ID = ?";
    private static final String GET_FRIENDS_ID_LIST =
            "SELECT * FROM Relationship WHERE (account_one_ID = ? OR account_two_id = ?) AND status = 1";
    private static final String SELECT_BY_ID = "SELECT * FROM Relationship WHERE account_one_ID = ?";

    private ConnectionPool pool = new ConnectionPool();

    public boolean sendRequest(int account_one_id, int account_two_id) {
        Connection connection = pool.getConnection();

        try (PreparedStatement statement = connection.prepareStatement(SEND_REQUEST)) {
            statement.setInt(4, account_one_id);
            statement.setInt(3, 0);

            if (account_one_id < account_two_id) {
                statement.setInt(1, account_one_id);
                statement.setInt(2, account_two_id);
            } else {
                statement.setInt(1, account_two_id);
                statement.setInt(2, account_one_id);
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

    public boolean acceptRequest(int acceptor_id, int requester_id) {
        Connection connection = pool.getConnection();

        try (PreparedStatement statement = connection.prepareStatement(ACCEPT_REQUEST)) {
            statement.setInt(1, acceptor_id);
            if (acceptor_id > requester_id) {
                statement.setInt(2, acceptor_id);
                statement.setInt(3, requester_id);
            } else {
                statement.setInt(3, requester_id);
                statement.setInt(2, acceptor_id);
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

    public boolean declineRequest(int decliner_id, int requester_id) {
        Connection connection = pool.getConnection();

        try (PreparedStatement statement = connection.prepareStatement(DECLINE_REQUEST)) {
            statement.setInt(1, decliner_id);
            if (decliner_id > requester_id) {
                statement.setInt(2, decliner_id);
                statement.setInt(3, requester_id);
            } else {
                statement.setInt(3, requester_id);
                statement.setInt(2, decliner_id);
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

    public boolean breakRelationship(int breaker_id, int friend_id) {
        Connection connection = pool.getConnection();

        try (PreparedStatement statement = connection.prepareStatement(BREAK_RELATIONSHIP)) {
            statement.setInt(1, breaker_id);
            if (breaker_id > friend_id) {
                statement.setInt(2, friend_id);
                statement.setInt(3, breaker_id);
            } else {
                statement.setInt(2, breaker_id);
                statement.setInt(3, friend_id);
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

    public boolean blockAccount(int blocker_id, int friend_id) {
        Connection connection = pool.getConnection();

        try (PreparedStatement statement = connection.prepareStatement(BLOCK_ACCOUNT)) {
            statement.setInt(1, blocker_id);
            if (blocker_id > friend_id) {
                statement.setInt(2, friend_id);
                statement.setInt(3, blocker_id);
            } else {
                statement.setInt(2, blocker_id);
                statement.setInt(3, friend_id);
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
        int account_id = account.getAccount_ID();
        List<Account> friendsList = new ArrayList<>();
        Connection connection = pool.getConnection();

        try (PreparedStatement statement = connection.prepareStatement(GET_FRIENDS_ID_LIST)) {
            statement.setInt(1, account_id);
            statement.setInt(2, account_id);
            try (ResultSet resultSet = statement.executeQuery()) {
                AbstractDao<Account> accountDao = new AccountDao();
                while (resultSet.next()) {
                    int account_one_id = resultSet.getInt(1);
                    if (account_one_id != account_id) {
                        friendsList.add(accountDao.getById(account_one_id));
                    }
                    int account_two_id = resultSet.getInt(2);
                    if (account_two_id != account_id) {
                        friendsList.add(accountDao.getById(account_two_id));
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

    public String getRelationStringById(int account_one_id) {
        Connection connection = pool.getConnection();

        try (PreparedStatement statement = connection.prepareStatement(SELECT_BY_ID)) {
            StringBuilder stringBuilder = new StringBuilder();
            statement.setInt(1, account_one_id);
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