package com.getjavajob.training.okhanzhin.socialnetwork.dao.hibernate;

import com.getjavajob.training.okhanzhin.socialnetwork.dao.interfaces.RelationRepository;
import com.getjavajob.training.okhanzhin.socialnetwork.domain.Account;
import com.getjavajob.training.okhanzhin.socialnetwork.domain.Relationship;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Repository
public class RelationHibernateRepository extends AbstractHibernateRepository<Relationship, Long> implements RelationRepository {
    private static final String GET_RELATION_BY_ACCOUNTS = "SELECT r FROM Relationship r WHERE " +
            "(r.accountOne = :accountOne AND r.accountTwo = :accountTwo) OR " +
            "(r.accountOne = :accountTwo AND r.accountTwo = :accountOne)";
    private static final String GET_ACCEPTED_RELATIONS =
            "SELECT r FROM Relationship r JOIN FETCH r.accountOne a1 JOIN FETCH r.accountTwo a2 " +
                    "WHERE (r.accountOne = :account OR r.accountTwo = :account) AND r.relationStatus = 1";
    private static final String GET_BLOCKED_RELATIONS =
            "SELECT r FROM Relationship r JOIN FETCH r.accountOne a1 JOIN FETCH r.accountTwo a2 " +
                    "WHERE (r.accountOne = :account OR r.accountTwo = :account) AND r.relationStatus = 3";

    public RelationHibernateRepository() {
        setEntityClass(Relationship.class);
    }

    @Override
    protected List<String> makePaths() {
        return null;
    }

    public Relationship getRelation(Account accountOne, Account accountTwo) {
        TypedQuery<Relationship> query = entityManager.createQuery(GET_RELATION_BY_ACCOUNTS, Relationship.class).
                setParameter("accountOne", accountOne).
                setParameter("accountTwo", accountTwo);

        return query.getResultStream().findFirst().orElse(null);
    }

    public List<Account> getFriendsList(Account account) {
        TypedQuery<Relationship> query = entityManager.createQuery(GET_ACCEPTED_RELATIONS, Relationship.class).
                setParameter("account", account);

        return retrieveAccounts(account, query);
    }

    public List<Account> getBlockedAccountsList(Account account) {
        TypedQuery<Relationship> query = entityManager.createQuery(GET_BLOCKED_RELATIONS, Relationship.class).
                setParameter("account", account);

        return retrieveAccounts(account, query);
    }

    private List<Account> retrieveAccounts(Account account, TypedQuery<Relationship> query) {
        List<Relationship> relationshipList = query.getResultList();
        List<Account> accountList = new ArrayList<>();

        for (Relationship relationship : relationshipList) {
            if (relationship.getAccountOne().equals(account)) {
                accountList.add(relationship.getAccountTwo());
            } else {
                accountList.add(relationship.getAccountOne());
            }
        }

        return accountList;
    }

    public Boolean isRelationExist(Account accountOne, Account accountTwo) {
        boolean isRelationExist = true;
        if (Objects.isNull(getRelation(accountOne, accountTwo))) {
            isRelationExist = false;
        }

        return isRelationExist;
    }
}
