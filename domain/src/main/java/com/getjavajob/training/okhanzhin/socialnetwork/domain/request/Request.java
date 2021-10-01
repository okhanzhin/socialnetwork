package com.getjavajob.training.okhanzhin.socialnetwork.domain.request;

import com.getjavajob.training.okhanzhin.socialnetwork.domain.Account;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = "requests")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "requestType", discriminatorType = DiscriminatorType.STRING)
public class Request {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long requestID;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sourceID", nullable = false, updatable = false)
    private Account source;
    @Enumerated(EnumType.ORDINAL)
    private Status requestStatus;

    public Request(Account source, Status requestStatus) {
        this.source = source;
        this.requestStatus = requestStatus;
    }

    public Request(Long requestID, Account source, Status requestStatus) {
        this(source, requestStatus);
        this.requestID = requestID;
    }

    public Request() {
    }

    public Long getRequestID() {
        return requestID;
    }

    public void setRequestID(Long requestID) {
        this.requestID = requestID;
    }

    public Account getSource() {
        return source;
    }

    public void setSource(Account source) {
        this.source = source;
    }

    public Status getRequestStatus() {
        return requestStatus;
    }

    public void setRequestStatus(Status status) {
        this.requestStatus = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Request request = (Request) o;
        return source.getAccountID().equals(request.source.getAccountID()) &&
                requestStatus == request.requestStatus;
    }

    @Override
    public int hashCode() {
        return Objects.hash(requestID, source.getAccountID(), requestStatus);
    }

    public enum Status {

        UNCONFIRMED(0), ACCEPTED(1), DECLINED(2);

        private final byte status;
        private static final Status[] statuses = new Status[Status.values().length];

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
