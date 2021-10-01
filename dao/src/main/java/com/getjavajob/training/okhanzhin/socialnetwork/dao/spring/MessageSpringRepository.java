package com.getjavajob.training.okhanzhin.socialnetwork.dao.spring;

import com.getjavajob.training.okhanzhin.socialnetwork.dao.QueryConstants;
import com.getjavajob.training.okhanzhin.socialnetwork.dao.interfaces.MessageRepository;
import com.getjavajob.training.okhanzhin.socialnetwork.domain.Account;
import com.getjavajob.training.okhanzhin.socialnetwork.domain.Message;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.requireNonNull;

@Repository
public interface MessageSpringRepository extends AbstractSpringRepository<Message, Long>, MessageRepository {

    @Query(QueryConstants.GET_MESSAGE_SEQUENCE)
    List<Message> getMessageSequence(@Param("accountID1") Long id1, @Param("accountID2") Long id2);

    default long[] getInterlocutorIdentifiers(Account account) {
        Long accountID = account.getAccountID();
        List<Integer> resultList = new ArrayList<>();

        for (Long result : getIdentifiers(accountID, accountID)) {
            resultList.add(result.intValue());
        }

        return requireNonNull(resultList).stream().mapToLong(l -> l).toArray();
    }

    @Query(value = QueryConstants.GET_INTERLOCUTORS_IDENTIFIERS, nativeQuery = true)
    List<Long> getIdentifiers(Long targetID, Long sourceID);
}
