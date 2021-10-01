package com.getjavajob.training.okhanzhin.socialnetwork.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = "relations")
public class Relationship {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false, nullable = false)
    private Long relationID;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "accountOneID", updatable = false, nullable = false)
    private Account accountOne;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "accountTwoID", updatable = false, nullable = false)
    private Account accountTwo;
    @Enumerated(EnumType.ORDINAL)
    private Status relationStatus;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "actionAccountID", nullable = false)
    private Account actionAccount;

    public Relationship(Account accountOne, Account accountTwo, Status relationStatus) {
        this.accountOne = accountOne;
        this.accountTwo = accountTwo;
        this.relationStatus = relationStatus;
        this.actionAccount = accountOne;
    }

    public Relationship(Account accountOne, Account accountTwo) {
        this.accountOne = accountOne;
        this.accountTwo = accountTwo;
        this.relationStatus = Status.PENDING;
        this.actionAccount = accountOne;
    }

    public Relationship() {
    }

    public Long getRelationID() {
        return relationID;
    }

    public void setRelationID(Long relationID) {
        this.relationID = relationID;
    }

    public Account getAccountOne() {
        return accountOne;
    }

    public void setAccountOne(Account accountOne) {
        this.accountOne = accountOne;
    }

    public Account getAccountTwo() {
        return accountTwo;
    }

    public void setAccountTwo(Account accountTwo) {
        this.accountTwo = accountTwo;
    }

    public Status getRelationStatus() {
        return relationStatus;
    }

    public void setRelationStatus(Status relationStatus) {
        this.relationStatus = relationStatus;
    }

    public Account getActionAccount() {
        return actionAccount;
    }

    public void setActionAccount(Account actionAccount) {
        this.actionAccount = actionAccount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Relationship)) return false;
        Relationship that = (Relationship) o;
        return (accountOne.getAccountID().equals(that.accountOne.getAccountID()) &&
                accountTwo.getAccountID().equals(that.accountTwo.getAccountID())) ||
                (accountOne.getAccountID().equals(that.accountTwo.getAccountID()) &&
                accountTwo.getAccountID().equals(that.accountOne.getAccountID()));
    }

    @Override
    public int hashCode() {
        return Objects.hash(relationID, accountOne.getAccountID(), accountTwo.getAccountID());
    }

    @Override
    public String toString() {
        return new StringBuilder().
                append("Relationship{").
                append("accountOneID='").append(accountOne.getAccountID()).append('\'').
                append(", accountTwoID='").append(accountTwo.getAccountID()).append('\'').
                append(", relationStatus='").append(relationStatus).append('\'').
                append(", actionAccountID='").append(actionAccount.getAccountID()).append('\'').
                append('}').toString();
    }

    public enum Status {

        PENDING(0), ACCEPTED(1), DECLINED(2), BLOCKED(3);

        private final byte status;
        private static final Member.Status[] STATUSES = new Member.Status[Member.Status.values().length];

        static {
            int i = 0;
            for (Member.Status statusValue : Member.Status.values()) {
                STATUSES[i] = statusValue;
                i++;
            }
        }

        Status(int status) {
            this.status = (byte) status;
        }

        public byte getStatus() {
            return status;
        }

        public static Member.Status fromValue(byte value) {
            return STATUSES[value];
        }
    }
}