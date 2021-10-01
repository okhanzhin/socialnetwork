package com.getjavajob.training.okhanzhin.socialnetwork.domain.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

public class CommunityTransfer implements Serializable {
    private static final long serialVersionUID = -3627391966086190917L;

    private Long commID;
    private String communityName;
    private String commDescription;
    private LocalDate dateOfRegistration;
    private boolean picAttached;
    private byte[] picture;

    public CommunityTransfer(String communityName) {
        this.communityName = communityName;
    }

    public CommunityTransfer(String communityName, LocalDate dateOfRegistration) {
        this(communityName);
        this.dateOfRegistration = dateOfRegistration;
    }

    public CommunityTransfer(Long commID, String communityName, LocalDate dateOfRegistration) {
        this(communityName, dateOfRegistration);
        this.commID = commID;
    }

    public CommunityTransfer() {
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

    public byte[] getPicture() {
        return picture;
    }

    public void setPicture(byte[] picture) {
        this.picture = picture;
    }

    public static class TransferBuilder {
        private final CommunityTransfer transfer;

        public TransferBuilder() {
            this.transfer = new CommunityTransfer();
        }

        public CommunityTransfer.TransferBuilder commID(long id) {
            transfer.commID = id;
            return this;
        }

        public CommunityTransfer.TransferBuilder communityName(String communityName) {
            transfer.communityName = communityName;
            return this;
        }

        public CommunityTransfer.TransferBuilder commDescription(String commDescription) {
            transfer.commDescription = commDescription;
            return this;
        }

        public CommunityTransfer.TransferBuilder dateOfRegistration(LocalDate dateOfRegistration) {
            transfer.dateOfRegistration = dateOfRegistration;
            return this;
        }

        public CommunityTransfer.TransferBuilder picAttached(boolean picAttached) {
            transfer.picAttached = picAttached;
            return this;
        }

        public CommunityTransfer.TransferBuilder picture(byte[] picture) {
            transfer.picture = picture;
            return this;
        }

        public CommunityTransfer build() {
            return transfer;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CommunityTransfer)) return false;
        CommunityTransfer commTransfer = (CommunityTransfer) o;
        return commID.equals(commTransfer.commID) &&
                communityName.equals(commTransfer.communityName) &&
                dateOfRegistration.equals(commTransfer.dateOfRegistration);
    }

    @Override
    public int hashCode() {
        return Objects.hash(commID, communityName, dateOfRegistration);
    }

    @Override
    public String toString() {
        return new StringBuilder().
                append("CommunityTransfer{").
                append("id='").append(commID).append('\'').
                append(", communityName='").append(communityName).append('\'').
                append(", description='").append(commDescription).append('\'').
                append(", dateOfRegistration='").append(dateOfRegistration).append('\'').
                append(", picAttached='").append(picAttached).append('\'').
                append('}').toString();
    }
}
