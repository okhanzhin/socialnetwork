package com.getjavajob.training.okhanzhin.socialnetwork.dao.interfaces;

import com.getjavajob.training.okhanzhin.socialnetwork.domain.Account;
import com.getjavajob.training.okhanzhin.socialnetwork.domain.Community;
import com.getjavajob.training.okhanzhin.socialnetwork.domain.Member;

import java.util.List;

public interface MemberRepository extends AbstractRepository<Member, Long> {

    Member getMember(Account account, Community community);

    void deleteMember(Account account, Community community);

    Member getOwnerByCommId(Long id);

    List<Member> getCommunityMembers(Long id);

    List<Member> getCommunityModerators(Long id);
}
