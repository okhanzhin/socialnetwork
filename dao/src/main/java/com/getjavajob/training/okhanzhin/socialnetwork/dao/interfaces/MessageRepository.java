package com.getjavajob.training.okhanzhin.socialnetwork.dao.interfaces;

import com.getjavajob.training.okhanzhin.socialnetwork.domain.Account;
import com.getjavajob.training.okhanzhin.socialnetwork.domain.Message;

import java.util.List;

public interface MessageRepository extends AbstractRepository<Message, Long> {

    List<Message> getMessageSequence(Long id1, Long id2);

    long[] getInterlocutorIdentifiers(Account account);
}
