package com.getjavajob.training.okhanzhin.socialnetwork.domain.request;

import com.getjavajob.training.okhanzhin.socialnetwork.domain.Account;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.util.Objects;

@Entity
@DiscriminatorValue("account")
public class AccountRequest extends Request {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "accTargetID", updatable = false)
    private Account accountTarget;

    public AccountRequest(Account source, Account accountTarget, Status status) {
        super(source, status);
        this.accountTarget = accountTarget;
    }

    public AccountRequest(Long requestID, Account source, Account accountTarget , Status requestStatus) {
        super(requestID, source, requestStatus);
        this.accountTarget = accountTarget;
    }

    public AccountRequest() {
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
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        AccountRequest that = (AccountRequest) o;
        return Objects.equals(accountTarget, that.accountTarget);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), accountTarget);
    }

    @Override
    public String toString() {
        return new StringBuilder().
                append("AccountRequest{").
                append("requestID='").append(super.getRequestID()).append('\'').
                append("sourceAccountID='").append(super.getSource().getAccountID()).append('\'').
                append("targetAccountID='").append(accountTarget.getAccountID()).append('\'').
                append("status='").append(super.getRequestStatus().name()).append('\'').
                append('}').toString();
    }
}
