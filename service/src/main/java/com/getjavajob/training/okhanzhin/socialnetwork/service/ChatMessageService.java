package com.getjavajob.training.okhanzhin.socialnetwork.service;

import com.getjavajob.training.okhanzhin.socialnetwork.dao.interfaces.ChatRoomRepository;
import com.getjavajob.training.okhanzhin.socialnetwork.dao.interfaces.MessageRepository;
import com.getjavajob.training.okhanzhin.socialnetwork.domain.Account;
import com.getjavajob.training.okhanzhin.socialnetwork.domain.ChatRoom;
import com.getjavajob.training.okhanzhin.socialnetwork.domain.Message;
import com.getjavajob.training.okhanzhin.socialnetwork.domain.dto.ChatDialogue;
import com.getjavajob.training.okhanzhin.socialnetwork.domain.dto.MessageTransfer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class ChatMessageService {
    private static final Logger logger = LoggerFactory.getLogger(ChatMessageService.class);

    private final MessageRepository messageRepository;
    private final ChatRoomRepository chatRoomRepository;

    @Autowired
    public ChatMessageService(MessageRepository messageRepository,
                              ChatRoomRepository chatRoomRepository) {
        this.messageRepository = messageRepository;
        this.chatRoomRepository = chatRoomRepository;
    }

    @Transactional
    public ChatRoom createChatRoom(Account interlocutorOne, Account interlocutorTwo) {
        return chatRoomRepository.create(new ChatRoom(interlocutorOne, interlocutorTwo));
    }

    public ChatRoom getChatRoom(Account interlocutorOne, Account interlocutorTwo) {
        return chatRoomRepository.getByInterlocutorsFetchAttributes(interlocutorOne, interlocutorTwo);
    }

    public Boolean isChatRoomExist(Account interlocutorOne, Account interlocutorTwo) {
        return chatRoomRepository.isChatRoomExist(interlocutorOne, interlocutorTwo);
    }

    @Transactional
    public void deleteChatRoom(long chatRoomID) {
        chatRoomRepository.deleteById(chatRoomID);
    }

    @Transactional
    public Message sendMessage(ChatRoom chatRoom, Account source, Account interlocutor, MessageTransfer transfer) {
        Message message = new Message(chatRoom, source, interlocutor,
                transfer.getContent(), LocalDateTime.now());

        return messageRepository.create(message);
    }

    public List<ChatDialogue> getChatList(Account profile) {
        List<ChatRoom> accountChatRooms = chatRoomRepository.getAccountChatRooms(profile);
        List<ChatDialogue> dialogues = new ArrayList<>();

        for (ChatRoom chatRoom : accountChatRooms) {
            ChatDialogue chatDialogue = configChatDialogue(chatRoom, profile);
            dialogues.add(chatDialogue);
        }

        Collections.reverse(dialogues);

        return dialogues;
    }

    public ChatDialogue getChatDialogue(ChatRoom chatRoom, Account profileAccount) {
        return configChatDialogue(chatRoom, profileAccount);
    }

    private ChatDialogue configChatDialogue(ChatRoom chatRoom, Account profileAccount) {
        ChatDialogue chatDialogue;

        if (profileAccount.getAccountID().equals(chatRoom.getInterlocutorOne().getAccountID())) {
            chatDialogue = new ChatDialogue(chatRoom.getChatRoomID(),
                    chatRoom.getInterlocutorOne(),
                    chatRoom.getInterlocutorTwo(),
                    chatRoom.getMessages());
        } else {
            chatDialogue = new ChatDialogue(chatRoom.getChatRoomID(),
                    chatRoom.getInterlocutorTwo(),
                    chatRoom.getInterlocutorOne(),
                    chatRoom.getMessages());
        }

        return chatDialogue;
    }
}
