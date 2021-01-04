package com.getjavajob.training.okhanzhin.socialnetwork.domain;

import java.util.Objects;

public class Phone {
    private long phoneID;
    private long accountID;
    private String phoneNumber;
    private String phoneType;

    public Phone(String phoneNumber, String phoneType) {
        this.phoneNumber = phoneNumber;
        this.phoneType = phoneType;
    }

    public Phone(long accountID, String phoneNumber, String phoneType) {
        this(phoneNumber, phoneType);
        this.accountID = accountID;
    }

    public Phone() {
    }

    public long getPhoneID() {
        return phoneID;
    }

    public void setPhoneID(long phoneID) {
        this.phoneID = phoneID;
    }

    public long getAccountID() {
        return accountID;
    }

    public void setAccountID(long accountID) {
        this.accountID = accountID;
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
        return accountID == phone.accountID &&
                Objects.equals(phoneNumber, phone.phoneNumber) &&
                Objects.equals(phoneType, phone.phoneType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(phoneID, accountID, phoneNumber, phoneType);
    }

    @Override
    public String toString() {
        return new StringBuilder().
                append("Phone{").
                append("id='").append(phoneID).append('\'').
                append(", accountID=").append(accountID).append('\'').
                append(", phoneNumber='").append(phoneNumber).append('\'').
                append(", phoneType='").append(phoneType).append('\'').
                append('}').toString();
    }
}
