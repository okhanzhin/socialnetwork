package com.getjavajob.training.okhanzhin.socialnetwork.service;

import com.getjavajob.training.okhanzhin.socialnetwork.dao.interfaces.RelationRepository;
import com.getjavajob.training.okhanzhin.socialnetwork.domain.Account;
import com.getjavajob.training.okhanzhin.socialnetwork.domain.Relationship;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class RelationService {
    public static final Relationship.Status PENDING = Relationship.Status.PENDING;
    public static final Relationship.Status ACCEPTED = Relationship.Status.ACCEPTED;
    public static final Relationship.Status DECLINED = Relationship.Status.DECLINED;
    public static final Relationship.Status BLOCKED = Relationship.Status.BLOCKED;

    private final RelationRepository relationRepository;

    @Autowired
    public RelationService(RelationRepository relationRepository) {
        this.relationRepository = relationRepository;
    }

    @Transactional
    public Relationship createRelation(Account creator, Account recipient) {
        Relationship relationship = new Relationship(creator, recipient, PENDING);

        return relationRepository.create(relationship);
    }

    @Transactional
    public Relationship acceptRelation(Account acceptor, Account requester) {
        Relationship relationship = relationRepository.getRelation(acceptor, requester);
        relationship.setRelationStatus(ACCEPTED);

        return relationRepository.update(relationship);
    }

    @Transactional
    public Relationship declineRelation(Account decliner, Account requester) {
        Relationship relationship = relationRepository.getRelation(decliner, requester);
        relationship.setRelationStatus(DECLINED);

        return relationRepository.update(relationship);
    }

    @Transactional
    public Relationship pendRelation(Account breaker, Account friend) {
        Relationship relationship = relationRepository.getRelation(breaker, friend);
        relationship.setRelationStatus(PENDING);

        return relationRepository.update(relationship);
    }

    @Transactional
    public Relationship blockRelation(Account blocker, Account requester) {
        Relationship relationship = relationRepository.getRelation(blocker, requester);
        relationship.setRelationStatus(BLOCKED);

        return relationRepository.update(relationship);
    }

    public List<Account> getAccountFriends(Account account) {
        return relationRepository.getFriendsList(account);
    }

    public List<Account> getAccountBlockedUsers(Account account) {
        return relationRepository.getBlockedAccountsList(account);
    }

    public Boolean isRelationExist(Account accountOne, Account accountTwo) {
        return relationRepository.isRelationExist(accountOne, accountTwo);
    }
}
