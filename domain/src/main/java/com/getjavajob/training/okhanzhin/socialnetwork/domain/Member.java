package com.getjavajob.training.okhanzhin.socialnetwork.domain;

import java.util.Objects;

public class Member {
    private long accountID;
    private long groupID;
    private byte memberStatus;

    public Member(long accountID, long groupID, byte memberStatus) {
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

    public byte getMemberStatus() {
        return memberStatus;
    }

    public void setMemberStatus(byte memberStatus) {
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

    //todo make as enum
    enum MemberStatus{OWNER(0), ADMIN(1), USER(2);
        int value;

        MemberStatus(int value) {
            this.value = value;
        }
    }
}
