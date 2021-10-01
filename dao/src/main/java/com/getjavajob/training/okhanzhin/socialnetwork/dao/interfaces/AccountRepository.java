package com.getjavajob.training.okhanzhin.socialnetwork.dao.interfaces;

import com.getjavajob.training.okhanzhin.socialnetwork.domain.Account;
import com.getjavajob.training.okhanzhin.socialnetwork.domain.Community;

import java.util.List;

public interface AccountRepository extends AbstractRepository<Account, Long> {

    Account getByEmail(String email);

    Account getByEmailFetchPhones(String email);

    Account getByIdFetchPhones(Long id);

    List<Account> getAccountsForCommunity(Community community);

    List<Account> getTodayBirthdayAccounts();

    Boolean isAccountAvailable(String email);

    List<String> getExistingEmails();

    void updatePassword(String password, Long accountID);
}