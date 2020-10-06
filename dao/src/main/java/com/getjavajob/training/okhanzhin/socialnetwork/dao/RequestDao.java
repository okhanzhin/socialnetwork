package com.getjavajob.training.okhanzhin.socialnetwork.dao;

import com.getjavajob.training.okhanzhin.socialnetwork.domain.Account;
import com.getjavajob.training.okhanzhin.socialnetwork.domain.Group;
import com.getjavajob.training.okhanzhin.socialnetwork.domain.Request;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RequestDao extends AbstractDao<Request> {
    private static final String GROUP_REQUEST_TYPE = "group";
    private static final String RELATION_REQUEST_TYPE = "user";
    private static final String CREATE_REQUEST =
            "INSERT INTO Requests (creator_ID, recipient_ID, requestType, requestStatus) VALUES (?, ?, ?, ?)";
    private static final String GET_REQUEST_ID = "SELECT request_ID FROM Requests WHERE creator_ID = ? AND recipient_ID = ?";
    private static final String GET_REQUEST = "SELECT * FROM Requests WHERE creator_ID = ? AND recipient_ID = ?";
    private static final String GET_BY_REQUEST_ID = "SELECT FROM Requests WHERE request_ID = ?";
    private static final String UPDATE_REQUEST =
            "UPDATE Requests SET requestStatus = ? WHERE creator_ID = ? AND recipient_ID = ?";
    private static final String DELETE_REQUEST =
            "DELETE FROM Requests WHERE creator_ID = ? AND recipient_ID = ?";
    private static final String GET_REQUESTS_BY_GROUP_ID =
            "SELECT * FROM Requests WHERE recipent_ID = ? AND requestType = 'group'";
    private static final String GET_PENDING_REQUESTS_BY_ACCOUNT_ID =
            "SELECT * FROM Requests WHERE recipient_ID = ? AND requestType = 'user' AND requestStatus = 0";
    private static final String GET_OUTGOING_REQUESTS_BY_ACCOUNT_ID =
            "SELECT * FROM Requests WHERE creator_ID = ? AND requestType = 'user' AND requestStatus = 0";
    private static final String GET_ACCEPTED_REQUESTS_BY_ACCOUNT_ID =
            "SELECT * FROM Requests WHERE (creator_ID = ? OR recipient_ID = ?) " +
                    "AND requestType = 'user' AND requestStatus = 1";

    private ConnectionPool pool = ConnectionPool.getPool();

    @Override
    public Request create(Request request) {
        Connection connection = pool.getConnection();

        try (PreparedStatement statement = connection.prepareStatement(CREATE_REQUEST)) {
            statement.setLong(1, request.getCreatorID());
            statement.setLong(2, request.getRecipientID());
            statement.setString(3, request.getRequestType());
            statement.setByte(4, request.getRequestStatus());
            statement.executeUpdate();
            connection.commit();
            try (PreparedStatement getIDStatement = connection.prepareStatement(GET_REQUEST_ID)) {
                getIDStatement.setLong(1, request.getCreatorID());
                getIDStatement.setLong(2, request.getRecipientID());
                try (ResultSet resultSet = getIDStatement.executeQuery()) {
                    while (resultSet.next()) {
                        request.setRequestID(resultSet.getLong("request_ID"));
                    }
                    return request;
                } catch (SQLException e) {
                    throw new DaoException("Can't execute query of getting request by Creator/RecipientID.", e);
                }
            } catch (SQLException e) {
                throw new DaoException("Can't get statement for receiving request.", e);
            }
        } catch (SQLException e) {
            throw new DaoException("Can't create new request.", e);
        } finally {
            try {
                connection.rollback();
                pool.returnConnection(connection);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private Request createRequestFromResultSet(ResultSet resultSet) throws SQLException {
        Request request = new Request();
        request.setRequestID(resultSet.getLong("request_ID"));
        request.setCreatorID(resultSet.getLong("creator_ID"));
        request.setRecipientID(resultSet.getLong("recipient_ID"));
        request.setRequestType(resultSet.getString("requestType"));
        request.setRequestStatus(resultSet.getByte("requestStatus"));

        return request;
    }

    @Override
    public Request getById(long requestID) {
        Request request = null;
        Connection connection = pool.getConnection();

        try (PreparedStatement statement = connection.prepareStatement(GET_BY_REQUEST_ID)) {
            statement.setLong(1, requestID);
            try (ResultSet resultSet = statement.getResultSet()) {
                while (resultSet.next()) {
                    request = createRequestFromResultSet(resultSet);
                }
                return request;
            } catch (SQLException e) {
                throw new DaoException("Can't create request from ResultSet.", e);
            }
        } catch (SQLException e) {
            throw new DaoException("Can't return request on a given query.", e);
        } finally {
            pool.returnConnection(connection);
        }
    }

    public Request getByCreatorRecipientID(long creatorID, long recipientID) {
        System.out.println("Creator " + creatorID + "," + "Recipient " + recipientID);
        Request request = null;
        Connection connection = pool.getConnection();

        try (PreparedStatement statement = connection.prepareStatement(GET_REQUEST)) {
            statement.setLong(1, creatorID);
            statement.setLong(2, recipientID);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    request = createRequestFromResultSet(resultSet);
                    System.out.println("DAO REQUEST " + request.toString());
                }
                return request;
            } catch (SQLException e) {
                throw new DaoException("Can't create request from ResultSet.", e);
            }
        } catch (SQLException e) {
            throw new DaoException("Can't return request on a given query.", e);
        } finally {
            pool.returnConnection(connection);
        }
    }

    @Override
    public void update(Request request) {
        Connection connection = pool.getConnection();

        try (PreparedStatement statement = connection.prepareStatement(UPDATE_REQUEST)) {
            statement.setByte(1, request.getRequestStatus());
            statement.setLong(2, request.getCreatorID());
            statement.setLong(3, request.getRecipientID());
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
    public void delete(Request request) {
        Connection connection = pool.getConnection();

        try (PreparedStatement statement = connection.prepareStatement(DELETE_REQUEST)) {
            statement.setLong(1, request.getCreatorID());
            statement.setLong(2, request.getRecipientID());
            statement.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            throw new DaoException("Can't delete row with the specified attributes: request does not exist.", e);
        } finally {
            try {
                connection.rollback();
                pool.returnConnection(connection);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public List<Request> getGroupRequestsList(Group group) {
        List<Request> requestsList = new ArrayList<>();
        long groupID = group.getGroupID();
        final Connection connection = pool.getConnection();

        try (PreparedStatement statement = connection.prepareStatement(GET_REQUESTS_BY_GROUP_ID)) {
            statement.setLong(1, groupID);
            try (ResultSet resultSet = statement.executeQuery()) {
                Request request;
                while (resultSet.next()) {
                    request = createRequestFromResultSet(resultSet);
                    requestsList.add(request);
                }
                return requestsList;
            } catch (SQLException e) {
                throw new DaoException("Can't create list of requests from ResultSet.", e);
            }
        } catch (SQLException e) {
            throw new DaoException("Can't create statement for getting requests list of requests.", e);
        } finally {
            pool.returnConnection(connection);
        }
    }

    public List<Request> getPendingRequestsList(Account account) {
        List<Request> requestsList = new ArrayList<>();
        long accountID = account.getAccountID();
        final Connection connection = pool.getConnection();

        try (PreparedStatement statement = connection.prepareStatement(GET_PENDING_REQUESTS_BY_ACCOUNT_ID)) {
            statement.setLong(1, accountID);
            try (ResultSet resultSet = statement.executeQuery()) {
                Request request;
                while (resultSet.next()) {
                    request = createRequestFromResultSet(resultSet);
                    requestsList.add(request);
                }
                return requestsList;
            } catch (SQLException e) {
                throw new DaoException("Can't create list of pending requests from ResultSet.", e);
            }
        } catch (SQLException e) {
            throw new DaoException("Can't create statement for getting list of requests.", e);
        } finally {
            pool.returnConnection(connection);
        }
    }

    public List<Request> getOutgoingRequestsList(Account account) {
        List<Request> requestsList = new ArrayList<>();
        long accountID = account.getAccountID();
        final Connection connection = pool.getConnection();

        try (PreparedStatement statement = connection.prepareStatement(GET_OUTGOING_REQUESTS_BY_ACCOUNT_ID)) {
            statement.setLong(1, accountID);
            try (ResultSet resultSet = statement.executeQuery()) {
                Request request;
                while (resultSet.next()) {
                    request = createRequestFromResultSet(resultSet);
                    requestsList.add(request);
                }
                return requestsList;
            } catch (SQLException e) {
                throw new DaoException("Can't create list of outgoing requests from ResultSet.", e);
            }
        } catch (SQLException e) {
            throw new DaoException("Can't create statement for getting list of requests.", e);
        } finally {
            pool.returnConnection(connection);
        }
    }

    public List<Request> getAcceptedRequestsList(Account account) {
        List<Request> requestsList = new ArrayList<>();
        long accountID = account.getAccountID();
        final Connection connection = pool.getConnection();

        try (PreparedStatement statement = connection.prepareStatement(GET_ACCEPTED_REQUESTS_BY_ACCOUNT_ID)) {
            statement.setLong(1, accountID);
            statement.setLong(2, accountID);
            try (ResultSet resultSet = statement.executeQuery()) {
                Request request;
                while (resultSet.next()) {
                    request = createRequestFromResultSet(resultSet);
                    requestsList.add(request);
                }
                return requestsList;
            } catch (SQLException e) {
                throw new DaoException("Can't create list of accepted requests from ResultSet.", e);
            }
        } catch (SQLException e) {
            throw new DaoException("Can't create statement for getting list of requests.", e);
        } finally {
            pool.returnConnection(connection);
        }
    }
}



