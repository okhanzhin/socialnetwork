package com.getjavajob.training.okhanzhin.socialnetwork.domain.picture;

import com.getjavajob.training.okhanzhin.socialnetwork.domain.Account;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "acc_pictures")
public class AccountPicture extends Picture implements Serializable {
    private static final long serialVersionUID = -2957836497971530558L;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "accountID")
    private Account account;

    public AccountPicture(Long picID, Account account, byte[] content) {
        super(picID, content);
        this.account = account;
    }

    public AccountPicture() {
    }

    public AccountPicture(byte[] content) {
        super(content);
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AccountPicture)) return false;
        if (!super.equals(o)) return false;
        AccountPicture that = (AccountPicture) o;
        return Objects.equals(account.getAccountID(), that.account.getAccountID());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), account.getAccountID());
    }

    @Override
    public String toString() {
        return new StringBuilder().
                append("AccountPicture{").
                append("accountID=").append(account.getAccountID()).
                append('}').toString();
    }
}
