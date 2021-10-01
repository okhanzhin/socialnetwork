package com.getjavajob.training.okhanzhin.socialnetwork.dao.spring;

import com.getjavajob.training.okhanzhin.socialnetwork.dao.QueryConstants;
import com.getjavajob.training.okhanzhin.socialnetwork.dao.interfaces.PictureRepository;
import com.getjavajob.training.okhanzhin.socialnetwork.domain.Account;
import com.getjavajob.training.okhanzhin.socialnetwork.domain.Community;
import com.getjavajob.training.okhanzhin.socialnetwork.domain.picture.Picture;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PictureSpringRepository extends AbstractSpringRepository<Picture, Long>, PictureRepository {

    @Query(QueryConstants.GET_ACCOUNT_PICTURE)
    Picture getAccountPicture(@Param("account") Account account);

    @Query(QueryConstants.GET_COMMUNITY_PICTURE)
    Picture getCommunityPicture(@Param("community") Community community);
}
