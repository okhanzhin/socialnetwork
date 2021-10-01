package com.getjavajob.training.okhanzhin.socialnetwork.service;

import com.getjavajob.training.okhanzhin.socialnetwork.dao.interfaces.RelationRepository;
import com.getjavajob.training.okhanzhin.socialnetwork.domain.Account;
import com.getjavajob.training.okhanzhin.socialnetwork.domain.Relationship;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class RelationshipServiceTest {
    private static final String ROLE_USER = "USER";

    @Mock
    private RelationRepository relationRepository;

    @InjectMocks
    private RelationService relationService;

    private Account account;
    private Account account2;
    private Relationship pendingRelation;
    private Relationship acceptedRelation;
    private Relationship declinedRelation;
    private Relationship blockedRelation;
    List<Account> accounts;

    @Before
    public void init() {
        account = new Account(1L, "One", "One",
                "onee221@gmail.com", "onepass", LocalDate.now(), ROLE_USER);
        account2 = new Account(2L, "Two", "Two",
                "onee222@gmail.com", "onepass", LocalDate.now(), ROLE_USER);
        Account account3 = new Account(3L, "Three", "Three",
                "onee223@gmail.com", "onepass", LocalDate.now(), ROLE_USER);
        pendingRelation = new Relationship(account, account2, Relationship.Status.PENDING);
        acceptedRelation = new Relationship(account, account2, Relationship.Status.ACCEPTED);
        declinedRelation = new Relationship(account, account2, Relationship.Status.DECLINED);
        blockedRelation = new Relationship(account, account2, Relationship.Status.BLOCKED);
        accounts = Arrays.asList(account2, account3);
    }

    @Test
    public void createRelation() {
        when(relationRepository.create(pendingRelation)).thenReturn(pendingRelation);

        assertEquals(pendingRelation, relationService.createRelation(account, account2));
        verify(relationRepository).create(pendingRelation);
    }

    @Test
    public void acceptRelation() {
        when(relationRepository.getRelation(account, account2)).thenReturn(pendingRelation);
        when(relationRepository.update(pendingRelation)).thenReturn(acceptedRelation);

        assertEquals(acceptedRelation, relationService.acceptRelation(account, account2));
        verify(relationRepository).update(acceptedRelation);
    }

    @Test
    public void declineRelation() {
        when(relationRepository.getRelation(account, account2)).thenReturn(acceptedRelation);
        when(relationRepository.update(acceptedRelation)).thenReturn(declinedRelation);

        assertEquals(declinedRelation, relationService.declineRelation(account, account2));
        verify(relationRepository).update(acceptedRelation);
    }

    @Test
    public void pendRelation() {
        when(relationRepository.getRelation(account, account2)).thenReturn(declinedRelation);
        when(relationRepository.update(declinedRelation)).thenReturn(pendingRelation);

        assertEquals(pendingRelation, relationService.pendRelation(account, account2));
        verify(relationRepository).update(declinedRelation);
    }

    @Test
    public void blockRelation() {
        when(relationRepository.getRelation(account, account2)).thenReturn(declinedRelation);
        when(relationRepository.update(declinedRelation)).thenReturn(blockedRelation);

        assertEquals(blockedRelation, relationService.blockRelation(account, account2));
        verify(relationRepository).update(declinedRelation);
    }

    @Test
    public void getAccountFriends() {
        when(relationRepository.getFriendsList(account)).thenReturn(accounts);

        assertEquals(accounts, relationService.getAccountFriends(account));
        verify(relationRepository).getFriendsList(account);
    }

    @Test
    public void getAccountBlockedUsers() {
        when(relationRepository.getBlockedAccountsList(account)).thenReturn(accounts);

        assertEquals(accounts, relationService.getAccountBlockedUsers(account));
        verify(relationRepository).getBlockedAccountsList(account);
    }

    @Test
    public void isRelationExist() {
        when(relationRepository.isRelationExist(account, account2)).thenReturn(true);

        assertTrue(relationService.isRelationExist(account, account2));
        verify(relationRepository).isRelationExist(account, account2);
    }
}
