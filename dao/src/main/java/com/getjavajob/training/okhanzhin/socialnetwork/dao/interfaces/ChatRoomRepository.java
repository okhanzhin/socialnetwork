package com.getjavajob.training.okhanzhin.socialnetwork.dao.interfaces;

import com.getjavajob.training.okhanzhin.socialnetwork.domain.Account;
import com.getjavajob.training.okhanzhin.socialnetwork.domain.ChatRoom;

import java.util.List;

public interface ChatRoomRepository extends AbstractRepository<ChatRoom, Long> {

    ChatRoom getByIdFetchAttributes(Long id);

    ChatRoom getByInterlocutors(Account interlocutorOne, Account interlocutorTwo);

    ChatRoom getByInterlocutorsFetchAttributes(Account interlocutorOne, Account interlocutorTwo);

    List<ChatRoom> getAccountChatRooms(Account account);

    Boolean isChatRoomExist(Account interlocutorOne, Account interlocutorTwo);
}
