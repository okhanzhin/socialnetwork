package com.getjavajob.training.okhanzhin.socialnetwork.dao;

import com.getjavajob.training.okhanzhin.socialnetwork.dao.interfaces.AccountRepository;
import com.getjavajob.training.okhanzhin.socialnetwork.dao.interfaces.CommunityRepository;
import com.getjavajob.training.okhanzhin.socialnetwork.dao.interfaces.PostRepository;
import com.getjavajob.training.okhanzhin.socialnetwork.domain.Account;
import com.getjavajob.training.okhanzhin.socialnetwork.domain.post.AccountPost;
import com.getjavajob.training.okhanzhin.socialnetwork.domain.post.CommunityPost;
import com.getjavajob.training.okhanzhin.socialnetwork.domain.post.Post;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@DataJpaTest
public class PostRepositoryTest extends AbstractRepositoryTest {

    @Autowired
    private PostRepository postRepository;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private CommunityRepository communityRepository;

    @Transactional
    @Test
    public void create() {
        Account accountOne = accountRepository.getById(1L);
        Account accountTwo = accountRepository.getById(2L);

        Post expectedPost = new AccountPost(accountOne, accountTwo, "PostText", LocalDateTime.now());

        int rowsCount = 4;
        assertEquals(rowsCount, postRepository.getAll().size());
        rowsCount++;

        Post actualPost = postRepository.create(new AccountPost(
                accountOne, accountTwo, "PostText", LocalDateTime.now()));

        expectedPost.setPostID(actualPost.getPostID());

        assertEquals(rowsCount, postRepository.getAll().size());
        assertEquals(expectedPost, actualPost);
    }

    @Transactional
    @Test
    public void getAccountPostsById() {
        List<Post> expectedList = new ArrayList<>();
        expectedList.add(new AccountPost(accountRepository.getById(1L), accountRepository.getById(2L),
                "Text1", LocalDateTime.of(2021, 3, 26, 17, 33)));

        List<Post> actualList = postRepository.getAccountPostsById(2L);

        assertEquals(expectedList, actualList);
    }

    @Transactional
    @Test
    public void getCommunityPostsById() {
        List<Post> expectedList = new ArrayList<>();

        expectedList.add(new CommunityPost(accountRepository.getById(1L), communityRepository.getById(2L),
                "Text3", LocalDateTime.of(2021, 3, 26, 17, 33)));

        List<Post> actualList = postRepository.getCommunityPostsById(2L);

        assertEquals(expectedList, actualList);
    }
}
