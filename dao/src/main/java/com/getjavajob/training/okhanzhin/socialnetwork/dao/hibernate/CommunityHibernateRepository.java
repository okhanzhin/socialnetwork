package com.getjavajob.training.okhanzhin.socialnetwork.dao.hibernate;

import com.getjavajob.training.okhanzhin.socialnetwork.dao.interfaces.CommunityRepository;
import com.getjavajob.training.okhanzhin.socialnetwork.domain.Account;
import com.getjavajob.training.okhanzhin.socialnetwork.domain.Community;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;

@Repository
public class CommunityHibernateRepository extends AbstractHibernateRepository<Community, Long> implements CommunityRepository {
    private static final String GET_COMMUNITY_LIST_BY_ACCOUNT_ID = "SELECT c FROM Community c JOIN Member m " +
            "ON c.commID = m.community.commID WHERE m.account = :account";

    public CommunityHibernateRepository() {
        setEntityClass(Community.class);
    }

    @Override
    protected List<String> makePaths() {
        List<String> paths = new ArrayList<>();
        paths.add("communityName");

        return paths;
    }

    public List<Community> getAccountCommunities(Account account) {
        TypedQuery<Community> query = entityManager.
                createQuery(GET_COMMUNITY_LIST_BY_ACCOUNT_ID, Community.class).
                setParameter("account", account);

        return query.getResultList();
    }
}
