package com.getjavajob.training.okhanzhin.socialnetwork.dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

public class ConnectionPool {
    private static final String CONFIG_FILE = "configDB.properties";
    private static final int INITIAL_POOL_SIZE = 10;
    private static final int TIMEOUT = 1;

    private String url;
    private String user;
    private String password;
    private BlockingQueue<Connection> pool;

    public ConnectionPool() {
        try {
            Properties props = new Properties();
            props.load(this.getClass().getClassLoader().getResourceAsStream(CONFIG_FILE));
            String driver = props.getProperty("database.driver");
            Class.forName(driver);
            this.url = props.getProperty("database.url");
            this.user = props.getProperty("database.user");
            this.password = props.getProperty("database.password");
            pool = new ArrayBlockingQueue<>(INITIAL_POOL_SIZE);

            for (int i = 0; i < INITIAL_POOL_SIZE; i++) {
                Connection connection = createConnection(url, user, password);
                pool.add(connection);
            }
        } catch (IOException | ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    private Connection createConnection(String url, String user, String password) throws SQLException {
        Connection connection = DriverManager.getConnection(url, user, password);
        connection.setAutoCommit(false);
        return connection;
    }

    public Connection getConnection() {
        try {
            Connection connection = pool.peek();
            if (connection != null && connection.isValid(TIMEOUT)) {
                return pool.poll(TIMEOUT, TimeUnit.SECONDS);
            } else {
                if (connection != null) {
                    connection.close();
                }
                return getConnection();
            }
        } catch (SQLException | InterruptedException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void close(Connection connection) {
        try {
            if (connection.isValid(TIMEOUT)) {
                pool.add(connection);
            } else {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}