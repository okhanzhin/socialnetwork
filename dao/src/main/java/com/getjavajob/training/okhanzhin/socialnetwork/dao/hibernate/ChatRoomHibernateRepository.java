package com.getjavajob.training.okhanzhin.socialnetwork.dao.hibernate;

import com.getjavajob.training.okhanzhin.socialnetwork.dao.interfaces.ChatRoomRepository;
import com.getjavajob.training.okhanzhin.socialnetwork.domain.Account;
import com.getjavajob.training.okhanzhin.socialnetwork.domain.ChatRoom;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityGraph;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class ChatRoomHibernateRepository extends AbstractHibernateRepository<ChatRoom, Long> implements ChatRoomRepository {
    private static final String GET_CHAT_ROOM_BY_INTERLOCUTORS = "SELECT c FROM ChatRoom c " +
            "WHERE (c.interlocutorOne = :interlocutorOne AND c.interlocutorTwo = :interlocutorTwo) OR " +
            "(c.interlocutorOne = :interlocutorTwo AND c.interlocutorTwo = :interlocutorOne)";
    private static final String GET_CHAT_ROOMS_LIST_BY_ACCOUNT_ID = "SELECT DISTINCT c FROM ChatRoom c JOIN FETCH c.messages m " +
            "WHERE c.interlocutorOne = :account OR c.interlocutorTwo = :account";

    public ChatRoomHibernateRepository() {
        setEntityClass(ChatRoom.class);
    }

    @Override
    protected List<String> makePaths() {
        return null;
    }

    @Override
    public ChatRoom getByIdFetchAttributes(Long id) {
        EntityGraph<?> graph = entityManager.getEntityGraph("graph.ChatRoom.interlocutors.messages");
        Map<String, Object> hints = new HashMap<>();
        hints.put("javax.persistence.fetchgraph", graph);

        return entityManager.find(ChatRoom.class, id, hints);
    }

    @Override
    public ChatRoom getByInterlocutors(Account interlocutorOne, Account interlocutorTwo) {
        TypedQuery<ChatRoom> query = entityManager.createQuery(GET_CHAT_ROOM_BY_INTERLOCUTORS, ChatRoom.class).
                setParameter("interlocutorOne", interlocutorOne).
                setParameter("interlocutorTwo", interlocutorTwo);

        return query.getSingleResult();
    }

    @Override
    public ChatRoom getByInterlocutorsFetchAttributes(Account interlocutorOne, Account interlocutorTwo) {
        EntityGraph<?> graph = entityManager.getEntityGraph("graph.ChatRoom.interlocutors.messages");
        TypedQuery<ChatRoom> query = entityManager.createQuery(GET_CHAT_ROOM_BY_INTERLOCUTORS, ChatRoom.class).
                setParameter("interlocutorOne", interlocutorOne).
                setParameter("interlocutorTwo", interlocutorTwo).
                setHint("javax.persistence.fetchgraph", graph);

        return query.getSingleResult();
    }

    @Override
    public List<ChatRoom> getAccountChatRooms(Account account) {
        EntityGraph<?> graph = entityManager.getEntityGraph("graph.ChatRoom.interlocutors.messages");
        TypedQuery<ChatRoom> query = entityManager.
                createQuery(GET_CHAT_ROOMS_LIST_BY_ACCOUNT_ID, ChatRoom.class).
                setParameter("account", account).
                setHint("javax.persistence.fetchgraph", graph);;

        return query.getResultList();
    }

    @Override
    public Boolean isChatRoomExist(Account interlocutorOne, Account interlocutorTwo) {
        boolean isExist = true;
        try {
            TypedQuery<ChatRoom> query = entityManager.createQuery(GET_CHAT_ROOM_BY_INTERLOCUTORS, ChatRoom.class).
                    setParameter("interlocutorOne", interlocutorOne).
                    setParameter("interlocutorTwo", interlocutorTwo);

            query.getSingleResult();
        } catch (NoResultException e) {
            isExist = false;
        }

        return isExist;
    }
}
