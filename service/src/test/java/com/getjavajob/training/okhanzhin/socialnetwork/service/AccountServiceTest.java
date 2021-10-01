package com.getjavajob.training.okhanzhin.socialnetwork.service;

import com.getjavajob.training.okhanzhin.socialnetwork.dao.interfaces.AccountRepository;
import com.getjavajob.training.okhanzhin.socialnetwork.dao.interfaces.PictureRepository;
import com.getjavajob.training.okhanzhin.socialnetwork.domain.Account;
import com.getjavajob.training.okhanzhin.socialnetwork.domain.Community;
import com.getjavajob.training.okhanzhin.socialnetwork.domain.Phone;
import com.getjavajob.training.okhanzhin.socialnetwork.domain.dto.AccountTransfer;
import com.getjavajob.training.okhanzhin.socialnetwork.domain.picture.AccountPicture;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AccountServiceTest {
    private static final String ROLE_USER = "USER";

    @Mock
    private AccountRepository accountRepository;
    @Mock
    private PictureRepository pictureRepository;

    @InjectMocks
    private AccountService accountService;

    private AccountTransfer transfer;
    private Account account;
    private Community community;

    @Before
    public void init() {
        transfer = new AccountTransfer(1L, "One", "One",
                "onee221@gmail.com", "onepass", LocalDate.now(), ROLE_USER);
        account = new Account(1L, "One", "One",
                "onee221@gmail.com", "onepass", LocalDate.now(), ROLE_USER);
        community = new Community("First Group");
    }

    @Test
    public void createAccount() {
        transfer.setPicture(new byte[0]);
        AccountPicture accountPicture = new AccountPicture(transfer.getPicture());
        accountPicture.setAccount(account);

        Account actual = accountService.create(transfer);

        verify(pictureRepository, times(1)).create(accountPicture);
        assertEquals(account, actual);
    }

    @Test
    public void updateAccount() {
        transfer.setPicture(new byte[]{1, 2, 3, 4, 5});
        transfer.setPicAttached(true);

        Account storedAccount = new Account(1L, "Two", "Two",
                "onee221@gmail.com", "onepass", LocalDate.now(), ROLE_USER);
        AccountPicture storedPicture = new AccountPicture(new byte[0]);
        storedPicture.setPicID(1L);
        storedPicture.setAccount(storedAccount);
        Account updatedAccount = account;

        when(accountRepository.getByIdFetchPhones(1L)).thenReturn(storedAccount);
        when(pictureRepository.getAccountPicture(storedAccount)).thenReturn(storedPicture);
        when(accountRepository.update(updatedAccount)).thenReturn(account);

        assertEquals(account, accountService.update(transfer));
        verify(accountRepository, times(1)).update(updatedAccount);
        verify(pictureRepository, times(1)).getAccountPicture(storedAccount);
        verify(pictureRepository, times(1)).update(storedPicture);
    }

    @Test
    public void delete() {
        accountService.delete(account);
        verify(accountRepository).deleteById(account.getAccountID());
    }

    @Test
    public void getById() {
        when(accountRepository.getById(account.getAccountID())).thenReturn(account);

        assertEquals(account, accountService.getById(account.getAccountID()));
        verify(accountRepository).getById(account.getAccountID());
    }

    @Test
    public void getByIdFetchPhones() {
        account.setPhones(Collections.singletonList(new Phone("89991112233", "work")));

        when(accountRepository.getByIdFetchPhones(account.getAccountID())).thenReturn(account);

        assertEquals(account, accountService.getByIdFetchPhones(account.getAccountID()));
        verify(accountRepository).getByIdFetchPhones(account.getAccountID());
    }

    @Test
    public void getByEmail() {
        when(accountRepository.getByEmail(account.getEmail())).thenReturn(account);

        assertEquals(account, accountService.getByEmail(account.getEmail()));
        verify(accountRepository).getByEmail(account.getEmail());
    }

    @Test
    public void getByEmailFetchPhones() {
        account.setPhones(Collections.singletonList(new Phone("89991112233", "work")));

        when(accountRepository.getByEmailFetchPhones(account.getEmail())).thenReturn(account);

        assertEquals(account, accountService.getByEmailFetchPhones(account.getEmail()));
        verify(accountRepository).getByEmailFetchPhones(account.getEmail());
    }

    @Test
    public void getAccountsListForGroup() {
        List<Account> accounts = new ArrayList<>();
        accounts.add(new Account(1L, "One", "One",
                "onee221@gmail.com", "onepass", LocalDate.now(), ROLE_USER));
        accounts.add(new Account(2L, "Two", "Two",
                "twotwotwo@gmail.com", "twopass", LocalDate.now(), ROLE_USER));

        when(accountRepository.getAccountsForCommunity(community)).thenReturn(accounts);

        assertEquals(accounts, accountService.getAccountsListForGroup(community));
        verify(accountRepository).getAccountsForCommunity(community);
    }

    @Test
    public void isAccountAvailable() {
        account.setEnabled(true);

        when(accountRepository.isAccountAvailable(account.getEmail())).thenReturn(account.isEnabled());

        assertTrue(accountService.isAccountAvailable(account.getEmail()));
        verify(accountRepository).isAccountAvailable(account.getEmail());
    }

    @Test
    public void isEmailExist() {
        String currentEmail = "onee221@gmail.com";

        when(accountRepository.getExistingEmails()).thenReturn(Collections.singletonList(currentEmail));

        assertTrue(accountService.isEmailExist(currentEmail));
        verify(accountRepository).getExistingEmails();
    }

    @Test
    public void switchAccount() {
        account.setEnabled(true);

        when(accountRepository.getById(account.getAccountID())).thenReturn(account);

        accountService.switchAccount(account.getAccountID());
        assertFalse(account.isEnabled());
        verify(accountRepository).update(account);
    }

    @Test
    public void updatePassword() {
        String password = "somePassword";
        accountService.updatePassword(password, account);
        verify(accountRepository).updatePassword(password, account.getAccountID());
    }
}
