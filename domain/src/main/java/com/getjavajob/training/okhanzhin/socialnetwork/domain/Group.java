package com.getjavajob.training.okhanzhin.socialnetwork.domain;

public class Group {
    private int groupID;
    private String groupName;
    private String groupDescription;

    public Group(String groupName) {
        this.groupName = groupName;
    }

    public Group() {
    }

    public int getGroupID() {
        return groupID;
    }

    public void setGroupID(int group_ID) {
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
                append("groupID='").append(groupID).append('\'').
                append(", groupName='").append(groupName).append('\'').
                append(", groupDescription='").append(groupDescription).append('\'').
                append('}').toString();
    }
}
