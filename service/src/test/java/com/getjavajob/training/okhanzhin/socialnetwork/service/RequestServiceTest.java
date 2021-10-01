package com.getjavajob.training.okhanzhin.socialnetwork.service;

import com.getjavajob.training.okhanzhin.socialnetwork.dao.interfaces.RequestRepository;
import com.getjavajob.training.okhanzhin.socialnetwork.domain.Account;
import com.getjavajob.training.okhanzhin.socialnetwork.domain.Community;
import com.getjavajob.training.okhanzhin.socialnetwork.domain.request.AccountRequest;
import com.getjavajob.training.okhanzhin.socialnetwork.domain.request.CommunityRequest;
import com.getjavajob.training.okhanzhin.socialnetwork.domain.request.Request;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class RequestServiceTest {
    private static final String ROLE_USER = "USER";

    @Mock
    private RequestRepository requestRepository;

    @InjectMocks
    private RequestService requestService;

    private Account homeAccount;
    private Account account;
    private Account account2;
    private Account account3;
    private Community community;

    @Before
    public void init() {
        homeAccount = new Account(4L, "Home", "Home",
                "home@gmail.com", "onepass", LocalDate.now(), ROLE_USER);
        account = new Account(1L, "One", "One",
                "onee221@gmail.com", "onepass", LocalDate.now(), ROLE_USER);
        account2 = new Account(2L, "Two", "Two",
                "onee222@gmail.com", "onepass", LocalDate.now(), ROLE_USER);
        account3 = new Account(3L, "Three", "Three",
                "onee223@gmail.com", "onepass", LocalDate.now(), ROLE_USER);
        community = new Community(1L, "First Community", LocalDate.now());
    }

    @Test
    public void createAccountRequest() {
        Request accountRequest = new AccountRequest(1L, account, account2, Request.Status.UNCONFIRMED);

        when(requestRepository.create(accountRequest)).thenReturn(accountRequest);

        assertEquals(accountRequest, requestService.createAccountRequest(account, account2));
        verify(requestRepository).create(accountRequest);
    }

    @Test
    public void createCommunityRequest() {
        Request communityRequest = new CommunityRequest(1L, account, community, Request.Status.UNCONFIRMED);

        when(requestRepository.create(communityRequest)).thenReturn(communityRequest);

        assertEquals(communityRequest, requestService.createCommunityRequest(account, community));
        verify(requestRepository).create(communityRequest);
    }

    @Test
    public void acceptAccountRequest() {
        Request unconfirmedRequest = new AccountRequest(1L, account, account2, Request.Status.UNCONFIRMED);
        Request confirmedRequest = new AccountRequest(1L, account, account2, Request.Status.ACCEPTED);

        when(requestRepository.getAccountRequest(account, account2)).thenReturn(unconfirmedRequest);
        when(requestRepository.update(confirmedRequest)).thenReturn(confirmedRequest);

        assertEquals(confirmedRequest, requestService.acceptAccountRequest(account, account2));
        verify(requestRepository).update(confirmedRequest);
    }

    @Test
    public void acceptCommunityRequest() {
        Request unconfirmedRequest = new CommunityRequest(1L, account, community, Request.Status.UNCONFIRMED);
        Request confirmedRequest = new CommunityRequest(1L, account, community, Request.Status.ACCEPTED);

        when(requestRepository.getCommunityRequest(account, community)).thenReturn(unconfirmedRequest);
        when(requestRepository.update(confirmedRequest)).thenReturn(confirmedRequest);

        assertEquals(confirmedRequest, requestService.acceptCommunityRequest(account, community));
        verify(requestRepository).update(confirmedRequest);
    }


    @Test
    public void deleteRequest() {
        Request accountRequest = new AccountRequest(1L, account, account2, Request.Status.UNCONFIRMED);

        requestService.deleteRequest(accountRequest);
        verify(requestRepository).deleteById(accountRequest.getRequestID());
    }

    @Test
    public void getById() {
        Request accountRequest = new AccountRequest(1L, account, account2, Request.Status.UNCONFIRMED);

        when(requestRepository.getById(accountRequest.getRequestID())).thenReturn(accountRequest);

        assertEquals(accountRequest, requestService.getById(accountRequest.getRequestID()));
        verify(requestRepository).getById(accountRequest.getRequestID());
    }

    @Test
    public void setRequestUnconfirmed() {
        Request confirmedRequest = new AccountRequest(1L, account, account2, Request.Status.ACCEPTED);
        Request unconfirmedRequest = new AccountRequest(1L, account, account2, Request.Status.UNCONFIRMED);

        when(requestRepository.update(confirmedRequest)).thenReturn(unconfirmedRequest);

        requestService.setRequestUnconfirmed(confirmedRequest);
        verify(requestRepository).update(confirmedRequest);
    }

    @Test
    public void getAccountRequest() {
        Request accountRequest = new AccountRequest(1L, account, account2, Request.Status.ACCEPTED);

        when(requestRepository.getAccountRequest(account, account2)).thenReturn(accountRequest);

        assertEquals(accountRequest, requestService.getAccountRequest(account, account2));
        verify(requestRepository).getAccountRequest(account, account2);
    }

    @Test
    public void getRequestingAccounts() {
        Request communityRequest = new CommunityRequest(1L, account, community, Request.Status.ACCEPTED);

        when(requestRepository.getCommunityRequest(account, community)).thenReturn(communityRequest);

        assertEquals(communityRequest, requestService.getCommunityRequest(account, community));
        verify(requestRepository).getCommunityRequest(account, community);
    }

    @Test
    public void getListOfCommunityRequests() {
        Request request1 = new CommunityRequest(1L, account, community, Request.Status.UNCONFIRMED);
        Request request2 = new CommunityRequest(2L, account2, community, Request.Status.UNCONFIRMED);
        Request request3 = new CommunityRequest(3L, account3, community, Request.Status.UNCONFIRMED);
        List<Request> requests = Arrays.asList(request1, request2, request3);
        List<Account> accounts = new ArrayList<>();

        for (Request request : requests) {
            accounts.add(request.getSource());
        }

        when(requestRepository.getCommunityRequests(community)).thenReturn(requests);

        assertEquals(accounts, requestService.getRequestingAccounts(community));
        verify(requestRepository).getCommunityRequests(community);
    }

    @Test
    public void getPendingRequests() {
        Request request1 = new AccountRequest(1L, account, homeAccount, Request.Status.UNCONFIRMED);
        Request request2 = new AccountRequest(2L, account2, homeAccount, Request.Status.UNCONFIRMED);
        Request request3 = new AccountRequest(3L, account3, homeAccount, Request.Status.UNCONFIRMED);
        List<Request> requests = Arrays.asList(request1, request2, request3);
        List<Account> accounts = new ArrayList<>();

        for (Request request : requests) {
            accounts.add(request.getSource());
        }

        when(requestRepository.getPendingRequests(homeAccount)).thenReturn(requests);

        assertEquals(accounts, requestService.getPendingAccounts(homeAccount));
        verify(requestRepository).getPendingRequests(homeAccount);
    }

    @Test
    public void getOutgoingRequests() {
        Request request1 = new AccountRequest(1L, homeAccount, account, Request.Status.UNCONFIRMED);
        Request request2 = new AccountRequest(2L, homeAccount, account2, Request.Status.UNCONFIRMED);
        Request request3 = new AccountRequest(3L, homeAccount, account3, Request.Status.UNCONFIRMED);
        List<Request> requests = Arrays.asList(request1, request2, request3);
        List<Account> accounts = new ArrayList<>();

        for (Request request : requests) {
            AccountRequest accountRequest = (AccountRequest) request;
            accounts.add(accountRequest.getAccountTarget());
        }

        when(requestRepository.getOutgoingRequests(homeAccount)).thenReturn(requests);

        assertEquals(accounts, requestService.getOutgoingAccounts(homeAccount));
        verify(requestRepository).getOutgoingRequests(homeAccount);
    }

    @Test
    public void getAcceptedRequests() {
        Request request1 = new AccountRequest(1L, homeAccount, account, Request.Status.ACCEPTED);
        Request request2 = new AccountRequest(2L, homeAccount, account2, Request.Status.ACCEPTED);
        Request request3 = new AccountRequest(3L, homeAccount, account3, Request.Status.ACCEPTED);
        List<Request> requests = Arrays.asList(request1, request2, request3);

        when(requestRepository.getAcceptedRequestsList(homeAccount)).thenReturn(requests);

        assertEquals(requests, requestService.getAcceptedRequests(homeAccount));
        verify(requestRepository).getAcceptedRequestsList(homeAccount);
    }
}
