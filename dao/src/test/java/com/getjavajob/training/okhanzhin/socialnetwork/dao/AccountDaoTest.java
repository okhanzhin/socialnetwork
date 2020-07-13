package com.getjavajob.training.okhanzhin.socialnetwork.dao;

import com.getjavajob.training.okhanzhin.socialnetwork.domain.Account;

import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class AccountDaoTest extends PrepareDaoTest {
    private AbstractDao<Account> testAccountDao;

    @Before
    public void setUp() {
        this.testAccountDao = new AccountDao();
    }

    @Test
    public void create() {
        Account exceptedAccount = new Account("Petrov", "Petr", "petrov@gmail.com", "PetrovPas");
        Account actualAccount = testAccountDao.create(exceptedAccount);

        exceptedAccount.setAccount_ID(1);
        assertEquals(exceptedAccount, actualAccount);
    }


    @Test
    public void getById() {
        Account returnAccount = testAccountDao.getById(1);

        assertEquals("Petrov", returnAccount.getSurname());
        assertEquals("Petr", returnAccount.getName());
        assertEquals("petrov@gmail.com", returnAccount.getEmail());
        assertEquals("PetrovPas", returnAccount.getPassword());
    }


    @Test
    public void update() {
        Account testAccountForUpdate = new Account("Petrov", "Petr", "petrov@gmail.com", "PetrovPas");
        testAccountForUpdate.setAccount_ID(1);
        testAccountForUpdate.setMiddlename("Petrovich");
        testAccountForUpdate.setDateOfBirth(LocalDate.of(2000, 10, 20));
        testAccountForUpdate.setHomePhone("9202022020");
        testAccountForUpdate.setWorkPhone("9303033030");
        testAccountForUpdate.setSkype("12345");
        testAccountForUpdate.setIcq("09876");
        testAccountForUpdate.setHomeAddress("HomeAddress1");
        testAccountForUpdate.setWorkAddress("WorkAddress1");
        testAccountForUpdate.setAddInfo("Some info about account 1");

        testAccountDao.update(testAccountForUpdate);
        Account returnAccount = testAccountDao.getById(1);

        assertEquals("Petrovich", returnAccount.getMiddlename());
        assertEquals(LocalDate.of(2000, 10, 20), returnAccount.getDateOfBirth());
        assertEquals("9202022020", returnAccount.getHomePhone());
        assertEquals("9303033030", returnAccount.getWorkPhone());
        assertEquals("12345", returnAccount.getSkype());
        assertEquals("09876", returnAccount.getIcq());
        assertEquals("HomeAddress1", returnAccount.getHomeAddress());
        assertEquals("WorkAddress1", returnAccount.getWorkAddress());
        assertEquals("Some info about account 1", returnAccount.getAddInfo());
    }


    @Test
    public void delete() {
        Account accountForRemoval = new Account("Ivanov", "Ivan", "ivanov@gmail.com", "IvanovPas");
        accountForRemoval = testAccountDao.create(accountForRemoval);

        assertNotNull(testAccountDao.getById(2));
        testAccountDao.delete(accountForRemoval);
        assertNull(testAccountDao.getById(2));
    }


    @Test
    public void getAll() {
        List<Account> exceptedList = new ArrayList<>();

        Account firstAccount = testAccountDao.getById(1);
        firstAccount.setDateOfBirth(LocalDate.of(2020, 2, 20));
        testAccountDao.update(firstAccount);
        exceptedList.add(testAccountDao.getById(1));

        Account secondAccount = new Account("Ivanov", "Ivan", "ivanov@gmail.com", "IvanovPas");
        secondAccount.setDateOfBirth(LocalDate.of(2020, 3, 30));
        exceptedList.add(secondAccount);
        testAccountDao.create(secondAccount);
        testAccountDao.update(secondAccount);

        List<Account> actualList = testAccountDao.getAll();

        assertEquals(exceptedList, actualList);
    }
}