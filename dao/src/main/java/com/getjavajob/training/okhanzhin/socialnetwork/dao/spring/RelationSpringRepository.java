package com.getjavajob.training.okhanzhin.socialnetwork.dao.spring;

import com.getjavajob.training.okhanzhin.socialnetwork.dao.QueryConstants;
import com.getjavajob.training.okhanzhin.socialnetwork.dao.interfaces.RelationRepository;
import com.getjavajob.training.okhanzhin.socialnetwork.domain.Account;
import com.getjavajob.training.okhanzhin.socialnetwork.domain.Relationship;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Repository
public interface RelationSpringRepository extends AbstractSpringRepository<Relationship, Long>, RelationRepository {

    @Query(QueryConstants.GET_RELATION_BY_ACCOUNTS)
    Relationship getRelation(@Param("accountOne") Account accountOne, @Param("accountTwo") Account accountTwo);

    default List<Account> getFriendsList(Account account) {
        return retrieveAccounts(account, getAcceptedRelations(account));
    }

    @Query(QueryConstants.GET_ACCEPTED_RELATIONS)
    List<Relationship> getAcceptedRelations(@Param("account") Account account);

    default List<Account> getBlockedAccountsList(Account account) {
        return retrieveAccounts(account, getBlockedRelations(account));
    }

    @Query(QueryConstants.GET_BLOCKED_RELATIONS)
    List<Relationship> getBlockedRelations(@Param("account") Account account);

    default List<Account> retrieveAccounts(Account account, List<Relationship> relationshipList) {
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

    default Boolean isRelationExist(Account accountOne, Account accountTwo) {
        boolean isRelationExist = true;
        if (Objects.isNull(getRelation(accountOne, accountTwo))) {
            isRelationExist = false;
        }

        return isRelationExist;
    }
}
