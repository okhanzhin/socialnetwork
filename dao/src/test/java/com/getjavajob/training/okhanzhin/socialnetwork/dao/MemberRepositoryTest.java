package com.getjavajob.training.okhanzhin.socialnetwork.dao;

import com.getjavajob.training.okhanzhin.socialnetwork.dao.interfaces.AccountRepository;
import com.getjavajob.training.okhanzhin.socialnetwork.dao.interfaces.CommunityRepository;
import com.getjavajob.training.okhanzhin.socialnetwork.dao.interfaces.MemberRepository;
import com.getjavajob.training.okhanzhin.socialnetwork.domain.Member;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

@RunWith(SpringRunner.class)
@DataJpaTest
public class MemberRepositoryTest extends AbstractRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private CommunityRepository communityRepository;

    @Transactional
    @Test
    public void create() {
        Member expectedMember = new Member(
                accountRepository.getById(2L), communityRepository.getById(3L), Member.Status.USER);

        int rowsCount = 4;
        assertEquals(rowsCount, memberRepository.getAll().size());
        rowsCount++;

        Member actualMember = memberRepository.create(new Member(
                accountRepository.getById(2L), communityRepository.getById(3L), Member.Status.USER));

        assertEquals(rowsCount, memberRepository.getAll().size());
        assertEquals(expectedMember, actualMember);
    }

    @Transactional
    @Test
    public void update() {
        Member expectedMember = new Member(
                accountRepository.getById(2L), communityRepository.getById(3L), Member.Status.USER);

        Member actualMember = memberRepository.create(new Member(
                accountRepository.getById(2L), communityRepository.getById(3L), Member.Status.USER));

        expectedMember.setMemberStatus(Member.Status.MODERATOR);
        actualMember.setMemberStatus(Member.Status.MODERATOR);
        memberRepository.update(actualMember);

        assertEquals(expectedMember, memberRepository.getMember(actualMember.getAccount(), actualMember.getCommunity()));
    }

    @Transactional
    @Test
    public void getMember() {
        Member expectedMember = new Member(
                accountRepository.getById(1L), communityRepository.getById(1L), Member.Status.USER);

        Member actualMember = memberRepository.getMember(expectedMember.getAccount(), expectedMember.getCommunity());

        assertEquals(expectedMember, actualMember);
    }

    @Transactional
    @Test
    public void getNullMember() {
        Member expectedMember = new Member(
                accountRepository.getById(1L), communityRepository.getById(3L), Member.Status.USER);

        assertNull(memberRepository.getMember(expectedMember.getAccount(), expectedMember.getCommunity()));
    }

    @Transactional
    @Test
    public void deleteMember() {
        Member expectedMember = new Member(
                accountRepository.getById(1L), communityRepository.getById(1L), Member.Status.USER);

        memberRepository.deleteMember(expectedMember.getAccount(), expectedMember.getCommunity());

        assertEquals(3, memberRepository.getAll().size());
    }

    @Transactional
    @Test
    public void getAll() {
        List<Member> expectedList = new ArrayList<>();
        expectedList.add(new Member(accountRepository.getById(1L), communityRepository.getById(1L), Member.Status.OWNER));
        expectedList.add(new Member(accountRepository.getById(2L), communityRepository.getById(1L), Member.Status.USER));
        expectedList.add(new Member(accountRepository.getById(3L), communityRepository.getById(2L), Member.Status.USER));
        expectedList.add(new Member(accountRepository.getById(2L), communityRepository.getById(2L), Member.Status.MODERATOR));

        List<Member> actualList = memberRepository.getAll();

        assertEquals(expectedList, actualList);
    }

    @Transactional
    @Test
    public void getOwnerByGroupId() {
        Member expectedMember = new Member(accountRepository.getById(1L), communityRepository.getById(1L), Member.Status.OWNER);
        Member actualMember = memberRepository.getOwnerByCommId(communityRepository.getById(1L).getCommID());

        assertEquals(expectedMember, actualMember);
    }

    @Transactional
    @Test
    public void getGroupMembers() {
        List<Member> expectedList = new ArrayList<>();
        expectedList.add(new Member(accountRepository.getById(1L), communityRepository.getById(1L), Member.Status.OWNER));
        expectedList.add(new Member(accountRepository.getById(2L), communityRepository.getById(1L), Member.Status.USER));

        List<Member> actualList = memberRepository.getCommunityMembers(communityRepository.getById(1L).getCommID());

        assertEquals(expectedList, actualList);
    }

    @Transactional
    @Test
    public void getGroupModerators() {
        List<Member> expectedList = new ArrayList<>();
        expectedList.add(new Member(accountRepository.getById(2L), communityRepository.getById(2L), Member.Status.MODERATOR));

        List<Member> actualList = memberRepository.getCommunityModerators(communityRepository.getById(2L).getCommID());

        assertEquals(expectedList, actualList);
    }
}
