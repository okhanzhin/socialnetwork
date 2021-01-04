package com.getjavajob.training.okhanzhin.socialnetwork.dao;

import com.getjavajob.training.okhanzhin.socialnetwork.domain.Account;
import com.getjavajob.training.okhanzhin.socialnetwork.domain.Group;
import com.getjavajob.training.okhanzhin.socialnetwork.domain.Member;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:dao-context.xml", "classpath*:test-context.xml"})
public class GroupDaoTest extends PrepareDaoTest {
    @Autowired
    private GroupDao groupDao;

    @Test
    public void create() {
        Group exceptedGroup = new Group("First Group");
        Group actualGroup = groupDao.create(exceptedGroup);

        exceptedGroup.setGroupID(actualGroup.getGroupID());
        assertEquals(exceptedGroup, actualGroup);
    }

    @Test
    public void getById() {
        Group exceptedGroup = groupDao.create(new Group("First Group"));
        Group actualGroup = groupDao.getById(exceptedGroup.getGroupID());

        assertEquals(exceptedGroup, actualGroup);
    }

    @Test
    public void searchEntities() {
        List<Group> exceptedAccounts = new ArrayList<>();
        exceptedAccounts.add(groupDao.create(new Group("First Group")));
        exceptedAccounts.add(groupDao.create(new Group("Second Group")));
        exceptedAccounts.add(groupDao.create(new Group("Third Group")));
        exceptedAccounts.add(groupDao.create(new Group("Fourth Group")));

        List<Group> actualAccounts = groupDao.searchEntities("Group", 1);

        assertEquals(exceptedAccounts, actualAccounts);
    }

    @Test
    public void getCountOfSearchResults() {
        List<Group> exceptedAccounts = new ArrayList<>();
        exceptedAccounts.add(groupDao.create(new Group("First Group")));
        exceptedAccounts.add(groupDao.create(new Group("Second Group")));
        exceptedAccounts.add(groupDao.create(new Group("Third Group")));
        exceptedAccounts.add(groupDao.create(new Group("Fourth Group")));

        assertEquals(exceptedAccounts.size(), groupDao.getCountOfSearchResults("Group"));
    }

    @Test
    public void update() {
        Group exceptedGroup = groupDao.create(new Group("First Group"));
        exceptedGroup.setGroupDescription("Testing update feature");

        groupDao.update(exceptedGroup);
        Group actualGroup = groupDao.getById(exceptedGroup.getGroupID());

        assertEquals(exceptedGroup, actualGroup);
    }

    @Test
    public void deleteById() {
        Group groupForRemoval = groupDao.create(new Group("First Group"));

        assertNotNull(groupDao.getById(groupForRemoval.getGroupID()));
        groupDao.deleteById(groupForRemoval.getGroupID());
        assertNull(groupDao.getById(groupForRemoval.getGroupID()));
    }


    @Test
    public void getGroupsListForAccount() {
        AccountDao accountDao = new AccountDao(this.jdbcTemplate);
        Account account = new Account("Petrov", "Petr", "petrov@gmail.com", "PetrovPas", LocalDate.now());
        accountDao.create(account);

        List<Group> exceptedGroups = new ArrayList<>();
        Group group1 = groupDao.create(new Group("First Group"));
        exceptedGroups.add(group1);
        Group group2 = groupDao.create(new Group("Second Group"));
        exceptedGroups.add(group2);
        Group group3 = groupDao.create(new Group("Third Group"));
        exceptedGroups.add(group3);

        MemberDao memberDao = new MemberDao(this.jdbcTemplate);
        memberDao.create(new Member(account.getAccountID(), group1.getGroupID(), Member.Status.USER));
        memberDao.create(new Member(account.getAccountID(), group2.getGroupID(), Member.Status.USER));
        memberDao.create(new Member(account.getAccountID(), group3.getGroupID(), Member.Status.USER));

        List<Group> actualGroups = groupDao.getGroupsListForAccount(account);

        assertEquals(exceptedGroups, actualGroups);
    }
}