package com.getjavajob.training.okhanzhin.socialnetwork.dao;

import com.getjavajob.training.okhanzhin.socialnetwork.domain.Member;

import java.sql.*;

import java.util.ArrayList;
import java.util.List;

public class MemberDao extends AbstractDao<Member> {
    private static final String CREATE_MEMBER =
            "INSERT INTO Members (account_ID, group_ID, memberStatus) VALUES (?, ?, ?)";
    private static final String GET_MEMBER_BY_ACCOUNT_ID =
            "SELECT * FROM Members WHERE account_ID = ?";
    private static final String GET_OWNER_BY_GROUP_ID =
            "SELECT * FROM Members WHERE group_ID = ? AND memberStatus = 0";
    private static final String UPDATE_MEMBER_STATUS =
            "UPDATE Members SET memberStatus = ? WHERE account_ID = ? AND group_ID = ?";
    private static final String DELETE_MEMBER =
            "DELETE FROM Members WHERE account_ID = ? AND group_ID = ? AND memberStatus IN (1, 2)";
    private static final String GET_MEMBERS_BY_GROUP_ID =
            "SELECT * FROM Members WHERE group_ID = ?";
    private static final String GET_MODERATORS_BY_GROUP_ID =
            "SELECT * FROM Members WHERE group_ID = ? AND memberStatus = 1";

    private ConnectionPool pool = ConnectionPool.getPool();

    @Override
    public Member create(Member member) {
        Connection connection = pool.getConnection();

        try (PreparedStatement statement = connection.prepareStatement(CREATE_MEMBER)) {
            statement.setLong(1, member.getAccountID());
            statement.setLong(2, member.getGroupID());
            statement.setByte(3, member.getMemberStatus());
            statement.executeUpdate();
            connection.commit();
            return member;
        } catch (SQLException e) {
            throw new DaoException("Can't create new group member.", e);
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
    public Member getById(long accountID) {
        Member member = null;
        Connection connection = pool.getConnection();

        try (PreparedStatement statement = connection.prepareStatement(GET_MEMBER_BY_ACCOUNT_ID)) {
            statement.setLong(1, accountID);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    member = createMemberFromResultSet(resultSet);
                }
                return member;
            } catch (SQLException e) {
                throw new DaoException("Can't create member from ResultSet.", e);
            }
        } catch (SQLException e) {
            throw new DaoException("Can't return member on a given query.", e);
        } finally {
            pool.returnConnection(connection);
        }
    }

    public Member getOwnerByGroupId(long groupID) {
        Member member = null;
        Connection connection = pool.getConnection();

        try (PreparedStatement statement = connection.prepareStatement(GET_OWNER_BY_GROUP_ID)) {
            statement.setLong(1, groupID);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    member = createMemberFromResultSet(resultSet);
                }
                return member;
            } catch (SQLException e) {
                throw new DaoException("Can't create owner member from ResultSet.", e);
            }
        } catch (SQLException e) {
            throw new DaoException("Can't return owner member on a given query.", e);
        } finally {
            pool.returnConnection(connection);
        }
    }

    private Member createMemberFromResultSet(ResultSet resultSet) throws SQLException {
        Member member = new Member();
        member.setAccountID(resultSet.getLong("account_ID"));
        member.setGroupID(resultSet.getLong("group_ID"));
        member.setMemberStatus(resultSet.getByte("memberStatus"));

        return member;
    }

    @Override
    public void update(Member member) {
        Connection connection = pool.getConnection();

        try (PreparedStatement statement = connection.prepareStatement(UPDATE_MEMBER_STATUS)) {
            statement.setByte(1, member.getMemberStatus());
            statement.setLong(2, member.getAccountID());
            statement.setLong(3, member.getGroupID());
            statement.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            throw new DaoException("Can't update row on a given query.", e);
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
    public void delete(Member member) {
        Connection connection = pool.getConnection();

        try (PreparedStatement statement = connection.prepareStatement(DELETE_MEMBER)) {
            statement.setLong(1, member.getAccountID());
            statement.setLong(2, member.getGroupID());
            statement.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            throw new DaoException("Can't delete row with the specified attributes: " +
                    "member either does not exist or has a status that cannot be deleted.", e);
        } finally {
            try {
                connection.rollback();
                pool.returnConnection(connection);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }


    public List<Member> getListById(long groupID) {
        List<Member> members = new ArrayList<>();
        final Connection connection = pool.getConnection();

        try (PreparedStatement statement = connection.prepareStatement(GET_MEMBERS_BY_GROUP_ID)) {
            statement.setLong(1, groupID);
            try (ResultSet resultSet = statement.executeQuery()) {
                Member member;
                while (resultSet.next()) {
                    member = createMemberFromResultSet(resultSet);
                    members.add(member);
                }
                return members;
            } catch (SQLException e) {
                throw new DaoException("Can't create list of members from ResultSet.", e);
            }
        } catch (SQLException e) {
            throw new DaoException("Can't create statement for getting list of members.", e);
        } finally {
            pool.returnConnection(connection);
        }
    }

    public List<Member> getListOfModerators(long groupID) {
        List<Member> moderators = new ArrayList<>();
        final Connection connection = pool.getConnection();

        try (PreparedStatement statement = connection.prepareStatement(GET_MODERATORS_BY_GROUP_ID)) {
            statement.setLong(1, groupID);
            try (ResultSet resultSet = statement.executeQuery()) {
                Member member;
                while (resultSet.next()) {
                    member = createMemberFromResultSet(resultSet);
                    moderators.add(member);
                }
                return moderators;
            } catch (SQLException e) {
                throw new DaoException("Can't create list of moderators from ResultSet.", e);
            }
        } catch (SQLException e) {
            throw new DaoException("Can't create statement for getting list of moderators.", e);
        } finally {
            pool.returnConnection(connection);
        }
    }
}


