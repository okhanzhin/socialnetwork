package com.getjavajob.training.okhanzhin.socialnetwork.dao;

import com.getjavajob.training.okhanzhin.socialnetwork.domain.Phone;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static java.sql.Statement.RETURN_GENERATED_KEYS;
import static java.util.Objects.requireNonNull;

@Repository
public class PhoneDao extends AbstractDao<Phone> {
    private final static String INSERT_BY_ACCOUNT_ID_NUMBER_TYPE = "INSERT INTO Phones (account_ID, phoneNumber, phoneType) VALUES (?,?,?)";
    private final static String UPDATE_PHONE_BY_PHONE_ID = "UPDATE Phones SET phoneNumber = ?, phoneType = ? WHERE phone_ID = ?";
    private final static String GET_PHONES_BY_ACCOUNT_ID = "SELECT * FROM Phones WHERE account_ID = ?";

    @Autowired
    public PhoneDao(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
        setTableName("Phones");
        setIdName("phone_ID");
    }

    public Phone create(Phone phone) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        this.jdbcTemplate.update(connection -> {
            PreparedStatement createStatement = connection.prepareStatement(INSERT_BY_ACCOUNT_ID_NUMBER_TYPE, RETURN_GENERATED_KEYS);
            createStatement.setLong(1, phone.getAccountID());
            createStatement.setString(2, phone.getPhoneNumber());
            createStatement.setString(3, phone.getPhoneType());
            return createStatement;
        }, keyHolder);
        phone.setPhoneID(requireNonNull(keyHolder.getKey()).longValue());

        return phone;
    }

    public void update(Phone phone) {
        this.jdbcTemplate.update(connection -> {
            PreparedStatement updateStatement = connection.prepareStatement(UPDATE_PHONE_BY_PHONE_ID);
            updateStatement.setString(1, phone.getPhoneNumber());
            updateStatement.setString(2, phone.getPhoneType());
            updateStatement.setLong(3, phone.getPhoneID());
            return updateStatement;
        });
    }

    public List<Phone> getAllPhonesByAccountId(long id) {
        List<Phone> phones;
        phones = this.jdbcTemplate.query(GET_PHONES_BY_ACCOUNT_ID, new Object[]{id}, getListExtractor());

        return phones;
    }

    private ResultSetExtractor<List<Phone>> getListExtractor() {
        return resultSet -> {
            List<Phone> entities = new ArrayList<>();
            while (resultSet.next()) {
                entities.add(createEntityFromResultSet(resultSet));
            }
            return entities;
        };
    }

    @Override
    protected Phone createEntityFromResultSet(ResultSet resultSet) throws SQLException {
        Phone phone = new Phone();
        phone.setPhoneID(resultSet.getLong("phone_ID"));
        phone.setAccountID(resultSet.getLong("account_ID"));
        phone.setPhoneNumber(resultSet.getString("phoneNumber"));
        phone.setPhoneType(resultSet.getString("phoneType"));

        return phone;
    }

    @Override
    protected Object[] makeValues(String value) {
        return new Object[0];
    }
}
