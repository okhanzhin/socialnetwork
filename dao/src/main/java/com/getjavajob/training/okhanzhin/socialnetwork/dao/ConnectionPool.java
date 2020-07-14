package com.getjavajob.training.okhanzhin.socialnetwork.dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class ConnectionPool {
    private static final String CONFIG_FILE = "configDB.properties";
    private static final String DRIVER = "database.driver";
    private static final String URL = "database.url";
    private static final String USER = "database.user";
    private static final String PASSWORD = "database.password";
    private static final int TIMEOUT = 0;
    private static ConnectionPool instance;
    private static ThreadLocal<Connection> threadLocal;
    private final int INITIAL_POOL_SIZE;
    private String url;
    private String user;
    private String password;
    private BlockingQueue<Connection> pool;

    private ConnectionPool(int initial_pool_size) {
        INITIAL_POOL_SIZE = initial_pool_size;
        try {
            Properties props = new Properties();
            props.load(this.getClass().getClassLoader().getResourceAsStream(CONFIG_FILE));
            String driver = props.getProperty(DRIVER);
            Class.forName(driver);
            this.url = props.getProperty(URL);
            this.user = props.getProperty(USER);
            this.password = props.getProperty(PASSWORD);
            pool = new LinkedBlockingQueue<>(INITIAL_POOL_SIZE); // may be need LinkedBlockingQueue

            Connection connection;
            for (int i = 0; i < INITIAL_POOL_SIZE; i++) {
                connection = createConnection(url, user, password);
                pool.offer(connection);
            }
        } catch (IOException | ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public static ConnectionPool getPool() {
        if (instance == null) {

            synchronized (ConnectionPool.class) {
                if (instance == null) {
                    instance = new ConnectionPool(5);
                    threadLocal = new ThreadLocal<>();
                }
            }
        }
        return instance;
    }

    public static ConnectionPool getPool(int poolSize) {
        if (instance == null) {

            synchronized (ConnectionPool.class) {
                if (instance == null) {
                    instance = new ConnectionPool(poolSize);
                    threadLocal = new ThreadLocal<>();
                }
            }
        }
        return instance;
    }

    private Connection createConnection(String url, String user, String password) throws SQLException {
        Connection connection = DriverManager.getConnection(url, user, password);
        connection.setAutoCommit(false);
        return connection;
    }

    private Connection setConnection() throws Exception {
        try {
            Connection connection = pool.take();
            if (!connection.isValid(TIMEOUT)) {
                connection.close();
                connection = createConnection(URL, USER, PASSWORD);
                pool.offer(connection); // put connection back to pool
            }
            threadLocal.set(connection);
            return connection;
        } catch (InterruptedException | SQLException e) {
            throw new Exception();
        }
    }

    public synchronized Connection getConnection() {
        Connection connection = null;

        try {
            while (pool.size() == 0) {
                System.out.println("Pool size equals 0");
                wait();
            }
            connection = pool.poll();

            if (connection != null) {
                System.out.println("Connection not null");
                if (!connection.isValid(TIMEOUT)) {
                    connection.close();
                    connection = pool.poll();
                }
                return connection;
            }
        } catch (SQLException | InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Connection is null");
        return getConnection();
    }

    public synchronized void close(Connection connection) {
        try {
            if (!connection.isValid(TIMEOUT)) {
                System.out.println("Connection closed by timeout");
                connection.close();
                connection = createConnection(URL, USER, PASSWORD);
            }
            System.out.println("pool.size() Before = " + pool.size());
            pool.offer(connection);
            System.out.println("pool.size() After = " + pool.size());
            notify();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}