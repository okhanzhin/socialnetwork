package com.getjavajob.training.okhanzhin.socialnetwork.dao;

import com.getjavajob.training.okhanzhin.socialnetwork.domain.Account;
import org.junit.*;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class RelationshipDaoTest extends PrepareDaoTest {
    private static final String CONNECT_ACCOUNTS = "TruncateTable.sql";

    private static AbstractDao<Account> accountDao = new AccountDao();
    private static List<Account> accounts = new ArrayList<>();
    private static RelationshipDao relationDao;

    @BeforeClass
    public static void setUp() {
        relationDao = new RelationshipDao();
    }

    @Ignore
    public void clearDataBase() {
        PrepareDaoTest.executeQuery("TruncateTable.sql");
        Account account1 = accountDao.getById(1);
        Account account2 = accountDao.getById(2);

        accountDao.delete(account1);
        accountDao.delete(account2);
    }

    @Test
    public void sendRequest() {
        Account account1 = new Account("One", "One", "onee221@gmail.com", "onepass");
        accountDao.create(account1);
        Account account2 = new Account("Two", "Two", "twotwo@gmail.com", "twopass");
        accountDao.create(account2);

        int first_ID = accountDao.getById(1).getAccount_ID();
        int second_ID = accountDao.getById(2).getAccount_ID();

        assertTrue(relationDao.sendRequest(first_ID, second_ID));

        String excepted = "1_2_0_1";
        assertEquals(excepted, relationDao.getRelationStringById(first_ID));
    }

    @Test
    public void acceptRequest() {
        Account account1 = new Account("One", "One", "onee221@gmail.com", "onepass");
        accountDao.create(account1);
        Account account2 = new Account("Two", "Two", "twotwo@gmail.com", "twopass");
        accountDao.create(account2);

        assertTrue(relationDao.sendRequest(accountDao.getById(1).getAccount_ID(), accountDao.getById(2).getAccount_ID()));
        assertTrue(relationDao.acceptRequest(accountDao.getById(2).getAccount_ID(), accountDao.getById(1).getAccount_ID()));
        String excepted = "1_2_1_2";

        assertEquals(excepted, relationDao.getRelationStringById(accountDao.getById(1).getAccount_ID()));
    }

//    @Test
//    @Ignore
//    public void declineRequest() {
//        int first_ID = accountDao.getById(1).getAccount_ID();
//        int second_ID = accountDao.getById(2).getAccount_ID();
//
//        assertTrue(relationDao.declineRequest(second_ID, first_ID));
//    }
//
//    @Test
//    @Ignore
//    public void breakRelationship() {
//        int first_ID = accountDao.getById(1).getAccount_ID();
//        int second_ID = accountDao.getById(2).getAccount_ID();
//
//        assertTrue(relationDao.breakRelationship(second_ID, first_ID));
//    }
//
//    @Test
//    @Ignore
//    public void blockAccount() {
//        int first_ID = accountDao.getById(1).getAccount_ID();
//        int second_ID = accountDao.getById(2).getAccount_ID();
//
//        assertTrue(relationDao.blockAccount(second_ID, first_ID));
//    }
//
//    @Test
//    @Ignore
//    public void getFriendsList() {
//        List<Integer> idnumbers = new ArrayList<>();
//        for (int i = 1; i <= 5; i++) {
//            idnumbers.add(accountDao.getById(i).getAccount_ID());
//        }
//        System.out.println(idnumbers.toString());
//
//        relationDao.sendRequest(idnumbers.get(0), idnumbers.get(1));
//        relationDao.sendRequest(idnumbers.get(0), idnumbers.get(2));
//        relationDao.sendRequest(idnumbers.get(0), idnumbers.get(3));
//        relationDao.acceptRequest(idnumbers.get(1), idnumbers.get(0));
//        relationDao.acceptRequest(idnumbers.get(2), idnumbers.get(0));
//        relationDao.acceptRequest(idnumbers.get(3), idnumbers.get(0));
//    }
}