package com.getjavajob.training.okhanzhin.socialnetwork.domain.request;

import com.getjavajob.training.okhanzhin.socialnetwork.domain.Account;
import com.getjavajob.training.okhanzhin.socialnetwork.domain.Community;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.util.Objects;

@Entity
@DiscriminatorValue("comm")
public class CommunityRequest extends Request {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "commTargetID", updatable = false)
    private Community communityTarget;

    public CommunityRequest(Account source, Community communityTarget, Status status) {
        super(source, status);
        this.communityTarget = communityTarget;
    }

    public CommunityRequest(Long requestID, Account source, Community communityTarget, Status requestStatus) {
        super(requestID, source, requestStatus);
        this.communityTarget = communityTarget;
    }

    public CommunityRequest() {
        super();
    }

    public Community getCommunityTarget() {
        return communityTarget;
    }

    public void setCommunityTarget(Community communityTarget) {
        this.communityTarget = communityTarget;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        CommunityRequest that = (CommunityRequest) o;
        return Objects.equals(communityTarget, that.communityTarget);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), communityTarget);
    }

    @Override
    public String toString() {
        return new StringBuilder().
                append("CommunityRequest{").
                append("requestID='").append(super.getRequestID()).append('\'').
                append("sourceAccountID='").append(super.getSource().getAccountID()).append('\'').
                append("targetCommID='").append(communityTarget.getCommID()).append('\'').
                append("status='").append(super.getRequestStatus().name()).append('\'').
                append('}').toString();
    }
}
