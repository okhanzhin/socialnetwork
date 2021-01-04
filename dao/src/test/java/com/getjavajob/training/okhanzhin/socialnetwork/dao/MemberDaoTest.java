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
public class MemberDaoTest extends PrepareDaoTest {
    @Autowired
    private MemberDao memberDao;
    @Autowired
    private AccountDao accountDao;
    @Autowired
    private GroupDao groupDao;

    @Test
    public void create() {
        Account account = accountDao.create(new Account("Petrov", "Petr", "petrov@gmail.com", "PetrovPas", LocalDate.now()));
        Group group = groupDao.create(new Group("First Group"));
        Member exceptedMember = new Member(account.getAccountID(), group.getGroupID(), Member.Status.USER);
        Member actualMember = memberDao.create(exceptedMember);

        assertEquals(exceptedMember, actualMember);
    }

    @Test
    public void getOwnerByGroupId() {
        Account account = accountDao.create(new Account("Petrov", "Petr", "petrov@gmail.com", "PetrovPas", LocalDate.now()));
        Group group = groupDao.create(new Group("First Group"));
        Member exceptedMember = memberDao.create(new Member(account.getAccountID(), group.getGroupID(), Member.Status.OWNER));
        Member actualMember = memberDao.getOwnerByGroupId(group.getGroupID());

        assertEquals(exceptedMember, actualMember);
    }

    @Test
    public void update() {
        Account account = accountDao.create(new Account("Petrov", "Petr", "petrov@gmail.com", "PetrovPas", LocalDate.now()));
        Group group = groupDao.create(new Group("First Group"));
        Member exceptedMember = new Member(account.getAccountID(), group.getGroupID(), Member.Status.USER);
        memberDao.create(exceptedMember);
        exceptedMember.setMemberStatus(Member.Status.OWNER);
        memberDao.update(exceptedMember);

        assertEquals(exceptedMember, memberDao.getOwnerByGroupId(group.getGroupID()));
    }

    @Test
    public void delete() {
        Account account = accountDao.create(new Account("Petrov", "Petr", "petrov@gmail.com", "PetrovPas", LocalDate.now()));
        Group group = groupDao.create(new Group("First Group"));
        Member exceptedMember = new Member(account.getAccountID(), group.getGroupID(), Member.Status.USER);
        memberDao.create(exceptedMember);

        assertNotNull(memberDao.getById(account.getAccountID()));
        memberDao.delete(exceptedMember);
        assertNull(memberDao.getById(account.getAccountID()));
    }

    @Test
    public void getGroupMembers() {
        Account account1 = accountDao.create(new Account("Petrov", "Petr", "petrov@gmail.com", "PetrovPas", LocalDate.now()));
        Account account2 = accountDao.create(new Account("Sidorov", "Sidr", "sidorov@gmail.com", "SidorovPas", LocalDate.now()));
        Account account3 = accountDao.create(new Account("Ivanov", "Ivan", "ivanov@gmail.com", "IvanovPas", LocalDate.now()));
        Group group = groupDao.create(new Group("First Group"));
        List<Member> exceptedMembers = new ArrayList<>();
        Member member1 = new Member(account1.getAccountID(), group.getGroupID(), Member.Status.USER);
        exceptedMembers.add(member1);
        Member member2 = new Member(account2.getAccountID(), group.getGroupID(), Member.Status.USER);
        exceptedMembers.add(member2);
        Member member3 = new Member(account3.getAccountID(), group.getGroupID(), Member.Status.USER);
        exceptedMembers.add(member3);

        for (Member exceptedMember : exceptedMembers) {
            memberDao.create(exceptedMember);
        }

        assertEquals(exceptedMembers, memberDao.getGroupMembers(group.getGroupID()));
    }

    @Test
    public void getGroupModerators() {
        Account account1 = accountDao.create(new Account("Petrov", "Petr", "petrov@gmail.com", "PetrovPas", LocalDate.now()));
        Account account2 = accountDao.create(new Account("Sidorov", "Sidr", "sidorov@gmail.com", "SidorovPas", LocalDate.now()));
        Account account3 = accountDao.create(new Account("Ivanov", "Ivan", "ivanov@gmail.com", "IvanovPas", LocalDate.now()));
        Group group = groupDao.create(new Group("First Group"));
        List<Member> exceptedMembers = new ArrayList<>();
        Member moderator1 = new Member(account1.getAccountID(), group.getGroupID(), Member.Status.MODERATOR);
        exceptedMembers.add(moderator1);
        Member moderator2 = new Member(account2.getAccountID(), group.getGroupID(), Member.Status.MODERATOR);
        exceptedMembers.add(moderator2);
        Member moderator3 = new Member(account3.getAccountID(), group.getGroupID(), Member.Status.MODERATOR);
        exceptedMembers.add(moderator3);

        for (Member exceptedMember : exceptedMembers) {
            memberDao.create(exceptedMember);
        }

        assertEquals(exceptedMembers, memberDao.getGroupModerators(group.getGroupID()));
    }
}