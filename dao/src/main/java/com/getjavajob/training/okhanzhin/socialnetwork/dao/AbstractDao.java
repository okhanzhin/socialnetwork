package com.getjavajob.training.okhanzhin.socialnetwork.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public abstract class AbstractDao<T> {
    private static final String SELECT_ALL = "SELECT * FROM ";
    private static final String WHERE = " WHERE ";
    private static final String LIMIT_ROWS = " LIMIT ?, ?";
    private static final String DELETE_BY_ID = "DELETE FROM ";
    private static final String SELECT_COUNT = "SELECT COUNT(*) FROM ";
    public static final int RECORDS_PER_PAGE = 8;

    protected JdbcTemplate jdbcTemplate;

    private String tableName;
    private String idName;
    private String likeQuery;

    @Autowired
    public AbstractDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    protected void setTableName(String tableName) {
        this.tableName = tableName;
    }

    protected void setIdName(String idName) {
        this.idName = idName;
    }

    protected void setLikeQuery(String likeQuery) {
        this.likeQuery = likeQuery;
    }

    private String getSelectByIdQuery() {
        return SELECT_ALL + tableName + WHERE + idName + " = ?";
    }

    private String getSelectAllQuery() {
        return SELECT_ALL + tableName;
    }

    private String getDeleteByIdQuery() {
        return DELETE_BY_ID + tableName + WHERE + idName + " = ?";
    }

    private String getSearchQuery() {
        return getSelectAllQuery() + WHERE + likeQuery + LIMIT_ROWS;
    }

    private String getCountQuery() {
        return SELECT_COUNT + tableName + WHERE + likeQuery;
    }

    protected abstract T createEntityFromResultSet(ResultSet resultSet) throws SQLException;

    protected abstract Object[] makeValues(String value);

    public T getById(long id) {
        return this.jdbcTemplate.query(getSelectByIdQuery(), new Object[]{id}, getSingleObjectExtractor());
    }

    public List<T> getAll() {
        return jdbcTemplate.query(getSelectAllQuery(), getListExtractor());
    }

    public void deleteById(long id) {
        jdbcTemplate.update(getDeleteByIdQuery(), id);
    }

    public List<T> searchEntities(String value, int currentPage) {
        int startRecord = currentPage * RECORDS_PER_PAGE - RECORDS_PER_PAGE;
        Object[] likeQueryParameters = makeValues(value);

        if (likeQueryParameters.length > 1) {
            return jdbcTemplate.query(getSearchQuery(),
                    new Object[]{likeQueryParameters[0], likeQueryParameters[1], startRecord, RECORDS_PER_PAGE},
                    getListExtractor());
        } else {
            return jdbcTemplate.query(getSearchQuery(),
                    new Object[]{likeQueryParameters[0], startRecord, RECORDS_PER_PAGE},
                    getListExtractor());
        }
    }

    public int getCountOfSearchResults(String value) {
        Integer count = jdbcTemplate.queryForObject(getCountQuery(), makeValues(value), Integer.class);

        return (count != null) ? count : 0;
    }

    private ResultSetExtractor<T> getSingleObjectExtractor() {
        return resultSet -> {
            if (resultSet.next()) {
                return createEntityFromResultSet(resultSet);
            } else {
                return null;
            }
        };
    }

    private ResultSetExtractor<List<T>> getListExtractor() {
        return rs -> {
            List<T> entities = new ArrayList<>();
            while (rs.next()) {
                entities.add(createEntityFromResultSet(rs));
            }
            return entities;
        };
    }

    protected Long getGeneratedId(PreparedStatement statement) throws SQLException {
        try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
            if (generatedKeys.next()) {
                return generatedKeys.getLong(1);
            } else {
                throw new SQLException("Creating account failed, no ID obtained.");
            }
        }
    }
}
