package com.getjavajob.training.okhanzhin.socialnetwork.service;

import com.getjavajob.training.okhanzhin.socialnetwork.dao.AccountDao;
import com.getjavajob.training.okhanzhin.socialnetwork.dao.PhoneDao;
import com.getjavajob.training.okhanzhin.socialnetwork.dao.RelationshipDao;
import com.getjavajob.training.okhanzhin.socialnetwork.dao.RequestDao;
import com.getjavajob.training.okhanzhin.socialnetwork.domain.Account;
import com.getjavajob.training.okhanzhin.socialnetwork.domain.Group;
import com.getjavajob.training.okhanzhin.socialnetwork.domain.Phone;
import com.getjavajob.training.okhanzhin.socialnetwork.domain.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class AccountService {
    public static final int RECORDS_PER_PAGE = 8;

    private final AccountDao accountDao;
    private final PhoneDao phoneDao;
    private final RelationshipDao relationDao;
    private final RequestDao requestDao;

    @Autowired
    public AccountService(AccountDao accountDao, PhoneDao phoneDao, RelationshipDao relationDao, RequestDao requestDao) {
        this.accountDao = accountDao;
        this.phoneDao = phoneDao;
        this.relationDao = relationDao;
        this.requestDao = requestDao;
    }

    @Transactional
    public Account createAccount(Account account) {
        account.setDateOfRegistration(LocalDate.now());
        Account newAccount = accountDao.create(account);
        List<Phone> phones = new ArrayList<>();

        if (account.getPhones() != null) {
            for (Phone phone : account.getPhones()) {
                phone.setAccountID(account.getAccountID());
                phones.add(phoneDao.create(phone));
            }
            newAccount.setPhones(phones);
        }

        return newAccount;
    }

    @Transactional
    public void updateAccount(Account account) {
        Account updatableAccount = accountDao.getById(account.getAccountID());
        List<Phone> storagePhones = phoneDao.getAllPhonesByAccountId(updatableAccount.getAccountID());

        if (storagePhones.size() != 0) {
            updatableAccount.setPhones(storagePhones);
        } else {
            updatableAccount.setPhones(null);
        }

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
            if (account.getPicture() != null) {
                updatableAccount.setPicture(account.getPicture());
            } else {
                updatableAccount.setPicture(updatableAccount.getPicture());
            }
        }

        accountDao.update(updatableAccount);

        if (updatableAccount.getPhones() != null && account.getPhones().size() != 0) {
            List<Phone> dataBasePhones = updatableAccount.getPhones();
            List<Phone> inputPhones = account.getPhones();

            inputPhones.removeIf(inputPhone -> inputPhone.getPhoneNumber().equals(""));

            if (inputPhones.size() == 0) {
                for (Phone dataBasePhone : dataBasePhones) {
                    phoneDao.deleteById(dataBasePhone.getPhoneID());
                }
                return;
            }

            if (dataBasePhones.size() == inputPhones.size()) {
                for (int i = 0; i < dataBasePhones.size(); i++) {
                    if (inputPhones.get(i).getPhoneType().equals(dataBasePhones.get(i).getPhoneType())) {
                        if (!inputPhones.get(i).getPhoneNumber().equals(dataBasePhones.get(i).getPhoneNumber())) {
                            dataBasePhones.get(i).setPhoneNumber(inputPhones.get(i).getPhoneNumber());
                            phoneDao.update(dataBasePhones.get(i));
                        }
                    } else {
                        phoneDao.create(inputPhones.get(i));
                    }
                }
            } else {
                if (inputPhones.get(0).getPhoneType().equals(dataBasePhones.get(0).getPhoneType())) {
                    if (!inputPhones.get(0).getPhoneNumber().equals(dataBasePhones.get(0).getPhoneNumber())) {
                        dataBasePhones.get(0).setPhoneNumber(inputPhones.get(0).getPhoneNumber());
                        phoneDao.update(dataBasePhones.get(0));
                    }
                    if (dataBasePhones.size() > inputPhones.size()) {
                        phoneDao.deleteById(dataBasePhones.get(1).getPhoneID());
                    } else {
                        phoneDao.create(inputPhones.get(1));
                    }
                } else {
                    if (dataBasePhones.size() > inputPhones.size()) {
                        if (!inputPhones.get(0).getPhoneNumber().equals(dataBasePhones.get(1).getPhoneNumber())) {
                            dataBasePhones.get(1).setPhoneNumber(inputPhones.get(0).getPhoneNumber());
                            phoneDao.update(dataBasePhones.get(1));
                        }
                        phoneDao.deleteById(dataBasePhones.get(0).getPhoneID());
                    } else {
                        dataBasePhones.get(0).setPhoneType(inputPhones.get(0).getPhoneType());
                        dataBasePhones.get(0).setPhoneNumber(inputPhones.get(0).getPhoneNumber());
                        phoneDao.update(dataBasePhones.get(0));
                        phoneDao.create(inputPhones.get(1));
                    }
                }
            }
        } else if (updatableAccount.getPhones() == null) {
            if (account.getPhones().size() != 0) {
                for (Phone inputPhone : account.getPhones()) {
                    phoneDao.create(inputPhone);
                }
            }
        } else {
            for (Phone phone : updatableAccount.getPhones()) {
                phoneDao.deleteById(phone.getPhoneID());
            }
        }
    }

    @Transactional
    public void delete(Account account) {
        accountDao.deleteById(account.getAccountID());
    }


    public Account getById(long id) {
        Account account = accountDao.getById(id);
        if (account != null) {
            setUpPhones(account);
        }

        return account;
    }

    public Account getByEmail(String email) {
        Account account = accountDao.getByEmail(email);
        if (account != null) {
            setUpPhones(account);
        }

        return account;
    }

    public List<Account> getAccountsListForGroup(Group group) {
        List<Account> accounts = accountDao.getAccountsForGroup(group);
        for (Account account : accounts) {
            setUpPhones(account);
        }

        return accounts;
    }

    public List<Account> getUnconfirmedRequestAccountsForGroup(Group group) {
        List<Account> accounts = accountDao.getUnconfirmedRequestAccountsForGroup(group);
        for (Account account : accounts) {
            setUpPhones(account);
        }

        return accounts;
    }

    @Transactional
    public void createRelation(long creatorID, long recipientID) {
        relationDao.createRelation(creatorID, recipientID);
    }

    @Transactional
    public void acceptRelation(long creatorID, long recipientID) {
        relationDao.acceptRelation(creatorID, recipientID);
    }

    @Transactional
    public void declineRelation(long creatorID, long recipientID) {
        relationDao.declineRelation(creatorID, recipientID);
    }

    @Transactional
    public void deleteRelation(long creatorID, long recipientID) {
        relationDao.breakRelation(creatorID, recipientID);
    }

    public List<Account> getAccountFriends(Account account) {
        List<Account> accounts = relationDao.getFriendsList(account);
        for (Account accountEntry : accounts) {
            setUpPhones(account);
        }

        return accounts;
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

    public List<Account> searchForAccounts(String value, int currentPage) {
        return accountDao.searchEntities(value, currentPage);
    }

    public int getNumberOfPages(String value) {
        int rows = accountDao.getCountOfSearchResults(value);
        int numOfPages = rows / RECORDS_PER_PAGE;

        if (numOfPages % RECORDS_PER_PAGE > 0) {
            numOfPages++;
        }

        return numOfPages;
    }

    private void setUpPhones(Account account) {
        account.setPhones(phoneDao.getAllPhonesByAccountId(account.getAccountID()));
    }
}
