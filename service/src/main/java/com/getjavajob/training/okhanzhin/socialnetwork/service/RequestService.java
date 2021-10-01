package com.getjavajob.training.okhanzhin.socialnetwork.service;

import com.getjavajob.training.okhanzhin.socialnetwork.dao.interfaces.RequestRepository;
import com.getjavajob.training.okhanzhin.socialnetwork.domain.Account;
import com.getjavajob.training.okhanzhin.socialnetwork.domain.Community;
import com.getjavajob.training.okhanzhin.socialnetwork.domain.request.AccountRequest;
import com.getjavajob.training.okhanzhin.socialnetwork.domain.request.CommunityRequest;
import com.getjavajob.training.okhanzhin.socialnetwork.domain.request.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RequestService {
    public static final Request.Status ACCEPTED = Request.Status.ACCEPTED;
    public static final Request.Status UNCONFIRMED = Request.Status.UNCONFIRMED;

    private final RequestRepository requestRepository;

    @Autowired
    public RequestService(RequestRepository requestRepository) {
        this.requestRepository = requestRepository;
    }

    @Transactional
    public Request createAccountRequest(Account creator, Account recipient) {
        return requestRepository.create(new AccountRequest(creator, recipient, UNCONFIRMED));
    }

    @Transactional
    public Request createCommunityRequest(Account creator, Community recipient) {
        return requestRepository.create(new CommunityRequest(creator, recipient, UNCONFIRMED));
    }

    @Transactional
    public Request acceptAccountRequest(Account creator, Account recipient) {
        Request request = getAccountRequest(creator, recipient);
        request.setRequestStatus(ACCEPTED);

        return requestRepository.update(request);
    }

    @Transactional
    public Request acceptCommunityRequest(Account creator, Community recipient) {
        Request request = getCommunityRequest(creator, recipient);
        request.setRequestStatus(ACCEPTED);

        return requestRepository.update(request);
    }

    @Transactional
    public void deleteRequest(Request request) {
        requestRepository.deleteById(request.getRequestID());
    }

    public Request getById(long requestID) {
        return requestRepository.getById(requestID);
    }

    @Transactional
    public void setRequestUnconfirmed(Request request) {
        request.setRequestStatus(UNCONFIRMED);
        requestRepository.update(request);
    }

    public Request getAccountRequest(Account creator, Account recipient) {
        return requestRepository.getAccountRequest(creator, recipient);
    }

    public Request getCommunityRequest(Account creator, Community recipient) {
        return requestRepository.getCommunityRequest(creator, recipient);
    }

    public List<Account> getRequestingAccounts(Community community) {
        List<Request> communityRequestsList = requestRepository.getCommunityRequests(community);
        List<Account> accounts = new ArrayList<>();

        if (!communityRequestsList.isEmpty()) {
            for (Request request : communityRequestsList) {
                accounts.add(request.getSource());
            }
        }

        return accounts;
    }

    public List<Account> getPendingAccounts(Account account) {
        List<Request> pendingRequests = requestRepository.getPendingRequests(account);
        List<Account> accounts = new ArrayList<>();

        if (!pendingRequests.isEmpty()) {
            for (Request request : pendingRequests) {
                accounts.add(request.getSource());
            }
        }

        return accounts;
    }

    public List<Account> getOutgoingAccounts(Account account) {
        List<Request> outgoingRequests = requestRepository.getOutgoingRequests(account);
        List<Account> accounts = new ArrayList<>();

        if (!outgoingRequests.isEmpty()) {
            for (Request request : outgoingRequests) {
                AccountRequest accountRequest = (AccountRequest) request;
                accounts.add(accountRequest.getAccountTarget());
            }
        }

        return accounts;
    }

    public List<AccountRequest> getAcceptedRequests(Account account) {
        List<Request> requests = requestRepository.getAcceptedRequestsList(account);

        return requests.stream().map(e -> (AccountRequest) e).collect(Collectors.toList());
    }
}
