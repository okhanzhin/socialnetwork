package com.getjavajob.training.okhanzhin.socialnetwork.dao;

import com.getjavajob.training.okhanzhin.socialnetwork.dao.interfaces.AccountRepository;
import com.getjavajob.training.okhanzhin.socialnetwork.dao.interfaces.CommunityRepository;
import com.getjavajob.training.okhanzhin.socialnetwork.domain.Account;
import com.getjavajob.training.okhanzhin.socialnetwork.domain.Phone;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@DataJpaTest
public class AccountRepositoryTest extends AbstractRepositoryTest {

    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private CommunityRepository communityRepository;

    @Transactional
    @Test
    public void create() {
        Account expectedAccount = new Account(
                "Petrov", "Petr", "petrov@gmail.com", "PetrovPas",
                LocalDate.now(), "ADMIN");

        int rowsCount = 3;
        assertEquals(rowsCount, accountRepository.getAll().size());
        rowsCount++;

        Account actualAccount = accountRepository.create(new Account(
                "Petrov", "Petr", "petrov@gmail.com", "PetrovPas",
                LocalDate.now(), "ADMIN"));
        expectedAccount.setAccountID(actualAccount.getAccountID());

        assertEquals(rowsCount, accountRepository.getAll().size());
        assertEquals(expectedAccount, actualAccount);
    }

    @Transactional
    @Test
    public void update() {
        Account expectedAccount = new Account(
                "Petrov", "Petr", "petrov@gmail.com", "PetrovPas",
                LocalDate.now(), "ADMIN");

        Account actualAccount = accountRepository.create(new Account(
                "Petrov", "Petr", "petrov@gmail.com", "PetrovPas",
                LocalDate.now(), "ADMIN"));
        expectedAccount.setAccountID(actualAccount.getAccountID());

        expectedAccount.setName("Ivan");
        actualAccount.setName("Ivan");
        accountRepository.update(actualAccount);

        assertEquals(expectedAccount, accountRepository.getById(actualAccount.getAccountID()));
    }

    @Test
    public void getById() {
        Account expectedAccount = new Account(
                "SurnameOne", "NameOne", "name@gmail.com", "one",
                LocalDate.of(2021, 3 ,13), "ADMIN");
        Account actualAccount = accountRepository.getById(1L);
        expectedAccount.setAccountID(actualAccount.getAccountID());

        assertEquals(expectedAccount, actualAccount);
    }

    @Transactional
    @Test
    public void getByIdFetchPhones() {
        Account expectedAccount = new Account(
                "SurnameOne", "NameOne", "name@gmail.com", "one",
                LocalDate.of(2021, 3 ,13), "ADMIN");
        List<Phone> phones = new ArrayList<>();

        phones.add(new Phone(1L, expectedAccount, "12345", "home"));
        phones.add(new Phone(2L, expectedAccount, "23456", "work"));
        expectedAccount.setPhones(phones);

        Account actualAccount = accountRepository.getByIdFetchPhones(1L);
        expectedAccount.setAccountID(actualAccount.getAccountID());

        assertEquals(expectedAccount.getPhones(), actualAccount.getPhones());
    }

    @Transactional
    @Test
    public void getAll() {
        List<Account> expectedList = new ArrayList<>();
        expectedList.add(new Account(
                1L, "SurnameOne", "NameOne", "name@gmail.com", "one",
                LocalDate.of(2021, 3, 13), "ADMIN"));
        expectedList.add(new Account(
                2L, "SurnameTwo", "NameTwo", "surnameTwo@gmail.com", "two",
                LocalDate.of(2021, 3 ,8), "USER"));
        expectedList.add(new Account(
                3L,"SurnameThee", "NameThree", "surnameThree@gmail.com", "three",
                LocalDate.of(2021, 3, 8), "USER"));

        List<Account> actualList = accountRepository.getAll();

        assertEquals(expectedList, actualList);
    }

    @Transactional
    @Test
    public void deleteById() {
        accountRepository.deleteById(1L);

        assertEquals(2, accountRepository.getAll().size());
    }

