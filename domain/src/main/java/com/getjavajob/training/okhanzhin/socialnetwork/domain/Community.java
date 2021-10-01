package com.getjavajob.training.okhanzhin.socialnetwork.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "communities")
public class Community implements Serializable {
    private static final long serialVersionUID = 4316846601455294955L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false, nullable = false)
    private Long commID;
    @Column(name = "commName", unique = true)
    private String communityName;
    private String commDescription;
    private LocalDate dateOfRegistration;
    private boolean picAttached;

    public Community(String communityName) {
        this.communityName = communityName;
    }

    public Community(String communityName, LocalDate dateOfRegistration) {
        this(communityName);
        this.dateOfRegistration = dateOfRegistration;
    }

    public Community(Long commID, String communityName, LocalDate dateOfRegistration) {
        this(communityName, dateOfRegistration);
        this.commID = commID;
    }

    public Community() {
    }

    public Long getCommID() {
        return commID;
    }

    public void setCommID(Long commID) {
        this.commID = commID;
    }

    public String getCommunityName() {
        return communityName;
    }

    public void setCommunityName(String groupName) {
        this.communityName = groupName;
    }

    public String getCommDescription() {
        return commDescription;
    }

    public void setCommDescription(String groupDescription) {
        this.commDescription = groupDescription;
    }

    public LocalDate getDateOfRegistration() {
        return dateOfRegistration;
    }

    public void setDateOfRegistration(LocalDate dateOfRegistration) {
        this.dateOfRegistration = dateOfRegistration;
    }

    public boolean isPicAttached() {
        return picAttached;
    }

    public void setPicAttached(boolean picAttached) {
        this.picAttached = picAttached;
    }

    public static class CommunityBuilder {
        private final Community community;

        public CommunityBuilder() {
            community = new Community();
        }

        public CommunityBuilder commID(Long id) {
            community.commID = id;
            return this;
        }

        public CommunityBuilder communityName(String communityName) {
            community.communityName = communityName;
            return this;
        }

        public CommunityBuilder commDescription(String commDescription) {
            community.commDescription = commDescription;
            return this;
        }

        public CommunityBuilder dateOfRegistration(LocalDate dateOfRegistration) {
            community.dateOfRegistration = dateOfRegistration;
            return this;
        }

        public CommunityBuilder picAttached(boolean picAttached) {
            community.picAttached = picAttached;
            return this;
        }

        public Community build() {
            return community;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Community)) return false;
        Community community = (Community) o;
        return commID.equals(community.commID) &&
                communityName.equals(community.communityName) &&
                dateOfRegistration.equals(community.dateOfRegistration);
    }

    @Override
    public int hashCode() {
        return Objects.hash(commID, communityName, dateOfRegistration);
    }

    @Override
    public String toString() {
        return new StringBuilder().
                append("Community{").
                append("id='").append(commID).append('\'').
                append(", communityName='").append(communityName).append('\'').
                append(", description='").append(commDescription).append('\'').
                append(", dateOfRegistration='").append(dateOfRegistration).append('\'').
                append(", picAttached='").append(picAttached).append('\'').
                append('}').toString();
    }
}
