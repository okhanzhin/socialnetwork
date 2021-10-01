package com.getjavajob.training.okhanzhin.socialnetwork.dao;

import com.getjavajob.training.okhanzhin.socialnetwork.dao.interfaces.AccountRepository;
import com.getjavajob.training.okhanzhin.socialnetwork.dao.interfaces.ChatRoomRepository;
import com.getjavajob.training.okhanzhin.socialnetwork.dao.interfaces.MessageRepository;
import com.getjavajob.training.okhanzhin.socialnetwork.domain.Account;
import com.getjavajob.training.okhanzhin.socialnetwork.domain.ChatRoom;
import com.getjavajob.training.okhanzhin.socialnetwork.domain.Message;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@DataJpaTest
public class ChatRoomRepositoryTest extends AbstractRepositoryTest {

    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private ChatRoomRepository chatRoomRepository;
    @Autowired
    private MessageRepository messageRepository;

    @Transactional
    @Test
    public void create() {
        Account interlocutorOne = accountRepository.getById(1L);
        Account interlocutorTwo = accountRepository.getById(2L);

        ChatRoom exceptedChatRoom = new ChatRoom(interlocutorOne, interlocutorTwo);

        int rowsCount = 2;
        assertEquals(rowsCount, chatRoomRepository.getAll().size());
        rowsCount++;

        ChatRoom actualChatRoom = chatRoomRepository.create(new ChatRoom(interlocutorOne, interlocutorTwo));
        exceptedChatRoom.setChatRoomID(actualChatRoom.getChatRoomID());

        assertEquals(rowsCount, chatRoomRepository.getAll().size());
        assertEquals(exceptedChatRoom, actualChatRoom);
    }

    @Transactional
    @Test
    public void update() {
        Account interlocutorOne = accountRepository.getById(1L);
        Account interlocutorTwo = accountRepository.getById(2L);

        ChatRoom exceptedChatRoom = new ChatRoom(interlocutorOne, interlocutorTwo);
        ChatRoom actualChatRoom = chatRoomRepository.getById(1L);

        exceptedChatRoom.setChatRoomID(actualChatRoom.getChatRoomID());
        exceptedChatRoom.setMessages(actualChatRoom.getMessages());

        Message newMessage = new Message(actualChatRoom, interlocutorOne, interlocutorTwo, "Test", LocalDateTime.now());
        messageRepository.create(newMessage);

        chatRoomRepository.update(actualChatRoom);

        assertEquals(messageRepository.getMessageSequence(1L, 2L).size(), actualChatRoom.getMessages().size());
    }

    @Transactional
    @Test
    public void deleteById() {
        Account interlocutorOne = accountRepository.getById(1L);
        Account interlocutorTwo = accountRepository.getById(2L);

        chatRoomRepository.deleteById(1L);

        assertEquals(new ArrayList<>().size(), messageRepository.getMessageSequence(1L, 2L).size());
    }

    @Transactional
    @Test
    public void getByIdFetchAttributes() {
        Account interlocutorOne = accountRepository.getById(1L);
        Account interlocutorTwo = accountRepository.getById(2L);
        List<Message> messageSequence = messageRepository.getMessageSequence(1L, 2L);

        ChatRoom exceptedChatRoom = new ChatRoom(interlocutorOne, interlocutorTwo);
        exceptedChatRoom.setMessages(messageSequence);

        ChatRoom actualChatRoom = chatRoomRepository.getByIdFetchAttributes(1L);
        exceptedChatRoom.setChatRoomID(actualChatRoom.getChatRoomID());

        assertEquals(exceptedChatRoom, actualChatRoom);
    }

    @Transactional
    @Test
    public void getByInterlocutors() {
        Account interlocutorOne = accountRepository.getById(1L);
        Account interlocutorTwo = accountRepository.getById(2L);

        ChatRoom exceptedChatRoom = new ChatRoom(interlocutorOne, interlocutorTwo);
        ChatRoom actualChatRoom = chatRoomRepository.getByInterlocutors(interlocutorTwo, interlocutorOne);
        exceptedChatRoom.setChatRoomID(actualChatRoom.getChatRoomID());

        assertEquals(exceptedChatRoom, actualChatRoom);
    }

    @Transactional
    @Test
    public void getByInterlocutorsFetchAttributes() {
        Account interlocutorOne = accountRepository.getById(1L);
        Account interlocutorTwo = accountRepository.getById(2L);
        List<Message> messageSequence = messageRepository.getMessageSequence(1L, 2L);

        ChatRoom exceptedChatRoom = new ChatRoom(interlocutorOne, interlocutorTwo);
        exceptedChatRoom.setMessages(messageSequence);

        ChatRoom actualChatRoom = chatRoomRepository.getByInterlocutorsFetchAttributes(interlocutorOne, interlocutorTwo);
        exceptedChatRoom.setChatRoomID(actualChatRoom.getChatRoomID());

        assertEquals(exceptedChatRoom, actualChatRoom);
    }

    @Transactional
    @Test
    public void getAccountChatRooms() {
        Account interlocutorOne = accountRepository.getById(1L);
        Account interlocutorTwo = accountRepository.getById(4L);

        assertEquals(2, chatRoomRepository.getAccountChatRooms(interlocutorOne).size());
        assertEquals(0, chatRoomRepository.getAccountChatRooms(interlocutorTwo).size());
    }

    @Transactional
    @Test
    public void isChatRoomActive() {
        Account interlocutorOne = accountRepository.getById(1L);
        Account interlocutorTwo = accountRepository.getById(2L);
        Account interlocutorThree = accountRepository.getById(3L);

        assertTrue(chatRoomRepository.isChatRoomExist(interlocutorOne, interlocutorTwo));
        assertFalse(chatRoomRepository.isChatRoomExist(interlocutorTwo, interlocutorThree));
    }
}
