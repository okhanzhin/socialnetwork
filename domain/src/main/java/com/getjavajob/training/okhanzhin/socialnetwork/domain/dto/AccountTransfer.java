package com.getjavajob.training.okhanzhin.socialnetwork.domain.dto;

import  com.getjavajob.training.okhanzhin.socialnetwork.domain.Phone;
import org.springframework.format.annotation.DateTimeFormat;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@XmlRootElement(name = "account")
@XmlAccessorType(XmlAccessType.FIELD)
public class AccountTransfer implements Serializable {
    private static final long serialVersionUID = -7824592821810504915L;

    @XmlTransient
    private Long accountID;
    private String surname;
    private String middlename;
    private String name;
    private String email;
    @XmlTransient
    private String password;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate dateOfBirth;
    private String skype;
    private String icq;
    private String homeAddress;
    private String workAddress;
    private String addInfo;
    private LocalDate dateOfRegistration;
    private String role;
    private boolean enabled;
    @XmlElementWrapper(name = "phones")
    @XmlElement(name = "phone")
    private List<Phone> phones;
    private boolean picAttached;
    @XmlTransient
    private byte[] picture;

    public AccountTransfer(String surname, String name, String email, String password) {
        this.surname = surname;
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public AccountTransfer(String surname, String name, String email, String password,
                   LocalDate dateOfRegistration, String role) {
        this(surname, name, email, password);
        this.dateOfRegistration = dateOfRegistration;
        this.role = role;
    }

    public AccountTransfer(Long accountID, String surname, String name, String email, String password,
                   LocalDate dateOfRegistration, String role) {
        this(surname, name, email, password, dateOfRegistration, role);
        this.accountID = accountID;
    }

    public AccountTransfer() {
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

    public byte[] getPicture() {
        return picture;
    }

    public void setPicture(byte[] picture) {
        this.picture = picture;
    }

    public static class TransferBuilder {
        private final AccountTransfer transfer;

        public TransferBuilder() {
            transfer = new AccountTransfer();
        }

        public AccountTransfer.TransferBuilder accountID(Long id) {
            transfer.accountID = id;
            return this;
        }

        public AccountTransfer.TransferBuilder surname(String surname) {
            transfer.surname = surname;
            return this;
        }

        public AccountTransfer.TransferBuilder middlename(String middlename) {
            transfer.middlename = middlename;
            return this;
        }

        public AccountTransfer.TransferBuilder name(String name) {
            transfer.name = name;
            return this;
        }

        public AccountTransfer.TransferBuilder email(String email) {
            transfer.email = email;
            return this;
        }

        public AccountTransfer.TransferBuilder password(String password) {
            transfer.password = password;
            return this;
        }

        public AccountTransfer.TransferBuilder dateOfBirth(LocalDate dateOfBirth) {
            transfer.dateOfBirth = dateOfBirth;
            return this;
        }

        public AccountTransfer.TransferBuilder skype(String skype) {
            transfer.skype = skype;
            return this;
        }

        public AccountTransfer.TransferBuilder icq(String icq) {
            transfer.icq = icq;
            return this;
        }

        public AccountTransfer.TransferBuilder homeAddress(String homeAddress) {
            transfer.homeAddress = homeAddress;
            return this;
        }

        public AccountTransfer.TransferBuilder workAddress(String workAddress) {
            transfer.workAddress = workAddress;
            return this;
        }

        public AccountTransfer.TransferBuilder addInfo(String addInfo) {
            transfer.addInfo = addInfo;
            return this;
        }

        public AccountTransfer.TransferBuilder dateOfRegistration(LocalDate dateOfRegistration) {
            transfer.dateOfRegistration = dateOfRegistration;
            return this;
        }

        public AccountTransfer.TransferBuilder role(String role) {
            transfer.role = role;
            return this;
        }

        public AccountTransfer.TransferBuilder enabled(boolean enabled) {
            transfer.enabled = enabled;
            return this;
        }

        public AccountTransfer.TransferBuilder phones(List<Phone> phones) {
            transfer.phones = phones;
            return this;
        }

        public AccountTransfer.TransferBuilder picAttached(boolean picAttached) {
            transfer.picAttached = picAttached;
            return this;
        }

        public AccountTransfer.TransferBuilder picture(byte[] picture) {
            transfer.picture = picture;
            return this;
        }

        public AccountTransfer build() {
            return transfer;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AccountTransfer)) return false;
        AccountTransfer accTransfer = (AccountTransfer) o;
        return accountID.equals(accTransfer.accountID) &&
                surname.equals(accTransfer.surname) &&
                Objects.equals(middlename, accTransfer.middlename) &&
                name.equals(accTransfer.name) &&
                email.equals(accTransfer.email) &&
                password.equals(accTransfer.password) &&
                Objects.equals(dateOfBirth, accTransfer.dateOfBirth) &&
                Objects.equals(skype, accTransfer.skype) &&
                Objects.equals(icq, accTransfer.icq);
    }

    @Override
    public int hashCode() {
        return Objects.hash(accountID, surname, name, email, password, dateOfBirth);
    }

    @Override
    public String toString() {
        return new StringBuilder().
                append("AccountTransfer{").
                append("id='").append(accountID).append('\'').
                append(", surname='").append(surname).append('\'').
                append(", middlename='").append(middlename).append('\'').
                append(", name='").append(name).append('\'').
                append(", email='").append(email).append('\'').
                append(", password='").append(password).append('\'').
                append(", dateOfBirth='").append(dateOfBirth).append('\'').
                append(", skype='").append(skype).append('\'').
                append(", icq='").append(icq).append('\'').
                append(", homeAddress='").append(homeAddress).append('\'').
                append(", workAddress='").append(workAddress).append('\'').
                append(", addInfo='").append(addInfo).append('\'').
                append(", dateOfRegistration='").append(dateOfRegistration).append('\'').
                append(", role='").append(role).append('\'').
                append(", enabled='").append(enabled).append('\'').
                append(" picAttached='").append(picAttached).append('\'').
                append('}').toString();
    }
}
