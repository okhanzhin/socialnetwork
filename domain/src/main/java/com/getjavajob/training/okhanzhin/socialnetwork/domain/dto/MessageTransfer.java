package com.getjavajob.training.okhanzhin.socialnetwork.domain.dto;

import java.util.Objects;

public class MessageTransfer {
    private Long sourceID;
    private Long targetID;
    private String content;

    public MessageTransfer(Long sourceID, Long targetID, String content) {
        this.sourceID = sourceID;
        this.targetID = targetID;
        this.content = content;
    }

    public MessageTransfer() {
    }

    public Long getSourceID() {
        return sourceID;
    }

    public void setSourceID(Long sourceID) {
        this.sourceID = sourceID;
    }

    public Long getTargetID() {
        return targetID;
    }

    public void setTargetID(Long targetID) {
        this.targetID = targetID;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MessageTransfer)) return false;
        MessageTransfer that = (MessageTransfer) o;
        return sourceID.equals(that.sourceID) &&
                targetID.equals(that.targetID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sourceID, targetID);
    }

    @Override
    public String toString() {
        return new StringBuilder().
                append("MessageTransfer{").
                append("sourceID=").append(sourceID).append('\'').
                append(", targetID=").append(targetID).append('\'').
                append('}').toString();
    }
}
