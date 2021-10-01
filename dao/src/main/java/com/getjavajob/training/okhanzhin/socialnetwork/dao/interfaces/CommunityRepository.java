package com.getjavajob.training.okhanzhin.socialnetwork.dao.interfaces;

import com.getjavajob.training.okhanzhin.socialnetwork.domain.Account;
import com.getjavajob.training.okhanzhin.socialnetwork.domain.Community;

import java.util.List;

public interface CommunityRepository extends AbstractRepository<Community, Long> {

    List<Community> getAccountCommunities(Account account);
}
