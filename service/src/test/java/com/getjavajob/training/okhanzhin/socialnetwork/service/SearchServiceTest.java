package com.getjavajob.training.okhanzhin.socialnetwork.service;

import com.getjavajob.training.okhanzhin.socialnetwork.dao.interfaces.AccountRepository;
import com.getjavajob.training.okhanzhin.socialnetwork.dao.interfaces.CommunityRepository;
import com.getjavajob.training.okhanzhin.socialnetwork.domain.Account;
import com.getjavajob.training.okhanzhin.socialnetwork.domain.Community;
import com.getjavajob.training.okhanzhin.socialnetwork.domain.dto.SearchEntry;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class SearchServiceTest {
    private static final String ROLE_USER = "USER";
    public static final String FILTER = "one";
    private static final String ACCOUNT_SEARCH_ENTRY_TYPE = "account";
    private static final String COMMUNITY_SEARCH_ENTRY_TYPE = "community";

    @Mock
    private AccountRepository accountRepository;
    @Mock
    private CommunityRepository communityRepository;

    @InjectMocks
    private SearchService searchService;

    private Account account;
    private Account account2;
    private Account account3;
    private Community community;
    private Community community2;
    private Community community3;

    @Before
    public void init() {
        account = new Account(1L, "One", "One",
                "onee221@gmail.com", "onepass", LocalDate.now(), ROLE_USER);
        account2 = new Account(2L, "Two", "Two",
                "onee222@gmail.com", "onepass", LocalDate.now(), ROLE_USER);
        account3 = new Account(3L, "Three", "Three",
                "onee223@gmail.com", "onepass", LocalDate.now(), ROLE_USER);
        community = new Community(1L, "First One Community", LocalDate.now());
        community2 = new Community(2L, "Second One Community", LocalDate.now());
        community3 = new Community(3L, "Third One Community", LocalDate.now());
    }

    @Test
    public void searchForAccounts() {
        List<Account> accounts = Arrays.asList(account, account2, account3);

        when(accountRepository.searchEntitiesPagination(FILTER, 1)).thenReturn(accounts);

        assertEquals(accounts, searchService.searchForAccounts(FILTER, 1));
        verify(accountRepository).searchEntitiesPagination(FILTER, 1);
    }

    @Test
    public void searchForCommunities() {
        List<Community> communities = Arrays.asList(community, community2, community3);

        when(communityRepository.searchEntitiesPagination(FILTER, 1)).thenReturn(communities);

        assertEquals(communities, searchService.searchForCommunities(FILTER, 1));
        verify(communityRepository).searchEntitiesPagination(FILTER, 1);
    }

    @Test
    public void getNumberOfPagesForAccountEntries() {
        when(accountRepository.getCountOfSearchResults(FILTER)).thenReturn(8L);

        assertEquals(0, searchService.getNumberOfPagesForAccountEntries(FILTER));
        verify(accountRepository).getCountOfSearchResults(FILTER);
    }

    @Test
    public void getNumberOfPagesForCommEntries() {
        when(communityRepository.getCountOfSearchResults(FILTER)).thenReturn(3L);

        assertEquals(0, searchService.getNumberOfPagesForCommEntries(FILTER));
        verify(communityRepository).getCountOfSearchResults(FILTER);
    }

    @Test
    public void getSearchEntries() {
        List<Account> accounts = Arrays.asList(account, account2, account3);
        List<Community> communities = Arrays.asList(community, community2, community3);
        List<SearchEntry> searchEntries = new ArrayList<>();

        searchEntries.add(new SearchEntry(account.getSurname() + " " + account.getName(),
                "/" + ACCOUNT_SEARCH_ENTRY_TYPE + "/" + account.getAccountID()));
        searchEntries.add(new SearchEntry(account2.getSurname() + " " + account2.getName(),
                "/" + ACCOUNT_SEARCH_ENTRY_TYPE + "/" + account2.getAccountID()));
        searchEntries.add(new SearchEntry(account3.getSurname() + " " + account3.getName(),
                "/" + ACCOUNT_SEARCH_ENTRY_TYPE + "/" + account3.getAccountID()));
        searchEntries.add(new SearchEntry(community.getCommunityName(),
                "/" + COMMUNITY_SEARCH_ENTRY_TYPE + "/" + community.getCommID()));
        searchEntries.add(new SearchEntry(community2.getCommunityName(),
                "/" + COMMUNITY_SEARCH_ENTRY_TYPE + "/" + community2.getCommID()));
        searchEntries.add(new SearchEntry(community3.getCommunityName(),
                "/" + COMMUNITY_SEARCH_ENTRY_TYPE + "/" + community3.getCommID()));

        when(accountRepository.searchAllEntities(FILTER)).thenReturn(accounts);
        when(communityRepository.searchAllEntities(FILTER)).thenReturn(communities);

        assertEquals(searchEntries, searchService.getSearchEntries(FILTER));
        verify(accountRepository).searchAllEntities(FILTER);
        verify(communityRepository).searchAllEntities(FILTER);
    }
}
