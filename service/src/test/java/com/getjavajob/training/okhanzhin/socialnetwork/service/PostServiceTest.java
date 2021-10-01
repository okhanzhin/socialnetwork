package com.getjavajob.training.okhanzhin.socialnetwork.service;

import com.getjavajob.training.okhanzhin.socialnetwork.dao.interfaces.PostRepository;
import com.getjavajob.training.okhanzhin.socialnetwork.domain.Account;
import com.getjavajob.training.okhanzhin.socialnetwork.domain.Community;
import com.getjavajob.training.okhanzhin.socialnetwork.domain.dto.PostView;
import com.getjavajob.training.okhanzhin.socialnetwork.domain.post.AccountPost;
import com.getjavajob.training.okhanzhin.socialnetwork.domain.post.CommunityPost;
import com.getjavajob.training.okhanzhin.socialnetwork.domain.post.Post;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class PostServiceTest {
    private static final String ROLE_USER = "USER";
    public static final String SOME_TEXT = "SomeText";

    @Mock
    private PostRepository postRepository;

    @InjectMocks
    private PostService postService;

    private Account account;
    private Account account2;
    private Community community;
    private AccountPost accountPost;
    private AccountPost accountPost2;
    private CommunityPost communityPost;
    private CommunityPost communityPost2;

    @Before
    public void init() {
        account = new Account(1L, "One", "One",
                "onee221@gmail.com", "onepass", LocalDate.now(), ROLE_USER);
        account2 = new Account(2L, "Two", "Two",
                "onee222@gmail.com", "onepass", LocalDate.now(), ROLE_USER);
        community = new Community(1L, "First Community", LocalDate.now());
        accountPost = new AccountPost(1L, account, account2, SOME_TEXT, LocalDateTime.now());
        accountPost2 = new AccountPost(2L, account, account2, SOME_TEXT, LocalDateTime.now());
        communityPost = new CommunityPost(1L, account, community, SOME_TEXT, LocalDateTime.now());
        communityPost2 = new CommunityPost(2L, account2, community, SOME_TEXT, LocalDateTime.now());
    }

    @Test
    public void publishPost() {
        Post post = accountPost;
        when(postRepository.create(post)).thenReturn(post);

        postService.publishPost(post);
        verify(postRepository).create(post);
    }

    @Test
    public void getAccountPosts() {
        PostView postView = new PostView(account, accountPost);
        PostView postView2 = new PostView(account, accountPost2);
        List<Post> posts = Arrays.asList(accountPost, accountPost2);
        List<PostView> postViews = Arrays.asList(postView, postView2);

        when(postRepository.getAccountPostsById(account2.getAccountID())).thenReturn(posts);

        List<PostView> actual = postService.getAccountPosts(account2);
        verify(postRepository).getAccountPostsById(account2.getAccountID());
        assertEquals(postViews, actual);
    }

    @Test
    public void getCommunityPosts() {
        PostView postView = new PostView(account, communityPost);
        PostView postView2 = new PostView(account, communityPost2);
        List<Post> posts = Arrays.asList(communityPost, communityPost2);
        List<PostView> postViews = Arrays.asList(postView, postView2);

        when(postRepository.getCommunityPostsById(community.getCommID())).thenReturn(posts);

        List<PostView> actual = postService.getCommunityPosts(community);
        verify(postRepository).getCommunityPostsById(community.getCommID());
        assertEquals(postViews, actual);
    }
}
