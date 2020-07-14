package com.getjavajob.training.okhanzhin.socialnetwork.dao;

import com.getjavajob.training.okhanzhin.socialnetwork.domain.Account;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class AccountDao extends AbstractDao<Account> {
    private static final String GET_ACCOUNT_ID_BY_EMAIL = "SELECT account_ID FROM Accounts WHERE email = ?";
    private static final String SELECT_ALL = "SELECT * FROM Accounts";
    private static final String SELECT_BY_ACCOUNT_ID = SELECT_ALL + " WHERE account_ID = ?";
    private static final String DELETE_BY_ID = "DELETE FROM Accounts WHERE account_ID = ?";
    private static final String INSERT_BY_FULL_NAME_EMAIL_PASSWORD = "INSERT INTO Accounts " +
            "(surname, name, email, password) " +
            "VALUES (?, ?, ?, ?)";
    private static final String UPDATE_ACCOUNT_BY_ACCOUNT_ID = "UPDATE Accounts SET middlename = ?, " +
            "dateOfBirth = ?, " +
            "homePhone = ?, " +
            "workPhone = ?, " +
            "skype = ?, " +
            "icq = ?, " +
            "homeAddress = ?, " +
            "workAddress = ?, " +
            "addInfo = ? " +
            "WHERE account_ID = ?";

    private ConnectionPool pool = ConnectionPool.getPool();

    public AccountDao() {
    }

    @Override
    public Account create(Account account) {
        Connection connection = pool.getConnection();

        if (connection != null) {
            try (PreparedStatement createStatement = connection.prepareStatement(INSERT_BY_FULL_NAME_EMAIL_PASSWORD)) {
                createStatement.setString(1, account.getSurname());
                createStatement.setString(2, account.getName());
                createStatement.setString(3, account.getEmail());
                createStatement.setString(4, account.getPassword());
                createStatement.executeUpdate();
                connection.commit();
                try (PreparedStatement getIDStatement = connection.prepareStatement(GET_ACCOUNT_ID_BY_EMAIL)) {
                    getIDStatement.setString(1, account.getEmail());
                    try (ResultSet resultSet = getIDStatement.executeQuery()) {
                        while (resultSet.next()) {
                            account.setAccountID(resultSet.getInt("account_ID"));

                        }
                        return account;
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
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
        }

        return null;
    }

    @Override
    public Account getById(int accountID) {
        Account account = null;
        Connection connection = pool.getConnection();
        if (connection != null) {
            try (PreparedStatement selectStatement = connection.prepareStatement(SELECT_BY_ACCOUNT_ID)) {
                selectStatement.setInt(1, accountID);
                try (ResultSet resultSet = selectStatement.executeQuery()) {
                    while (resultSet.next()) {
                        account = createAccountFromResultSet(resultSet);
                    }
                    return account;
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                pool.close(connection);
            }
        }

        return null;
    }

    private Account createAccountFromResultSet(ResultSet resultSet) throws SQLException {
        Account account = new Account();

        account.setAccountID(resultSet.getInt("account_ID"));
        account.setSurname(resultSet.getString("surname"));

        if (resultSet.getString("middlename") != null) {
            account.setMiddlename(resultSet.getString("middlename"));
        } else {
            account.setMiddlename(null);
        }
        account.setName(resultSet.getString("name"));
        account.setEmail(resultSet.getString("email"));
        account.setPassword(resultSet.getString("password"));
        if ((resultSet.getDate("dateOfBirth") != null)) {
            account.setDateOfBirth((resultSet.getDate("dateOfBirth").toLocalDate()));
        } else {
            account.setDateOfBirth(null);
        }
        if (resultSet.getString("homePhone") != null) {
            account.setHomePhone(resultSet.getString("homePhone"));
        } else {
            account.setHomePhone(null);
        }
        if (resultSet.getString("workPhone") != null) {
            account.setWorkPhone(resultSet.getString("workPhone"));
        } else {
            account.setWorkPhone(null);
        }
        if (resultSet.getString("skype") != null) {
            account.setSkype(resultSet.getString("skype"));
        } else {
            account.setSkype(null);
        }
        if (resultSet.getString("icq") != null) {
            account.setIcq(resultSet.getString("icq"));
        } else {
            account.setIcq(null);
        }
        if (resultSet.getString("homeAddress") != null) {
            account.setHomeAddress(resultSet.getString("homeAddress"));
        } else {
            account.setHomeAddress(null);
        }
        if (resultSet.getString("workAddress") != null) {
            account.setWorkAddress(resultSet.getString("workAddress"));
        } else {
            account.setWorkAddress(null);
        }
        if (resultSet.getString("addInfo") != null) {
            account.setAddInfo(resultSet.getString("addInfo"));
        } else {
            account.setAddInfo(null);
        }

        return account;
    }

    @Override
    public void update(Account account) {
        Connection connection = pool.getConnection();
        if (connection != null) {
            try (PreparedStatement prepStatement = connection.prepareStatement(UPDATE_ACCOUNT_BY_ACCOUNT_ID)) {
                prepStatement.setInt(10, account.getAccountID());

                if (account.getMiddlename() != null) {
                    prepStatement.setString(1, account.getMiddlename());
                } else {
                    prepStatement.setString(1, null);
                }
                if (account.getDateOfBirth() != null) {
                    prepStatement.setDate(2, Date.valueOf(account.getDateOfBirth()));
                } else {
                    prepStatement.setDate(2, null);
                }
                if (account.getHomePhone() != null) {
                    prepStatement.setString(3, account.getHomePhone());
                } else {
                    prepStatement.setString(3, null);
                }
                if (account.getWorkPhone() != null) {
                    prepStatement.setString(4, account.getWorkPhone());
                } else {
                    prepStatement.setString(4, null);
                }
                if (account.getSkype() != null) {
                    prepStatement.setString(5, account.getSkype());
                } else {
                    prepStatement.setString(5, null);
                }
                if (account.getIcq() != null) {
                    prepStatement.setString(6, account.getIcq());
                } else {
                    prepStatement.setString(6, null);
                }
                if (account.getHomeAddress() != null) {
                    prepStatement.setString(7, account.getHomeAddress());
                } else {
                    prepStatement.setString(7, null);
                }
                if (account.getWorkAddress() != null) {
                    prepStatement.setString(8, account.getWorkAddress());
                } else {
                    prepStatement.setString(8, null);
                }
                if (account.getAddInfo() != null) {
                    prepStatement.setString(9, account.getAddInfo());
                } else {
                    prepStatement.setString(9, null);
                }

                prepStatement.executeUpdate();
                connection.commit();
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
        }
    }

    @Override
    public void delete(Account account) {
        Connection connection = pool.getConnection();

        if (connection != null) {
            try (PreparedStatement deleteStatement = connection.prepareStatement(DELETE_BY_ID)) {
                deleteStatement.setInt(1, account.getAccountID());
                deleteStatement.executeUpdate();
                connection.commit();
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
        }
    }

    @Override
    public List<Account> getAll() {
        List<Account> accountsList = new ArrayList<>();
        Connection connection = pool.getConnection();

        if (connection != null) {
            try (Statement getAllStatement = connection.createStatement()) {
                try (ResultSet resultSet = getAllStatement.executeQuery(SELECT_ALL)) {
                    Account account;
                    while (resultSet.next()) {
                        account = createAccountFromResultSet(resultSet);
                        accountsList.add(account);
                    }
                    return accountsList;
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                pool.close(connection);
            }
        }

        return new ArrayList<>();
    }
}