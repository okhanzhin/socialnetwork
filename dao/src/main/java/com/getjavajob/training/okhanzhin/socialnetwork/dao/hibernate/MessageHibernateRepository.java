package com.getjavajob.training.okhanzhin.socialnetwork.dao.hibernate;

import com.getjavajob.training.okhanzhin.socialnetwork.dao.interfaces.MessageRepository;
import com.getjavajob.training.okhanzhin.socialnetwork.domain.Account;
import com.getjavajob.training.okhanzhin.socialnetwork.domain.Message;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.requireNonNull;

@Repository
public class MessageHibernateRepository extends AbstractHibernateRepository<Message, Long> implements MessageRepository {
    private static final String GET_MESSAGE_SEQUENCE = "SELECT m FROM Message m WHERE " +
                    "(m.source.accountID = :accountID1 AND m.target.accountID = :accountID2) " +
                    "OR (m.source.accountID = :accountID2 AND m.target.accountID = :accountID1)";
    private static final String GET_INTERLOCUTORS_IDENTIFIERS = "SELECT sourceID FROM messages " +
                    "WHERE targetID = ? UNION SELECT targetID FROM messages WHERE sourceID = ?";

    public MessageHibernateRepository() {
        setEntityClass(Message.class);
    }

    @Override
    protected List<String> makePaths() {
        return null;
    }

    public List<Message> getMessageSequence(Long id1, Long id2) {
        TypedQuery<Message> query = entityManager.
                createQuery(GET_MESSAGE_SEQUENCE, Message.class).
                setParameter("accountID1", id1).
                setParameter("accountID2", id2);

        return query.getResultList();
    }

    public long[] getInterlocutorIdentifiers(Account account) {
        Long profileAccountID = account.getAccountID();
        Query query = entityManager.createNativeQuery(GET_INTERLOCUTORS_IDENTIFIERS);
        query.setParameter(1, profileAccountID);
        query.setParameter(2, profileAccountID);

        List<Integer> resultList = new ArrayList<>();

        for (Object result : query.getResultList()) {
            resultList.add((Integer) result);
        }

        return requireNonNull(resultList).stream().mapToLong(l -> l).toArray();
    }
}
