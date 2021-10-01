package com.getjavajob.training.okhanzhin.socialnetwork.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlTransient;
import java.util.Objects;

@Entity
@Table(name = "phones")
@XmlAccessorType(XmlAccessType.FIELD)
public class Phone {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false, nullable = false)
    @XmlTransient
    private Long phoneID;
    @ManyToOne
    @JoinColumn(name = "accountID", nullable = false)
    @XmlTransient
    private Account account;
    private String phoneNumber;
    private String phoneType;

    public Phone(String phoneNumber, String phoneType) {
        this.phoneNumber = phoneNumber;
        this.phoneType = phoneType;
    }

    public Phone(Account account, String phoneNumber, String phoneType) {
        this(phoneNumber, phoneType);
        this.account = account;
    }

    public Phone(Long phoneID, Account account, String phoneNumber, String phoneType) {
        this(account, phoneNumber, phoneType);
        this.phoneID = phoneID;
    }

    public Phone() {
    }

    public Long getPhoneID() {
        return phoneID;
    }

    public void setPhoneID(Long phoneID) {
        this.phoneID = phoneID;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPhoneType() {
        return phoneType;
    }

    public void setPhoneType(String phoneType) {
        this.phoneType = phoneType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Phone phone = (Phone) o;
        return account.getAccountID().equals(phone.account.getAccountID()) &&
                Objects.equals(phoneNumber, phone.phoneNumber) &&
                Objects.equals(phoneType, phone.phoneType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(phoneID, account.getAccountID(), phoneNumber, phoneType);
    }

    @Override
    public String toString() {
        return new StringBuilder().
                append("Phone{").
                append("id='").append(phoneID).append('\'').
                append(", accountID=").append(account.getAccountID()).append('\'').
                append(", phoneNumber='").append(phoneNumber).append('\'').
                append(", phoneType='").append(phoneType).append('\'').
                append('}').toString();
    }
}
