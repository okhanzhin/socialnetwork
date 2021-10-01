package com.getjavajob.training.okhanzhin.socialnetwork.dao.spring;

import com.getjavajob.training.okhanzhin.socialnetwork.dao.QueryConstants;
import com.getjavajob.training.okhanzhin.socialnetwork.dao.interfaces.PostRepository;
import com.getjavajob.training.okhanzhin.socialnetwork.domain.post.Post;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostSpringRepository extends AbstractSpringRepository<Post, Long>, PostRepository {

    @Query(QueryConstants.GET_ACCOUNT_POSTS)
    List<Post> getAccountPostsById(@Param("targetID") Long id);

    @Query(QueryConstants.GET_COMMUNITY_POSTS)
    List<Post> getCommunityPostsById(@Param("targetID") Long id);
}
