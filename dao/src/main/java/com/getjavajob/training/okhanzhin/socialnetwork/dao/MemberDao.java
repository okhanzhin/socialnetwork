package com.getjavajob.training.okhanzhin.socialnetwork.dao;

import com.getjavajob.training.okhanzhin.socialnetwork.domain.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static com.getjavajob.training.okhanzhin.socialnetwork.domain.Member.Status.fromValue;

@Repository
public class MemberDao extends AbstractDao<Member> {
    private static final String CREATE_MEMBER =
            "INSERT INTO Members (account_ID, group_ID, memberStatus) VALUES (?, ?, ?)";
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

    @Autowired
    public MemberDao(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
        setTableName("Members");
        setIdName("account_ID");
    }

    public Member create(Member member) {
        this.jdbcTemplate.update(connection -> {
            PreparedStatement createStatement = connection.prepareStatement(CREATE_MEMBER);
            createStatement.setLong(1, member.getAccountID());
            createStatement.setLong(2, member.getGroupID());
            createStatement.setByte(3, member.getMemberStatus().getStatus());
            return createStatement;
        });

        return member;
    }

    public Member getOwnerByGroupId(long groupID) {
        return this.jdbcTemplate.query(GET_OWNER_BY_GROUP_ID, new Object[]{groupID}, getSingleMemberExtractor());
    }

    public void update(Member member) {
        this.jdbcTemplate.update(connection -> {
            PreparedStatement updateStatement = connection.prepareStatement(UPDATE_MEMBER_STATUS);
            updateStatement.setByte(1, member.getMemberStatus().getStatus());
            updateStatement.setLong(2, member.getAccountID());
            updateStatement.setLong(3, member.getGroupID());
            return updateStatement;
        });
    }

    public void delete(Member member) {
        this.jdbcTemplate.update(connection -> {
            PreparedStatement deleteStatement = connection.prepareStatement(DELETE_MEMBER);
            deleteStatement.setLong(1, member.getAccountID());
            deleteStatement.setLong(2, member.getGroupID());
            return deleteStatement;
        });
    }

    public List<Member> getGroupMembers(long groupID) {
        List<Member> members;
        members = this.jdbcTemplate.query(GET_MEMBERS_BY_GROUP_ID, new Object[]{groupID}, getListExtractor());

        return members;
    }

    public List<Member> getGroupModerators(long groupID) {
        List<Member> members;
        members = this.jdbcTemplate.query(GET_MODERATORS_BY_GROUP_ID, new Object[]{groupID}, getListExtractor());

        return members;
    }

    private ResultSetExtractor<Member> getSingleMemberExtractor() {
        return resultSet -> {
            if (resultSet.next()) {
                return createEntityFromResultSet(resultSet);
            } else {
                return null;
            }
        };
    }

    private ResultSetExtractor<List<Member>> getListExtractor() {
        return resultSet -> {
            List<Member> entities = new ArrayList<>();
            while (resultSet.next()) {
                entities.add(createEntityFromResultSet(resultSet));
            }
            return entities;
        };
    }

    @Override
    protected Member createEntityFromResultSet(ResultSet resultSet) throws SQLException {
        Member member = new Member();
        member.setAccountID(resultSet.getLong("account_ID"));
        member.setGroupID(resultSet.getLong("group_ID"));
        member.setMemberStatus(fromValue(resultSet.getByte("memberStatus")));

        return member;
    }

    @Override
    protected Object[] makeValues(String value) {
        return new Object[0];
    }
}


