package com.getjavajob.training.okhanzhin.socialnetwork.dao.spring;

import com.getjavajob.training.okhanzhin.socialnetwork.dao.QueryConstants;
import com.getjavajob.training.okhanzhin.socialnetwork.dao.interfaces.MemberRepository;
import com.getjavajob.training.okhanzhin.socialnetwork.domain.Account;
import com.getjavajob.training.okhanzhin.socialnetwork.domain.Community;
import com.getjavajob.training.okhanzhin.socialnetwork.domain.Member;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MemberSpringRepository extends AbstractSpringRepository<Member, Long>, MemberRepository {

    default Member getMember(Account account, Community community) {
        Optional<Member> optional = findByAccountAndCommunity(account, community);

        return optional.orElse(null);
    }

    Optional<Member> findByAccountAndCommunity(Account account, Community community);

    @Query(QueryConstants.DELETE_MEMBER)
    @Modifying
    void deleteMember(@Param("account") Account account, @Param("community") Community community);

    @Query(QueryConstants.GET_OWNER_BY_COMMUNITY_ID)
    Member getOwnerByCommId(@Param("commID") Long id);

    @Query(QueryConstants.GET_MEMBERS_BY_COMMUNITY_ID)
    List<Member> getCommunityMembers(@Param("commID") Long id);

    @Query(QueryConstants.GET_COMMUNITY_MODERATORS)
    List<Member> getCommunityModerators(@Param("commID") Long id);
}
