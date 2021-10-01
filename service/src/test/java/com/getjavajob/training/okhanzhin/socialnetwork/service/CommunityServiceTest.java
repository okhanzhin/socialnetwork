package com.getjavajob.training.okhanzhin.socialnetwork.service;

import com.getjavajob.training.okhanzhin.socialnetwork.dao.interfaces.AccountRepository;
import com.getjavajob.training.okhanzhin.socialnetwork.dao.interfaces.CommunityRepository;
import com.getjavajob.training.okhanzhin.socialnetwork.dao.interfaces.MemberRepository;
import com.getjavajob.training.okhanzhin.socialnetwork.dao.interfaces.PictureRepository;
import com.getjavajob.training.okhanzhin.socialnetwork.dao.interfaces.RequestRepository;
import com.getjavajob.training.okhanzhin.socialnetwork.domain.Account;
import com.getjavajob.training.okhanzhin.socialnetwork.domain.Community;
import com.getjavajob.training.okhanzhin.socialnetwork.domain.Member;
import com.getjavajob.training.okhanzhin.socialnetwork.domain.dto.CommunityTransfer;
import com.getjavajob.training.okhanzhin.socialnetwork.domain.picture.CommunityPicture;
import com.getjavajob.training.okhanzhin.socialnetwork.domain.request.CommunityRequest;
import com.getjavajob.training.okhanzhin.socialnetwork.domain.request.Request;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CommunityServiceTest {
    private static final String ROLE_USER = "USER";

    @Mock
    private CommunityRepository communityRepository;
    @Mock
    private AccountRepository accountRepository;
    @Mock
    private MemberRepository memberRepository;
    @Mock
    private RequestRepository requestRepository;
    @Mock
    private PictureRepository pictureRepository;

    @InjectMocks
    private CommunityService communityService;

    private CommunityTransfer transfer;
    private Community community;
    private Account account;
    private Account account2;
    private Account account3;
    private Member owner;
    private Member user;
    private Member moderator;

    @Before
    public void init() {
        transfer = new CommunityTransfer(1L, "First Community", LocalDate.now());
        community = new Community(1L, "First Community", LocalDate.now());
        account = new Account(1L, "One", "One",
                "onee221@gmail.com", "onepass", LocalDate.now(), ROLE_USER);
        account2 = new Account(2L, "Two", "Two",
                "onee222@gmail.com", "onepass", LocalDate.now(), ROLE_USER);
        account3 = new Account(3L, "Three", "Three",
                "onee223@gmail.com", "onepass", LocalDate.now(), ROLE_USER);
        owner = new Member(account, community, Member.Status.OWNER);
        user = new Member(account, community, Member.Status.USER);
        moderator = new Member(account, community, Member.Status.MODERATOR);
    }

    @Test
    public void createCommunity() {
        transfer.setPicture(new byte[0]);
        CommunityPicture communityPicture = new CommunityPicture(transfer.getPicture());
        communityPicture.setCommunity(community);

        Community actual = communityService.create(transfer);

        verify(pictureRepository, times(1)).create(communityPicture);
        assertEquals(community, actual);
    }

    @Test
    public void updateCommunity() {
        transfer.setPicture(new byte[]{1, 2, 3, 4, 5});
        transfer.setPicAttached(true);

        Community storedCommunity = new Community(1L, "Stored Community", LocalDate.now());
        CommunityPicture storedPicture = new CommunityPicture(new byte[0]);
        storedPicture.setPicID(1L);
        storedPicture.setCommunity(storedCommunity);
        Community updatedCommunity = community;

        when(communityRepository.getById(1L)).thenReturn(storedCommunity);
        when(pictureRepository.getCommunityPicture(storedCommunity)).thenReturn(storedPicture);
        when(communityRepository.update(updatedCommunity)).thenReturn(community);

        assertEquals(community, communityService.update(transfer));
        verify(communityRepository, times(1)).update(updatedCommunity);
        verify(pictureRepository, times(1)).getCommunityPicture(storedCommunity);
        verify(pictureRepository, times(1)).update(storedPicture);
    }

    @Test
    public void delete() {
        communityService.delete(community);
        verify(communityRepository).deleteById(community.getCommID());
    }

    @Test
    public void getById() {
        when(communityRepository.getById(community.getCommID())).thenReturn(community);

        assertEquals(community, communityService.getById(community.getCommID()));
        verify(communityRepository).getById(community.getCommID());
    }

    @Test
    public void getAccountCommunities() {
        List<Community> communities = new ArrayList<>();
        communities.add(new Community("First Community"));
        communities.add(new Community("Second Community"));
        Account account = new Account(1L, "One", "One",
                "onee221@gmail.com", "onepass", LocalDate.now(), ROLE_USER);

        when(communityRepository.getAccountCommunities(account)).thenReturn(communities);

        assertEquals(communities, communityService.getAccountCommunities(account));
        verify(communityRepository).getAccountCommunities(account);
    }

    @Test
    public void createOwner() {
        when(memberRepository.create(owner)).thenReturn(owner);

        communityService.createOwner(account, community);
        verify(memberRepository).create(owner);
    }

    @Test
    public void getOwner() {
        when(memberRepository.getOwnerByCommId(community.getCommID())).thenReturn(owner);

        assertEquals(owner, communityService.getOwner(community));
        verify(memberRepository).getOwnerByCommId(community.getCommID());
    }

    @Test
    public void setOwner() {
        when(memberRepository.getOwnerByCommId(community.getCommID())).thenReturn(owner);
        when(accountRepository.getById(account2.getAccountID())).thenReturn(account2);
        when(memberRepository.update(owner)).thenReturn(owner);

        communityService.setOwner(account2.getAccountID(), community.getCommID());
        verify(memberRepository).getOwnerByCommId(community.getCommID());
        verify(accountRepository).getById(account2.getAccountID());
        verify(memberRepository).update(owner);
    }

    @Test
    public void setModerator() {
        when(memberRepository.getMember(account, community)).thenReturn(user);
        when(accountRepository.getById(account.getAccountID())).thenReturn(account);
        when(communityRepository.getById(community.getCommID())).thenReturn(community);
        when(memberRepository.getCommunityModerators(community.getCommID())).thenReturn(new ArrayList<>());

        communityService.setModerator(account.getAccountID(), community.getCommID());
        verify(memberRepository).getCommunityModerators(community.getCommID());
        verify(memberRepository).update(user);
    }

    @Test
    public void setUser() {
        when(memberRepository.getMember(account, community)).thenReturn(moderator);
        when(accountRepository.getById(account.getAccountID())).thenReturn(account);
        when(communityRepository.getById(community.getCommID())).thenReturn(community);

        communityService.setUser(account.getAccountID(), community.getCommID());
        verify(memberRepository).update(moderator);
    }

    @Test
    public void createMember() {
        when(accountRepository.getById(account.getAccountID())).thenReturn(account);
        when(communityRepository.getById(community.getCommID())).thenReturn(community);
        when(memberRepository.getCommunityMembers(community.getCommID())).thenReturn(new ArrayList<>());
        when(memberRepository.create(user)).thenReturn(user);

        assertEquals(user, communityService.createMember(account.getAccountID(), community.getCommID()));
        verify(memberRepository).create(user);
    }

    @Test
    public void getMember() {
        when(accountRepository.getById(account.getAccountID())).thenReturn(account);
        when(communityRepository.getById(community.getCommID())).thenReturn(community);
        when(memberRepository.getMember(account, community)).thenReturn(user);

        assertEquals(user, communityService.getMember(account.getAccountID(), community.getCommID()));
        verify(memberRepository).getMember(account, community);
    }

    @Test
    public void deleteMemberFromCommunity() {
        when(memberRepository.getMember(account, community)).thenReturn(user);
        when(accountRepository.getById(account.getAccountID())).thenReturn(account);
        when(communityRepository.getById(community.getCommID())).thenReturn(community);
        when(memberRepository.getCommunityMembers(community.getCommID())).thenReturn(Collections.singletonList(user));

        communityService.deleteMemberFromCommunity(account.getAccountID(), community.getCommID());
        verify(memberRepository).deleteMember(account, community);
    }

    @Test
    public void getListOfMembers() {
        Member member1 = new Member(account, community, Member.Status.USER);
        Member member2 = new Member(account2, community, Member.Status.OWNER);
        Member member3 = new Member(account3, community, Member.Status.MODERATOR);
        List<Member> members = Arrays.asList(member1, member2, member3);

        when(memberRepository.getCommunityMembers(community.getCommID())).thenReturn(members);

        assertEquals(members, communityService.getListOfMembers(community.getCommID()));
        verify(memberRepository).getCommunityMembers(community.getCommID());
    }

    @Test
    public void getListOfModerators() {
        Member member1 = new Member(account, community, Member.Status.MODERATOR);
        Member member2 = new Member(account2, community, Member.Status.MODERATOR);
        Member member3 = new Member(account3, community, Member.Status.MODERATOR);
        List<Member> members = Arrays.asList(member1, member2, member3);

        when(memberRepository.getCommunityModerators(community.getCommID())).thenReturn(members);

        assertEquals(members, communityService.getListOfModerators(community.getCommID()));
        verify(memberRepository).getCommunityModerators(community.getCommID());
    }

    @Test
    public void isAbleToModify() {
        Member member1 = new Member(account, community, Member.Status.USER);
        Member member2 = new Member(account2, community, Member.Status.OWNER);
        Member member3 = new Member(account3, community, Member.Status.MODERATOR);
        List<Member> members = Arrays.asList(member1, member2, member3);

        when(memberRepository.getCommunityMembers(community.getCommID())).thenReturn(members);

        assertTrue(communityService.isAbleToModify(2, community.getCommID()));
        assertFalse(communityService.isAbleToModify(1, community.getCommID()));
    }

    @Test
    public void convertMembersToAccounts() {
        List<Member> members = new ArrayList<>();
        members.add(user);

        assertEquals(Collections.singletonList(user.getAccount()), communityService.convertMembersToAccounts(members));
    }

    @Test
    public void getUnconfirmedAccountsForCommunity() {
        List<Account> accounts = Arrays.asList(account, account2, account3);
        List<Request> requests = new ArrayList<>();
        requests.add(new CommunityRequest(account, community, Request.Status.UNCONFIRMED));
        requests.add(new CommunityRequest(account2, community, Request.Status.UNCONFIRMED));
        requests.add(new CommunityRequest(account3, community, Request.Status.UNCONFIRMED));

        when(requestRepository.getCommunityRequests(community)).thenReturn(requests);

        assertEquals(accounts, communityService.getUnconfirmedAccountsForCommunity(community));
        verify(requestRepository).getCommunityRequests(community);
    }

    @Test
    public void configureCommunityPermissions() {
        List<Request> requests = new ArrayList<>();
        requests.add(new CommunityRequest(account2, community, Request.Status.UNCONFIRMED));
        requests.add(new CommunityRequest(account3, community, Request.Status.UNCONFIRMED));

        Map<String, Object> exceptedPermissions = new HashMap<>();
        exceptedPermissions.put("visitorID", account.getAccountID());
        exceptedPermissions.put("visitorStatus", user.getMemberStatus().getStatus());
        exceptedPermissions.put("isAbleToModify",
                communityService.isAbleToModify(account.getAccountID(), community.getCommID()));
        exceptedPermissions.put("isMember", true);
        exceptedPermissions.put("isSubscribed", false);

        when(accountRepository.getById(account.getAccountID())).thenReturn(account);
        when(communityRepository.getById(community.getCommID())).thenReturn(community);
        when(memberRepository.getMember(account, community)).thenReturn(user);
        when(requestRepository.getCommunityRequests(community)).thenReturn(requests);
        when(memberRepository.getCommunityMembers(community.getCommID())).thenReturn(Collections.singletonList(user));

        assertEquals(exceptedPermissions, communityService.configureCommunityPermissions(account, community));
    }
}