package com.getjavajob.training.okhanzhin.socialnetwork.dao;

import com.getjavajob.training.okhanzhin.socialnetwork.dao.interfaces.AccountRepository;
import com.getjavajob.training.okhanzhin.socialnetwork.dao.interfaces.CommunityRepository;
import com.getjavajob.training.okhanzhin.socialnetwork.domain.Community;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@DataJpaTest
public class CommunityRepositoryTest extends AbstractRepositoryTest {

    @Autowired
    private CommunityRepository communityRepository;
    @Autowired
    private AccountRepository accountRepository;

    @Transactional
    @Test
    public void create() {
        Community expectedCommunity = new Community("New Community", LocalDate.now());

        int rowsCount = 3;
        assertEquals(rowsCount, communityRepository.getAll().size());
        rowsCount++;

        Community actualCommunity = communityRepository.create(new Community(
                "New Community", LocalDate.now()));

        expectedCommunity.setCommID(actualCommunity.getCommID());

        assertEquals(rowsCount, communityRepository.getAll().size());
        assertEquals(expectedCommunity, actualCommunity);
    }

    @Transactional
    @Test
    public void update() {
        Community expectedCommunity = new Community("New Community", LocalDate.now());

        Community actualCommunity = communityRepository.create(new Community(
                "New Community", LocalDate.now()));

        expectedCommunity.setCommID(actualCommunity.getCommID());
        expectedCommunity.setCommunityName("Modified Community");
        actualCommunity.setCommunityName("Modified Community");

        communityRepository.update(actualCommunity);

        assertEquals(expectedCommunity, communityRepository.getById(actualCommunity.getCommID()));
    }

    @Transactional
    @Test
    public void getById() {
        Community expectedCommunity = new Community(
                "CommunityOne", LocalDate.of(2021, 3, 8));
        Community actualCommunity = communityRepository.getById(1L);

        expectedCommunity.setCommID(actualCommunity.getCommID());

        assertEquals(expectedCommunity, actualCommunity);
    }

    @Transactional
    @Test
    public void deleteById() {
        communityRepository.deleteById(1L);

        assertEquals(2, communityRepository.getAll().size());
    }

    @Transactional
    @Test
    public void getAll() {
        List<Community> expectedList = new ArrayList<>();
        expectedList.add(new Community(1L, "CommunityOne",
                LocalDate.of(2021, 3, 8)));
        expectedList.add(new Community(2L, "CommunityTwo",
                LocalDate.of(2021, 3, 8)));
        expectedList.add(new Community(3L, "CommunityThree",
                LocalDate.of(2021, 3, 8)));

        List<Community> actualList = communityRepository.getAll();

        assertEquals(expectedList, actualList);
    }

    @Transactional
    @Test
    public void getGroupsListForAccount() {
        List<Community> expectedList = new ArrayList<>();
        expectedList.add(new Community(1L, "CommunityOne",
                LocalDate.of(2021, 3, 8)));
        expectedList.add(new Community(2L, "CommunityTwo",
                LocalDate.of(2021, 3, 8)));

        List<Community> actualList = communityRepository.getAccountCommunities(accountRepository.getById(2L));

        assertEquals(expectedList, actualList);
    }

    @Transactional
    @Test
    public void searchEntities() {
        List<Community> expectedList = new ArrayList<>();
        expectedList.add(new Community(1L, "CommunityOne",
                LocalDate.of(2021, 3, 8)));

        List<Community> actualList = communityRepository.searchEntitiesPagination("one", 1);

        assertEquals(expectedList, actualList);
    }

    @Transactional
    @Test
    public void getCountOfSearchResults() {
        List<Community> expectedList = new ArrayList<>();
        expectedList.add(new Community(1L, "CommunityOne",
                LocalDate.of(2021, 3, 8)));
        expectedList.add(new Community(2L, "CommunityTwo",
                LocalDate.of(2021, 3, 8)));
        expectedList.add(new Community(3L, "CommunityThree",
                LocalDate.of(2021, 3, 8)));


        long actualCount = accountRepository.getCountOfSearchResults("name");

        assertEquals(expectedList.size(), actualCount);
    }
}