    @Transactional
    @Test
    public void getByEmail() {
        Account expectedAccount = new Account(
                1L, "SurnameOne", "NameOne", "name@gmail.com", "one",
                LocalDate.of(2021, 3, 13), "ADMIN");
        List<Phone> phones = new ArrayList<>();

        phones.add(new Phone(1L, expectedAccount, "12345", "home"));
        phones.add(new Phone(2L, expectedAccount, "23456", "work"));
        expectedAccount.setPhones(phones);

        Account actualAccount = accountRepository.getByEmail("name@gmail.com");
        System.out.println(actualAccount.toString());
        expectedAccount.setAccountID(actualAccount.getAccountID());

        assertEquals(expectedAccount, actualAccount);
    }

    @Transactional
    @Test
    public void getByEmailFetchPhones() {
        Account expectedAccount = new Account(
                "SurnameOne", "NameOne", "name@gmail.com", "one",
                LocalDate.of(2021, 3 ,13), "ADMIN");
        List<Phone> phones = new ArrayList<>();

        phones.add(new Phone(1L, expectedAccount, "12345", "home"));
        phones.add(new Phone(2L, expectedAccount, "23456", "work"));
        expectedAccount.setPhones(phones);

        Account actualAccount = accountRepository.getByEmail("name@gmail.com");
        System.out.println(actualAccount.toString());
        expectedAccount.setAccountID(actualAccount.getAccountID());

        assertEquals(expectedAccount.getPhones(), actualAccount.getPhones());
    }

    @Transactional
    @Test
    public void getAccountForGroup() {
        List<Account> expectedList = new ArrayList<>();

        expectedList.add(new Account(
                1L, "SurnameOne", "NameOne", "name@gmail.com", "one",
                LocalDate.of(2021, 3, 13), "ADMIN"));
        expectedList.add(new Account(
                2L, "SurnameTwo", "NameTwo", "surnameTwo@gmail.com", "two",
                LocalDate.of(2021, 3 ,8), "USER"));

        List<Account> actualList = accountRepository.getAccountsForCommunity(communityRepository.getById(1L));

        assertEquals(expectedList, actualList);
    }

    @Transactional
    @Test
    public void searchEntities() {
        List<Account> expectedList = new ArrayList<>();
        expectedList.add(new Account(
                1L, "SurnameOne", "NameOne", "name@gmail.com", "one",
                LocalDate.of(2021, 3, 13), "ADMIN"));

        List<Account> actualList = accountRepository.searchEntitiesPagination("one", 1);

        assertEquals(expectedList, actualList);
    }

    @Transactional
    @Test
    public void getCountOfSearchResults() {
        List<Account> expectedList = new ArrayList<>();
        expectedList.add(new Account(
                1L, "SurnameOne", "NameOne", "name@gmail.com", "one",
                LocalDate.of(2021, 3, 13), "ADMIN"));
        expectedList.add(new Account(
                2L, "SurnameTwo", "NameTwo", "surnameTwo@gmail.com", "two",
                LocalDate.of(2021, 3 ,8), "USER"));
        expectedList.add(new Account(
                3L,"SurnameThee", "NameThree", "surnameThree@gmail.com", "three",
                LocalDate.of(2021, 3, 8), "USER"));

        long actualCount = accountRepository.getCountOfSearchResults("name");

        assertEquals(expectedList.size(), actualCount);
    }

    @Transactional
    @Test
    public void getExistingEmails() {
        List<String> exceptedList = Arrays.asList("name@gmail.com", "surnameTwo@gmail.com", "surnameThree@gmail.com");
        List<String> actualList = accountRepository.getExistingEmails();

        assertTrue(exceptedList.size() == actualList.size() &&
                                 exceptedList.containsAll(actualList) &&
                                 actualList.containsAll(exceptedList));
    }

    @Transactional
    @Test
    public void updatePassword() {
        String exceptedPassword = "somePassword";
        accountRepository.updatePassword(exceptedPassword, 1L);

        assertEquals(exceptedPassword, accountRepository.getById(1L).getPassword());
    }
}
