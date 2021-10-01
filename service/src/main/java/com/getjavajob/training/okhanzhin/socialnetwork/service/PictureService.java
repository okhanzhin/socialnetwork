package com.getjavajob.training.okhanzhin.socialnetwork.service;

import com.getjavajob.training.okhanzhin.socialnetwork.dao.interfaces.AccountRepository;
import com.getjavajob.training.okhanzhin.socialnetwork.dao.interfaces.CommunityRepository;
import com.getjavajob.training.okhanzhin.socialnetwork.dao.interfaces.PictureRepository;
import com.getjavajob.training.okhanzhin.socialnetwork.domain.Account;
import com.getjavajob.training.okhanzhin.socialnetwork.domain.Community;
import com.getjavajob.training.okhanzhin.socialnetwork.domain.picture.Picture;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PictureService {
    private final AccountRepository accountRepository;
    private final CommunityRepository communityRepository;
    private final PictureRepository pictureRepository;

    @Autowired
    public PictureService(AccountRepository accountRepository,
                          CommunityRepository communityRepository,
                          PictureRepository pictureRepository) {
        this.accountRepository = accountRepository;
        this.communityRepository = communityRepository;
        this.pictureRepository = pictureRepository;
    }

    public Picture getAccountPicture(long accountID) {
        Account account = accountRepository.getById(accountID);
        return pictureRepository.getAccountPicture(account);
    }

    public Picture getCommunityPicture(long commID) {
        Community community = communityRepository.getById(commID);
        return pictureRepository.getCommunityPicture(community);
    }

    @Transactional
    public void removeAccountPicture(long accountID) {
        Account account = accountRepository.getById(accountID);
        account.setPicAttached(false);
        accountRepository.update(account);

        Picture picture = getAccountPicture(accountID);
        picture.setContent(new byte[0]);
        pictureRepository.update(picture);
    }

    @Transactional
    public void removeCommunityPicture(long commID) {
        Community community = communityRepository.getById(commID);
        community.setPicAttached(false);
        communityRepository.update(community);

        Picture picture = getCommunityPicture(commID);
        picture.setContent(new byte[0]);
        pictureRepository.update(picture);
    }
}
