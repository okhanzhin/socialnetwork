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
    private static final int DEFAULT_POOL_SIZE = 5;
    private static final int TIMEOUT = 0;
    private static ConnectionPool instance;
    //    private static ThreadLocal<Connection> threadLocal;
    private String url;
    private String user;
    private String password;
    private BlockingQueue<Connection> pool;

    private ConnectionPool(int poolSize) {
        try {
            Properties props = new Properties();
            props.load(this.getClass().getClassLoader().getResourceAsStream(CONFIG_FILE));
            String driver = props.getProperty(DRIVER);
            Class.forName(driver);
            this.url = props.getProperty(URL);
            this.user = props.getProperty(USER);
            this.password = props.getProperty(PASSWORD);
            // TODO: 16.07.20 ask correct don't use local var for pool size
            pool = new LinkedBlockingQueue<>(poolSize);
            fillPool(poolSize);
        } catch (IOException | ClassNotFoundException e) {
            throw new DaoException("Can't create pool instance.", e);
        }
    }

    public static ConnectionPool getPool() {
        if (instance == null) {
            synchronized (ConnectionPool.class) {
                if (instance == null) {
                    instance = new ConnectionPool(DEFAULT_POOL_SIZE);
//                    threadLocal = new ThreadLocal<>();
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
//                    threadLocal = new ThreadLocal<>();
                }
            }
        }
        return instance;
    }

    private Connection createConnection(String url, String user, String password) {
        try {
            Connection connection = DriverManager.getConnection(url, user, password);
            connection.setAutoCommit(false);
            return connection;
        } catch (SQLException e) {
            throw new DaoException("Can't create new connection.", e);
        }

    }

//    private Connection setConnection() throws Exception {
//        try {
//            Connection connection = pool.take();
//            if (!connection.isValid(TIMEOUT)) {
//                connection.close();
//                connection = createConnection(URL, USER, PASSWORD);
//                pool.offer(connection); // put connection back to pool
//            }
//            threadLocal.set(connection);
//            return connection;
//        } catch (InterruptedException | SQLException e) {
//            throw new Exception();
//        }
//    }

    public synchronized Connection getConnection() {
        try {
            while (pool.size() == 0) {
                System.out.println("Pool size equals 0");
                wait();
            }

            if (!pool.peek().isValid(TIMEOUT)) {
                pool.clear();
                fillPool(DEFAULT_POOL_SIZE);
            }

            Connection connection = pool.poll();

            while (true) {
                if (connection != null) {
                    System.out.println("Connection not null");
                    if (!connection.isValid(TIMEOUT)) {
                        pool.remove(connection);
                        connection = pool.poll();
                    }
                    return connection;
                }
            }
        } catch (SQLException | InterruptedException e) {
            throw new DaoException("Can't getting connection from pool.", e);
        }
    }

    private void fillPool(int defaultPoolSize) {
        Connection connection;
        for (int i = 0; i < defaultPoolSize; i++) {
            connection = createConnection(url, user, password);
            pool.offer(connection);
        }
    }

    public synchronized void returnConnection(Connection connection) {
        try {
            if (pool.size() == DEFAULT_POOL_SIZE) {
                connection.close();
            }
            pool.put(connection);
            notify();
        } catch (InterruptedException | SQLException e) {
            throw new DaoException("Connection can't be returned.", e);
        }
        System.out.println(connection + " returned. Current pool size is: " + pool.size());
    }
}