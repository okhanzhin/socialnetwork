package com.getjavajob.training.okhanzhin.socialnetwork.domain;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

public class Group {
    private long groupID;
    private String groupName;
    private String groupDescription;
    private LocalDate dateOfRegistration;
    private byte[] picture;

    public Group(String groupName) {
        this.groupName = groupName;
    }

    public Group() {
    }

    public long getGroupID() {
        return groupID;
    }

    public void setGroupID(long group_ID) {
        this.groupID = group_ID;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getGroupDescription() {
        return groupDescription;
    }

    public void setGroupDescription(String groupDescription) {
        this.groupDescription = groupDescription;
    }

    public LocalDate getDateOfRegistration() {
        return dateOfRegistration;
    }

    public void setDateOfRegistration(LocalDate dateOfRegistration) {
        this.dateOfRegistration = dateOfRegistration;
    }

    public byte[] getPicture() {
        return picture;
    }

    public void setPicture(byte[] picture) {
        this.picture = picture;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Group group = (Group) o;

        return groupName.equals(group.groupName);
    }

    @Override
    public int hashCode() {
        return groupName != null ? groupName.hashCode() : 0;
    }

    @Override
    public String toString() {
        return new StringBuilder().
                append("Group{").
                append("id='").append(groupID).append('\'').
                append(", groupName='").append(groupName).append('\'').
                append(", groupDescription='").append(groupDescription).append('\'').
                append(", dateOfRegistration='").append(dateOfRegistration).append('\'').
                append('}').toString();
    }
}
