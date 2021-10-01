package com.getjavajob.training.okhanzhin.socialnetwork.domain.dto;

import com.getjavajob.training.okhanzhin.socialnetwork.domain.Account;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

public class SecurityAccount implements UserDetails {
    private static final long serialVersionUID = -3318491281816695566L;

    private Long accountID;
    private String username;
    private String password;
    private List<SimpleGrantedAuthority> authorities;
    private boolean enabled;

    public SecurityAccount(Long accountID, String username, String password, boolean enabled) {
        this.accountID = accountID;
        this.username = username;
        this.password = password;
        this.enabled = enabled;
    }

    public SecurityAccount() {
    }

    public Long getAccountID() {
        return accountID;
    }

    public void setAccountID(Long accountID) {
        this.accountID = accountID;
    }

    @Override
    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    @Override
    public boolean isAccountNonExpired() {
        return enabled;
    }

    @Override
    public boolean isAccountNonLocked() {
        return enabled;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return enabled;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SecurityAccount)) return false;
        SecurityAccount that = (SecurityAccount) o;
        return accountID.equals(that.accountID) &&
                username.equals(that.username) &&
                password.equals(that.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(accountID, username, password);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("SecurityAccount{");
        sb.append("id=").append(accountID);
        sb.append(", email='").append(username).append('\'');
        sb.append(", password='").append(password).append('\'');
        sb.append('}');
        return sb.toString();
    }

    public static UserDetails fromAccount(Account account) {
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_" + account.getRole()));
        return new org.springframework.security.core.userdetails.User(
                account.getEmail(),
                account.getPassword(),
                account.isEnabled(),
                account.isEnabled(),
                account.isEnabled(),
                account.isEnabled(),
                authorities
        );
    }
}
