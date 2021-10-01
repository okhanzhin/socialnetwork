package com.getjavajob.training.okhanzhin.socialnetwork.service;

import com.getjavajob.training.okhanzhin.socialnetwork.dao.interfaces.AbstractRepository;
import com.getjavajob.training.okhanzhin.socialnetwork.dao.interfaces.AccountRepository;
import com.getjavajob.training.okhanzhin.socialnetwork.dao.interfaces.CommunityRepository;
import com.getjavajob.training.okhanzhin.socialnetwork.domain.Account;
import com.getjavajob.training.okhanzhin.socialnetwork.domain.Community;
import com.getjavajob.training.okhanzhin.socialnetwork.domain.dto.SearchEntry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SearchService {
    private static final Logger logger = LoggerFactory.getLogger(SearchService.class);

    private static final int RECORDS_PER_PAGE = 8;
    private static final String ACCOUNT_SEARCH_ENTRY_TYPE = "account";
    private static final String COMMUNITY_SEARCH_ENTRY_TYPE = "community";

    private final AccountRepository accountRepository;
    private final CommunityRepository communityRepository;

    @Autowired
    public SearchService(AccountRepository accountRepository, CommunityRepository communityRepository) {
        this.accountRepository = accountRepository;
        this.communityRepository = communityRepository;
    }

    public List<Account> searchForAccounts(String value, int currentPage) {
        return accountRepository.searchEntitiesPagination(value, currentPage);
    }

    public List<Community> searchForCommunities(String value, int currentPage) {
        return communityRepository.searchEntitiesPagination(value, currentPage);
    }

    public long getNumberOfPagesForAccountEntries(String value) {
        return getNumberOfPages(accountRepository, value);
    }

    public long getNumberOfPagesForCommEntries(String value) {
        return getNumberOfPages(communityRepository, value);
    }

    private long getNumberOfPages(AbstractRepository<?, Long> repository, String value) {
        long rows = repository.getCountOfSearchResults(value);
        long numOfPages = 0;

        if (rows > RECORDS_PER_PAGE) {
            numOfPages = rows / RECORDS_PER_PAGE;

            if (numOfPages % RECORDS_PER_PAGE > 0) {
                numOfPages++;
            }
        }

        return numOfPages;
    }

    public List<SearchEntry> getSearchEntries(String value) {
        List<Account> accountList = accountRepository.searchAllEntities(value);
        List<Community> communityList = communityRepository.searchAllEntities(value);
        List<SearchEntry> searchEntries = new ArrayList<>();

        for (Account account : accountList) {
            searchEntries.add(new SearchEntry(account.getSurname() + " " + account.getName(),
                    "/" + ACCOUNT_SEARCH_ENTRY_TYPE + "/" + account.getAccountID()));
        }
        for (Community community : communityList) {
            searchEntries.add(new SearchEntry(community.getCommunityName(),
                    "/" + COMMUNITY_SEARCH_ENTRY_TYPE + "/" + community.getCommID()));
        }

        return searchEntries;
    }
}

