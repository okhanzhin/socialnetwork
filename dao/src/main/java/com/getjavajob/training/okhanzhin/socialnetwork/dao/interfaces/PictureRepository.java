package com.getjavajob.training.okhanzhin.socialnetwork.dao.interfaces;

import com.getjavajob.training.okhanzhin.socialnetwork.domain.Account;
import com.getjavajob.training.okhanzhin.socialnetwork.domain.Community;
import com.getjavajob.training.okhanzhin.socialnetwork.domain.picture.Picture;

public interface PictureRepository extends AbstractRepository<Picture, Long> {

    Picture getAccountPicture(Account account);

    Picture getCommunityPicture(Community community);
}
