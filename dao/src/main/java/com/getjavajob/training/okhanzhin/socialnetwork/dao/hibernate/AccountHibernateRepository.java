package com.getjavajob.training.okhanzhin.socialnetwork.dao.hibernate;

import com.getjavajob.training.okhanzhin.socialnetwork.dao.interfaces.AccountRepository;
import com.getjavajob.training.okhanzhin.socialnetwork.domain.Account;
import com.getjavajob.training.okhanzhin.socialnetwork.domain.Community;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityGraph;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class AccountHibernateRepository extends AbstractHibernateRepository<Account, Long> implements AccountRepository {
    private static final String GET_BY_EMAIL = "SELECT a FROM Account a WHERE a.email = :email";
    private static final String GET_ACCOUNTS_LIST_BY_COMMUNITY_ID = "SELECT a FROM Account a JOIN Member m " +
            "ON a.accountID = m.account.accountID WHERE m.community = :community";
    private static final String GET_TODAY_BIRTHDAY_ACCOUNTS = "SELECT a FROM Account a WHERE a.dateOfBirth = :dateOfBirth";
    public static final String IS_ACCOUNT_AVAILABLE = "SELECT a.enabled FROM Account a WHERE a.email= :email";
    private static final String GET_ALL_EMAILS = "SELECT a.email FROM Account a";
    private static final String UPDATE_PASSWORD_BY_ACCOUNT_ID = "UPDATE Account a SET a.password = :password " +
            "WHERE a.accountID = :accountID";

    public AccountHibernateRepository() {
        setEntityClass(Account.class);
    }

    @Override
    protected List<String> makePaths() {
        List<String> paths = new ArrayList<>();
        paths.add("surname");
        paths.add("name");

        return paths;
    }

    public Account getByEmail(String email) {
        TypedQuery<Account> query = entityManager.
                createQuery(GET_BY_EMAIL, Account.class).
                setParameter("email", email);

        return query.getResultList().stream().findFirst().orElse(null);
    }

    public Account getByIdFetchPhones(Long id) {
        EntityGraph<?> graph = entityManager.getEntityGraph("graph.Account.phones");
        Map<String, Object> hints = new HashMap<>();
        hints.put("javax.persistence.fetchgraph", graph);

        return entityManager.find(Account.class, id, hints);
    }

    public Account getByEmailFetchPhones(String email) {
        EntityGraph<?> graph = entityManager.getEntityGraph("graph.Account.phones");
        TypedQuery<Account> query = entityManager.createQuery(GET_BY_EMAIL, Account.class).
                setParameter("email", email).
                setHint("javax.persistence.fetchgraph", graph);

        return query.getSingleResult();
    }

    public List<Account> getAccountsForCommunity(Community community) {
        TypedQuery<Account> query = entityManager.
                createQuery(GET_ACCOUNTS_LIST_BY_COMMUNITY_ID, Account.class).
                setParameter("community", community);

        return query.getResultList();
    }

    public List<Account> getTodayBirthdayAccounts() {
        TypedQuery<Account> query = entityManager.
                createQuery(GET_TODAY_BIRTHDAY_ACCOUNTS, Account.class).
                setParameter("dateOfBirth", LocalDate.now());

        return query.getResultList();
    }

    public Boolean isAccountAvailable(String email) {
        TypedQuery<Boolean> query = entityManager.
                createQuery(IS_ACCOUNT_AVAILABLE, Boolean.class).
                setParameter("email", email);

        return query.getSingleResult();
    }

    public List<String> getExistingEmails() {
        TypedQuery<String> query = entityManager.
                createQuery(GET_ALL_EMAILS, String.class);

        return query.getResultList();
    }

    @Modifying
    public void updatePassword(String password, Long accountID) {
        Query query = entityManager.createQuery(UPDATE_PASSWORD_BY_ACCOUNT_ID);
        query.setParameter("password", password);
        query.setParameter("accountID", accountID);
        query.executeUpdate();
    }
}
