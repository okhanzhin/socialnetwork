package com.getjavajob.training.okhanzhin.socialnetwork.domain;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Account {
    private long accountID;
    private String surname;
    private String middlename;
    private String name;
    private String email;
    private String password;
    private LocalDate dateOfBirth;
    private String skype;
    private String icq;
    private String homeAddress;
    private String workAddress;
    private String addInfo;
    private LocalDate dateOfRegistration;
    private String role;
    private List<Phone> phones;
    private byte[] picture;

    public Account(String surname, String name, String email, String password) {
        this.surname = surname;
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public Account(String surname, String name, String email, String password, LocalDate dateOfRegistration) {
        this(surname, name, email, password);
        this.dateOfRegistration = dateOfRegistration;
    }

    public Account(long accountID, String surname, String name, String email, String password, LocalDate dateOfRegistration) {
        this(surname, name, email, password, dateOfRegistration);
        this.accountID = accountID;
    }

    public Account() {
    }

    public long getAccountID() {
        return accountID;
    }

    public void setAccountID(long id) {
        this.accountID = id;
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

    public List<Phone> getPhones() {
        return phones;
    }

    public void setPhones(List<Phone> phones) {
        this.phones = phones;
    }

    public byte[] getPicture() {
        return picture;
    }

    public void setPicture(byte[] picture) {
        this.picture = picture;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return accountID == account.accountID;
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
                append(" phones='").append(phones).append('\'').
                append('}').toString();
    }
}
