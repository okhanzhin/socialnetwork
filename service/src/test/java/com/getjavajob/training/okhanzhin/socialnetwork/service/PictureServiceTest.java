package com.getjavajob.training.okhanzhin.socialnetwork.service;

import com.getjavajob.training.okhanzhin.socialnetwork.dao.interfaces.AccountRepository;
import com.getjavajob.training.okhanzhin.socialnetwork.dao.interfaces.CommunityRepository;
import com.getjavajob.training.okhanzhin.socialnetwork.dao.interfaces.PictureRepository;
import com.getjavajob.training.okhanzhin.socialnetwork.domain.Account;
import com.getjavajob.training.okhanzhin.socialnetwork.domain.Community;
import com.getjavajob.training.okhanzhin.socialnetwork.domain.picture.AccountPicture;
import com.getjavajob.training.okhanzhin.socialnetwork.domain.picture.CommunityPicture;
import com.getjavajob.training.okhanzhin.socialnetwork.domain.picture.Picture;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDate;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class PictureServiceTest {
    private static final String ROLE_USER = "USER";

    @Mock
    private AccountRepository accountRepository;
    @Mock
    private CommunityRepository communityRepository;
    @Mock
    private PictureRepository pictureRepository;

    @InjectMocks
    private PictureService pictureService;

    private Account account;
    private Community community;
    private AccountPicture accountPicture;
    private CommunityPicture communityPicture;

    @Before
    public void init() {
        account = new Account(1L, "One", "One",
                "onee221@gmail.com", "onepass", LocalDate.now(), ROLE_USER);
        community = new Community(1L, "First Community", LocalDate.now());
        accountPicture = new AccountPicture(1L, account, new byte[0]);
        communityPicture = new CommunityPicture(1L, community, new byte[0]);
    }

    @Test
    public void getAccountPicture() {
        Picture picture = accountPicture;

        when(accountRepository.getById(account.getAccountID())).thenReturn(account);
        when(pictureRepository.getAccountPicture(account)).thenReturn(picture);

        assertEquals(picture, pictureService.getAccountPicture(account.getAccountID()));
        verify(accountRepository).getById(account.getAccountID());
        verify(pictureRepository).getAccountPicture(account);
    }

    @Test
    public void getCommunityPicture() {
        Picture picture = communityPicture;

        when(communityRepository.getById(community.getCommID())).thenReturn(community);
        when(pictureRepository.getCommunityPicture(community)).thenReturn(picture);

        assertEquals(picture, pictureService.getCommunityPicture(community.getCommID()));
        verify(communityRepository).getById(community.getCommID());
        verify(pictureRepository).getCommunityPicture(community);
    }

    @Test
    public void removeAccountPicture() {
        Picture picture = accountPicture;

        when(accountRepository.getById(account.getAccountID())).thenReturn(account);
        when(pictureRepository.getAccountPicture(account)).thenReturn(picture);

        pictureService.removeAccountPicture(account.getAccountID());
        verify(accountRepository, times(2)).getById(account.getAccountID());
        verify(accountRepository).update(account);
        verify(pictureRepository).update(picture);
    }

    @Test
    public void removeCommunityPicture() {
        Picture picture = communityPicture;

        when(communityRepository.getById(community.getCommID())).thenReturn(community);
        when(pictureRepository.getCommunityPicture(community)).thenReturn(picture);

        pictureService.removeCommunityPicture(community.getCommID());
        verify(communityRepository, times(2)).getById(community.getCommID());
        verify(communityRepository).update(community);
        verify(pictureRepository).update(picture);
    }
}
