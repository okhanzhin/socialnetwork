package com.getjavajob.training.okhanzhin.socialnetwork.domain.post;

import com.getjavajob.training.okhanzhin.socialnetwork.domain.Account;
import com.getjavajob.training.okhanzhin.socialnetwork.domain.Community;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@DiscriminatorValue("comm")
public class CommunityPost extends Post {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "commTargetID", updatable = false)
    private Community communityTarget;

    public CommunityPost(Account source, Community communityTarget, String content) {
        super(source, content);
        this.communityTarget = communityTarget;
    }

    public CommunityPost(Account source, Community communityTarget, String content, LocalDateTime publicationDate) {
        super(source, content, publicationDate);
        this.communityTarget = communityTarget;
    }

    public CommunityPost(Long postID, Account source, Community communityTarget, String content, LocalDateTime publicationDate) {
        super(postID, source, content, publicationDate);
        this.communityTarget = communityTarget;
    }

    public CommunityPost() {
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
        if (!(o instanceof CommunityPost)) return false;
        if (!super.equals(o)) return false;
        CommunityPost communityPost = (CommunityPost) o;
        return communityTarget.equals(communityPost.communityTarget);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), communityTarget);
    }

    @Override
    public String toString() {
        return new StringBuilder().
                append("CommunityPost{").
                append("postID=").append(super.getPostID()).append('\'').
                append(", sourceAccountID=").append(super.getSource().getAccountID()).append('\'').
                append(", targetCommunityID=").append(communityTarget.getCommID()).append('\'').
                append(", publicationDate=").append(super.getPublicationDate()).append('\'').
                append('}').toString();
    }
}
