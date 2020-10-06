package com.getjavajob.training.okhanzhin.socialnetwork.service;

import com.getjavajob.training.okhanzhin.socialnetwork.dao.*;
import com.getjavajob.training.okhanzhin.socialnetwork.domain.Account;
import com.getjavajob.training.okhanzhin.socialnetwork.domain.Group;
import com.getjavajob.training.okhanzhin.socialnetwork.domain.Member;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GroupService {
    private static final byte OWNER_STATUS = 0;
    private static final byte MODERATOR_STATUS = 1;
    private static final byte USER_STATUS = 2;
    private final GroupDao groupDao;
    private final MemberDao memberDao;

    public GroupService(GroupDao groupDao, MemberDao memberDao, RequestDao requestDao) {
        this.groupDao = groupDao;
        this.memberDao = memberDao;
    }

    public GroupService() {
        this.groupDao = new GroupDao();
        this.memberDao = new MemberDao();
    }

    public Group createGroup(Group group) {
        return groupDao.create(group);
    }

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
            if (group.getPicture().length != 0) {
                updatableGroup.setPicture(group.getPicture());
            } else {
                updatableGroup.setPicture(updatableGroup.getPicture());
            }
        }

        groupDao.update(updatableGroup);
    }

    public void deleteGroup(Group group) {
        groupDao.delete(group);
    }

    public Group getGroupById(long groupID) {
        return groupDao.getById(groupID);
    }

    public List<Group> getGroupsListForAccount(Account account) {
        return groupDao.getGroupsListForAccount(account);
    }

    public void createGroupOwner(Account account, Group group) throws ServiceException {
        long accountID = account.getAccountID();
        long groupID = group.getGroupID();
        Member owner = memberDao.getOwnerByGroupId(groupID);
        if (owner == null) {
            owner = new Member(accountID, groupID, OWNER_STATUS);
            memberDao.create(owner);
        } else {
            throw new ServiceException("This group already has an owner.");
        }
    }

    public Member getGroupOwner(Group group) {
        long groupID = group.getGroupID();
        return memberDao.getOwnerByGroupId(groupID);
    }

    public void setGroupOwner(Account account, Group group) throws ServiceException {
        long accountID = account.getAccountID();
        long groupID = group.getGroupID();
        Member member = memberDao.getOwnerByGroupId(groupID);

        if (member.getAccountID() != accountID) {
            member.setAccountID(accountID);
            memberDao.update(member);
        } else {
            throw new ServiceException("Current account is already the owner of the group");
        }
    }

    public void setGroupModerator(Account account, Group group) throws ServiceException {
        long accountID = account.getAccountID();
        long groupID = group.getGroupID();
        Member member = new Member(accountID, groupID, MODERATOR_STATUS);
        List<Member> moderators = memberDao.getListOfModerators(groupID);

        if (!moderators.contains(member)) {
            memberDao.update(member);
        } else {
            throw new ServiceException("Current account is already the moderator of the group");
        }
    }

    public void setGroupUser(Account account, Group group) throws ServiceException {
        long accountID = account.getAccountID();
        long groupID = group.getGroupID();
        Member member = new Member(accountID, groupID, USER_STATUS);
        List<Member> users = memberDao.getListById(groupID);
        memberDao.update(member);
    }

    public Member createGroupMember(long accountID, long groupID) throws ServiceException {
        Member member = new Member(accountID, groupID, USER_STATUS);
        List<Member> users = memberDao.getListById(groupID);

        if (!users.contains(member)) {
            memberDao.create(member);
            return member;
        } else {
            throw new ServiceException("Current account is already a member of the group.");
        }
    }

    public Member getMemberByAccountId(long accountID) {
        return memberDao.getById(accountID);
    }

    public void deleteMemberFromGroup(Account account, Group group) throws ServiceException {
        long accountID = account.getAccountID();
        long groupID = group.getGroupID();
        Member user = new Member(accountID, groupID, USER_STATUS);
        List<Member> users = memberDao.getListById(groupID);

        if (users.contains(user)) {
            memberDao.delete(user);
        } else {
            throw new ServiceException("Current account isn't a member of the group.");
        }
    }

    public List<Member> getListOfMembers(long groupID) {
        return memberDao.getListById(groupID);
    }

    public List<Member> getListOfModerators(long groupID) {
        return memberDao.getListOfModerators(groupID);
    }

    public boolean isAbleToModify(long accountID, long groupID) {
        List<Member> members = getListOfMembers(groupID);
        boolean isAbleToModify = false;
        for (Member member : members) {
            if (member.getAccountID() == accountID &&
                    (member.getMemberStatus() == OWNER_STATUS ||
                            member.getMemberStatus() == MODERATOR_STATUS)) {
                isAbleToModify = true;
                break;
            }
        }

        return isAbleToModify;
    }

    public List<Account> convertMembersToAccounts(List<Member> members) {
        List<Account> accounts = new ArrayList<>();
        if (!members.isEmpty()) {
            AccountDao accountDao = new AccountDao();
            for (Member member : members) {
                accounts.add(accountDao.getById(member.getAccountID()));
            }
        }
        return accounts;
    }
}
