package com.getjavajob.training.okhanzhin.socialnetwork.domain;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "chat_rooms")
@NamedEntityGraph(name = "graph.ChatRoom.interlocutors.messages",
        attributeNodes = {@NamedAttributeNode("interlocutorOne"),
                          @NamedAttributeNode("interlocutorTwo"),
                          @NamedAttributeNode("messages")})
public class ChatRoom implements Serializable {
    private static final long serialVersionUID = -1992669203071834416L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false, nullable = false)
    private Long chatRoomID;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "interlocutorOneID", updatable = false, nullable = false)
    private Account interlocutorOne;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "interlocutorTwoID", updatable = false, nullable = false)
    private Account interlocutorTwo;
    @OneToMany(mappedBy = "chatRoom", fetch = FetchType.LAZY,
            cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Message> messages = new ArrayList<>();

    public ChatRoom(Account interlocutorOne, Account interlocutorTwo) {
        this.interlocutorOne = interlocutorOne;
        this.interlocutorTwo = interlocutorTwo;
    }

    public ChatRoom(Long chatRoomID, Account interlocutorOne, Account interlocutorTwo) {
        this(interlocutorOne, interlocutorTwo);
        this.chatRoomID = chatRoomID;
    }

    public ChatRoom() {
    }

    public Long getChatRoomID() {
        return chatRoomID;
    }

    public void setChatRoomID(Long chatRoomID) {
        this.chatRoomID = chatRoomID;
    }

    public Account getInterlocutorOne() {
        return interlocutorOne;
    }

    public void setInterlocutorOne(Account interlocutorOne) {
        this.interlocutorOne = interlocutorOne;
    }

    public Account getInterlocutorTwo() {
        return interlocutorTwo;
    }

    public void setInterlocutorTwo(Account interlocutorTwo) {
        this.interlocutorTwo = interlocutorTwo;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ChatRoom)) return false;
        ChatRoom chatRoom = (ChatRoom) o;
        return interlocutorOne.equals(chatRoom.interlocutorOne) &&
                interlocutorTwo.equals(chatRoom.interlocutorTwo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(chatRoomID, interlocutorOne, interlocutorTwo);
    }

    @Override
    public String toString() {
        return new StringBuilder().
                append("ChatRoom{").
                append("chatRoomID=").append(chatRoomID).append('\'').
                append(", interlocutorOne=").append(interlocutorOne).append('\'').
                append(", interlocutorTwo=").append(interlocutorTwo).append('\'').
                append('}').toString();
    }
}
