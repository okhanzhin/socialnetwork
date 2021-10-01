package com.getjavajob.training.okhanzhin.socialnetwork.dao.hibernate;

import com.getjavajob.training.okhanzhin.socialnetwork.dao.interfaces.PictureRepository;
import com.getjavajob.training.okhanzhin.socialnetwork.domain.Account;
import com.getjavajob.training.okhanzhin.socialnetwork.domain.Community;
import com.getjavajob.training.okhanzhin.socialnetwork.domain.picture.Picture;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class PictureHibernateRepository extends AbstractHibernateRepository<Picture, Long> implements PictureRepository {
    private static final String GET_ACCOUNT_PICTURE = "SELECT p FROM AccountPicture p JOIN FETCH p.account a " +
            "WHERE p.account = :account";
    private static final String GET_COMMUNITY_PICTURE = "SELECT p FROM CommunityPicture p JOIN FETCH p.community c " +
            "WHERE p.community = :community";

    public PictureHibernateRepository() {
        setEntityClass(Picture.class);
    }

    @Override
    protected List<String> makePaths() {
        return null;
    }

    public Picture getAccountPicture(Account account) {
        TypedQuery<Picture> query = entityManager.createQuery(GET_ACCOUNT_PICTURE, Picture.class).
                setParameter("account", account);

        return query.getSingleResult();
    }

    public Picture getCommunityPicture(Community community) {
        TypedQuery<Picture> query = entityManager.createQuery(GET_COMMUNITY_PICTURE, Picture.class).
                setParameter("community", community);

        return query.getSingleResult();
    }
}
