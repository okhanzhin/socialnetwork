package com.getjavajob.training.okhanzhin.socialnetwork.dao;

import com.getjavajob.training.okhanzhin.socialnetwork.domain.Group;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class GroupDaoTest extends PrepareDaoTest {
    private AbstractDao<Group> testGroupDao;

    @Before
    public void setUp() {
        this.testGroupDao = new GroupDao();
    }
    
    @Test
    public void create() {
        Group exceptedGroup = new Group("First Group");
        Group actualGroup = testGroupDao.create(exceptedGroup);

        exceptedGroup.setGroup_ID(1);
        assertEquals(exceptedGroup, actualGroup);
    }

    @Test
    public void getById() {
        Group returnGroup = testGroupDao.getById(1);

        assertEquals("First Group", returnGroup.getGroupName());
    }

    @Test
    public void update() {
        Group testGroupForUpdate = new Group("First Group");
        testGroupForUpdate.setGroup_ID(1);
        testGroupForUpdate.setGroupDescription("Testing update feature");

        testGroupDao.update(testGroupForUpdate);
        Group returnGroup = testGroupDao.getById(1);

        assertEquals("Testing update feature", returnGroup.getGroupDescription());
    }

    @Test
    public void delete() {
        Group groupForRemoval = new Group("Second Group");
        testGroupDao.create(groupForRemoval);

        assertNotNull(testGroupDao.getById(2));
        testGroupDao.delete(groupForRemoval);
        assertNull(testGroupDao.getById(2));
    }

    @Test
    public void getAll() {
        List<Group> exceptedList = new ArrayList<>();
        exceptedList.add(testGroupDao.getById(1));
        Group secondGroup = new Group("Second Group");
        exceptedList.add(secondGroup);
        testGroupDao.create(secondGroup);

        List<Group> actualList = testGroupDao.getAll();

        assertEquals(exceptedList, actualList);
    }
}