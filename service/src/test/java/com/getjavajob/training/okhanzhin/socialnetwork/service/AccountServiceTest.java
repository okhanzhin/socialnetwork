package com.getjavajob.training.okhanzhin.socialnetwork.service;

import com.getjavajob.training.okhanzhin.socialnetwork.dao.AccountDao;
import com.getjavajob.training.okhanzhin.socialnetwork.dao.RelationshipDao;
import com.getjavajob.training.okhanzhin.socialnetwork.domain.Account;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class AccountServiceTest {
    private AccountService accountService;

    @Mock
    private AccountDao accountDao;
    @Mock
    private RelationshipDao relationDao;

    public AccountServiceTest() {
        MockitoAnnotations.initMocks(this);
        this.accountService = new AccountService(accountDao, relationDao);
    }

    @Test
    public void createAccount() {
        Account account = new Account("One", "One", "onee221@gmail.com", "onepass");
        when(accountDao.create(account)).thenReturn(account);
        Account actual = accountService.createAccount(account);
        verify(accountDao, times(1)).create(account);
        assertEquals(account, actual);
    }

    @Test
    public void updateAccount() {
        Account account = new Account("Two", "Two", "onee221@gmail.com", "twopass");
        accountService.updateAccount(account);
        verify(accountDao).update(account);
    }

    @Test
    public void delete() {
        Account account = new Account("Two", "Two", "onee221@gmail.com", "twopass");
        accountService.delete(account);
        verify(accountDao).delete(account);
    }

    @Test
    public void addFriend() {
        Account account1 = new Account("One", "One", "one221@gmail.com", "onepass");
        account1.setAccountID(1);
        Account account2 = new Account("Two", "Two", "two222@gmail.com", "twopass");
        account2.setAccountID(2);

        when(relationDao.sendRequest(account1.getAccountID(), account2.getAccountID())).thenReturn(true);
        assertTrue(accountService.addFriend(account1, account2));
        verify(relationDao, times(1)).sendRequest(account1.getAccountID(), account2.getAccountID());
    }

    @Test
    public void deleteFriend() {
        Account account1 = new Account("One", "One", "one221@gmail.com", "onepass");
        account1.setAccountID(1);
        Account account2 = new Account("Two", "Two", "two222@gmail.com", "twopass");
        account2.setAccountID(2);

        when(relationDao.breakRelationship(account1.getAccountID(), account2.getAccountID())).thenReturn(true);
        assertTrue(accountService.deleteFriend(account1, account2));
        verify(relationDao, times(1)).breakRelationship(account1.getAccountID(), account2.getAccountID());
    }

    @Test
    public void getAccountFriendsList() {
        Account account1 = new Account("One", "One", "one221@gmail.com", "onepass");
        account1.setAccountID(1);
        Account account2 = new Account("Two", "Two", "two222@gmail.com", "twopass");
        account2.setAccountID(2);
        Account account3 = new Account("Three", "Three", "three333@gmail.com", "threepass");
        account2.setAccountID(3);
        List<Account> exceptedList = Arrays.asList(account2, account3);

        when(relationDao.getFriendsList(account1)).thenReturn(exceptedList);
        List<Account> actualList = accountService.getAccountFriendsList(account1);
        verify(relationDao, times(1)).getFriendsList(account1);
        assertEquals(exceptedList, actualList);
    }
}