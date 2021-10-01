package com.getjavajob.training.okhanzhin.socialnetwork.domain.dto;

import java.util.List;
import java.util.Objects;

public class Notification {
    private long notificationID;

    private String content;
    private List<String> recipientEmails;

    public Notification(String content, List<String> recipientEmails) {
        this.content = content;
        this.recipientEmails = recipientEmails;
    }

    public Notification() {
    }

    public long getNotificationID() {
        return notificationID;
    }

    public void setNotificationID(long notificationID) {
        this.notificationID = notificationID;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<String> getRecipientEmails() {
        return recipientEmails;
    }

    public void setRecipientEmails(List<String> recipientEmails) {
        this.recipientEmails = recipientEmails;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Notification)) return false;
        Notification that = (Notification) o;
        return notificationID == that.notificationID;
    }

    @Override
    public int hashCode() {
        return Objects.hash(notificationID);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Notification{");
        sb.append("notificationId=").append(notificationID);
        sb.append(", content='").append(content).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
