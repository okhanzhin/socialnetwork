package com.getjavajob.training.okhanzhin.socialnetwork.dao;

import com.getjavajob.training.okhanzhin.socialnetwork.domain.Group;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class GroupDao extends AbstractDao<Group> {
    private static final String INSERT_BY_NAME = "INSERT INTO Groups (groupName, groupDescription) VALUES (?, NULL)";
    private static final String GET_GROUP_ID_BY_NAME = "SELECT group_ID FROM Groups WHERE groupName = ?";
    private static final String SELECT_ALL = "SELECT * FROM Groups";
    private static final String SELECT_BY_GROUP_ID = SELECT_ALL + " WHERE group_ID = ?";
    private static final String UPDATE_GROUP_BY_GROUP_ID = "UPDATE Groups SET groupDescription = ? WHERE group_ID = ?";
    private static final String DELETE_BY_ID = "DELETE FROM Groups WHERE group_ID = ?";

    private ConnectionPool pool = ConnectionPool.getPool();

    public GroupDao() {
    }

    @Override
    public Group create(Group group) {
        Connection connection = pool.getConnection();

        if (connection != null) {
            try (PreparedStatement createStatement = connection.prepareStatement(INSERT_BY_NAME)) {
                createStatement.setString(1, group.getGroupName());
                createStatement.executeUpdate();
                connection.commit();
                try (PreparedStatement getIdStatement = connection.prepareStatement(GET_GROUP_ID_BY_NAME)) {
                    getIdStatement.setString(1, group.getGroupName());
                    try (ResultSet resultSet = getIdStatement.executeQuery()) {
                        while (resultSet.next()) {
                            group.setGroupID(resultSet.getInt("group_ID"));
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

        return null;
    }

    @Override
    public Group getById(int group_ID) {
        Connection connection = pool.getConnection();
        Group group = null;

        if (connection != null) {
            try (PreparedStatement selectStatement = connection.prepareStatement(SELECT_BY_GROUP_ID)) {
                selectStatement.setInt(1, group_ID);
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

        return null;
    }

    private Group createGroupFromResultSet(ResultSet resultSet) throws SQLException {
        Group group = new Group();
        group.setGroupID(resultSet.getInt("group_ID"));
        group.setGroupName(resultSet.getString("groupName"));
        if (resultSet.getString("groupDescription") != null) {
            group.setGroupDescription(resultSet.getString("groupDescription"));
        }

        return group;
    }

    @Override
    public void update(Group group) {
        Connection connection = pool.getConnection();

        if (connection != null) {
            try (PreparedStatement updateStatement = connection.prepareStatement(UPDATE_GROUP_BY_GROUP_ID)) {
                updateStatement.setInt(2, group.getGroupID());
                updateStatement.setString(1, group.getGroupDescription());
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
    }

    @Override
    public void delete(Group group) {
        Connection connection = pool.getConnection();
        if (connection != null) {
            try (PreparedStatement deleteStatement = connection.prepareStatement(DELETE_BY_ID)) {
                deleteStatement.setInt(1, group.getGroupID());
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
    }

    @Override
    public List<Group> getAll() {
        List<Group> groupsList = new ArrayList<>();
        Connection connection = pool.getConnection();

        if (connection != null) {
            try (Statement getAllStatement = connection.createStatement()) {
                try (ResultSet resultSet = getAllStatement.executeQuery(SELECT_ALL)) {
                    Group group;
                    while (resultSet.next()) {
                        group = createGroupFromResultSet(resultSet);
                        groupsList.add(group);
                    }
                    return groupsList;
                } catch (SQLException e) {
                    throw new DaoException("Can't create groups list from ResultSet.", e);
                }
            } catch (SQLException e) {
                throw new DaoException("Can't create statement for getting groups list.", e);
            } finally {
                pool.returnConnection(connection);
            }
        }

        return null;
    }
}