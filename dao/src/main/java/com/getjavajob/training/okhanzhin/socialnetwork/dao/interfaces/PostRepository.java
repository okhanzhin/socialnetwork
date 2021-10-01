package com.getjavajob.training.okhanzhin.socialnetwork.dao.interfaces;

import com.getjavajob.training.okhanzhin.socialnetwork.domain.post.Post;

import java.util.List;

public interface PostRepository extends AbstractRepository<Post, Long> {

    List<Post> getAccountPostsById(Long id);

    List<Post> getCommunityPostsById(Long id);
}
