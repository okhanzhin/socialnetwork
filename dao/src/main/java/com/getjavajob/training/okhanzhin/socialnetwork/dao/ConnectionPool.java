package com.getjavajob.training.okhanzhin.socialnetwork.dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.atomic.AtomicInteger;

public class ConnectionPool {
    private static final String CONFIG_FILE = "configDB.properties";
    private static final String DRIVER = "database.driver";
    private static final String URL = "database.url";
    private static final String USER = "database.user";
    private static final String PASSWORD = "database.password";
    private static final int DEFAULT_POOL_SIZE = 10;
    private final int poolSize;
    private static final int TIMEOUT = 0;
    private static ConnectionPool instance;
    private AtomicInteger connectionCount;
    private String url;
    private String user;
    private String password;
    private ConcurrentLinkedDeque<Connection> deque;

    private ConnectionPool(int poolSize) {
        try {
            Properties props = new Properties();
            props.load(this.getClass().getClassLoader().getResourceAsStream(CONFIG_FILE));
            String driver = props.getProperty(DRIVER);
            Class.forName(driver);
            this.url = props.getProperty(URL);
            this.user = props.getProperty(USER);
            this.password = props.getProperty(PASSWORD);
            this.poolSize = poolSize;
            connectionCount = new AtomicInteger(0);
            deque = new ConcurrentLinkedDeque<Connection>();
        } catch (IOException | ClassNotFoundException e) {
            throw new DaoException("Can't create pool instance.", e);
        }
    }

    public static ConnectionPool getPool() {
        if (instance == null) {
            synchronized (ConnectionPool.class) {
                if (instance == null) {
                    instance = new ConnectionPool(DEFAULT_POOL_SIZE);
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
                }
            }
        }
        return instance;
    }

    private Connection createConnection(String url, String user, String password) {
        try {
            Connection connection = DriverManager.getConnection(url, user, password);
            System.out.println("Create new Connection");
            connection.setAutoCommit(false);
            return connection;
        } catch (SQLException e) {
            throw new DaoException("Can't create new connection.", e);
        }
    }

    public synchronized Connection getConnection() {
        try {
            while (true) {
                if (connectionCount.get() < poolSize && deque.size() == 0) {
                    connectionCount.incrementAndGet();
                    return createConnection(url, user, password);
                } else {
                    while (deque.size() > 0) {
                        Connection connection = deque.pollLast();

                        if (connection == null) throw new AssertionError();
                        if (connection.isValid(TIMEOUT)) {
                            System.out.println("Valid connection " + "Current pool size " + deque.size());
                            return connection;
                        } else {
                            System.out.println("Invalid connection" + "Current pool size " + deque.size());
                            connectionCount.decrementAndGet();
                        }
                    }

                    if (connectionCount.get() == poolSize) {
                        System.out.println("Waiting for connection");
                        wait();
                    }
                }
            }
        } catch (InterruptedException | SQLException e) {
            throw new DaoException("Failed to get connection from pool.", e);
        }
    }

    public synchronized void returnConnection(Connection connection) {
        try {
            if (deque.size() == poolSize) {
                connection.close();
            }
            deque.addLast(connection);
            connectionCount.decrementAndGet();
            notify();
        } catch (SQLException e) {
            throw new DaoException("Connection can't be returned.", e);
        }
        System.out.println(connection + " returned. Current pool size is: " + deque.size());
    }
}