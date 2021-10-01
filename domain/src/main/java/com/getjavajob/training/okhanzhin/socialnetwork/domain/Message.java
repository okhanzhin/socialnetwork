package com.getjavajob.training.okhanzhin.socialnetwork.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "messages")
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private Long messageID;
    @ManyToOne
    @JoinColumn(name = "chatRoomID", nullable = false)
    private ChatRoom chatRoom;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sourceID", nullable = false, updatable = false)
    private Account source;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "targetID", nullable = false, updatable = false)
    private Account target;
    private String content;
    private LocalDateTime publicationDate;

    public Message(ChatRoom chatRoom, Account source, Account target, String content) {
        this.chatRoom = chatRoom;
        this.source = source;
        this.target = target;
        this.content = content;
    }

    public Message(ChatRoom chatRoom, Account source, Account target,
                   String content, LocalDateTime publicationDate) {
        this(chatRoom, source, target, content);
        this.publicationDate = publicationDate;
    }

    public Message(Long messageID, ChatRoom chatRoom,  Account source, Account target,
                   String content, LocalDateTime publicationDate) {
        this(chatRoom, source, target, content, publicationDate);
        this.messageID = messageID;
    }

    public Message() {
    }

    public Long getMessageID() {
        return messageID;
    }

    public void setMessageID(Long messageID) {
        this.messageID = messageID;
    }

    public ChatRoom getChatRoom() {
        return chatRoom;
    }

    public void setChatRoom(ChatRoom chatRoom) {
        this.chatRoom = chatRoom;
    }

    public Account getSource() {
        return source;
    }

    public void setSource(Account source) {
        this.source = source;
    }

    public Account getTarget() {
        return target;
    }

    public void setTarget(Account target) {
        this.target = target;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(LocalDateTime publicationDateTime) {
        this.publicationDate = publicationDateTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Message)) return false;
        Message message = (Message) o;
        return chatRoom.equals(message.chatRoom) &&
                source.equals(message.source) &&
                target.equals(message.target);
    }

    @Override
    public int hashCode() {
        return Objects.hash(messageID, source.getAccountID(), target.getAccountID());
    }

    @Override
    public String toString() {
        return new StringBuilder().
                append("Message{").
                append("messageID=").append(messageID).append('\'').
                append(", chatRoomID=").append(chatRoom).append('\'').
                append(", sourceID=").append(source.getAccountID()).append('\'').
                append(", targetID=").append(target.getAccountID()).append('\'').
                append(", publicationDate=").append(publicationDate).append('\'').
                append('}').toString();
    }
}