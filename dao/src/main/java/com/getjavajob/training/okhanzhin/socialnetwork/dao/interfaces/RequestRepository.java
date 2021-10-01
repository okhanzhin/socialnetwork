package com.getjavajob.training.okhanzhin.socialnetwork.dao.interfaces;

import com.getjavajob.training.okhanzhin.socialnetwork.domain.Account;
import com.getjavajob.training.okhanzhin.socialnetwork.domain.Community;
import com.getjavajob.training.okhanzhin.socialnetwork.domain.request.Request;

import java.util.List;

public interface RequestRepository extends AbstractRepository<Request, Long> {

    Request getAccountRequest(Account sourceAccount, Account targetAccount);

    Request getCommunityRequest(Account sourceAccount, Community targetCommunity);

    List<Request> getCommunityRequests(Community community);

    List<Request> getPendingRequests(Account account);

    List<Request> getOutgoingRequests(Account account);

    List<Request> getAcceptedRequestsList(Account account);
}
