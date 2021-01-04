package com.getjavajob.training.okhanzhin.socialnetwork.service;

import com.getjavajob.training.okhanzhin.socialnetwork.dao.RequestDao;
import com.getjavajob.training.okhanzhin.socialnetwork.domain.Account;
import com.getjavajob.training.okhanzhin.socialnetwork.domain.Group;
import com.getjavajob.training.okhanzhin.socialnetwork.domain.Request;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class RequestServiceTest {
    @Mock
    private RequestDao requestDao;

    @InjectMocks
    private RequestService requestService;

    @Test
    public void createRequest() {
        Request request = new Request(1, 1, "user", Request.Status.UNCONFIRMED);

        when(requestDao.create(request)).thenReturn(request);
        requestService.createRequest(request);
        verify(requestDao).create(request);
    }

    @Test
    public void acceptRequest() {
        Request request = new Request(1, 1, "user", Request.Status.UNCONFIRMED);

        requestService.acceptRequest(request);
        verify(requestDao).update(request);
    }

    @Test
    public void deleteRequest() {
        Request request = new Request(1, 1, "user", Request.Status.UNCONFIRMED);
        request.setRequestID(1);

        requestService.deleteRequest(request);
        verify(requestDao).deleteById(request.getRequestID());
    }

    @Test
    public void getById() {
        Request request = new Request(1, 1, "user", Request.Status.UNCONFIRMED);
        request.setRequestID(1);

        requestService.getById(request.getRequestID());
        verify(requestDao).getById(request.getRequestID());
    }

    @Test
    public void setRequestUnconfirmed() {
        Request request = new Request(1, 1, "user", Request.Status.ACCEPTED);
        request.setRequestID(1);

        requestService.setRequestUnconfirmed(request);
        verify(requestDao).update(request);
    }

    @Test
    public void getByCreatorRecipientID() {
        Request request = new Request(1, 1, "user", Request.Status.ACCEPTED);
        request.setRequestID(1);

        when(requestDao.getByCreatorRecipientID(request.getCreatorID(), request.getRecipientID())).thenReturn(request);
        requestService.getByCreatorRecipientID(request.getCreatorID(), request.getRecipientID());
        verify(requestDao).getByCreatorRecipientID(request.getCreatorID(), request.getRecipientID());
    }

    @Test
    public void getListOfGroupRequests() {
        Group group = new Group("First Group");
        group.setGroupID(1);
        Request request1 = new Request(1, group.getGroupID(), "user", Request.Status.UNCONFIRMED);
        Request request2 = new Request(2, group.getGroupID(), "user", Request.Status.UNCONFIRMED);
        Request request3 = new Request(3, group.getGroupID(), "user", Request.Status.UNCONFIRMED);
        List<Request> requests = Arrays.asList(request1, request2, request3);

        when(requestDao.getGroupRequestsList(group)).thenReturn(requests);
        requestService.getListOfGroupRequests(group);
        verify(requestDao).getGroupRequestsList(group);
    }

    @Test
    public void getPendingRequests() {
        Account account = new Account(1, "One", "One", "onee221@gmail.com", "onepass", LocalDate.now());
        Request request1 = new Request(1, account.getAccountID(), "user", Request.Status.UNCONFIRMED);
        Request request2 = new Request(2, account.getAccountID(), "user", Request.Status.UNCONFIRMED);
        Request request3 = new Request(3, account.getAccountID(), "user", Request.Status.UNCONFIRMED);
        List<Request> requests = Arrays.asList(request1, request2, request3);

        when(requestDao.getPendingRequestsList(account)).thenReturn(requests);
        requestService.getPendingRequests(account);
        verify(requestDao).getPendingRequestsList(account);
    }

    @Test
    public void getOutgoingRequests() {
        Account account = new Account(1, "One", "One", "onee221@gmail.com", "onepass", LocalDate.now());
        Request request1 = new Request(account.getAccountID(), 1, "user", Request.Status.UNCONFIRMED);
        Request request2 = new Request(account.getAccountID(), 2, "user", Request.Status.UNCONFIRMED);
        Request request3 = new Request(account.getAccountID(), 3, "user", Request.Status.UNCONFIRMED);
        List<Request> requests = Arrays.asList(request1, request2, request3);

        when(requestDao.getOutgoingRequestsList(account)).thenReturn(requests);
        requestService.getOutgoingRequests(account);
        verify(requestDao).getOutgoingRequestsList(account);
    }

    @Test
    public void getAcceptedRequests() {
        Account account = new Account(1, "One", "One", "onee221@gmail.com", "onepass", LocalDate.now());
        Request request1 = new Request(account.getAccountID(), 1, "user", Request.Status.ACCEPTED);
        Request request2 = new Request(account.getAccountID(), 2, "user", Request.Status.ACCEPTED);
        Request request3 = new Request(account.getAccountID(), 3, "user", Request.Status.ACCEPTED);
        List<Request> requests = Arrays.asList(request1, request2, request3);

        when(requestDao.getAcceptedRequestsList(account)).thenReturn(requests);
        requestService.getAcceptedRequests(account);
        verify(requestDao).getAcceptedRequestsList(account);
    }
}