package com.getjavajob.training.okhanzhin.socialnetwork.service;

import com.getjavajob.training.okhanzhin.socialnetwork.dao.AccountDao;
import com.getjavajob.training.okhanzhin.socialnetwork.dao.RelationshipDao;
import com.getjavajob.training.okhanzhin.socialnetwork.dao.RequestDao;
import com.getjavajob.training.okhanzhin.socialnetwork.domain.Account;
import com.getjavajob.training.okhanzhin.socialnetwork.domain.Group;
import com.getjavajob.training.okhanzhin.socialnetwork.domain.Request;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AccountService {
    private final AccountDao accountDao;
    private final RelationshipDao relationDao;
    private final RequestDao requestDao;

    public AccountService(AccountDao accountDao, RelationshipDao relationDao, RequestDao requestDao) {
        this.accountDao = accountDao;
        this.relationDao = relationDao;
        this.requestDao = requestDao;
    }

    public AccountService() {
        this.accountDao = new AccountDao();
        this.relationDao = new RelationshipDao();
        this.requestDao = new RequestDao();
    }

    public Account createAccount(Account account) {
        return accountDao.create(account);
    }

    public void updateAccount(Account account) {
        Account updatableAccount = accountDao.getById(account.getAccountID());

        if (!updatableAccount.getSurname().equals(account.getSurname())) {
            updatableAccount.setSurname(account.getSurname());
        }
        if (!updatableAccount.getName().equals(account.getName())) {
            updatableAccount.setName(account.getName());
        }

        if (account.getMiddlename() != null && account.getMiddlename().length() != 0) {
            if (updatableAccount.getMiddlename() == null) {
                updatableAccount.setMiddlename(account.getMiddlename());
            } else {
                if (!updatableAccount.getMiddlename().equals(account.getMiddlename())) {
                    updatableAccount.setMiddlename(account.getMiddlename());
                }
            }
        } else if (account.getMiddlename() != null && account.getMiddlename().length() == 0) {
            updatableAccount.setMiddlename(null);
        }

        if (account.getDateOfBirth() != null) {
            if (updatableAccount.getDateOfBirth() == null) {
                updatableAccount.setDateOfBirth(account.getDateOfBirth());
            } else if (updatableAccount.getDateOfBirth() != null) {
                if (!updatableAccount.getDateOfBirth().equals(account.getDateOfBirth())) {
                    updatableAccount.setDateOfBirth(account.getDateOfBirth());
                }
            }
        } else {
            updatableAccount.setDateOfBirth(null);
        }

        if (account.getHomePhone() != null && account.getHomePhone().length() != 0) {
            if (updatableAccount.getHomePhone() == null) {
                updatableAccount.setHomePhone(account.getHomePhone());
            } else {
                if (!updatableAccount.getHomePhone().equals(account.getHomePhone())) {
                    updatableAccount.setHomePhone(account.getHomePhone());
                }
            }
        } else if (account.getHomePhone() != null && account.getHomePhone().length() == 0) {
            updatableAccount.setHomePhone(null);
        }

        if (account.getWorkPhone() != null && account.getWorkPhone().length() != 0) {
            if (updatableAccount.getWorkPhone() == null) {
                updatableAccount.setWorkPhone(account.getWorkPhone());
            } else {
                if (!updatableAccount.getWorkPhone().equals(account.getWorkPhone())) {
                    updatableAccount.setWorkPhone(account.getWorkPhone());
                }
            }
        } else if (account.getWorkPhone() != null && account.getWorkPhone().length() == 0) {
            updatableAccount.setWorkPhone(null);
        }

        if (account.getSkype() != null && account.getSkype().length() != 0) {
            if (updatableAccount.getSkype() == null) {
                updatableAccount.setSkype(account.getSkype());
            } else {
                if (!updatableAccount.getSkype().equals(account.getSkype())) {
                    updatableAccount.setSkype(account.getSkype());
                }
            }
        } else if (account.getSkype() != null && account.getSkype().length() == 0) {
            updatableAccount.setSkype(null);
        }

        if (account.getIcq() != null && account.getIcq().length() != 0) {
            if (updatableAccount.getIcq() == null) {
                updatableAccount.setIcq(account.getIcq());
            } else {
                if (!updatableAccount.getIcq().equals(account.getIcq())) {
                    updatableAccount.setIcq(account.getIcq());
                }
            }
        } else if (account.getIcq() != null && account.getIcq().length() == 0) {
            updatableAccount.setIcq(null);
        }

        if (account.getHomeAddress() != null && account.getHomeAddress().length() != 0) {
            if (updatableAccount.getHomeAddress() == null) {
                updatableAccount.setHomeAddress(account.getHomeAddress());
            } else {
                if (!updatableAccount.getHomeAddress().equals(account.getHomeAddress())) {
                    updatableAccount.setHomeAddress(account.getHomeAddress());
                }
            }
        } else if (account.getHomeAddress() != null && account.getHomeAddress().length() == 0) {
            updatableAccount.setHomeAddress(null);
        }

        if (account.getWorkAddress() != null && account.getWorkAddress().length() != 0) {
            if (updatableAccount.getWorkAddress() == null) {
                updatableAccount.setWorkAddress(account.getWorkAddress());
            } else {
                if (!updatableAccount.getWorkAddress().equals(account.getWorkAddress())) {
                    updatableAccount.setWorkAddress(account.getWorkAddress());
                }
            }
        } else if (account.getWorkAddress() != null && account.getWorkAddress().length() == 0) {
            updatableAccount.setWorkAddress(null);
        }

        if (account.getAddInfo() != null && account.getAddInfo().length() != 0) {
            if (updatableAccount.getAddInfo() == null) {
                updatableAccount.setAddInfo(account.getAddInfo());
            } else {
                if (!updatableAccount.getAddInfo().equals(account.getAddInfo())) {
                    updatableAccount.setAddInfo(account.getAddInfo());
                }
            }
        } else if (account.getAddInfo() != null && account.getAddInfo().length() == 0) {
            updatableAccount.setAddInfo(account.getAddInfo());
        }

        if (account.getRole() != null && account.getRole().length() != 0) {
            if (updatableAccount.getRole() == null) {
                updatableAccount.setRole(account.getRole());
            } else {
                if (!updatableAccount.getRole().equals(account.getRole())) {
                    updatableAccount.setRole(account.getRole());
                }
            }
        } else if (account.getRole() != null && account.getRole().length() == 0) {
            updatableAccount.setRole(null);
        }

        if (updatableAccount.getPicture() != null && account.getPicture().length != 0) {
            if (!Arrays.equals(updatableAccount.getPicture(), account.getPicture())) {
                updatableAccount.setPicture(account.getPicture());
            }
        } else if (updatableAccount.getPicture() == null) {
            if (account.getPicture().length != 0) {
                updatableAccount.setPicture(account.getPicture());
            } else {
                updatableAccount.setPicture(updatableAccount.getPicture());
            }
        }

        accountDao.update(updatableAccount);
    }

    public void delete(Account account) {
        accountDao.delete(account);
    }

    public Account getById(long id) {
        return accountDao.getById(id);
    }

    public Account getByEmail(String email) {
        return accountDao.getByEmail(email);
    }

    public List<Account> getAccountsListForGroup(Group group) {
        return accountDao.getAccountsForGroup(group);
    }

    public List<Account> getUnconfirmedRequestAccountsForGroup(Group group) {
        return accountDao.getUnconfirmedRequestAccountsForGroup(group);
    }

    public boolean createRelation(long creatorID, long recipientID) {
        return relationDao.createRelation(creatorID, recipientID);
    }

    public boolean acceptRelation(long creatorID, long recipientID) {
        return relationDao.acceptRelation(creatorID, recipientID);
    }

    public boolean declineRelation(long creatorID, long recipientID) {
        return relationDao.declineRelation(creatorID, recipientID);
    }

    public boolean deleteRelation(long creatorID, long recipientID) {
        return relationDao.breakRelation(creatorID, recipientID);
    }

    public List<Account> getAccountFriends(Account account) {
        return relationDao.getFriendsList(account);
    }

    public List<Account> getPendingRequestAccounts(Account account) {
        List<Request> pendingRequests = requestDao.getPendingRequestsList(account);
        List<Account> pendingAccounts = new ArrayList<>();

        if (!pendingRequests.isEmpty()) {
            for (Request request : pendingRequests) {
                pendingAccounts.add(accountDao.getById(request.getCreatorID()));
            }
        }
        return pendingAccounts;
    }

    public List<Account> getOutgoingRequestAccounts(Account account) {
        List<Request> outgoingRequests = requestDao.getOutgoingRequestsList(account);
        List<Account> outgoingAccounts = new ArrayList<>();

        if (!outgoingRequests.isEmpty()) {
            for (Request request : outgoingRequests) {
                outgoingAccounts.add(accountDao.getById(request.getRecipientID()));
            }
        }
        return outgoingAccounts;
    }
}
