package com.getjavajob.training.okhanzhin.socialnetwork.service;

import com.getjavajob.training.okhanzhin.socialnetwork.dao.AbstractDao;
import com.getjavajob.training.okhanzhin.socialnetwork.dao.AccountDao;
import com.getjavajob.training.okhanzhin.socialnetwork.dao.RelationshipDao;
import com.getjavajob.training.okhanzhin.socialnetwork.domain.Account;

import java.util.List;

public class AccountService {
    private AbstractDao<Account> accountDao = new AccountDao();
    private RelationshipDao relationDao = new RelationshipDao();

    public Account createAccount(Account account) {
        return accountDao.create(account);
    }

    public void updateAccount(Account account) {
        accountDao.update(account);
    }

    public void delete(Account account) {
        accountDao.delete(account);
    }

    public boolean addFriend(Account loggedAccount, Account addedAccount) {
        return relationDao.sendRequest(loggedAccount.getAccount_ID(), addedAccount.getAccount_ID());
    }

    public boolean deleteFriend(Account loggedAccount, Account deletedAccount) {
        return relationDao.breakRelationship(loggedAccount.getAccount_ID(), deletedAccount.getAccount_ID());
    }

    public List<Account> getAccountFriendsList(Account account) {
        return relationDao.getFriendsList(account);
    }
}
