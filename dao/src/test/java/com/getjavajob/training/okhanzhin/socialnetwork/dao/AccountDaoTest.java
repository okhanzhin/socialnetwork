package com.getjavajob.training.okhanzhin.socialnetwork.dao;

import com.getjavajob.training.okhanzhin.socialnetwork.domain.Account;
import com.getjavajob.training.okhanzhin.socialnetwork.domain.Group;
import com.getjavajob.training.okhanzhin.socialnetwork.domain.Member;
import com.getjavajob.training.okhanzhin.socialnetwork.domain.Request;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:dao-context.xml", "classpath*:test-context.xml"})
public class AccountDaoTest extends PrepareDaoTest {
    @Autowired
    private AccountDao accountDao;
    @Autowired
    private GroupDao groupDao;
    @Autowired
    private MemberDao memberDao;
    @Autowired
    private RequestDao requestDao;

    @Test
    public void create() {
        Account exceptedAccount = new Account("Petrov", "Petr", "petrov@gmail.com", "PetrovPas", LocalDate.now());
        Account actualAccount = accountDao.create(exceptedAccount);

        exceptedAccount.setAccountID(actualAccount.getAccountID());
        assertEquals(exceptedAccount, actualAccount);
    }

    @Test
    public void getById() {
        Account exceptedAccount = accountDao.create(new Account("Khanzhin", "Oleg", "khanzhin@gmail.com", "Pas", LocalDate.now()));
        Account returnedAccount = accountDao.getById(exceptedAccount.getAccountID());

        assertEquals("Khanzhin", returnedAccount.getSurname());
        assertEquals("Oleg", returnedAccount.getName());
        assertEquals("khanzhin@gmail.com", returnedAccount.getEmail());
        assertEquals("Pas", returnedAccount.getPassword());
    }

    @Test
    public void searchEntities() {
        List<Account> exceptedAccounts = new ArrayList<>();
        exceptedAccounts.add(accountDao.create(new Account("SurnameOne", "NameOne", "name@gmail.com", "one", LocalDate.now())));
        exceptedAccounts.add(accountDao.create(new Account("SurnameTwo", "NameTwo", "surnameTwo@gmail.com", "two", LocalDate.now())));
        exceptedAccounts.add(accountDao.create(new Account("SurnameThee", "NameThree", "surnameThree@gmail.com", "three", LocalDate.now())));

        List<Account> actualAccounts = accountDao.searchEntities("Surname", 1);

        assertEquals(exceptedAccounts, actualAccounts);
    }

    @Test
    public void getCountOfSearchResults() {
        List<Account> exceptedAccounts = new ArrayList<>();
        exceptedAccounts.add(accountDao.create(new Account("SurnameOne", "NameOne", "name@gmail.com", "one", LocalDate.now())));
        exceptedAccounts.add(accountDao.create(new Account("SurnameTwo", "NameTwo", "surnameTwo@gmail.com", "two", LocalDate.now())));
        exceptedAccounts.add(accountDao.create(new Account("SurnameThee", "NameThree", "surnameThree@gmail.com", "three", LocalDate.now())));

        assertEquals(exceptedAccounts.size(), accountDao.getCountOfSearchResults("Surname"));
    }

    @Test
    public void getByEmail() {
        Account exceptedAccount = accountDao.create(new Account("Vodkin", "Ivan", "vodkin@gmail.com", "Pas", LocalDate.now()));
        Account returnedAccount = accountDao.getByEmail(exceptedAccount.getEmail());

        assertEquals("Vodkin", returnedAccount.getSurname());
        assertEquals("Ivan", returnedAccount.getName());
        assertEquals("vodkin@gmail.com", returnedAccount.getEmail());
        assertEquals("Pas", returnedAccount.getPassword());
    }

    @Test
    public void update() {
        Account testAccountForUpdate = new Account("Sidorov", "Sidr", "sidorov@gmail.com", "SidorovPas", LocalDate.now());
        Account returnedAccount = accountDao.create(testAccountForUpdate);
        long returnedId = returnedAccount.getAccountID();
        testAccountForUpdate.setAccountID(returnedId);

        assertEquals(testAccountForUpdate, accountDao.getById(returnedId));

        testAccountForUpdate.setMiddlename("Petrovich");
        testAccountForUpdate.setDateOfBirth(LocalDate.of(2000, 10, 20));
        testAccountForUpdate.setSkype("12345");
        testAccountForUpdate.setIcq("09876");
        testAccountForUpdate.setHomeAddress("HomeAddress1");
        testAccountForUpdate.setWorkAddress("WorkAddress1");
        testAccountForUpdate.setAddInfo("Some info about account 1");
        accountDao.update(testAccountForUpdate);
        Account accountAfterUpdate = accountDao.getById(returnedId);

        assertEquals(returnedId, accountAfterUpdate.getAccountID());
        assertEquals("Petrovich", accountAfterUpdate.getMiddlename());
        assertEquals(LocalDate.of(2000, 10, 20), accountAfterUpdate.getDateOfBirth());
        assertEquals("12345", accountAfterUpdate.getSkype());
        assertEquals("09876", accountAfterUpdate.getIcq());
        assertEquals("HomeAddress1", accountAfterUpdate.getHomeAddress());
        assertEquals("WorkAddress1", accountAfterUpdate.getWorkAddress());
        assertEquals("Some info about account 1", accountAfterUpdate.getAddInfo());
    }

    @Test
    public void delete() {
        Account accountForRemoval = new Account("Ivanov", "Ivan", "ivanov@gmail.com", "IvanovPas", LocalDate.now());
        accountForRemoval = accountDao.create(accountForRemoval);
        long returnedId = accountForRemoval.getAccountID();

        assertNotNull(accountDao.getById(returnedId));
        accountDao.deleteById(returnedId);
        assertNull(accountDao.getById(returnedId));
    }


    @Test
    public void getAccountsForGroup() {
        Group testGroup = groupDao.create(new Group("TestGroupOne"));
        List<Account> exceptedAccounts = new ArrayList<>();
        exceptedAccounts.add(accountDao.create(new Account("SurnameOne", "NameOne", "name@gmail.com", "one", LocalDate.now())));
        exceptedAccounts.add(accountDao.create(new Account("SurnameTwo", "NameTwo", "surnameTwo@gmail.com", "two", LocalDate.now())));

        for (Account account : exceptedAccounts) {
            memberDao.create(new Member(account.getAccountID(), testGroup.getGroupID(), Member.Status.USER));
        }

        assertEquals(exceptedAccounts, accountDao.getAccountsForGroup(testGroup));
    }

    @Test
    public void getUnconfirmedRequestAccountsForGroup() {
        Group testGroup = groupDao.create(new Group("TestGroupTwo"));
        List<Account> exceptedAccounts = new ArrayList<>();
        exceptedAccounts.add(accountDao.create(new Account("SurnameThree", "NameThree", "surnameThree@gmail.com", "three", LocalDate.now())));
        exceptedAccounts.add(accountDao.create(new Account("SurnameFour", "NameFour", "surnameFour@gmail.com", "four", LocalDate.now())));

        for (Account account : exceptedAccounts) {
            requestDao.create(new Request(account.getAccountID(), testGroup.getGroupID(), "group", Request.Status.UNCONFIRMED));
        }

        List<Account> actualAccounts = accountDao.getUnconfirmedRequestAccountsForGroup(testGroup);
        actualAccounts.forEach(System.out::println);

        assertEquals(exceptedAccounts, actualAccounts);
    }
}