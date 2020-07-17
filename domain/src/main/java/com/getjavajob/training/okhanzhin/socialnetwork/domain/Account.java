package com.getjavajob.training.okhanzhin.socialnetwork.domain;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Account {
    private int accountID;
    private String surname;
    private String middlename;
    private String name;
    private String email;
    private String password;
    private LocalDate dateOfBirth;
    private String homePhone;
    private String workPhone;
    private String skype;
    private String icq;
    private String homeAddress;
    private String workAddress;
    private String addInfo;

    public Account(String surname, String name, String email, String password) {
        this.surname = surname;
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public Account() {
    }

    public int getAccountID() {
        return accountID;
    }

    public void setAccountID(int id) {
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

    public String getHomePhone() {
        return homePhone;
    }

    public void setHomePhone(String homePhone) {
        this.homePhone = homePhone;
    }

    public String getWorkPhone() {
        return workPhone;
    }

    public void setWorkPhone(String workPhone) {
        this.workPhone = workPhone;
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


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return accountID == account.accountID &&
                surname.equals(account.surname) &&
                name.equals(account.name) &&
                email.equals(account.email) &&
                password.equals(account.password) &&
                dateOfBirth.equals(account.dateOfBirth);
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
                append('}').toString();
    }
}
