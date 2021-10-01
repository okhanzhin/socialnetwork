package com.getjavajob.training.okhanzhin.socialnetwork.domain.dto;

import com.getjavajob.training.okhanzhin.socialnetwork.domain.Account;
import com.getjavajob.training.okhanzhin.socialnetwork.domain.Message;

import java.util.List;
import java.util.Objects;

public class ChatDialogue implements Comparable<ChatDialogue> {

    private Long chatRoomID;
    private Account profile;
    private Account interlocutor;
    private List<Message> messages;
    private Message lastMessage;

    public ChatDialogue(Long chatRoomID, Account profile, Account interlocutor, List<Message> messages) {
        this.chatRoomID = chatRoomID;
        this.profile = profile;
        this.interlocutor = interlocutor;
        this.messages = messages;
        if (!messages.isEmpty()) {
            this.lastMessage = messages.get(messages.size() - 1);
        } else {
            this.lastMessage = null;
        }
    }

    public Long getChatRoomID() {
        return chatRoomID;
    }

    public void setChatRoomID(Long chatRoomID) {
        this.chatRoomID = chatRoomID;
    }

    public Account getProfile() {
        return profile;
    }

    public void setProfile(Account profile) {
        this.profile = profile;
    }

    public Account getInterlocutor() {
        return interlocutor;
    }

    public void setInterlocutor(Account interlocutor) {
        this.interlocutor = interlocutor;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }

    public Message getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(Message lastMessage) {
        this.lastMessage = lastMessage;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChatDialogue that = (ChatDialogue) o;
        return Objects.equals(profile, that.profile) &&
                Objects.equals(interlocutor, that.interlocutor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(profile, interlocutor);
    }

    @Override
    public int compareTo(ChatDialogue o) {
        return getLastMessage().getPublicationDate().compareTo(o.getLastMessage().getPublicationDate());
    }

    @Override
    public String toString() {
        return new StringBuilder().
                append("ChatDialog{").
                append("chatRoomID=").append(chatRoomID).append('\'').
                append(", profile=").append(profile).append('\'').
                append(", interlocutor=").append(interlocutor).append('\'').
                append(", lastMessage=").append(lastMessage.getContent()).append('\'').
                append('}').toString();
    }
}
