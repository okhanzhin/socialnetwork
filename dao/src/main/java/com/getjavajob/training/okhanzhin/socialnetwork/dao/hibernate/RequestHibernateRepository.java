package com.getjavajob.training.okhanzhin.socialnetwork.dao.hibernate;

import com.getjavajob.training.okhanzhin.socialnetwork.dao.interfaces.RequestRepository;
import com.getjavajob.training.okhanzhin.socialnetwork.domain.Account;
import com.getjavajob.training.okhanzhin.socialnetwork.domain.Community;
import com.getjavajob.training.okhanzhin.socialnetwork.domain.request.Request;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class RequestHibernateRepository extends AbstractHibernateRepository<Request, Long> implements RequestRepository {
    private static final String GET_ACCOUNT_REQUEST = "SELECT r FROM AccountRequest r WHERE r.source = :source " +
            "AND r.accountTarget = :accTarget";
    private static final String GET_COMMUNITY_REQUEST = "SELECT r FROM CommunityRequest r WHERE r.source = :source " +
            "AND r.communityTarget = :commTarget";
    private static final String GET_UNCONFIRMED_GROUP_REQUESTS =
            "SELECT r FROM CommunityRequest r JOIN FETCH r.source s JOIN FETCH r.communityTarget ct " +
                    "WHERE r.communityTarget = :community AND r.requestStatus = 0";
    private static final String GET_PENDING_REQUESTS_BY_ACCOUNT_ID =
            "SELECT r FROM AccountRequest r JOIN FETCH r.source s JOIN FETCH r.accountTarget at " +
                    "WHERE r.accountTarget = :account AND r.requestStatus = 0";
    private static final String GET_OUTGOING_REQUESTS_BY_ACCOUNT_ID =
            "SELECT r FROM AccountRequest r JOIN FETCH r.source s JOIN FETCH r.accountTarget at " +
                    "WHERE r.source = :account AND r.requestStatus = 0";
    private static final String GET_ACCEPTED_REQUESTS_BY_ACCOUNT_ID =
            "SELECT r FROM AccountRequest r JOIN FETCH r.source s JOIN FETCH r.accountTarget at " +
                    "WHERE (r.source = :account OR r.accountTarget = :account) AND r.requestStatus = 1";

    public RequestHibernateRepository() {
        setEntityClass(Request.class);
    }

    @Override
    protected List<String> makePaths() {
        return null;
    }

    public Request getAccountRequest(Account sourceAccount, Account targetAccount) {
        TypedQuery<Request> query = entityManager.createQuery(GET_ACCOUNT_REQUEST, Request.class).
                setParameter("source", sourceAccount).
                setParameter("accTarget", targetAccount);

        return query.getSingleResult();
    }

    public Request getCommunityRequest(Account sourceAccount, Community targetCommunity) {
        TypedQuery<Request> query = entityManager.createQuery(GET_COMMUNITY_REQUEST, Request.class).
                setParameter("source", sourceAccount).
                setParameter("commTarget", targetCommunity);

        return query.getSingleResult();
    }

    public List<Request> getCommunityRequests(Community community) {
        TypedQuery<Request> query = entityManager.createQuery(GET_UNCONFIRMED_GROUP_REQUESTS, Request.class).
                setParameter("community", community);

        return query.getResultList();
    }

    public List<Request> getPendingRequests(Account account) {
        TypedQuery<Request> query = entityManager.createQuery(GET_PENDING_REQUESTS_BY_ACCOUNT_ID, Request.class).
                setParameter("account", account);

        return query.getResultList();
    }

    public List<Request> getOutgoingRequests(Account account) {
        TypedQuery<Request> query = entityManager.createQuery(GET_OUTGOING_REQUESTS_BY_ACCOUNT_ID, Request.class).
                setParameter("account", account);

        return query.getResultList();
    }

    public List<Request> getAcceptedRequestsList(Account account) {
        TypedQuery<Request> query = entityManager.createQuery(GET_ACCEPTED_REQUESTS_BY_ACCOUNT_ID, Request.class).
                setParameter("account", account);

        return query.getResultList();
    }
}
