package com.getjavajob.training.okhanzhin.socialnetwork.service;

import com.getjavajob.training.okhanzhin.socialnetwork.dao.RequestDao;
import com.getjavajob.training.okhanzhin.socialnetwork.domain.Account;
import com.getjavajob.training.okhanzhin.socialnetwork.domain.Group;
import com.getjavajob.training.okhanzhin.socialnetwork.domain.Request;

import java.util.List;

public class RequestService {
    private static final byte REQUEST_CREATED_STATUS = 0;
    private final RequestDao requestDao;

    public RequestService(RequestDao requestDao) {
        this.requestDao = requestDao;
    }

    public RequestService() {
        this.requestDao = new RequestDao();
    }

    public Request createGroupRequest(Request request) {
        return requestDao.create(request);
    }

    public Request createRelationRequest(Request request) {
        return requestDao.create(request);
    }

    public void acceptRequest(Request request) {
        requestDao.update(request);
    }

    public void deleteRequest(Request request) {
        requestDao.delete(request);
    }

    public Request getById(long requestID) {
        return requestDao.getById(requestID);
    }

    public void setRequestUnconfirmed(Request request) {
        request.setRequestStatus(REQUEST_CREATED_STATUS);
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
