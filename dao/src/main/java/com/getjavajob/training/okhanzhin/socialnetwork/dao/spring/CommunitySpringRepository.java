package com.getjavajob.training.okhanzhin.socialnetwork.dao.spring;

import com.getjavajob.training.okhanzhin.socialnetwork.dao.QueryConstants;
import com.getjavajob.training.okhanzhin.socialnetwork.dao.interfaces.CommunityRepository;
import com.getjavajob.training.okhanzhin.socialnetwork.domain.Account;
import com.getjavajob.training.okhanzhin.socialnetwork.domain.Community;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommunitySpringRepository extends AbstractSpringRepository<Community, Long>, CommunityRepository {
    Logger logger = LoggerFactory.getLogger(CommunitySpringRepository.class);

    int RECORD_PER_PAGE = 8;

    @Override
    default long getCountOfSearchResults(String filter) {
        long count = countByCommunityNameContaining(filter);
        logger.warn("Count of Search Results: {}", count);

        return count;
    }

    long countByCommunityNameContaining(String filter);

    @Override
    default List<Community> searchEntitiesPagination(String filter, int currentPage) {
        Pageable page = PageRequest.of(currentPage - 1, RECORD_PER_PAGE);
        Page<Community> pageWithResults = findByCommunityNameContainingIgnoreCase(filter, page);

        return pageWithResults.getContent();
    }

    Page<Community> findByCommunityNameContainingIgnoreCase(String commNameFilter, Pageable pageable);

    @Override
    default List<Community> searchAllEntities(String filter) {
        Pageable page = PageRequest.of(0, Integer.MAX_VALUE);
        Page<Community> pageWithResults = findAllByCommunityNameContaining(filter, page);

        return pageWithResults.getContent();
    }

    Page<Community> findAllByCommunityNameContaining(String commNameFilter, Pageable page);

    @Query(QueryConstants.GET_COMMUNITY_LIST_BY_ACCOUNT_ID)
    List<Community> getAccountCommunities(@Param("account") Account account);
}
