package com.getjavajob.training.okhanzhin.socialnetwork.dao;

import com.getjavajob.training.okhanzhin.socialnetwork.dao.interfaces.AccountRepository;
import com.getjavajob.training.okhanzhin.socialnetwork.dao.interfaces.RelationRepository;
import com.getjavajob.training.okhanzhin.socialnetwork.domain.Account;
import com.getjavajob.training.okhanzhin.socialnetwork.domain.Relationship;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@DataJpaTest
public class RelationshipRepositoryTest extends AbstractRepositoryTest {

    @Autowired
    private RelationRepository relationRepository;
    @Autowired
    private AccountRepository accountRepository;

    @Transactional
    @Test
    public void create() {
        Account accountOne = accountRepository.getById(1L);
        Account accountTwo = accountRepository.getById(2L);

        Relationship expectedRelationship = new Relationship(accountOne, accountTwo);

        int rowsCount = 3;
        assertEquals(rowsCount, relationRepository.getAll().size());
        rowsCount++;

        Relationship actualRelationship = relationRepository.create(new Relationship(accountOne, accountTwo));
        expectedRelationship.setRelationID(actualRelationship.getRelationID());

        assertEquals(rowsCount, relationRepository.getAll().size());
        assertEquals(expectedRelationship, actualRelationship);
    }

    @Transactional
    @Test
    public void update() {
        Account accountOne = accountRepository.getById(1L);
        Account accountTwo = accountRepository.getById(2L);

        Relationship expectedRelationship = new Relationship(accountOne, accountTwo);
        Relationship actualRelationship = relationRepository.create(new Relationship(accountOne, accountTwo));

        expectedRelationship.setRelationID(actualRelationship.getRelationID());
        expectedRelationship.setRelationStatus(Relationship.Status.ACCEPTED);
        actualRelationship.setRelationStatus(Relationship.Status.ACCEPTED);
        relationRepository.update(actualRelationship);

        assertEquals(expectedRelationship, relationRepository.getById(actualRelationship.getRelationID()));
    }

    @Transactional
    @Test
    public void getRelation() {
        Account accountOne = accountRepository.getById(1L);
        Account accountTwo = accountRepository.getById(2L);

        Relationship expectedRelationship = new Relationship(accountOne, accountTwo, Relationship.Status.ACCEPTED);
        Relationship actualRelationship = relationRepository.getRelation(accountOne, accountTwo);
        expectedRelationship.setRelationID(actualRelationship.getRelationID());

        assertEquals(expectedRelationship, actualRelationship);
    }

    @Transactional
    @Test
    public void getFriendsList() {
        Account accountOne = accountRepository.getById(1L);
        Account accountTwo = accountRepository.getById(2L);
        Account accountThree = accountRepository.getById(3L);

        List<Account> expectedList = new ArrayList<>();
        expectedList.add(accountTwo);
        expectedList.add(accountThree);

        List<Account> actualList = relationRepository.getFriendsList(accountOne);

        assertEquals(expectedList, actualList);
    }

    @Transactional
    @Test
    public void getBlockedAccountsList() {
        Account accountTwo = accountRepository.getById(2L);
        Account accountThree = accountRepository.getById(3L);

        List<Account> expectedList = new ArrayList<>();
        expectedList.add(accountTwo);

        List<Account> actualList = relationRepository.getBlockedAccountsList(accountThree);

        assertEquals(expectedList, actualList);
    }
}
