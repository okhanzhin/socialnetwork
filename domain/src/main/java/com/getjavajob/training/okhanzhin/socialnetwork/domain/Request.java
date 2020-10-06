package com.getjavajob.training.okhanzhin.socialnetwork.domain;

import java.util.Objects;

public class Request {
    private long requestID;
    private long creatorID;
    private long recipientID;
    private String requestType;
    private byte requestStatus;

    public Request(long creatorID, long recipientID, String requestType, byte requestStatus) {
        this.creatorID = creatorID;
        this.recipientID = recipientID;
        this.requestType = requestType;
        this.requestStatus = requestStatus;
    }

    public Request() {
    }

    public long getRequestID() {
        return requestID;
    }

    public void setRequestID(long requestID) {
        this.requestID = requestID;
    }

    public long getCreatorID() {
        return creatorID;
    }

    public void setCreatorID(long creatorID) {
        this.creatorID = creatorID;
    }

    public long getRecipientID() {
        return recipientID;
    }

    public void setRecipientID(long recipientID) {
        this.recipientID = recipientID;
    }

    public String getRequestType() {
        return requestType;
    }

    public void setRequestType(String requestType) {
        this.requestType = requestType;
    }

    public byte getRequestStatus() {
        return requestStatus;
    }

    public void setRequestStatus(byte requestStatus) {
        this.requestStatus = requestStatus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Request request = (Request) o;

        return creatorID == request.creatorID && recipientID == request.recipientID && requestType.equals(request.requestType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(creatorID, recipientID, requestStatus);
    }

    @Override
    public String toString() {
        return new StringBuilder().
                append("Request{").
                append("creatorID='").append(creatorID).append('\'').
                append(", recipientID='").append(recipientID).append('\'').
                append(", requestType='").append(requestType).append('\'').
                append(", requestStatus='").append(requestStatus).append('\'').
                append('}').toString();
    }
}
