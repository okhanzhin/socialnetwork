package com.getjavajob.training.okhanzhin.socialnetwork.service;

import com.getjavajob.training.okhanzhin.socialnetwork.dao.AbstractDao;
import com.getjavajob.training.okhanzhin.socialnetwork.dao.AccountDao;
import com.getjavajob.training.okhanzhin.socialnetwork.dao.RelationshipDao;
import com.getjavajob.training.okhanzhin.socialnetwork.domain.Account;

import java.util.List;

public class AccountService {
    private AbstractDao<Account> accountDao;
    private RelationshipDao relationDao;

    public AccountService() {
        this.accountDao = new AccountDao();
        this.relationDao = new RelationshipDao();
    }

    public AccountService(AbstractDao<Account> accountDao, RelationshipDao relationDao) {
        this.accountDao = accountDao;
        this.relationDao = relationDao;
    }

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
        return relationDao.sendRequest(loggedAccount.getAccountID(), addedAccount.getAccountID());
    }

    public boolean deleteFriend(Account loggedAccount, Account deletedAccount) {
        return relationDao.breakRelationship(loggedAccount.getAccountID(), deletedAccount.getAccountID());
    }

    public List<Account> getAccountFriendsList(Account account) {
        return relationDao.getFriendsList(account);
    }


    public List<Account> getAllAccountsList() {
        return accountDao.getAll();
    }
}
