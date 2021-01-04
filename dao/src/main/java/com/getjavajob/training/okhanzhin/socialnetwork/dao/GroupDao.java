package com.getjavajob.training.okhanzhin.socialnetwork.dao;

import com.getjavajob.training.okhanzhin.socialnetwork.domain.Account;
import com.getjavajob.training.okhanzhin.socialnetwork.domain.Group;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
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
import java.time.LocalDate;
import java.util.List;

import static java.sql.Statement.RETURN_GENERATED_KEYS;
import static java.util.Objects.requireNonNull;

@Repository
public class GroupDao extends AbstractDao<Group> {
    private static final String INSERT_BY_NAME =
            "INSERT INTO Groups (groupName, dateOfRegistration) VALUES (?, ?)";
    private static final String UPDATE_GROUP_BY_GROUP_ID =
            "UPDATE Groups SET groupName = ?, groupDescription = ?, picture = ? WHERE group_ID = ?";
    private static final String GET_GROUP_ID_LIST_BY_ACCOUNT_ID = "SELECT g.group_ID FROM Groups g\n" +
            "INNER JOIN Members m ON m.group_ID = g.group_ID \n" +
            "INNER JOIN Accounts a ON m.account_ID = a.account_ID \n" +
            "WHERE a.account_ID = ?";

    @Autowired
    public GroupDao(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
        setTableName("Groups");
        setIdName("group_ID");
        setLikeQuery("groupName LIKE ?");
    }

    public Group create(Group group) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        this.jdbcTemplate.update(connection -> {
            PreparedStatement createStatement = connection.prepareStatement(INSERT_BY_NAME, RETURN_GENERATED_KEYS);
            createStatement.setString(1, group.getGroupName());
            Date registrationDate = Date.valueOf(LocalDate.now());
            createStatement.setDate(2, registrationDate);
            group.setDateOfRegistration(registrationDate.toLocalDate());
            return createStatement;
        }, keyHolder);
        group.setGroupID(requireNonNull(keyHolder.getKey()).longValue());

        return group;
    }

    public void update(Group group) {
        this.jdbcTemplate.update(connection -> {
            PreparedStatement updateStatement = connection.prepareStatement(UPDATE_GROUP_BY_GROUP_ID);
            updateStatement.setLong(4, group.getGroupID());
            updateStatement.setString(1, group.getGroupName());
            updateStatement.setString(2, group.getGroupDescription());
            if (group.getPicture() != null) {
                updateStatement.setBlob(3, new SerialBlob(group.getPicture()));
            } else {
                updateStatement.setNull(3, Types.BLOB);
            }
            return updateStatement;
        });
    }

    public List<Group> getGroupsListForAccount(Account account) {
        List<Group> groups;
        groups = this.jdbcTemplate.query(GET_GROUP_ID_LIST_BY_ACCOUNT_ID, new Object[]{account.getAccountID()},
                (resultSet, i) -> getById(resultSet.getLong("group_ID")));

        return groups;
    }

    @Override
    protected Group createEntityFromResultSet(ResultSet resultSet) throws SQLException {
        Group group = new Group();
        group.setGroupID(resultSet.getLong("group_ID"));
        group.setGroupName(resultSet.getString("groupName"));

        if (resultSet.getString("groupDescription") != null) {
            group.setGroupDescription(resultSet.getString("groupDescription"));
        } else {
            group.setGroupDescription(null);
        }

        group.setDateOfRegistration(resultSet.getDate("dateOfRegistration").toLocalDate());

        if (resultSet.getBlob("picture") != null) {
            Blob blob = resultSet.getBlob("picture");
            int blobLength = (int) blob.length();
            group.setPicture(blob.getBytes(1, blobLength));
        } else {
            group.setPicture(null);
        }

        return group;
    }

    @Override
    protected Object[] makeValues(String value) {
        return new Object[]{"%" + value + "%"};
    }
}