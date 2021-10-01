package com.getjavajob.training.okhanzhin.socialnetwork.dao.hibernate;

import com.getjavajob.training.okhanzhin.socialnetwork.dao.interfaces.MemberRepository;
import com.getjavajob.training.okhanzhin.socialnetwork.domain.Account;
import com.getjavajob.training.okhanzhin.socialnetwork.domain.Community;
import com.getjavajob.training.okhanzhin.socialnetwork.domain.Member;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class MemberHibernateRepository extends AbstractHibernateRepository<Member, Long> implements MemberRepository {
    private static final String GET_MEMBER = "SELECT m FROM Member m WHERE m.account = :account " +
            "AND m.community = :community";
    private static final String DELETE_MEMBER = "DELETE FROM Member m WHERE m.account = :account " +
            "AND m.community = :community";
    private static final String GET_OWNER_BY_COMMUNITY_ID = "SELECT m FROM Member m " +
            "WHERE m.community.commID = :commID AND m.memberStatus = 0";
    private static final String GET_MEMBERS_BY_COMMUNITY_ID = "SELECT m FROM Member m WHERE m.community.commID = :commID";
    private static final String GET_COMMUNITY_MODERATORS = "SELECT m FROM Member m WHERE m.community.commID= :commID " +
            "AND m.memberStatus = 1";

    public MemberHibernateRepository() {
        setEntityClass(Member.class);
    }

    @Override
    protected List<String> makePaths() {
        return null;
    }

    public Member getMember(Account account, Community community) {
        TypedQuery<Member> query = entityManager.
                createQuery(GET_MEMBER, Member.class).
                setParameter("account", account).
                setParameter("community", community);

        return query.getResultStream().findFirst().orElse(null);
    }

    public void deleteMember(Account account, Community community) {
        Query query = entityManager.
                createQuery(DELETE_MEMBER).
                setParameter("account", account).
                setParameter("community", community);

        query.executeUpdate();
    }

    public Member getOwnerByCommId(Long id) {
        TypedQuery<Member> query = entityManager.
                createQuery(GET_OWNER_BY_COMMUNITY_ID, Member.class).
                setParameter("commID", id);

        return query.getSingleResult();
    }

    public List<Member> getCommunityMembers(Long id) {
        TypedQuery<Member> query = entityManager.
                createQuery(GET_MEMBERS_BY_COMMUNITY_ID, Member.class).
                setParameter("commID", id);

        return query.getResultList();
    }

    public List<Member> getCommunityModerators(Long id) {
        TypedQuery<Member> query = entityManager.
                createQuery(GET_COMMUNITY_MODERATORS, Member.class).
                setParameter("commID", id);

        return query.getResultList();
    }
}
