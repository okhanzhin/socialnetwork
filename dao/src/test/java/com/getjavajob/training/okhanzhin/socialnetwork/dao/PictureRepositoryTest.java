package com.getjavajob.training.okhanzhin.socialnetwork.dao;

import com.getjavajob.training.okhanzhin.socialnetwork.dao.interfaces.AccountRepository;
import com.getjavajob.training.okhanzhin.socialnetwork.dao.interfaces.PictureRepository;
import com.getjavajob.training.okhanzhin.socialnetwork.domain.Account;
import com.getjavajob.training.okhanzhin.socialnetwork.domain.picture.AccountPicture;
import com.getjavajob.training.okhanzhin.socialnetwork.domain.picture.Picture;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@DataJpaTest
public class PictureRepositoryTest extends AbstractRepositoryTest {

    @Autowired
    private PictureRepository pictureRepository;
    @Autowired
    private AccountRepository accountRepository;

    @Transactional
    @Test
    public void create() {
        Account account = accountRepository.getById(1L);
        AccountPicture expectedPicture = new AccountPicture(new byte[]{1, 2, 3, 4, 5});
        expectedPicture.setAccount(account);
        AccountPicture actualPicture = new AccountPicture(new byte[]{1, 2, 3, 4, 5});
        actualPicture.setAccount(account);
        Picture returnedPicture = pictureRepository.create(actualPicture);
        expectedPicture.setPicID(returnedPicture.getPicID());

        assertEquals(expectedPicture, returnedPicture);
    }

    @Transactional
    @Test
    public void update() {
        Account account = accountRepository.getById(2L);
        AccountPicture expectedPicture = new AccountPicture(new byte[]{1, 2, 3, 4, 5});
        expectedPicture.setAccount(account);
        AccountPicture actualPicture = new AccountPicture(new byte[]{1, 2, 3, 4, 5});
        actualPicture.setAccount(account);
        Picture returnedPicture = pictureRepository.create(actualPicture);
        expectedPicture.setPicID(returnedPicture.getPicID());

        expectedPicture.setContent(new byte[]{1});
        returnedPicture.setContent(new byte[]{1});
        pictureRepository.update(returnedPicture);

        assertEquals(expectedPicture, pictureRepository.getAccountPicture(account));
    }

    @Transactional
    @Test
    public void getById() {
        Account account = accountRepository.getById(1L);
        AccountPicture expectedPicture = new AccountPicture(hexStringToByteArray("C9CBBBCCCEB9C8CABCCCCEB9C9CBBB"));
        expectedPicture.setAccount(account);
        Picture actualPicture = pictureRepository.getAccountPicture(account);
        expectedPicture.setPicID(actualPicture.getPicID());

        assertEquals(expectedPicture, actualPicture);
    }

    public static byte[] hexStringToByteArray(String hex) {
        int l = hex.length();
        byte[] data = new byte[l / 2];
        for (int i = 0; i < l; i += 2) {
            data[i / 2] = (byte) ((Character.digit(hex.charAt(i), 16) << 4)
                    + Character.digit(hex.charAt(i + 1), 16));
        }
        return data;
    }
}
