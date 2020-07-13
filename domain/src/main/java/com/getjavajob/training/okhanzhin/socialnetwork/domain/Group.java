package com.getjavajob.training.okhanzhin.socialnetwork.domain;

public class Group {
    private int group_ID;
    private String groupName;
    private String groupDescription;

    public Group(String groupName) {
        this.groupName = groupName;
    }

    public Group() {
    }

    public int getGroup_ID() {
        return group_ID;
    }

    public void setGroup_ID(int group_ID) {
        this.group_ID = group_ID;
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
                append("group_ID='").append(group_ID).append('\'').
                append(", groupName='").append(groupName).append('\'').
                append(", groupDescription='").append(groupDescription).append('\'').
                append('}').toString();
    }
}
