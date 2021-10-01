package com.getjavajob.training.okhanzhin.socialnetwork.domain;

import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "members")
public class Member {

    @Embeddable
    public static class MemberPK implements Serializable {
        private static final long serialVersionUID = -5725430286343838172L;

        protected long accountID;
        protected long commID;

        public MemberPK() {
        }

        public MemberPK(long accountID, long commID) {
            this.accountID = accountID;
            this.commID = commID;
        }

        public long getAccountID() {
            return accountID;
        }

        public void setAccountID(long accountID) {
            this.accountID = accountID;
        }

        public long getCommID() {
            return commID;
        }

        public void setCommID(long commID) {
            this.commID = commID;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            MemberPK memberPK = (MemberPK) o;
            return accountID == memberPK.accountID &&
                    commID == memberPK.commID;
        }

        @Override
        public int hashCode() {
            return Objects.hash(accountID, commID);
        }
    }

    @EmbeddedId
    private final MemberPK memberPK = new MemberPK();
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "accountID", insertable = false, updatable = false)
    private Account account;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "commID", insertable = false, updatable = false)
    private Community community;
    @Enumerated(EnumType.ORDINAL)
    private Status memberStatus;

    public Member(Account account, Community community, Status memberStatus) {
        this.account = account;
        this.community = community;
        this.memberStatus = memberStatus;

        this.memberPK.accountID = account.getAccountID();
        this.memberPK.commID = community.getCommID();
    }

    public Member() {
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public Community getCommunity() {
        return community;
    }

    public void setCommunity(Community community) {
        this.community = community;
    }

    public Status getMemberStatus() {
        return memberStatus;
    }

    public void setMemberStatus(Status memberStatus) {
        this.memberStatus = memberStatus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Member member = (Member) o;

        return account.getAccountID().equals(member.getAccount().getAccountID()) &&
                community.getCommID().equals(member.getCommunity().getCommID());
    }

    @Override
    public int hashCode() {
        return Objects.hash(account.getAccountID(), community.getCommID(), memberStatus);
    }

    @Override
    public String toString() {
        return new StringBuilder().
                append("Member{").
                append("accountID='").append(account.getAccountID()).append('\'').
                append(", commID='").append(community.getCommID()).append('\'').
                append(", memberStatus='").append(memberStatus).append('\'').
                append('}').toString();
    }

    public enum Status {

        OWNER(0), MODERATOR(1), USER(2);

        private final byte status;
        private static final Status[] STATUSES = new Status[Status.values().length];

        static {
            int i = 0;
            for (Status statusValue : Status.values()) {
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

        public static Status fromValue(byte value) {
            return STATUSES[value];
        }
    }
}
