package com.getjavajob.training.okhanzhin.socialnetwork.dao.spring;

import com.getjavajob.training.okhanzhin.socialnetwork.dao.QueryConstants;
import com.getjavajob.training.okhanzhin.socialnetwork.dao.interfaces.RequestRepository;
import com.getjavajob.training.okhanzhin.socialnetwork.domain.Account;
import com.getjavajob.training.okhanzhin.socialnetwork.domain.Community;
import com.getjavajob.training.okhanzhin.socialnetwork.domain.request.Request;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RequestSpringRepository extends AbstractSpringRepository<Request, Long>, RequestRepository {

    @Query(QueryConstants.GET_ACCOUNT_REQUEST)
    Request getAccountRequest(@Param("source") Account sourceAccount, @Param("accTarget") Account targetAccount);

    @Query(QueryConstants.GET_COMMUNITY_REQUEST)
    Request getCommunityRequest(@Param("source") Account sourceAccount, @Param("commTarget") Community targetCommunity);

    @Query(QueryConstants.GET_UNCONFIRMED_GROUP_REQUESTS)
    List<Request> getCommunityRequests(@Param("community") Community community);

    @Query(QueryConstants.GET_PENDING_REQUESTS_BY_ACCOUNT_ID)
    List<Request> getPendingRequests(@Param("account") Account account);

    @Query(QueryConstants.GET_OUTGOING_REQUESTS_BY_ACCOUNT_ID)
    List<Request> getOutgoingRequests(@Param("account") Account account);

    @Query(QueryConstants.GET_ACCEPTED_REQUESTS_BY_ACCOUNT_ID)
    List<Request> getAcceptedRequestsList(@Param("account") Account account);
}
