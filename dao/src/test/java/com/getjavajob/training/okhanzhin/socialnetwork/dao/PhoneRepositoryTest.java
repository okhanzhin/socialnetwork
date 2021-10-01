package com.getjavajob.training.okhanzhin.socialnetwork.dao;

import com.getjavajob.training.okhanzhin.socialnetwork.dao.interfaces.AccountRepository;
import com.getjavajob.training.okhanzhin.socialnetwork.dao.interfaces.PhoneRepository;
import com.getjavajob.training.okhanzhin.socialnetwork.domain.Account;
import com.getjavajob.training.okhanzhin.socialnetwork.domain.Phone;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@DataJpaTest
public class PhoneRepositoryTest extends AbstractRepositoryTest{

    @Autowired
    private PhoneRepository phoneRepository;
    @Autowired
    private AccountRepository accountRepository;

    @Transactional
    @Test
    public void create() {
        Account account = accountRepository.getById(1L);
        Phone expectedPhone = new Phone(account, "123451011", "home");

        int rowsCount = 2;
        assertEquals(rowsCount, account.getPhones().size());

        account.getPhones().add(expectedPhone);
        accountRepository.update(account);

        Phone actualPhone = account.getPhones().get(2);
        expectedPhone.setPhoneID(actualPhone.getPhoneID());

        assertEquals(5, phoneRepository.getAll().size());
        assertEquals(expectedPhone, actualPhone);
    }

    @Transactional
    @Test
    public void update() {
        Account account = accountRepository.getById(1L);
        Phone expectedPhone = new Phone(account, "12345", "home");

        Phone actualPhone = phoneRepository.create(new Phone(account, "12345", "home"));

        expectedPhone.setPhoneID(actualPhone.getPhoneID());
        expectedPhone.setPhoneNumber("1234567890");
        actualPhone.setPhoneNumber("1234567890");
        phoneRepository.update(actualPhone);

        assertEquals(expectedPhone, phoneRepository.getById(actualPhone.getPhoneID()));
    }

    @Transactional
    @Test
    public void getById() {
        Account account = accountRepository.getById(1L);
        Phone expectedPhone = new Phone(account, "12345", "home");
        Phone actualPhone = phoneRepository.getById(1L);
        expectedPhone.setPhoneID(actualPhone.getPhoneID());

        assertEquals(expectedPhone, actualPhone);
    }

    @Transactional
    @Test
    public void deleteById() {
        phoneRepository.deleteById(1L);
        List<Phone> phones = phoneRepository.getAll();

        assertEquals(3, phones.size());
    }
}
