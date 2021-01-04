package com.getjavajob.training.okhanzhin.socialnetwork.dao;

import com.getjavajob.training.okhanzhin.socialnetwork.domain.Account;
import com.getjavajob.training.okhanzhin.socialnetwork.domain.Phone;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:dao-context.xml", "classpath*:test-context.xml"})
public class PhoneDaoTest extends PrepareDaoTest {
    @Autowired
    private PhoneDao phoneDao;
    @Autowired
    private AccountDao accountDao;

    @Test
    public void create() {
        Account testAccount = new Account("Petrov", "Petr", "petrov@gmail.com", "PetrovPas", LocalDate.now());
        long accountId = accountDao.create(testAccount).getAccountID();
        Phone exceptedPhone = new Phone(accountId, "89160000000", "home");
        Phone actualPhone = phoneDao.create(exceptedPhone);
        exceptedPhone.setPhoneID(actualPhone.getPhoneID());

        assertEquals(exceptedPhone, actualPhone);
    }

    @Test
    public void update() {
        Account testAccount = new Account("Sidorov", "Sidr", "sidorov@gmail.com", "SidorovPas", LocalDate.now());
        long accountId = accountDao.create(testAccount).getAccountID();
        Phone testPhoneForUpdate = new Phone(accountId, "89161111111", "home");
        Phone returnedPhone = phoneDao.create(testPhoneForUpdate);
        testPhoneForUpdate.setPhoneID(returnedPhone.getPhoneID());

        assertEquals(testPhoneForUpdate, phoneDao.getById(returnedPhone.getPhoneID()));

        testPhoneForUpdate.setPhoneNumber("89162222222");
        testPhoneForUpdate.setPhoneType("work");
        phoneDao.update(testPhoneForUpdate);
        Phone phoneAfterUpdate = phoneDao.getById(returnedPhone.getPhoneID());

        assertEquals(returnedPhone.getPhoneID(), phoneAfterUpdate.getPhoneID());
        assertEquals(returnedPhone.getAccountID(), phoneAfterUpdate.getAccountID());
        assertEquals("89162222222", phoneAfterUpdate.getPhoneNumber());
        assertEquals("work", phoneAfterUpdate.getPhoneType());
    }

    @Test
    public void getAllPhonesByAccountId() {
        Account testAccount = new Account("Ivanov", "Ivan", "ivanov@gmail.com", "IvanovPas", LocalDate.now());
        long accountId = accountDao.create(testAccount).getAccountID();
        List<Phone> exceptedPhones = new ArrayList<>();
        exceptedPhones.add(phoneDao.create(new Phone(accountId, "89163333333", "home")));
        exceptedPhones.add(phoneDao.create(new Phone(accountId, "89164444444", "work")));

        assertEquals(exceptedPhones, phoneDao.getAllPhonesByAccountId(accountId));
    }
}