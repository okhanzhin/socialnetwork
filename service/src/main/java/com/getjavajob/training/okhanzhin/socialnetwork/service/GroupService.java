package com.getjavajob.training.okhanzhin.socialnetwork.service;

import com.getjavajob.training.okhanzhin.socialnetwork.dao.*;
import com.getjavajob.training.okhanzhin.socialnetwork.domain.Account;
import com.getjavajob.training.okhanzhin.socialnetwork.domain.Group;
import com.getjavajob.training.okhanzhin.socialnetwork.domain.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class GroupService {
    public static final int RECORDS_PER_PAGE = 8;

    private final GroupDao groupDao;
    private final MemberDao memberDao;
    private final AccountDao accountDao;

    @Autowired
    public GroupService(GroupDao groupDao, MemberDao memberDao, AccountDao accountDao) {
        this.groupDao = groupDao;
        this.memberDao = memberDao;
        this.accountDao = accountDao;
    }

    @Transactional
    public Group createGroup(Group group) {
        return groupDao.create(group);
    }

    @Transactional
    public void updateGroup(Group group) {
        Group updatableGroup = groupDao.getById(group.getGroupID());

        if (!updatableGroup.getGroupName().equals(group.getGroupName())) {
            updatableGroup.setGroupName(group.getGroupName());
        }

        if (group.getGroupDescription() != null && group.getGroupDescription().length() != 0) {
            if (updatableGroup.getGroupDescription() == null) {
                updatableGroup.setGroupDescription(group.getGroupDescription());
            } else {
                if (!updatableGroup.getGroupDescription().equals(group.getGroupDescription())) {
                    updatableGroup.setGroupDescription(group.getGroupDescription());
                }
            }
        } else if (group.getGroupDescription() != null && group.getGroupDescription().length() == 0) {
            updatableGroup.setGroupDescription(null);
        }

        if (updatableGroup.getPicture() != null && group.getPicture().length != 0) {
            if (!Arrays.equals(updatableGroup.getPicture(), group.getPicture())) {
                updatableGroup.setPicture(group.getPicture());
            }
        } else if (updatableGroup.getPicture() == null) {
            if (group.getPicture() != null) {
                updatableGroup.setPicture(group.getPicture());
            } else {
                updatableGroup.setPicture(updatableGroup.getPicture());
            }
        }

        groupDao.update(updatableGroup);
    }

    @Transactional
    public void delete(Group group) {
        groupDao.deleteById(group.getGroupID());
    }

    public Group getById(long groupID) {
        return groupDao.getById(groupID);
    }

    public List<Group> getGroupsListForAccount(Account account) {
        return groupDao.getGroupsListForAccount(account);
    }

    @Transactional
    public void createGroupOwner(Account account, Group group) throws ServiceException {
        Member owner = memberDao.getOwnerByGroupId(group.getGroupID());

        if (owner == null) {
            owner = new Member(account.getAccountID(), group.getGroupID(), Member.Status.OWNER);
            memberDao.create(owner);
        } else {
            throw new ServiceException("This group already has an owner.");
        }
    }

    public Member getGroupOwner(Group group) {
        return memberDao.getOwnerByGroupId(group.getGroupID());
    }

    @Transactional
    public void setGroupOwner(Account account, Group group) throws ServiceException {
        Member member = memberDao.getOwnerByGroupId(group.getGroupID());

        if (member.getAccountID() != account.getAccountID()) {
            member.setAccountID(account.getAccountID());
            memberDao.update(member);
        } else {
            throw new ServiceException("Current account is already the owner of the group");
        }
    }
    @Transactional
    public void setGroupModerator(Account account, Group group) throws ServiceException {
        Member member = new Member(account.getAccountID(), group.getGroupID(), Member.Status.MODERATOR);
        List<Member> moderators = memberDao.getGroupModerators(group.getGroupID());

        if (!moderators.contains(member)) {
            memberDao.update(member);
        } else {
            throw new ServiceException("Current account is already the moderator of the group");
        }
    }

    @Transactional
    public void setGroupUser(Account account, Group group) throws ServiceException {
        Member member = new Member(account.getAccountID(), group.getGroupID(), Member.Status.USER);
        memberDao.update(member);
    }

    @Transactional
    public Member createGroupMember(long accountID, long groupID) throws ServiceException {
        Member member = new Member(accountID, groupID, Member.Status.USER);
        List<Member> users = memberDao.getGroupMembers(groupID);

        if (!users.contains(member)) {
            return memberDao.create(member);
        }
        return null;
    }

    public Member getMemberByAccountId(long accountID) {
        return memberDao.getById(accountID);
    }

    @Transactional
    public void deleteMemberFromGroup(Account account, Group group) throws ServiceException {
        Member user = new Member(account.getAccountID(), group.getGroupID(), Member.Status.USER);
        List<Member> users = memberDao.getGroupMembers(group.getGroupID());

        if (users.contains(user)) {
            memberDao.delete(user);
        }
    }

    public List<Member> getListOfMembers(long groupID) {
        return memberDao.getGroupMembers(groupID);

    }

    public List<Member> getListOfModerators(long groupID) {
        return memberDao.getGroupModerators(groupID);
    }

    public boolean isAbleToModify(long accountID, long groupID) {
        List<Member> members = getListOfMembers(groupID);
        boolean isAbleToModify = false;

        for (Member member : members) {
            if (member.getAccountID() == accountID &&
                    (member.getMemberStatus() == Member.Status.OWNER ||
                            member.getMemberStatus() == Member.Status.MODERATOR)) {
                isAbleToModify = true;
                break;
            }
        }

        return isAbleToModify;
    }

    public List<Account> convertMembersToAccounts(List<Member> members) {
        List<Account> accounts = new ArrayList<>();

        if (!members.isEmpty()) {
            for (Member member : members) {
                accounts.add(accountDao.getById(member.getAccountID()));
            }
        }

        return accounts;
    }

    public List<Group> searchForGroups(String value, int currentPage) {
        return groupDao.searchEntities(value, currentPage);
    }

    public int getNumberOfPages(String value) {
        int rows = groupDao.getCountOfSearchResults(value);
        int numOfPages = rows / RECORDS_PER_PAGE;

        if (numOfPages % RECORDS_PER_PAGE > 0) {
            numOfPages++;
        }

        return numOfPages;
    }
}
