package com.getjavajob.training.okhanzhin.socialnetwork.service;

import com.getjavajob.training.okhanzhin.socialnetwork.dao.AccountDao;
import com.getjavajob.training.okhanzhin.socialnetwork.dao.GroupDao;
import com.getjavajob.training.okhanzhin.socialnetwork.dao.MemberDao;
import com.getjavajob.training.okhanzhin.socialnetwork.domain.Account;
import com.getjavajob.training.okhanzhin.socialnetwork.domain.Group;
import com.getjavajob.training.okhanzhin.socialnetwork.domain.Member;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class GroupServiceTest {
    @Mock
    private GroupDao groupDao;
    @Mock
    private MemberDao memberDao;
    @Mock
    private AccountDao accountDao;

    @InjectMocks
    private GroupService groupService;

    @Test
    public void createGroup() {
        Group group = new Group("First Group");
        when(groupDao.create(group)).thenReturn(group);
        Group actualGroup = groupService.createGroup(group);

        verify(groupDao, times(1)).create(group);
        assertEquals(group, actualGroup);
    }

    @Test
    public void updateGroup() {
        Group group = new Group("First Group");
        group.setGroupID(1);

        when(groupDao.getById(group.getGroupID())).thenReturn(group);
        groupService.updateGroup(group);
        verify(groupDao).update(group);
    }

    @Test
    public void delete() {
        Group group = new Group("First Group");
        group.setGroupID(1);

        groupService.delete(group);
        verify(groupDao).deleteById(group.getGroupID());
    }

    @Test
    public void getById() {
        Group group = new Group("First Group");
        group.setGroupID(1);

        when(groupDao.getById(group.getGroupID())).thenReturn(group);
        groupService.getById(group.getGroupID());
        verify(groupDao).getById(group.getGroupID());
    }

    @Test
    public void getGroupsListForAccount() {
        List<Group> groups = new ArrayList<>();
        groups.add(new Group("First Group"));
        groups.add(new Group("Second Group"));
        Account account = new Account(1, "One", "One", "onee221@gmail.com", "onepass", LocalDate.now());

        when(groupDao.getGroupsListForAccount(account)).thenReturn(groups);
        groupService.getGroupsListForAccount(account);
        verify(groupDao).getGroupsListForAccount(account);
    }

    @Test
    public void createGroupOwner() {
        Group group = new Group("First Group");
        group.setGroupID(1);
        Account account = new Account(1, "One", "One", "onee221@gmail.com", "onepass", LocalDate.now());
        Member owner = new Member(account.getAccountID(), group.getGroupID(), Member.Status.OWNER);

        when(memberDao.getOwnerByGroupId(group.getGroupID())).thenReturn(null);
        groupService.createGroupOwner(account, group);
        verify(memberDao).create(owner);
    }

    @Test
    public void getGroupOwner() {
        Group group = new Group("First Group");
        group.setGroupID(1);
        Account account = new Account(1, "One", "One", "onee221@gmail.com", "onepass", LocalDate.now());
        Member member = new Member(account.getAccountID(), group.getGroupID(), Member.Status.OWNER);

        when(memberDao.getOwnerByGroupId(group.getGroupID())).thenReturn(member);
        groupService.getGroupOwner(group);
        verify(memberDao).getOwnerByGroupId(group.getGroupID());
    }

    @Test
    public void setGroupOwner() {
        Group group = new Group("First Group");
        group.setGroupID(1);
        Account account1 = new Account(1, "One", "One", "onee221@gmail.com", "onepass", LocalDate.now());
        Account account2 = new Account(2, "Two", "Two", "onee221@gmail.com", "twopass", LocalDate.now());
        Member owner = new Member(account1.getAccountID(), group.getGroupID(), Member.Status.OWNER);

        when(memberDao.getOwnerByGroupId(group.getGroupID())).thenReturn(owner);
        groupService.setGroupOwner(account2, group);
        verify(memberDao).update(owner);
    }

    @Test
    public void setGroupModerator() {
        Group group = new Group("First Group");
        group.setGroupID(1);
        Account account = new Account(1, "One", "One", "onee221@gmail.com", "onepass", LocalDate.now());
        Member member = new Member(account.getAccountID(), group.getGroupID(), Member.Status.MODERATOR);

        when(memberDao.getGroupModerators(group.getGroupID())).thenReturn(new ArrayList<>());
        groupService.setGroupModerator(account, group);
        verify(memberDao).update(member);
    }

    @Test
    public void setGroupUser() {
        Group group = new Group("First Group");
        group.setGroupID(1);
        Account account = new Account(1, "One", "One", "onee221@gmail.com", "onepass", LocalDate.now());
        Member member = new Member(account.getAccountID(), group.getGroupID(), Member.Status.USER);

        groupService.setGroupUser(account, group);
        verify(memberDao).update(member);
    }

    @Test
    public void createGroupMember() {
        Group group = new Group("First Group");
        group.setGroupID(1);
        Account account = new Account(1, "One", "One", "onee221@gmail.com", "onepass", LocalDate.now());
        Member member = new Member(account.getAccountID(), group.getGroupID(), Member.Status.USER);

        when(memberDao.getGroupMembers(group.getGroupID())).thenReturn(new ArrayList<>());
        groupService.createGroupMember(account.getAccountID(), group.getGroupID());
        verify(memberDao).create(member);
    }

    @Test
    public void getMemberByAccountId() {
        Group group = new Group("First Group");
        group.setGroupID(1);
        Account account = new Account(1, "One", "One", "onee221@gmail.com", "onepass", LocalDate.now());
        Member member = new Member(account.getAccountID(), group.getGroupID(), Member.Status.USER);

        when(memberDao.getById(account.getAccountID())).thenReturn(member);
        groupService.getMemberByAccountId(account.getAccountID());
        verify(memberDao).getById(account.getAccountID());
    }

    @Test
    public void deleteMemberFromGroup() {
        Group group = new Group("First Group");
        group.setGroupID(1);
        Account account = new Account(1, "One", "One", "onee221@gmail.com", "onepass", LocalDate.now());
        Member member = new Member(account.getAccountID(), group.getGroupID(), Member.Status.USER);

        when(memberDao.getGroupMembers(group.getGroupID())).thenReturn(Collections.singletonList(member));
        groupService.deleteMemberFromGroup(account, group);
        verify(memberDao).delete(member);
    }

    @Test
    public void getListOfMembers() {
        Group group = new Group("First Group");
        group.setGroupID(1);
        Member member1 = new Member(1, group.getGroupID(), Member.Status.USER);
        Member member2 = new Member(2, group.getGroupID(), Member.Status.OWNER);
        Member member3 = new Member(3, group.getGroupID(), Member.Status.MODERATOR);
        List<Member> members = Arrays.asList(member1, member2, member3);

        when(memberDao.getGroupMembers(group.getGroupID())).thenReturn(members);
        groupService.getListOfMembers(group.getGroupID());
        verify(memberDao).getGroupMembers(group.getGroupID());
    }

    @Test
    public void getListOfModerators() {
        Group group = new Group("First Group");
        group.setGroupID(1);
        Member member1 = new Member(1, group.getGroupID(), Member.Status.MODERATOR);
        Member member2 = new Member(2, group.getGroupID(), Member.Status.MODERATOR);
        Member member3 = new Member(3, group.getGroupID(), Member.Status.MODERATOR);
        List<Member> members = Arrays.asList(member1, member2, member3);

        when(memberDao.getGroupModerators(group.getGroupID())).thenReturn(members);
        groupService.getListOfModerators(group.getGroupID());
        verify(memberDao).getGroupModerators(group.getGroupID());
    }

    @Test
    public void isAbleToModify() {
        Group group = new Group("First Group");
        group.setGroupID(1);
        Member member1 = new Member(1, group.getGroupID(), Member.Status.USER);
        Member member2 = new Member(2, group.getGroupID(), Member.Status.OWNER);
        Member member3 = new Member(3, group.getGroupID(), Member.Status.MODERATOR);
        List<Member> members = Arrays.asList(member1, member2, member3);

        when(memberDao.getGroupMembers(group.getGroupID())).thenReturn(members);
        assertTrue(groupService.isAbleToModify(2, group.getGroupID()));
        assertFalse(groupService.isAbleToModify(1, group.getGroupID()));
    }

    @Test
    public void convertMembersToAccounts() {
        Group group = new Group("First Group");
        group.setGroupID(1);
        Account account = new Account(1, "One", "One", "onee221@gmail.com", "onepass", LocalDate.now());
        Member member = new Member(account.getAccountID(), group.getGroupID(), Member.Status.USER);
        List<Member> members = new ArrayList<>();
        members.add(member);

        when(accountDao.getById(member.getAccountID())).thenReturn(account);
        groupService.convertMembersToAccounts(members);
        verify(accountDao, times(1)).getById(member.getAccountID());
    }
}