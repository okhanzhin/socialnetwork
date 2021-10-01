package com.getjavajob.training.okhanzhin.socialnetwork.service;

import com.getjavajob.training.okhanzhin.socialnetwork.dao.interfaces.ChatRoomRepository;
import com.getjavajob.training.okhanzhin.socialnetwork.dao.interfaces.MessageRepository;
import com.getjavajob.training.okhanzhin.socialnetwork.domain.Account;
import com.getjavajob.training.okhanzhin.socialnetwork.domain.ChatRoom;
import com.getjavajob.training.okhanzhin.socialnetwork.domain.Message;
import com.getjavajob.training.okhanzhin.socialnetwork.domain.dto.ChatDialogue;
import com.getjavajob.training.okhanzhin.socialnetwork.domain.dto.MessageTransfer;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ChatMessageServiceTest {
    private static final String ROLE_USER = "USER";

    @Mock
    private ChatRoomRepository chatRoomRepository;
    @Mock
    private MessageRepository messageRepository;

    @InjectMocks
    private ChatMessageService chatMessageService;

    private Account account;
    private Account account2;
    private ChatRoom chatRoom;
    private ChatRoom chatRoom2;
    private MessageTransfer transfer;
    private Message message;

    @Before
    public void init() {
        account = new Account(1L, "One", "One",
                "onee221@gmail.com", "onepass", LocalDate.now(), ROLE_USER);
        account2 = new Account(2L, "Two", "Two",
                "onee222@gmail.com", "onepass", LocalDate.now(), ROLE_USER);
        Account account3 = new Account(3L, "Three", "Three",
                "onee223@gmail.com", "onepass", LocalDate.now(), ROLE_USER);
        chatRoom = new ChatRoom(1L, account, account2);
        chatRoom2 = new ChatRoom(2L, account, account3);;
        transfer = new MessageTransfer(account.getAccountID(), account2.getAccountID(),
                "Some Text");
        message = new Message(chatRoom, account, account2, transfer.getContent(), LocalDateTime.now());
        chatRoom.setMessages(Collections.singletonList(message));
        Message message2 = new Message(chatRoom2, account, account3, transfer.getContent(), LocalDateTime.now());
        chatRoom2.setMessages(Collections.singletonList(message2));
    }

    @Test
    public void createChatRoom() {
        when(chatRoomRepository.create(chatRoom)).thenReturn(chatRoom);

        assertEquals(chatRoom, chatMessageService.createChatRoom(account, account2));
        verify(chatRoomRepository).create(chatRoom);
    }

    @Test
    public void getChatRoom() {
        when(chatRoomRepository.getByInterlocutorsFetchAttributes(account, account2)).thenReturn(chatRoom);

        assertEquals(chatRoom, chatMessageService.getChatRoom(account, account2));
        verify(chatRoomRepository).getByInterlocutorsFetchAttributes(account, account2);
    }

    @Test
    public void isChatRoomExist() {
        when(chatRoomRepository.isChatRoomExist(account, account2)).thenReturn(true);

        assertTrue(chatMessageService.isChatRoomExist(account, account2));
        verify(chatRoomRepository).isChatRoomExist(account, account2);
    }

    @Test
    public void deleteChatRoom() {
        chatMessageService.deleteChatRoom(1L);
        verify(chatRoomRepository).deleteById(1L);
    }

    @Test
    public void sendMessage() {
        when(messageRepository.create(message)).thenReturn(message);

        assertEquals(message, chatMessageService.sendMessage(chatRoom, account, account2, transfer));
        verify(messageRepository).create(message);
    }

    @Test
    public void getChatList() {
        List<ChatRoom> chatRooms = Arrays.asList(chatRoom2, chatRoom);

        ChatDialogue chatDialogue = new ChatDialogue(chatRoom.getChatRoomID(), chatRoom.getInterlocutorOne(),
                chatRoom.getInterlocutorTwo(), chatRoom.getMessages());
        ChatDialogue chatDialogue2 = new ChatDialogue(chatRoom2.getChatRoomID(), chatRoom2.getInterlocutorOne(),
                chatRoom2.getInterlocutorTwo(), chatRoom2.getMessages());
        List<ChatDialogue> chatDialogues = Arrays.asList(chatDialogue, chatDialogue2);

        when(chatRoomRepository.getAccountChatRooms(account)).thenReturn(chatRooms);

        assertEquals(chatDialogues, chatMessageService.getChatList(account));
        verify(chatRoomRepository).getAccountChatRooms(account);
    }
}
