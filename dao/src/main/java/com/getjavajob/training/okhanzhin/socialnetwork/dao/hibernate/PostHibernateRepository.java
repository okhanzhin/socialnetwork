package com.getjavajob.training.okhanzhin.socialnetwork.dao.hibernate;

import com.getjavajob.training.okhanzhin.socialnetwork.dao.interfaces.PostRepository;
import com.getjavajob.training.okhanzhin.socialnetwork.domain.post.Post;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;

@Repository
public class PostHibernateRepository extends AbstractHibernateRepository<Post, Long> implements PostRepository {
    private static final String GET_ACCOUNT_POSTS =
            "SELECT p FROM AccountPost p JOIN FETCH p.source s " +
                    "WHERE p.accountTarget.accountID = :targetID ORDER BY p.publicationDate DESC";
    private static final String GET_COMMUNITY_POSTS =
            "SELECT p FROM CommunityPost p JOIN FETCH p.source s " +
                    "WHERE p.communityTarget.commID = :targetID ORDER BY p.publicationDate DESC";

    public PostHibernateRepository() {
        setEntityClass(Post.class);
    }

    @Override
    protected List<String> makePaths() {
        return null;
    }

    public List<Post> getAccountPostsById(Long id) {
        TypedQuery<Post> query = entityManager.createQuery(GET_ACCOUNT_POSTS, Post.class).
                setParameter("targetID", id);

        return retrieveList(query);
    }

    public List<Post> getCommunityPostsById(Long id) {
        TypedQuery<Post> query = entityManager.createQuery(GET_COMMUNITY_POSTS, Post.class).
                setParameter("targetID", id);

        return retrieveList(query);
    }

    private List<Post> retrieveList(TypedQuery<Post> query) {
        List<Post> postList = new ArrayList<>();

        for (Object result : query.getResultList()) {
            postList.add((Post) result);
        }

        return postList;
    }
}
