package com.getjavajob.training.okhanzhin.socialnetwork.service;

import com.getjavajob.training.okhanzhin.socialnetwork.dao.AccountDao;
import com.getjavajob.training.okhanzhin.socialnetwork.dao.PhoneDao;
import com.getjavajob.training.okhanzhin.socialnetwork.dao.RelationshipDao;
import com.getjavajob.training.okhanzhin.socialnetwork.dao.RequestDao;
import com.getjavajob.training.okhanzhin.socialnetwork.domain.Account;

import com.getjavajob.training.okhanzhin.socialnetwork.domain.Group;
import com.getjavajob.training.okhanzhin.socialnetwork.domain.Phone;
import com.getjavajob.training.okhanzhin.socialnetwork.domain.Request;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class AccountServiceTest {
    @Mock
    private AccountDao accountDao;
    @Mock
    private PhoneDao phoneDao;
    @Mock
    private RelationshipDao relationDao;
    @Mock
    private RequestDao requestDao;

    @InjectMocks
    private AccountService accountService;

    @Test
    public void createAccount() {
        Account account = new Account(1, "One", "One", "onee221@gmail.com", "onepass", LocalDate.now());
        when(accountDao.create(account)).thenReturn(account);
        Account actual = accountService.createAccount(account);

        verify(accountDao, times(1)).create(account);
        assertEquals(account, actual);
    }

    @Test
    public void updateAccount() {
        Account account = new Account(1, "One", "One", "onee221@gmail.com", "onepass", LocalDate.now());
        List<Phone> phones = new ArrayList<>();
        phones.add(new Phone(account.getAccountID(), "home", "89101112233"));

        when(accountDao.getById(1)).thenReturn(account);
        when(phoneDao.getAllPhonesByAccountId(account.getAccountID())).thenReturn(phones);
        accountService.updateAccount(account);
        verify(accountDao).update(account);
    }

    @Test
    public void delete() {
        Account account = new Account(1, "Two", "Two", "onee221@gmail.com", "twopass", LocalDate.now());

        accountService.delete(account);
        verify(accountDao).deleteById(account.getAccountID());
    }

    @Test
    public void getById() {
        Account account = new Account(1, "Two", "Two", "onee221@gmail.com", "twopass", LocalDate.now());

        when(accountDao.getById(account.getAccountID())).thenReturn(account);
        accountService.getById(account.getAccountID());
        verify(accountDao).getById(account.getAccountID());
    }

    @Test
    public void getByEmail() {
        Account account = new Account(1, "Two", "Two", "onee221@gmail.com", "twopass", LocalDate.now());

        accountService.getByEmail(account.getEmail());
        verify(accountDao).getByEmail(account.getEmail());
    }

    @Test
    public void getAccountsListForGroup() {
        List<Account> accounts = new ArrayList<>();
        accounts.add(new Account(1, "One", "One", "onee221@gmail.com", "onepass", LocalDate.now()));
        accounts.add(new Account(2, "Two", "Two", "twotwotwo@gmail.com", "twopass", LocalDate.now()));
        Group group = new Group("First Group");

        when(accountDao.getAccountsForGroup(group)).thenReturn(accounts);
        accountService.getAccountsListForGroup(group);
        verify(accountDao).getAccountsForGroup(group);
    }

    @Test
    public void getUnconfirmedRequestAccountsForGroup() {
        List<Account> accounts = new ArrayList<>();
        accounts.add(new Account(1, "One", "One", "onee221@gmail.com", "onepass", LocalDate.now()));
        accounts.add(new Account(2, "Two", "Two", "twotwotwo@gmail.com", "twopass", LocalDate.now()));
        Group group = new Group("First Group");

        when(accountDao.getUnconfirmedRequestAccountsForGroup(group)).thenReturn(accounts);
        accountService.getUnconfirmedRequestAccountsForGroup(group);
        verify(accountDao).getUnconfirmedRequestAccountsForGroup(group);
    }

    @Test
    public void addFriend() {
        Account account1 = new Account(1, "One", "One", "one221@gmail.com", "onepass", LocalDate.now());
        Account account2 = new Account(2, "Two", "Two", "two222@gmail.com", "twopass", LocalDate.now());

        accountService.createRelation(account1.getAccountID(), account2.getAccountID());
        verify(relationDao, times(1)).createRelation(account1.getAccountID(), account2.getAccountID());
    }

    @Test
    public void acceptRelation() {
        Account account1 = new Account(1, "One", "One", "one221@gmail.com", "onepass", LocalDate.now());
        Account account2 = new Account(2, "Two", "Two", "two222@gmail.com", "twopass", LocalDate.now());

        accountService.acceptRelation(account1.getAccountID(), account2.getAccountID());
        verify(relationDao, times(1)).acceptRelation(account1.getAccountID(), account2.getAccountID());
    }

    @Test
    public void declineRelation() {
        Account account1 = new Account(1, "One", "One", "one221@gmail.com", "onepass", LocalDate.now());
        Account account2 = new Account(2, "Two", "Two", "two222@gmail.com", "twopass", LocalDate.now());

        accountService.declineRelation(account1.getAccountID(), account2.getAccountID());
        verify(relationDao, times(1)).declineRelation(account1.getAccountID(), account2.getAccountID());
    }

    @Test
    public void deleteFriend() {
        Account account1 = new Account(1, "One", "One", "one221@gmail.com", "onepass", LocalDate.now());
        Account account2 = new Account(2, "Two", "Two", "two222@gmail.com", "twopass", LocalDate.now());

        accountService.deleteRelation(account1.getAccountID(), account2.getAccountID());
        verify(relationDao, times(1)).breakRelation(account1.getAccountID(), account2.getAccountID());
    }

    @Test
    public void getAccountFriends() {
        Account account1 = new Account(1, "One", "One", "one221@gmail.com", "onepass", LocalDate.now());
        Account account2 = new Account(2, "Two", "Two", "two222@gmail.com", "twopass", LocalDate.now());
        Account account3 = new Account(3, "Three", "Three", "three333@gmail.com", "threepass", LocalDate.now());
        List<Account> exceptedList = Arrays.asList(account2, account3);

        when(relationDao.getFriendsList(account1)).thenReturn(exceptedList);
        List<Account> actualList = accountService.getAccountFriends(account1);
        verify(relationDao, times(1)).getFriendsList(account1);
        assertEquals(exceptedList, actualList);
    }

    @Test
    public void getPendingRequestAccounts() {
        Account account1 = new Account(1, "One", "One", "one221@gmail.com", "onepass", LocalDate.now());
        Account account2 = new Account(2, "Two", "Two", "two222@gmail.com", "twopass", LocalDate.now());
        Account account3 = new Account(3, "Three", "Three", "three333@gmail.com", "threepass", LocalDate.now());
        List<Request> pendingRequests = new ArrayList<>();
        pendingRequests.add(new Request(account1.getAccountID(), account2.getAccountID(), "user", Request.Status.UNCONFIRMED));
        pendingRequests.add(new Request(account1.getAccountID(), account3.getAccountID(), "user", Request.Status.UNCONFIRMED));

        when(requestDao.getPendingRequestsList(account1)).thenReturn(pendingRequests);
        accountService.getPendingRequestAccounts(account1);
        verify(requestDao, times(1)).getPendingRequestsList(account1);
    }

    @Test
    public void getOutgoingRequestAccounts() {
        Account account1 = new Account(1, "One", "One", "one221@gmail.com", "onepass", LocalDate.now());
        Account account2 = new Account(2, "Two", "Two", "two222@gmail.com", "twopass", LocalDate.now());
        Account account3 = new Account(3, "Three", "Three", "three333@gmail.com", "threepass", LocalDate.now());
        List<Request> outgoingRequests = new ArrayList<>();
        outgoingRequests.add(new Request(account2.getAccountID(), account1.getAccountID(), "user", Request.Status.UNCONFIRMED));
        outgoingRequests.add(new Request(account3.getAccountID(), account1.getAccountID(), "user", Request.Status.UNCONFIRMED));

        when(requestDao.getOutgoingRequestsList(account1)).thenReturn(outgoingRequests);
        accountService.getOutgoingRequestAccounts(account1);
        verify(requestDao, times(1)).getOutgoingRequestsList(account1);
    }
}