package com.getjavajob.training.okhanzhin.socialnetwork.dao;

import com.getjavajob.training.okhanzhin.socialnetwork.dao.interfaces.AccountRepository;
import com.getjavajob.training.okhanzhin.socialnetwork.dao.interfaces.CommunityRepository;
import com.getjavajob.training.okhanzhin.socialnetwork.dao.interfaces.RequestRepository;
import com.getjavajob.training.okhanzhin.socialnetwork.domain.Account;
import com.getjavajob.training.okhanzhin.socialnetwork.domain.Community;
import com.getjavajob.training.okhanzhin.socialnetwork.domain.request.AccountRequest;
import com.getjavajob.training.okhanzhin.socialnetwork.domain.request.CommunityRequest;
import com.getjavajob.training.okhanzhin.socialnetwork.domain.request.Request;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@DataJpaTest
public class RequestRepositoryTest extends AbstractRepositoryTest {

    @Autowired
    private RequestRepository requestRepository;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private CommunityRepository communityRepository;

    @Transactional
    @Test
    public void create() {
        Account accountOne = accountRepository.getById(1L);
        Account accountTwo = accountRepository.getById(2L);

        Request expectedRequest = new AccountRequest(accountOne, accountTwo, Request.Status.UNCONFIRMED);

        int rowsCount = 5;
        assertEquals(rowsCount, requestRepository.getAll().size());
        rowsCount++;

        Request actualRequest = requestRepository.create(new AccountRequest(
                accountOne, accountTwo, Request.Status.UNCONFIRMED));
        expectedRequest.setRequestID(actualRequest.getRequestID());

        assertEquals(rowsCount, requestRepository.getAll().size());
        assertEquals(expectedRequest, actualRequest);
    }

    @Transactional
    @Test
    public void update() {
        Account accountOne = accountRepository.getById(1L);
        Account accountTwo = accountRepository.getById(2L);

        Request expectedRequest = new AccountRequest(accountOne, accountTwo, Request.Status.UNCONFIRMED);

        Request actualRequest = requestRepository.create(new AccountRequest(
                accountOne, accountTwo, Request.Status.UNCONFIRMED));
        expectedRequest.setRequestStatus(Request.Status.ACCEPTED);
        actualRequest.setRequestStatus(Request.Status.ACCEPTED);
        requestRepository.update(actualRequest);
        expectedRequest.setRequestID(actualRequest.getRequestID());

        assertEquals(expectedRequest, requestRepository.getById(actualRequest.getRequestID()));
    }

    @Transactional
    @Test
    public void getById() {
        Account accountOne = accountRepository.getById(1L);
        Account accountTwo = accountRepository.getById(2L);

        AccountRequest expectedRequest = new AccountRequest(accountOne, accountTwo, Request.Status.UNCONFIRMED);
        Request actualRequest = requestRepository.getById(1L);
        expectedRequest.setRequestID(actualRequest.getRequestID());

        assertEquals(expectedRequest, actualRequest);
    }

    @Transactional
    @Test
    public void deleteById() {
        requestRepository.deleteById(1L);

        assertEquals(4, requestRepository.getAll().size());
    }

    @Transactional
    @Test
    public void getAll() {
        List<Request> expectedList = new ArrayList<>();
        expectedList.add(new AccountRequest(
                1L, accountRepository.getById(1L), accountRepository.getById(2L), Request.Status.UNCONFIRMED));
        expectedList.add(new AccountRequest(
                2L, accountRepository.getById(2L), accountRepository.getById(3L), Request.Status.ACCEPTED));
        expectedList.add(new AccountRequest(
                3L, accountRepository.getById(3L), accountRepository.getById(1L), Request.Status.UNCONFIRMED));
        expectedList.add(new CommunityRequest(
                4L, accountRepository.getById(1L), communityRepository.getById(1L), Request.Status.UNCONFIRMED));
        expectedList.add(new CommunityRequest(
                5L, accountRepository.getById(2L), communityRepository.getById(1L), Request.Status.UNCONFIRMED));

        List<Request> actualList = requestRepository.getAll();

        assertEquals(expectedList, actualList);
    }

    @Transactional
    @Test
    public void getAccountRequest() {
        Account accountOne = accountRepository.getById(1L);
        Account accountTwo = accountRepository.getById(2L);

        Request expectedRequest = new AccountRequest(accountOne, accountTwo, Request.Status.UNCONFIRMED);

        Request actualRequest = requestRepository.getAccountRequest(
                accountOne, accountTwo);
        expectedRequest.setRequestID(actualRequest.getRequestID());

        assertEquals(expectedRequest, actualRequest);
    }

    @Transactional
    @Test
    public void getCommunityRequest() {
        Account accountOne = accountRepository.getById(1L);
        Community communityOne = communityRepository.getById(1L);

        Request expectedRequest = new CommunityRequest(accountOne, communityOne, Request.Status.UNCONFIRMED);

        Request actualRequest = requestRepository.getCommunityRequest(
                accountOne, communityOne);
        expectedRequest.setRequestID(actualRequest.getRequestID());

        assertEquals(expectedRequest, actualRequest);
    }

    @Transactional
    @Test
    public void getCommunityRequestsList() {
        List<Request> expectedList = new ArrayList<>();
        expectedList.add(new CommunityRequest(
                4L, accountRepository.getById(1L), communityRepository.getById(1L), Request.Status.UNCONFIRMED));
        expectedList.add(new CommunityRequest(
                5L, accountRepository.getById(2L), communityRepository.getById(1L), Request.Status.UNCONFIRMED));

        List<Request> actualList = requestRepository.getCommunityRequests(communityRepository.getById(1L));

        assertEquals(expectedList, actualList);
    }

    @Transactional
    @Test
    public void getPendingRequestsList() {
        List<Request> expectedList = new ArrayList<>();
        expectedList.add(new AccountRequest(
                3L, accountRepository.getById(3L), accountRepository.getById(1L), Request.Status.UNCONFIRMED));

        List<Request> actualList = requestRepository.getPendingRequests(accountRepository.getById(1L));

        assertEquals(expectedList, actualList);
    }

    @Transactional
    @Test
    public void getOutgoingRequestsList() {
        List<Request> expectedList = new ArrayList<>();
        expectedList.add(new AccountRequest(
                1L, accountRepository.getById(1L), accountRepository.getById(2L), Request.Status.UNCONFIRMED));

        List<Request> actualList = requestRepository.getOutgoingRequests(accountRepository.getById(1L));

        assertEquals(expectedList, actualList);
    }

    @Transactional
    @Test
    public void getAcceptedRequestsList() {
        List<Request> expectedList = new ArrayList<>();
        expectedList.add(new AccountRequest(
                2L, accountRepository.getById(2L), accountRepository.getById(3L), Request.Status.ACCEPTED));

        List<Request> actualList = requestRepository.getAcceptedRequestsList(accountRepository.getById(2L));

        assertEquals(expectedList, actualList);
    }
}
