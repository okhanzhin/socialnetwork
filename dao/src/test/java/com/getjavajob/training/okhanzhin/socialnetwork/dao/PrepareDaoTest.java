package com.getjavajob.training.okhanzhin.socialnetwork.dao;

import org.junit.After;
import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import static java.util.Objects.requireNonNull;

@ContextConfiguration(locations = {"classpath:dao-context.xml", "classpath:test-context.xml"})
public class PrepareDaoTest {
    private static final String CREATE_TEST_TABLES = "CreateTables.sql";
    private static final String TRUNCATE_TABLE = "TruncateTable.sql";
    private static final String DROP_TEST_TABLES = "DropTables.sql";

    @Autowired
    protected JdbcTemplate jdbcTemplate;

    public void executeQuery(String dbScript) {
        StringBuilder sb = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new InputStreamReader
                (requireNonNull(PrepareDaoTest.class.getClassLoader().getResourceAsStream(dbScript))))) {
            while (br.ready()) {
                sb.append(br.readLine());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        jdbcTemplate.execute(sb.toString());
    }

    @Before
    public void initDatabase() {
        System.out.println("CREATING TABLES");
        executeQuery(CREATE_TEST_TABLES);
    }

    @After
    public void postTest() {
        System.out.println("Post Test Processing.");
        executeQuery(TRUNCATE_TABLE);
    }

    @After
    public void clearDataBase() {
        System.out.println("CLEAR TABLES");
        executeQuery(DROP_TEST_TABLES);
    }
}
