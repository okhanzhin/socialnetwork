package com.getjavajob.training.okhanzhin.socialnetwork.dao;

import com.getjavajob.training.okhanzhin.socialnetwork.domain.Account;
import org.h2.tools.RunScript;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;

import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class PrepareDaoTest {
    private static final String CREATE_TEST_TABLES = "CreateTables.sql";
    private static final String DROP_TEST_TABLES = "DropTables.sql";
    private static final String TRUNCATE_TABLE = "TruncateTable.sql";
    private static final String CONFIG_FILE = "configDB.properties";

    @BeforeClass
    public static void initDatabase() {
        executeQuery(CREATE_TEST_TABLES);
    }

//    @AfterClass
//    public static void clearDatabase() {
//        executeQuery(DROP_TEST_TABLES);
//    }


    public static void executeQuery(String dbScript) {
        try {
            Properties props = new Properties();
            props.load(PrepareDaoTest.class.getClassLoader().getResourceAsStream(CONFIG_FILE));
            String driver = props.getProperty("database.driver");
            Class.forName(driver);
            String url = props.getProperty("database.url");
            String user = props.getProperty("database.user");
            String password = props.getProperty("database.password");
            Connection connection = DriverManager.getConnection(url, user, password);
            connection.setAutoCommit(false);
            InputStreamReader inputScript = new InputStreamReader(PrepareDaoTest.class.getClassLoader().getResourceAsStream(dbScript));
            RunScript.execute(connection, inputScript);
            connection.commit();
            connection.close();
        } catch (IOException | ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }
}