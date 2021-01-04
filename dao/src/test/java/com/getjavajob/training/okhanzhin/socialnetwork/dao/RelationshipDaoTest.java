package com.getjavajob.training.okhanzhin.socialnetwork.dao;

import com.getjavajob.training.okhanzhin.socialnetwork.domain.Account;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:dao-context.xml", "classpath*:test-context.xml"})
public class RelationshipDaoTest extends PrepareDaoTest {
    @Autowired
    private AccountDao accountDao;
    @Autowired
    private RelationshipDao relationDao;

    @Test
    public void sendRequest() {
        Account account1 = accountDao.create(
                new Account("One", "One", "onee221@gmail.com", "onepass", LocalDate.now()));
        Account account2 = accountDao.create(
                new Account("Two", "Two", "twotwo@gmail.com", "twopass", LocalDate.now()));

        long accountID1 = account1.getAccountID();
        long accountID2 = account2.getAccountID();

        relationDao.createRelation(accountID1, accountID2);

        String excepted = "1_2_0_1";
        assertEquals(excepted, relationDao.getRelationStringById(accountID1));
    }

    @Test
    public void acceptRequest() {
        Account account1 = accountDao.create(
                new Account("One", "One", "onee221@gmail.com", "onepass", LocalDate.now()));
        Account account2 = accountDao.create(
                new Account("Two", "Two", "twotwo@gmail.com", "twopass", LocalDate.now()));

        long accountID1 = account1.getAccountID();
        long accountID2 = account2.getAccountID();

        relationDao.createRelation(accountID1, accountID2);
        relationDao.acceptRelation(accountID2, accountID1);

        String excepted = "1_2_1_2";
        assertEquals(excepted, relationDao.getRelationStringById(accountID1));
    }

    @Test
    public void declineRequest() {
        Account account1 = accountDao.create(
                new Account("One", "One", "onee221@gmail.com", "onepass", LocalDate.now()));
        Account account2 = accountDao.create(
                new Account("Two", "Two", "twotwo@gmail.com", "twopass", LocalDate.now()));

        long accountID1 = account1.getAccountID();
        long accountID2 = account2.getAccountID();

        relationDao.createRelation(accountID1, accountID2);
        relationDao.declineRelation(accountID2, accountID1);

        String excepted = "1_2_2_2";
        assertEquals(excepted, relationDao.getRelationStringById(accountID1));
    }

    @Test
    public void breakRelationship() {
        Account account1 = accountDao.create(
                new Account("One", "One", "onee221@gmail.com", "onepass", LocalDate.now()));
        Account account2 = accountDao.create(
                new Account("Two", "Two", "twotwo@gmail.com", "twopass", LocalDate.now()));

        long accountID1 = account1.getAccountID();
        long accountID2 = account2.getAccountID();

        relationDao.createRelation(accountID1, accountID2);
        relationDao.breakRelation(accountID2, accountID1);

        String excepted = "1_2_0_2";
        assertEquals(excepted, relationDao.getRelationStringById(accountID1));
    }

    @Test
    public void blockAccount() {
        Account account1 = accountDao.create(
                new Account("One", "One", "onee221@gmail.com", "onepass", LocalDate.now()));
        Account account2 = accountDao.create(
                new Account("Two", "Two", "twotwo@gmail.com", "twopass", LocalDate.now()));

        long accountID1 = account1.getAccountID();
        long accountID2 = account2.getAccountID();

        relationDao.createRelation(accountID1, accountID2);
        relationDao.blockAccount(accountID2, accountID1);

        String excepted = "1_2_3_2";
        assertEquals(excepted, relationDao.getRelationStringById(accountID1));
    }

    @Test
    public void getFriendsList() {
        List<Account> exceptedList = new ArrayList<>();

        Account account1 = accountDao.create(
                new Account("One", "One", "onee221@gmail.com", "onepass", LocalDate.now()));
        exceptedList.add(account1);
        Account account2 = accountDao.create(
                new Account("Two", "Two", "twotwo@gmail.com", "twopass", LocalDate.now()));
        exceptedList.add(account2);
        Account account3 = accountDao.create(
                new Account("Three", "Three", "threeAddress@gmail.com", "threepass", LocalDate.now()));
        exceptedList.add(account3);

        exceptedList.remove(account1);

        System.out.println(Arrays.toString(exceptedList.toArray()));

        long accountID1 = account1.getAccountID();
        long accountID2 = account2.getAccountID();
        long accountID3 = account3.getAccountID();

        relationDao.createRelation(accountID1, accountID2);
        relationDao.createRelation(accountID1, accountID3);
        relationDao.acceptRelation(accountID2, accountID1);
        relationDao.acceptRelation(accountID3, accountID1);

        System.out.println(relationDao.getFriendsList(account1).toString());

        assertEquals(exceptedList.toString(), relationDao.getFriendsList(account1).toString());
    }
}