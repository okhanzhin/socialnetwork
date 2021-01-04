package com.getjavajob.training.okhanzhin.socialnetwork.service;

import com.getjavajob.training.okhanzhin.socialnetwork.dao.RequestDao;
import com.getjavajob.training.okhanzhin.socialnetwork.domain.Account;
import com.getjavajob.training.okhanzhin.socialnetwork.domain.Group;
import com.getjavajob.training.okhanzhin.socialnetwork.domain.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class RequestService {
    private final RequestDao requestDao;

    @Autowired
    public RequestService(RequestDao requestDao) {
        this.requestDao = requestDao;
    }

    @Transactional
    public Request createRequest(Request request) {
        return requestDao.create(request);
    }

    @Transactional
    public void acceptRequest(Request request) {
        requestDao.update(request);
    }

    @Transactional
    public void deleteRequest(Request request) {
        requestDao.deleteById(request.getRequestID());
    }

    public Request getById(long requestID) {
        return requestDao.getById(requestID);
    }

    @Transactional
    public void setRequestUnconfirmed(Request request) {
        request.setStatus(Request.Status.UNCONFIRMED);
        requestDao.update(request);
    }

    public Request getByCreatorRecipientID(long creatorID, long recipientID) {
        return requestDao.getByCreatorRecipientID(creatorID, recipientID);
    }

    public List<Request> getListOfGroupRequests(Group group) {
        return requestDao.getGroupRequestsList(group);
    }

    public List<Request> getPendingRequests(Account account) {
        return requestDao.getPendingRequestsList(account);
    }

    public List<Request> getOutgoingRequests(Account account) {
        return requestDao.getOutgoingRequestsList(account);
    }

    public List<Request> getAcceptedRequests(Account account) {
        return requestDao.getAcceptedRequestsList(account);
    }
}
