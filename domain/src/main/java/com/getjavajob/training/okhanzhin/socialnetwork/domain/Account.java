package com.getjavajob.training.okhanzhin.socialnetwork.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "accounts")
@NamedEntityGraph(name = "graph.Account.phones",
        attributeNodes = @NamedAttributeNode("phones"))
public class Account implements Serializable {
    private static final long serialVersionUID = -1075046152134874540L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false, nullable = false)
    private Long accountID;
    @Column(nullable = false)
    private String surname;
    private String middlename;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false, unique = true)
    private String email;
    @Column(nullable = false)
    private String password;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate dateOfBirth;
    private String skype;
    private String icq;
    private String homeAddress;
    private String workAddress;
    private String addInfo;
    private LocalDate dateOfRegistration;
    @Column(nullable = false)
    private String role;
    @Column(nullable = false)
    private boolean enabled;
    @JsonIgnore
    @OneToMany(mappedBy = "account", fetch = FetchType.LAZY,
            cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Phone> phones = new ArrayList<>();
    private boolean picAttached;

    public Account(String surname, String name, String email, String password) {
        this.surname = surname;
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public Account(String surname, String name, String email, String password,
                   LocalDate dateOfRegistration, String role) {
        this(surname, name, email, password);
        this.dateOfRegistration = dateOfRegistration;
        this.role = role;
    }

    public Account(Long accountID, String surname, String name, String email, String password,
                   LocalDate dateOfRegistration, String role) {
        this(surname, name, email, password, dateOfRegistration, role);
        this.accountID = accountID;
    }

    public Account() {
    }

    public Long getAccountID() {
        return accountID;
    }

    public void setAccountID(Long accountID) {
        this.accountID = accountID;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getMiddlename() {
        return middlename;
    }

    public void setMiddlename(String middlename) {
        this.middlename = middlename;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getSkype() {
        return skype;
    }

    public void setSkype(String skype) {
        this.skype = skype;
    }

    public String getIcq() {
        return icq;
    }

    public void setIcq(String icq) {
        this.icq = icq;
    }

    public String getHomeAddress() {
        return homeAddress;
    }

    public void setHomeAddress(String homeAddress) {
        this.homeAddress = homeAddress;
    }

    public String getWorkAddress() {
        return workAddress;
    }

    public void setWorkAddress(String workAddress) {
        this.workAddress = workAddress;
    }

    public String getAddInfo() {
        return addInfo;
    }

    public void setAddInfo(String addInfo) {
        this.addInfo = addInfo;
    }

    public LocalDate getDateOfRegistration() {
        return dateOfRegistration;
    }

    public void setDateOfRegistration(LocalDate dateOfRegistration) {
        this.dateOfRegistration = dateOfRegistration;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public List<Phone> getPhones() {
        return phones;
    }

    public void setPhones(List<Phone> phones) {
        this.phones = phones;
    }

    public boolean isPicAttached() {
        return picAttached;
    }

    public void setPicAttached(boolean picAttached) {
        this.picAttached = picAttached;
    }

    public static class AccountBuilder {
        private final Account account;

        public AccountBuilder() {
            account = new Account();
        }

        public AccountBuilder accountID(Long id) {
            account.accountID = id;
            return this;
        }

        public AccountBuilder surname(String surname) {
            account.surname = surname;
            return this;
        }

        public AccountBuilder middlename(String middlename) {
            account.middlename = middlename;
            return this;
        }

        public AccountBuilder name(String name) {
            account.name = name;
            return this;
        }

        public AccountBuilder email(String email) {
            account.email = email;
            return this;
        }

        public AccountBuilder password(String password) {
            account.password = password;
            return this;
        }

        public AccountBuilder dateOfBirth(LocalDate dateOfBirth) {
            account.dateOfBirth = dateOfBirth;
            return this;
        }

        public AccountBuilder skype(String skype) {
            account.skype = skype;
            return this;
        }

        public AccountBuilder icq(String icq) {
            account.icq = icq;
            return this;
        }

        public AccountBuilder homeAddress(String homeAddress) {
            account.homeAddress = homeAddress;
            return this;
        }

        public AccountBuilder workAddress(String workAddress) {
            account.workAddress = workAddress;
            return this;
        }

        public AccountBuilder addInfo(String addInfo) {
            account.addInfo = addInfo;
            return this;
        }

        public AccountBuilder dateOfRegistration(LocalDate dateOfRegistration) {
            account.dateOfRegistration = dateOfRegistration;
            return this;
        }

        public AccountBuilder role(String role) {
            account.role = role;
            return this;
        }

        public Account.AccountBuilder enabled(boolean enabled) {
            account.enabled = enabled;
            return this;
        }

        public AccountBuilder phones(List<Phone> phones) {
            account.phones = phones;
            return this;
        }

        public AccountBuilder picAttached(boolean picAttached) {
            account.picAttached = picAttached;
            return this;
        }

        public Account build() {
            return account;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Account)) return false;
        Account account = (Account) o;
        return accountID.equals(account.accountID) &&
                surname.equals(account.surname) &&
                Objects.equals(middlename, account.middlename) &&
                name.equals(account.name) &&
                email.equals(account.email) &&
                password.equals(account.password) &&
                Objects.equals(dateOfBirth, account.dateOfBirth) &&
                Objects.equals(skype, account.skype) &&
                Objects.equals(icq, account.icq);
    }

    @Override
    public int hashCode() {
        return Objects.hash(accountID, surname, name, email, password, dateOfBirth);
    }

    @Override
    public String toString() {
        return new StringBuilder().
                append("Account{").
                append("id='").append(accountID).append('\'').
                append(", surname='").append(surname).append('\'').
                append(", name='").append(name).append('\'').
                append(", email='").append(email).append('\'').
                append(", password='").append(password).append('\'').
                append(", dateOfBirth='").append(dateOfBirth).append('\'').
                append(", homeAddress='").append(homeAddress).append('\'').
                append(", workAddress='").append(workAddress).append('\'').
                append(", dateOfRegistration='").append(dateOfRegistration).append('\'').
                append(", role='").append(role).append('\'').
                append(", enabled='").append(enabled).append('\'').
                append(" phones='").append(phones).append('\'').
                append(" picAttached='").append(picAttached).append('\'').
                append('}').toString();
    }
}