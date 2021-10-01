package com.getjavajob.training.okhanzhin.socialnetwork.service;

import com.getjavajob.training.okhanzhin.socialnetwork.dao.interfaces.PostRepository;
import com.getjavajob.training.okhanzhin.socialnetwork.domain.Account;
import com.getjavajob.training.okhanzhin.socialnetwork.domain.Community;
import com.getjavajob.training.okhanzhin.socialnetwork.domain.dto.PostView;
import com.getjavajob.training.okhanzhin.socialnetwork.domain.post.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class PostService {
    private static final String ACCOUNT_POST_TYPE = "account";
    private static final String COMMUNITY_POST_TYPE = "community";

    private final PostRepository postRepository;

    @Autowired
    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Transactional
    public Post publishPost(Post post) {
        post.setPublicationDate(LocalDateTime.now());
        return postRepository.create(post);
    }

    public List<PostView> getAccountPosts(Account account) {
        return getPostViews(account.getAccountID(), ACCOUNT_POST_TYPE);
    }

    public List<PostView> getCommunityPosts(Community community) {
        return getPostViews(community.getCommID(), COMMUNITY_POST_TYPE);
    }

    private List<PostView> getPostViews(long targetID, String postType) {
        List<PostView> postViews = new ArrayList<>();
        List<Post> postObjects;

        if (postType.equals(ACCOUNT_POST_TYPE)) {
            postObjects = postRepository.getAccountPostsById(targetID);
        } else {
            postObjects = postRepository.getCommunityPostsById(targetID);
        }

        for (Post postObject : postObjects) {
            PostView postView = new PostView(postObject.getSource(), postObject);
            postViews.add(postView);
        }

        return postViews;
    }
}
