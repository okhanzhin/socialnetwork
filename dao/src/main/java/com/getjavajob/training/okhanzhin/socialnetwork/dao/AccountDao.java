package com.getjavajob.training.okhanzhin.socialnetwork.dao;

import com.getjavajob.training.okhanzhin.socialnetwork.domain.Account;
import com.getjavajob.training.okhanzhin.socialnetwork.domain.Group;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import javax.sql.rowset.serial.SerialBlob;
import java.sql.Blob;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;

import static java.sql.Statement.RETURN_GENERATED_KEYS;
import static java.util.Objects.requireNonNull;

@Repository
public class AccountDao extends AbstractDao<Account> {
    private static final String SELECT_ALL = "SELECT * FROM Accounts";
    private static final String SELECT_BY_EMAIL = SELECT_ALL + " WHERE email = ?";
    private static final String INSERT_BY_FULL_NAME_EMAIL_PASSWORD = "INSERT INTO Accounts " +
            "(surname, name, email, password, dateOfRegistration) VALUES (?, ?, ?, ?, ?)";
    private static final String UPDATE_ACCOUNT_BY_ACCOUNT_ID ="UPDATE Accounts SET surname = ?, " +
            "name = ?, middlename = ?, password = ?, dateOfBirth = ?, " +
            "skype = ?, icq = ?, homeAddress = ?, workAddress = ?, addInfo = ?, " +
            "role = ?, picture = ? WHERE account_ID = ?";
    private static final String GET_ACCOUNTS_LIST_BY_GROUP_ID = "SELECT * FROM Accounts a\n" +
            "INNER JOIN Members m ON m.account_ID = a.account_ID \n" +
            "INNER JOIN Groups g ON m.group_ID = g.group_ID \n" +
            "WHERE g.group_ID = ?";
    private static final String GET_UNACCEPTABLE_ACCOUNTS_LIST_BY_RECIPIENT_ID = "SELECT * FROM Accounts a\n" +
            "INNER JOIN Requests r ON r.creator_ID = a.account_ID \n" +
            "INNER JOIN Groups g ON r.recipient_ID = g.group_ID \n" +
            "WHERE g.group_ID = ? AND r.requestStatus = 0";

    @Autowired
    public AccountDao(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
        setTableName("Accounts");
        setIdName("account_ID");
        setLikeQuery("surname LIKE ? OR name LIKE ?");
    }

    public Account create(Account account) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        this.jdbcTemplate.update(connection -> {
            PreparedStatement createStatement = connection.prepareStatement(INSERT_BY_FULL_NAME_EMAIL_PASSWORD, RETURN_GENERATED_KEYS);
            createStatement.setString(1, account.getSurname());
            createStatement.setString(2, account.getName());
            createStatement.setString(3, account.getEmail());
            createStatement.setString(4, account.getPassword());
            createStatement.setDate(5, Date.valueOf(account.getDateOfRegistration()));
            return createStatement;
        }, keyHolder);
        account.setAccountID(requireNonNull(keyHolder.getKey()).longValue());

        return account;
    }

    public Account getByEmail(String email) {
         return this.jdbcTemplate.query(SELECT_BY_EMAIL, new Object[]{email}, getSingleAccountExtractor());
    }

    public void update(Account account) {
        this.jdbcTemplate.update(connection -> {
            PreparedStatement updateStatement = connection.prepareStatement(UPDATE_ACCOUNT_BY_ACCOUNT_ID);
            updateStatement.setLong(13, account.getAccountID());
            updateStatement.setString(1, account.getSurname());
            updateStatement.setString(2, account.getName());
            updateStatement.setString(3, account.getMiddlename());
            updateStatement.setString(4, account.getPassword());
            if (account.getDateOfBirth() != null) {
                updateStatement.setDate(5, Date.valueOf(account.getDateOfBirth()));
            } else {
                updateStatement.setNull(5, Types.DATE);
            }
            updateStatement.setString(6, account.getSkype());
            updateStatement.setString(7, account.getIcq());
            updateStatement.setString(8, account.getHomeAddress());
            updateStatement.setString(9, account.getWorkAddress());
            updateStatement.setString(10, account.getAddInfo());
            updateStatement.setString(11, account.getRole());
            if (account.getPicture() != null) {
                updateStatement.setBlob(12, new SerialBlob(account.getPicture()));
            } else {
                updateStatement.setNull(12, Types.BLOB);
            }
            return updateStatement;
        });
    }

    public List<Account> getAccountsForGroup(Group group) {
        List<Account> accounts;
        accounts = this.jdbcTemplate.query(GET_ACCOUNTS_LIST_BY_GROUP_ID, new Object[]{group.getGroupID()},
                (resultSet, i) -> getById(resultSet.getLong("account_ID")));

        return accounts;
    }

    public List<Account> getUnconfirmedRequestAccountsForGroup(Group group) {
        List<Account> accounts;
        accounts = this.jdbcTemplate.query(GET_UNACCEPTABLE_ACCOUNTS_LIST_BY_RECIPIENT_ID, new Object[]{group.getGroupID()},
                (resultSet, i) -> getById(resultSet.getLong("account_ID")));

        return accounts;
    }

    @Override
    protected Account createEntityFromResultSet(ResultSet resultSet) throws SQLException {
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

    private ResultSetExtractor<Account> getSingleAccountExtractor() {
        return resultSet -> {
            if (resultSet.next()) {
                return createEntityFromResultSet(resultSet);
            } else {
                return null;
            }
        };
    }

    @Override
    protected Object[] makeValues(String value) {
        return new Object[]{"%" + value + "%", "%" + value + "%"};
    }
}