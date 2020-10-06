package com.getjavajob.training.okhanzhin.socialnetwork.dao;

import com.getjavajob.training.okhanzhin.socialnetwork.domain.Account;
import com.getjavajob.training.okhanzhin.socialnetwork.domain.Group;

import javax.sql.rowset.serial.SerialBlob;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class AccountDao extends AbstractDao<Account> {
    private static final String GET_ACCOUNT_ID_REGISTRATION_DATE_BY_EMAIL = "SELECT account_ID, dateOfRegistration FROM Accounts WHERE email = ?";
    private static final String SELECT_ALL = "SELECT * FROM Accounts";
    private static final String SELECT_BY_ACCOUNT_ID = SELECT_ALL + " WHERE account_ID = ?";
    private static final String SELECT_BY_EMAIL = SELECT_ALL + " WHERE email = ?";
    private static final String DELETE_BY_ID = "DELETE FROM Accounts WHERE account_ID = ?";
    private static final String INSERT_BY_FULL_NAME_EMAIL_PASSWORD = "INSERT INTO Accounts " +
            "(surname, name, email, password, dateOfRegistration) " +
            "VALUES (?, ?, ?, ?, ?)";
    private static final String UPDATE_ACCOUNT_BY_ACCOUNT_ID ="UPDATE Accounts SET surname = ?, " +
            "name = ?, " +
            "middlename = ?, " +
            "password = ?, " +
            "dateOfBirth = ?, " +
            "homePhone = ?, " +
            "workPhone = ?, " +
            "skype = ?, " +
            "icq = ?, " +
            "homeAddress = ?, " +
            "workAddress = ?, " +
            "addInfo = ?, " +
            "role = ?, " +
            "picture = ? " +
            "WHERE account_ID = ?";
    private static final String GET_ACCOUNTS_LIST_BY_GROUP_ID = "SELECT * FROM Accounts a\n" +
            "INNER JOIN Members m ON m.account_ID = a.account_ID \n" +
            "INNER JOIN Groups g ON m.group_ID = g.group_ID \n" +
            "WHERE g.group_ID = ?";
    private static final String GET_UNACCEPTABLE_ACCOUNTS_LIST_BY_RECIPIENT_ID = "SELECT * FROM Accounts a\n" +
            "INNER JOIN Requests r ON r.creator_ID = a.account_ID \n" +
            "INNER JOIN Groups g ON r.recipient_ID = g.group_ID \n" +
            "WHERE g.group_ID = ? AND r.requestStatus = 0";

    private ConnectionPool pool = ConnectionPool.getPool();

    public AccountDao() {
    }

    @Override
    public Account create(Account account) {
        Connection connection = pool.getConnection();

        try (PreparedStatement createStatement = connection.prepareStatement(INSERT_BY_FULL_NAME_EMAIL_PASSWORD)) {
            createStatement.setString(1, account.getSurname());
            createStatement.setString(2, account.getName());
            createStatement.setString(3, account.getEmail());
            createStatement.setString(4, account.getPassword());
            Date registrationDate = Date.valueOf(LocalDate.now());
            createStatement.setDate(5, registrationDate);
            createStatement.executeUpdate();
            connection.commit();
            try (PreparedStatement getIDStatement = connection.prepareStatement(GET_ACCOUNT_ID_REGISTRATION_DATE_BY_EMAIL)) {
                getIDStatement.setString(1, account.getEmail());
                try (ResultSet resultSet = getIDStatement.executeQuery()) {
                    while (resultSet.next()) {
                        account.setAccountID(resultSet.getLong("account_ID"));
                        account.setDateOfRegistration(resultSet.getDate("dateOfRegistration").toLocalDate());
                    }
                    return account;
                } catch (SQLException e) {
                    throw new DaoException("Can't execute query of getting account by email statement.", e);
                }
            } catch (SQLException e) {
                throw new DaoException("Can't get statement for receiving account by email.", e);
            }
        } catch (SQLException e) {
            throw new DaoException("Can't insert account to database.", e);
        } finally {
            try {
                connection.rollback();
                pool.returnConnection(connection);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public Account getById(long accountID) {
        Account account = null;
        Connection connection = pool.getConnection();

        try (PreparedStatement selectStatement = connection.prepareStatement(SELECT_BY_ACCOUNT_ID)) {
            selectStatement.setLong(1, accountID);
            try (ResultSet resultSet = selectStatement.executeQuery()) {
                while (resultSet.next()) {
                    account = createAccountFromResultSet(resultSet);
                }
                return account;
            } catch (SQLException e) {
                throw new DaoException("Can't create account from ResultSet.", e);
            }
        } catch (SQLException e) {
            throw new DaoException("Can't return account on a given query", e);
        } finally {
            pool.returnConnection(connection);
        }

    }


    public Account getByEmail(String email) {
        Account account = null;
        Connection connection = pool.getConnection();

        try (PreparedStatement selectStatement = connection.prepareStatement(SELECT_BY_EMAIL)) {
            selectStatement.setString(1, email);
            try (ResultSet resultSet = selectStatement.executeQuery()) {
                while (resultSet.next()) {
                    account = createAccountFromResultSet(resultSet);
                }
                return account;
            } catch (SQLException e) {
                throw new DaoException("Can't create account from ResultSet.", e);
            }
        } catch (SQLException e) {
            throw new DaoException("Can't return account on a given query.", e);
        } finally {
            pool.returnConnection(connection);
        }
    }

    private Account createAccountFromResultSet(ResultSet resultSet) throws SQLException {
        Account account = new Account();
        account.setAccountID(resultSet.getLong("account_ID"));
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

        account.setDateOfRegistration(resultSet.getDate("dateOfRegistration").toLocalDate());

        if (resultSet.getString("role") != null) {
            account.setRole(resultSet.getString("role"));
        } else {
            account.setRole(null);
        }
        if (resultSet.getBlob("picture") != null) {
            Blob blob = resultSet.getBlob("picture");
            int blobLength = (int) blob.length();
            account.setPicture(blob.getBytes(1, blobLength));
        } else {
            account.setPicture(null);
        }

        return account;
    }

    @Override
    public void update(Account account) {
        Connection connection = pool.getConnection();

        try (PreparedStatement prepStatement = connection.prepareStatement(UPDATE_ACCOUNT_BY_ACCOUNT_ID)) {
            prepStatement.setLong(15, account.getAccountID());
            prepStatement.setString(1, account.getSurname());
            prepStatement.setString(2, account.getName());
            prepStatement.setString(3, account.getMiddlename());
            prepStatement.setString(4, account.getPassword());
            if (account.getDateOfBirth() != null) {
                prepStatement.setDate(5, Date.valueOf(account.getDateOfBirth()));
            } else {
                prepStatement.setNull(5, Types.DATE);
            }
            prepStatement.setString(6, account.getHomePhone());
            prepStatement.setString(7, account.getWorkPhone());
            prepStatement.setString(8, account.getSkype());
            prepStatement.setString(9, account.getIcq());
            prepStatement.setString(10, account.getHomeAddress());
            prepStatement.setString(11, account.getWorkAddress());
            prepStatement.setString(12, account.getAddInfo());
            prepStatement.setString(13, account.getRole());

            if (account.getPicture() != null) {
                prepStatement.setBlob(14, new SerialBlob(account.getPicture()));
            } else {
                prepStatement.setNull(14, Types.BLOB);
            }

            prepStatement.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            throw new DaoException("Can't update the row with the specified ID.", e);
        } finally {
            try {
                connection.rollback();
                pool.returnConnection(connection);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void delete(Account account) {
        Connection connection = pool.getConnection();

        try (PreparedStatement deleteStatement = connection.prepareStatement(DELETE_BY_ID)) {
            deleteStatement.setLong(1, account.getAccountID());
            deleteStatement.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            throw new DaoException("Can't delete the record with the specified ID.", e);
        } finally {
            try {
                connection.rollback();
                pool.returnConnection(connection);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public List<Account> getAccountsForGroup(Group group) {
        List<Account> accountsList = new ArrayList<>();
        Connection connection = pool.getConnection();

        try (PreparedStatement statement = connection.prepareStatement(GET_ACCOUNTS_LIST_BY_GROUP_ID)) {
            statement.setLong(1, group.getGroupID());
            try (ResultSet resultSet = statement.executeQuery()) {
                Account account;
                while (resultSet.next()) {
                    account = getById(resultSet.getLong("account_ID"));
                    accountsList.add(account);
                }
                return accountsList;
            } catch (SQLException e) {
                throw new DaoException("Can't create accounts list from ResultSet.", e);
            }
        } catch (SQLException e) {
            throw new DaoException("Can't create statement for getting accounts list.", e);
        } finally {
            pool.returnConnection(connection);
        }
    }

    public List<Account> getUnconfirmedRequestAccountsForGroup(Group group) {
        List<Account> accountsList = new ArrayList<>();
        Connection connection = pool.getConnection();

        try (PreparedStatement statement = connection.prepareStatement(GET_UNACCEPTABLE_ACCOUNTS_LIST_BY_RECIPIENT_ID)) {
            statement.setLong(1, group.getGroupID());
            try (ResultSet resultSet = statement.executeQuery()) {
                Account account;
                while (resultSet.next()) {
                    account = getById(resultSet.getLong("account_ID"));
                    accountsList.add(account);
                }
                return accountsList;
            } catch (SQLException e) {
                throw new DaoException("Can't create accounts list from ResultSet.", e);
            }
        } catch (SQLException e) {
            throw new DaoException("Can't create statement for getting accounts list.", e);
        } finally {
            pool.returnConnection(connection);
        }
    }
}