package com.getjavajob.training.okhanzhin.socialnetwork.dao;

import com.getjavajob.training.okhanzhin.socialnetwork.domain.Account;
import com.getjavajob.training.okhanzhin.socialnetwork.domain.Group;

import javax.sql.rowset.serial.SerialBlob;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class GroupDao extends AbstractDao<Group> {
    private static final String GET_GROUP_ID_BY_NAME = "SELECT group_ID FROM Groups WHERE groupName = ?";
    private static final String SELECT_ALL = "SELECT * FROM Groups";
    private static final String SELECT_BY_GROUP_ID = SELECT_ALL + " WHERE group_ID = ?";
    private static final String DELETE_BY_ID = "DELETE FROM Groups WHERE group_ID = ?";
    private static final String INSERT_BY_NAME =
            "INSERT INTO Groups (groupName, dateOfRegistration) VALUES (?, ?)";
    private static final String UPDATE_GROUP_BY_GROUP_ID =
            "UPDATE Groups SET groupName = ?, groupDescription = ?, picture = ? WHERE group_ID = ?";
    private static final String GET_GROUP_ID_LIST_BY_ACCOUNT_ID = "SELECT g.group_ID FROM Groups g\n" +
            "INNER JOIN Members m ON m.group_ID = g.group_ID \n" +
            "INNER JOIN Accounts a ON m.account_ID = a.account_ID \n" +
            "WHERE a.account_ID = ?";

    private ConnectionPool pool = ConnectionPool.getPool();

    public GroupDao() {
    }

    @Override
    public Group create(Group group) {
        Connection connection = pool.getConnection();

        try (PreparedStatement createStatement = connection.prepareStatement(INSERT_BY_NAME)) {
            createStatement.setString(1, group.getGroupName());
            Date registrationDate = Date.valueOf(LocalDate.now());
            createStatement.setDate(2, registrationDate);
            createStatement.executeUpdate();
            connection.commit();
            try (PreparedStatement getIdStatement = connection.prepareStatement(GET_GROUP_ID_BY_NAME)) {
                getIdStatement.setString(1, group.getGroupName());
                try (ResultSet resultSet = getIdStatement.executeQuery()) {
                    while (resultSet.next()) {
                        group.setGroupID(resultSet.getLong("group_ID"));
                    }
                    return group;
                } catch (SQLException e) {
                    throw new DaoException("Can't create group from ResultSet.", e);
                }
            } catch (SQLException e) {
                throw new DaoException("Can't create of configure getting group ID statement.", e);
            }
        } catch (SQLException e) {
            throw new DaoException("Can't insert group to database.", e);
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
    public Group getById(long group_ID) {
        Connection connection = pool.getConnection();
        Group group = null;

        try (PreparedStatement selectStatement = connection.prepareStatement(SELECT_BY_GROUP_ID)) {
            selectStatement.setLong(1, group_ID);
            try (ResultSet resultSet = selectStatement.executeQuery()) {
                while (resultSet.next()) {
                    group = createGroupFromResultSet(resultSet);
                }
                return group;
            } catch (SQLException e) {
                throw new DaoException("Can't create group from ResultSet.", e);
            }
        } catch (SQLException e) {
            throw new DaoException("Can't return group on a given query", e);
        } finally {
            pool.returnConnection(connection);
        }
    }

    private Group createGroupFromResultSet(ResultSet resultSet) throws SQLException {
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
    public void update(Group group) {
        Connection connection = pool.getConnection();

        try (PreparedStatement updateStatement = connection.prepareStatement(UPDATE_GROUP_BY_GROUP_ID)) {
            updateStatement.setLong(4, group.getGroupID());
            updateStatement.setString(1, group.getGroupName());
            updateStatement.setString(2, group.getGroupDescription());
            if (group.getPicture() != null) {
                updateStatement.setBlob(3, new SerialBlob(group.getPicture()));
            } else {
                updateStatement.setNull(3, Types.BLOB);
            }
            updateStatement.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            throw new DaoException("Can't update the record with the specified ID.", e);
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
    public void delete(Group group) {
        Connection connection = pool.getConnection();

        try (PreparedStatement deleteStatement = connection.prepareStatement(DELETE_BY_ID)) {
            deleteStatement.setLong(1, group.getGroupID());
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

    public List<Group> getGroupsListForAccount(Account account) {
        List<Group> groups = new ArrayList<>();
        Connection connection = pool.getConnection();

        try (PreparedStatement statement = connection.prepareStatement(GET_GROUP_ID_LIST_BY_ACCOUNT_ID)) {
            statement.setLong(1, account.getAccountID());
            try (ResultSet resultSet = statement.executeQuery()) {
                Group group;
                while (resultSet.next()) {
                    group = getById(resultSet.getLong("group_ID"));
                    groups.add(group);
                }
                return groups;
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