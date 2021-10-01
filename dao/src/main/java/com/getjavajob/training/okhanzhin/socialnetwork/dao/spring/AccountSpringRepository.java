package com.getjavajob.training.okhanzhin.socialnetwork.dao.spring;

import com.getjavajob.training.okhanzhin.socialnetwork.dao.QueryConstants;
import com.getjavajob.training.okhanzhin.socialnetwork.dao.interfaces.AccountRepository;
import com.getjavajob.training.okhanzhin.socialnetwork.domain.Account;
import com.getjavajob.training.okhanzhin.socialnetwork.domain.Community;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface AccountSpringRepository extends AbstractSpringRepository<Account, Long>, AccountRepository {
    Logger logger = LoggerFactory.getLogger(AccountSpringRepository.class);

    int RECORD_PER_PAGE = 8;

    @Override
    default long getCountOfSearchResults(String filter) {
        long count = countByNameContainingOrSurnameContaining(filter, filter);
        logger.info("Count of Search Results: {}", count);

        return count;
    }

    long countByNameContainingOrSurnameContaining(String surnameFilter, String nameFilter);

    @Override
    default List<Account> searchEntitiesPagination(String filter, int currentPage) {
        Pageable page = PageRequest.of(currentPage - 1, RECORD_PER_PAGE);
        Page<Account> pageWithResults = findBySurnameContainingIgnoreCaseOrNameContainingIgnoreCase(filter, filter, page);

        return pageWithResults.getContent();
    }

    Page<Account> findBySurnameContainingIgnoreCaseOrNameContainingIgnoreCase(String surnameFilter,
                                                                              String nameFilter,
                                                                              Pageable pageable);

    @Override
    default List<Account> searchAllEntities(String filter) {
        Pageable page = PageRequest.of(0, Integer.MAX_VALUE);
        Page<Account> pageWithResults = findAllBySurnameContainingIgnoreCaseOrNameContainingIgnoreCase(filter, filter, page);

        return pageWithResults.getContent();
    }

    Page<Account> findAllBySurnameContainingIgnoreCaseOrNameContainingIgnoreCase(String surnameFilter,
                                                                                 String nameFilter,
                                                                                 Pageable page);

    @Query(QueryConstants.GET_ACCOUNT_BY_ID)
    @EntityGraph(value = "graph.Account.phones")
    Account getByIdFetchPhones(@Param("id") Long id);

    @Query(QueryConstants.GET_ACCOUNT_BY_EMAIL)
    Account getByEmail(@Param("email") String email);

    @Query(QueryConstants.GET_ACCOUNT_BY_EMAIL)
    @EntityGraph(value = "graph.Account.phones")
    Account getByEmailFetchPhones(@Param("email") String email);

    @Query(QueryConstants.GET_ACCOUNTS_LIST_BY_COMMUNITY_ID)
    List<Account> getAccountsForCommunity(@Param("community") Community community);

    @Override
    default List<Account> getTodayBirthdayAccounts() {
        return findByDateOfBirth(LocalDate.now());
    }

    List<Account> findByDateOfBirth(LocalDate dateOfBirth);

    @Query(QueryConstants.IS_ACCOUNT_AVAILABLE)
    Boolean isAccountAvailable(@Param("email") String email);

    @Query(QueryConstants.GET_ALL_EMAILS)
    List<String> getExistingEmails();

    @Query(QueryConstants.UPDATE_PASSWORD_BY_ACCOUNT_ID)
    @Modifying
    void updatePassword(@Param("password") String password, @Param("accountID") Long accountID);
}
