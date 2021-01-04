package com.getjavajob.training.okhanzhin.socialnetwork.domain;

import java.util.Objects;

public class Member {
    private long accountID;
    private long groupID;
    private Status memberStatus;

    public Member(long accountID, long groupID, Status memberStatus) {
        this.accountID = accountID;
        this.groupID = groupID;
        this.memberStatus = memberStatus;
    }

    public Member() {
    }

    public long getAccountID() {
        return accountID;
    }

    public void setAccountID(long accountID) {
        this.accountID = accountID;
    }

    public long getGroupID() {
        return groupID;
    }

    public void setGroupID(long groupID) {
        this.groupID = groupID;
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

        return accountID == member.accountID && groupID == member.groupID;
    }

    @Override
    public int hashCode() {
        return Objects.hash(accountID, groupID, memberStatus);
    }

    @Override
    public String toString() {
        return new StringBuilder().
                append("Member{").
                append("accountID='").append(accountID).append('\'').
                append(", groupID='").append(groupID).append('\'').
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
