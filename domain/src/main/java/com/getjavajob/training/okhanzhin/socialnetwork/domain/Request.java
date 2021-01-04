package com.getjavajob.training.okhanzhin.socialnetwork.domain;

import java.util.Objects;

public class Request {
    private long requestID;
    private long creatorID;
    private long recipientID;
    private String requestType;
    private Status status;

    public Request(long creatorID, long recipientID, String requestType, Status status) {
        this.creatorID = creatorID;
        this.recipientID = recipientID;
        this.requestType = requestType;
        this.status = status;
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

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
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
        return Objects.hash(creatorID, recipientID, status);
    }

    @Override
    public String toString() {
        return new StringBuilder().
                append("Request{").
                append("creatorID='").append(creatorID).append('\'').
                append(", recipientID='").append(recipientID).append('\'').
                append(", requestType='").append(requestType).append('\'').
                append(", requestStatus='").append(status).append('\'').
                append('}').toString();
    }

    public enum Status {

        UNCONFIRMED(0), ACCEPTED(1), DECLINED(2);

        private final byte status;
        private static Status[] statuses = new Status[Status.values().length];

        static {
            int i = 0;
            for (Status statusValue : Status.values()) {
                statuses[i] = statusValue;
                i++;
            }
        }

        Status(int status) {
            this.status = (byte) status;
        }

        public byte getStatus() {
            return status;
        }

        public static Status fromValue(byte value) {
            return statuses[value];
        }
    }
}
