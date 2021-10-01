package com.getjavajob.training.okhanzhin.socialnetwork.dao.spring;

import com.getjavajob.training.okhanzhin.socialnetwork.dao.QueryConstants;
import com.getjavajob.training.okhanzhin.socialnetwork.dao.interfaces.ChatRoomRepository;
import com.getjavajob.training.okhanzhin.socialnetwork.domain.Account;
import com.getjavajob.training.okhanzhin.socialnetwork.domain.ChatRoom;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatRoomSpringRepository extends AbstractSpringRepository<ChatRoom, Long>, ChatRoomRepository {

    @Query(QueryConstants.GET_CHAT_ROOM_BY_ID)
    @EntityGraph(value = "graph.ChatRoom.interlocutors.messages")
    ChatRoom getByIdFetchAttributes(@Param("id") Long id);

    @Query(QueryConstants.GET_CHAT_ROOM_BY_INTERLOCUTORS)
    ChatRoom getByInterlocutors(@Param("interlocutorOne") Account interlocutorOne,
                                @Param("interlocutorTwo") Account interlocutorTwo);

    @Query(QueryConstants.GET_CHAT_ROOM_BY_INTERLOCUTORS)
    @EntityGraph(value = "graph.ChatRoom.interlocutors.messages")
    ChatRoom getByInterlocutorsFetchAttributes(@Param("interlocutorOne") Account interlocutorOne,
                                               @Param("interlocutorTwo") Account interlocutorTwo);

    @Query(QueryConstants.GET_CHAT_ROOMS_LIST_BY_ACCOUNT_ID)
    @EntityGraph(value = "graph.ChatRoom.interlocutors.messages")
    List<ChatRoom> getAccountChatRooms(@Param("account") Account account);

    @Override
    default Boolean isChatRoomExist(Account interlocutorOne, Account interlocutorTwo) {
        boolean isExist = true;
        if (getByInterlocutors(interlocutorOne, interlocutorTwo) == null) {
            isExist = false;
        }

        return isExist;
    }
}
