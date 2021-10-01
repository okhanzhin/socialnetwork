package com.getjavajob.training.okhanzhin.socialnetwork.domain.post;

import com.getjavajob.training.okhanzhin.socialnetwork.domain.Account;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@DiscriminatorValue("account")
public class AccountPost extends Post {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "accTargetID", updatable = false)
    private Account accountTarget;

    public AccountPost(Account source, Account accountTarget, String content) {
        super(source, content);
        this.accountTarget = accountTarget;
    }

    public AccountPost(Account source, Account accountTarget, String content, LocalDateTime publicationDate) {
        super(source, content, publicationDate);
        this.accountTarget = accountTarget;
    }

    public AccountPost(Long postID, Account source, Account accountTarget, String content, LocalDateTime publicationDate) {
        super(postID, source, content, publicationDate);
        this.accountTarget = accountTarget;
    }

    public AccountPost() {
        super();
    }

    public Account getAccountTarget() {
        return accountTarget;
    }

    public void setAccountTarget(Account accountTarget) {
        this.accountTarget = accountTarget;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AccountPost)) return false;
        if (!super.equals(o)) return false;
        AccountPost that = (AccountPost) o;
        return accountTarget.equals(that.accountTarget);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), accountTarget);
    }

    @Override
    public String toString() {
        return new StringBuilder().
                append("AccountPost{").
                append("postID=").append(super.getPostID()).append('\'').
                append(", sourceAccountID=").append(super.getSource().getAccountID()).append('\'').
                append(", targetAccountID=").append(accountTarget.getAccountID()).append('\'').
                append(", publicationDate=").append(super.getPublicationDate()).append('\'').
                append('}').toString();
    }
}
