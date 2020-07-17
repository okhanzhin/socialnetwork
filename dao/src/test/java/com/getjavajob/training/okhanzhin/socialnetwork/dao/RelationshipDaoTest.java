package com.getjavajob.training.okhanzhin.socialnetwork.dao;

import com.getjavajob.training.okhanzhin.socialnetwork.domain.Account;
import org.junit.*;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class RelationshipDaoTest extends PrepareDaoTest {
    private static final String TRUNCATE_TABLE = "TruncateTable.sql";

    private static AbstractDao<Account> accountDao;
    private static RelationshipDao relationDao;

    @BeforeClass
    public static void setUp() {
        accountDao = new AccountDao();
        relationDao = new RelationshipDao();
    }

    @After
    public void clearDataBase() {
        PrepareDaoTest.executeQuery(TRUNCATE_TABLE);
    }

    @Test
    public void sendRequest() {
        Account account1 = new Account("One", "One", "onee221@gmail.com", "onepass");
        accountDao.create(account1);
        Account account2 = new Account("Two", "Two", "twotwo@gmail.com", "twopass");
        accountDao.create(account2);

        int accountID1 = accountDao.getById(1).getAccountID();
        int accountID2 = accountDao.getById(2).getAccountID();

        assertTrue(relationDao.sendRequest(accountID1, accountID2));

        String excepted = "1_2_0_1";
        assertEquals(excepted, relationDao.getRelationStringById(accountID1));
    }

    @Test
    public void acceptRequest() {
        Account account1 = new Account("One", "One", "oneToOne@gmail.com", "onepass");
        accountDao.create(account1);
        Account account2 = new Account("Two", "Two", "twoOnTwo@gmail.com", "twopass");
        accountDao.create(account2);

        int accountID1 = accountDao.getById(1).getAccountID();
        int accountID2 = accountDao.getById(2).getAccountID();

        assertTrue(relationDao.sendRequest(accountID1, accountID2));
        assertTrue(relationDao.acceptRequest(accountID2, accountID1));
        String excepted = "1_2_1_2";

        assertEquals(excepted, relationDao.getRelationStringById(accountID1));
    }

    @Test
    public void declineRequest() {
        Account account1 = new Account("One", "One", "oneoneone@gmail.com", "onepass");
        accountDao.create(account1);
        Account account2 = new Account("Two", "Two", "twotwotwo@gmail.com", "twopass");
        accountDao.create(account2);

        int accountID1 = accountDao.getById(1).getAccountID();
        int accountID2 = accountDao.getById(2).getAccountID();

        assertTrue(relationDao.sendRequest(accountID1, accountID2));
        assertTrue(relationDao.declineRequest(accountID2, accountID1));
        String excepted = "1_2_2_2";

        assertEquals(excepted, relationDao.getRelationStringById(accountID1));
    }

    @Test
    public void breakRelationship() {
        Account account1 = new Account("One", "One", "oneone@gmail.com", "onepass");
        accountDao.create(account1);
        Account account2 = new Account("Two", "Two", "two222@gmail.com", "twopass");
        accountDao.create(account2);

        int accountID1 = accountDao.getById(1).getAccountID();
        int accountID2 = accountDao.getById(2).getAccountID();

        assertTrue(relationDao.sendRequest(accountID1, accountID2));
        assertTrue(relationDao.breakRelationship(accountID2, accountID1));
        String excepted = "1_2_0_2";

        assertEquals(excepted, relationDao.getRelationStringById(accountID1));
    }

    @Test
    public void blockAccount() {
        Account account1 = new Account("One", "One", "oneone1-1@gmail.com", "onepass");
        accountDao.create(account1);
        Account account2 = new Account("Two", "Two", "twoAddress@gmail.com", "twopass");
        accountDao.create(account2);

        int accountID1 = accountDao.getById(1).getAccountID();
        int accountID2 = accountDao.getById(2).getAccountID();

        assertTrue(relationDao.sendRequest(accountID1, accountID2));
        assertTrue(relationDao.blockAccount(accountID2, accountID1));
        String excepted = "1_2_3_2";

        assertEquals(excepted, relationDao.getRelationStringById(accountID1));
    }

    @Test
    public void getFriendsList() {
        List<Account> exceptedList = new ArrayList<>();

        Account account1 = new Account("One", "One", "oneone1-1-1@gmail.com", "onepass");
        account1.setAccountID(1);
        exceptedList.add(account1);
        Account account2 = new Account("Two", "Two", "twoAddress2@gmail.com", "twopass");
        account1.setAccountID(2);
        exceptedList.add(account2);
        Account account3 = new Account("Three", "Three", "threeAddress@gmail.com", "threepass");
        account1.setAccountID(3);
        exceptedList.add(account3);

        for (Account account : exceptedList) {
            accountDao.create(account);
        }

        exceptedList.remove(account1);

        int accountID1 = accountDao.getById(1).getAccountID();
        int accountID2 = accountDao.getById(2).getAccountID();
        int accountID3 = accountDao.getById(3).getAccountID();

        assertTrue(relationDao.sendRequest(accountID1, accountID2));
        assertTrue(relationDao.sendRequest(accountID1, accountID3));
        assertTrue(relationDao.acceptRequest(accountID2, accountID1));
        assertTrue(relationDao.acceptRequest(accountID3, accountID1));

        System.out.println(relationDao.getFriendsList(account1).toString());

        assertEquals(exceptedList.toString(), relationDao.getFriendsList(account1).toString());
    }
}