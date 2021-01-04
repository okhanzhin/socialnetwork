package com.getjavajob.training.okhanzhin.socialnetwork.dao;

import com.getjavajob.training.okhanzhin.socialnetwork.domain.Account;
import com.getjavajob.training.okhanzhin.socialnetwork.domain.Group;
import com.getjavajob.training.okhanzhin.socialnetwork.domain.Request;
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

import static com.getjavajob.training.okhanzhin.socialnetwork.domain.Request.Status.fromValue;
import static java.sql.Statement.RETURN_GENERATED_KEYS;
import static java.util.Objects.requireNonNull;

@Repository
public class RequestDao extends AbstractDao<Request> {
    private static final String CREATE_REQUEST =
            "INSERT INTO Requests (creator_ID, recipient_ID, requestType, requestStatus) VALUES (?, ?, ?, ?)";
    private static final String GET_REQUEST = "SELECT * FROM Requests WHERE creator_ID = ? AND recipient_ID = ?";
    private static final String UPDATE_REQUEST =
            "UPDATE Requests SET requestStatus = ? WHERE creator_ID = ? AND recipient_ID = ?";
    private static final String GET_REQUESTS_BY_GROUP_ID =
            "SELECT * FROM Requests WHERE recipient_ID = ? AND requestType = 'group'";
    private static final String GET_PENDING_REQUESTS_BY_ACCOUNT_ID =
            "SELECT * FROM Requests WHERE recipient_ID = ? AND requestType = 'user' AND requestStatus = 0";
    private static final String GET_OUTGOING_REQUESTS_BY_ACCOUNT_ID =
            "SELECT * FROM Requests WHERE creator_ID = ? AND requestType = 'user' AND requestStatus = 0";
    private static final String GET_ACCEPTED_REQUESTS_BY_ACCOUNT_ID =
            "SELECT * FROM Requests WHERE (creator_ID = ? OR recipient_ID = ?) " +
                    "AND requestType = 'user' AND requestStatus = 1";

    @Autowired
    public RequestDao(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
        setTableName("Requests");
        setIdName("request_ID");
    }

    public Request create(Request request) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        this.jdbcTemplate.update(connection -> {
            PreparedStatement createStatement = connection.prepareStatement(CREATE_REQUEST, RETURN_GENERATED_KEYS);
            createStatement.setLong(1, request.getCreatorID());
            createStatement.setLong(2, request.getRecipientID());
            createStatement.setString(3, request.getRequestType());
            createStatement.setByte(4, request.getStatus().getStatus());
            return createStatement;
        }, keyHolder);
        request.setRequestID(requireNonNull(keyHolder.getKey()).longValue());

        return request;
    }

    public Request getByCreatorRecipientID(long creatorID, long recipientID) {
        System.out.println("Creator " + creatorID + "," + "Recipient " + recipientID);

        return this.jdbcTemplate.query(GET_REQUEST, new Object[]{creatorID, recipientID}, getSingleRequestExtractor());
    }

    public void update(Request request) {
        this.jdbcTemplate.update(connection -> {
            PreparedStatement updateStatement = connection.prepareStatement(UPDATE_REQUEST);
            updateStatement.setByte(1, request.getStatus().getStatus());
            updateStatement.setLong(2, request.getCreatorID());
            updateStatement.setLong(3, request.getRecipientID());
            return updateStatement;
        });
    }


    public List<Request> getGroupRequestsList(Group group) {
        return this.jdbcTemplate.query(GET_REQUESTS_BY_GROUP_ID, new Object[]{group.getGroupID()}, getListExtractor());
    }

    public List<Request> getPendingRequestsList(Account account) {
        return this.jdbcTemplate.query(GET_PENDING_REQUESTS_BY_ACCOUNT_ID, new Object[]{account.getAccountID()}, getListExtractor());
    }

    public List<Request> getOutgoingRequestsList(Account account) {
        return this.jdbcTemplate.query(GET_OUTGOING_REQUESTS_BY_ACCOUNT_ID, new Object[]{account.getAccountID()}, getListExtractor());
    }

    public List<Request> getAcceptedRequestsList(Account account) {
        return this.jdbcTemplate.query(GET_ACCEPTED_REQUESTS_BY_ACCOUNT_ID, new Object[]{account.getAccountID(), account.getAccountID()}, getListExtractor());
    }

    private ResultSetExtractor<Request> getSingleRequestExtractor() {
        return resultSet -> {
            if (resultSet.next()) {
                Request request = createEntityFromResultSet(resultSet);
                System.out.println("DAO REQUEST " + request.toString());
                return request;
            } else {
                return null;
            }
        };
    }

    private ResultSetExtractor<List<Request>> getListExtractor() {
        return resultSet -> {
            List<Request> requests = new ArrayList<>();
            while (resultSet.next()) {
                requests.add(createEntityFromResultSet(resultSet));
            }
            return requests;
        };
    }

    @Override
    protected Request createEntityFromResultSet(ResultSet resultSet) throws SQLException {
        Request request = new Request();
        request.setRequestID(resultSet.getLong("request_ID"));
        request.setCreatorID(resultSet.getLong("creator_ID"));
        request.setRecipientID(resultSet.getLong("recipient_ID"));
        request.setRequestType(resultSet.getString("requestType"));
        request.setStatus(fromValue(resultSet.getByte("requestStatus")));

        return request;
    }

    @Override
    protected Object[] makeValues(String value) {
        return new Object[0];
    }
}



