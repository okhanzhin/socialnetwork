package com.getjavajob.training.okhanzhin.socialnetwork.dao;

import com.getjavajob.training.okhanzhin.socialnetwork.dao.interfaces.AccountRepository;
import com.getjavajob.training.okhanzhin.socialnetwork.dao.interfaces.ChatRoomRepository;
import com.getjavajob.training.okhanzhin.socialnetwork.dao.interfaces.MessageRepository;
import com.getjavajob.training.okhanzhin.socialnetwork.domain.Account;
import com.getjavajob.training.okhanzhin.socialnetwork.domain.ChatRoom;
import com.getjavajob.training.okhanzhin.socialnetwork.domain.Message;
import com.getjavajob.training.okhanzhin.socialnetwork.domain.dto.ChatDialogue;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@DataJpaTest
public class MessageRepositoryTest extends AbstractRepositoryTest {

    @Autowired
    private MessageRepository messageRepository;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private ChatRoomRepository chatRoomRepository;

    @Transactional
    @Test
    public void create() {
        Message expectedMessage = new Message(chatRoomRepository.getById(1L),
                accountRepository.getById(1L), accountRepository.getById(2L), "SomeText", LocalDateTime.now());

        int rowsCount = 4;
        assertEquals(rowsCount, messageRepository.getAll().size());
        rowsCount++;

        Message actualMessage = messageRepository.create(new Message(chatRoomRepository.getById(1L),
                accountRepository.getById(1L), accountRepository.getById(2L), "SomeText", LocalDateTime.now())
        );
        expectedMessage.setMessageID(actualMessage.getMessageID());

        assertEquals(rowsCount, messageRepository.getAll().size());
        assertEquals(expectedMessage, actualMessage);
    }

    @Transactional
    @Test
    public void getById() {
        Message expectedMessage = new Message(chatRoomRepository.getById(1L),
                accountRepository.getById(1L), accountRepository.getById(2L), "Text1",
                LocalDateTime.of(2021, 3, 26, 17, 33));
        Message actualMessage = messageRepository.getById(1L);

        expectedMessage.setMessageID(actualMessage.getMessageID());

        assertEquals(expectedMessage, actualMessage);
    }

    @Transactional
    @Test
    public void deleteById() {
        messageRepository.deleteById(1L);
        List<Message> messages = messageRepository.getAll();

        assertEquals(3, messages.size());
    }

    @Transactional
    @Test
    public void getMessageSequence() {
        List<Message> expectedList = new ArrayList<>();
        expectedList.add(new Message(1L, chatRoomRepository.getById(1L),
                accountRepository.getById(1L), accountRepository.getById(2L), "Text1",
                LocalDateTime.of(2021, 3, 26, 17, 33)));
        expectedList.add(new Message(2L, chatRoomRepository.getById(1L),
                accountRepository.getById(2L), accountRepository.getById(1L), "Text2",
                LocalDateTime.of(2021, 3, 26, 17, 33)));

        List<Message> actualList = messageRepository.getMessageSequence(
                accountRepository.getById(1L).getAccountID(),
                accountRepository.getById(2L).getAccountID());

        assertEquals(expectedList, actualList);
    }

    @Transactional
    @Test
    public void getInterlocutorIdentifiers() {
        long[] expectedIdentifiers = {2, 3};

        long[] actualIdentifiers = messageRepository.getInterlocutorIdentifiers(accountRepository.getById(1L));

        assertEquals(Arrays.toString(expectedIdentifiers), Arrays.toString(actualIdentifiers));
    }

    @Transactional
    @Test
    public void compareMessages() throws InterruptedException {
        Account account1 = accountRepository.create(new Account(
                "Petrov", "Petr", "petrov@gmail.com", "PetrovPas", LocalDate.now(), "USER"));
        Account account2 = accountRepository.create(new Account(
                "Sidorov", "Sidr", "sidorov@gmail.com", "SidorovPas", LocalDate.now(), "USER"));
        Account account3 = accountRepository.create(new Account(
                "Ivanov", "Ivan", "ivanov@gmail.com", "IvanovPas", LocalDate.now(), "USER"));

        Message message1 = new Message(chatRoomRepository.getById(1L),
                account1, account2, "Hello1", LocalDateTime.now());
        messageRepository.create(message1);
        Thread.sleep(3000);
        Message message2 = new Message(chatRoomRepository.getById(2L),
                account3, account1, "Hello2", LocalDateTime.now());
        messageRepository.create(message2);
        Thread.sleep(3000);
        Message message3 = new Message(chatRoomRepository.getById(2L),
                account1, account3, "Hello3", LocalDateTime.now());
        messageRepository.create(message3);
        Thread.sleep(3000);
        System.out.println(message3.toString());
        Message message4 = new Message(chatRoomRepository.getById(1L),
                account2, account1, "Hello4", LocalDateTime.now());
        messageRepository.create(message4);
        System.out.println(message4.toString());

        ChatRoom accountOneTwoRoom = chatRoomRepository.getById(1L);
        ChatRoom accountOneThreeRoom = chatRoomRepository.getById(2L);

        List<Message> accountOneTwo = accountOneTwoRoom.getMessages();
        List<Message> accountOneThree = accountOneThreeRoom.getMessages();

        ChatDialogue first = new ChatDialogue(accountOneTwoRoom.getChatRoomID(), account1, account2, accountOneTwo);
        System.out.println(first.toString());
        ChatDialogue second = new ChatDialogue(accountOneThreeRoom.getChatRoomID(), account1, account3, accountOneThree);
        System.out.println(second.toString());

        System.out.println(first.compareTo(second));
    }
}
