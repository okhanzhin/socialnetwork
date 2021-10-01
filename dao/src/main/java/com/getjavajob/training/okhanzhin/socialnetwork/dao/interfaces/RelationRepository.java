package com.getjavajob.training.okhanzhin.socialnetwork.dao.interfaces;

import com.getjavajob.training.okhanzhin.socialnetwork.domain.Account;
import com.getjavajob.training.okhanzhin.socialnetwork.domain.Relationship;

import java.util.List;

public interface RelationRepository extends AbstractRepository<Relationship, Long> {

    Relationship getRelation(Account accountOne, Account accountTwo);

    List<Account> getFriendsList(Account account);

    List<Account> getBlockedAccountsList(Account account);

    Boolean isRelationExist(Account accountOne, Account accountTwo);
}
