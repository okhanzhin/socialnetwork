package com.getjavajob.training.okhanzhin.socialnetwork.service;

import com.getjavajob.training.okhanzhin.socialnetwork.dao.interfaces.AccountRepository;
import com.getjavajob.training.okhanzhin.socialnetwork.dao.interfaces.PictureRepository;
import com.getjavajob.training.okhanzhin.socialnetwork.domain.Account;
import com.getjavajob.training.okhanzhin.socialnetwork.domain.Community;
import com.getjavajob.training.okhanzhin.socialnetwork.domain.Phone;
import com.getjavajob.training.okhanzhin.socialnetwork.domain.dto.AccountTransfer;
import com.getjavajob.training.okhanzhin.socialnetwork.domain.dto.SecurityAccount;
import com.getjavajob.training.okhanzhin.socialnetwork.domain.picture.AccountPicture;
import com.getjavajob.training.okhanzhin.socialnetwork.service.utils.TransferEntityConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Service
public class AccountService {
    private static final String EMPTY_PHONE_INPUT = "";
    private static final String ADMIN = "ADMIN";
    private static final Logger logger = LoggerFactory.getLogger(AccountService.class);

    private final AccountRepository accountRepository;
    private final PictureRepository pictureRepository;

    @Autowired
    public AccountService(AccountRepository accountRepository, PictureRepository pictureRepository) {
        this.accountRepository = accountRepository;
        this.pictureRepository = pictureRepository;
    }

    public Account getEntity(AccountTransfer transfer) {
        TransferEntityConverter<AccountTransfer, Account> converter = new TransferEntityConverter<>();

        return Objects.requireNonNull(converter.buildEntity(transfer, Account.class));
    }


    public AccountTransfer getTransfer(Account account) {
        TransferEntityConverter<Account, AccountTransfer> converter = new TransferEntityConverter<>();
        AccountTransfer transfer = Objects.requireNonNull(converter.buildTransfer(account, AccountTransfer.class));
        if (account.isPicAttached()) {
            transfer.setPicture(pictureRepository.getAccountPicture(account).getContent());
        }

        return transfer;
    }

    @Transactional
    public Account create(AccountTransfer transfer) {
        if (!transfer.isPicAttached()) {
            transfer.setPicture(new byte[0]);
        }

        Account account = getEntity(transfer);
        account.setDateOfRegistration(LocalDate.now());

        if (account.getPhones() != null) {
            for (Phone phone : account.getPhones()) {
                phone.setAccount(account);
            }
        }

        AccountPicture picture = new AccountPicture(transfer.getPicture());
        picture.setAccount(account);

        pictureRepository.create(picture);
        logger.info("Account ID {} has been created.", account.getAccountID());

        return account;
    }

    @Transactional
    public Account update(AccountTransfer transfer) {
        Account storedAccount = accountRepository.getByIdFetchPhones(transfer.getAccountID());

        if (transfer.isPicAttached()) {
            AccountPicture storedPicture = (AccountPicture) pictureRepository.getAccountPicture(storedAccount);
            if (!Arrays.equals(storedPicture.getContent(), transfer.getPicture())) {
                storedPicture.setContent(transfer.getPicture());
                pictureRepository.update(storedPicture);
            }
        }

        Account updatedAccount = updateFromTransfer(storedAccount, transfer);
        logger.info("Account ID {} has been updated.", updatedAccount.getAccountID());

        return accountRepository.update(updatedAccount);
    }

    private Account updateFromTransfer(Account account, AccountTransfer transfer) {
        account.setSurname(transfer.getSurname());
        account.setMiddlename(transfer.getMiddlename());
        account.setName(transfer.getName());
        account.setEmail(transfer.getEmail());
        account.setDateOfBirth(transfer.getDateOfBirth());
        account.setSkype(transfer.getSkype());
        account.setIcq(transfer.getIcq());
        account.setHomeAddress(transfer.getHomeAddress());
        account.setWorkAddress(transfer.getWorkAddress());
        account.setAddInfo(transfer.getAddInfo());
        account.setRole(transfer.getRole());
        account.getPhones().clear();
        if (transfer.getPhones() != null) {
            account.getPhones().addAll(populateEntityPhones(account, transfer.getPhones()));
        }
        if (transfer.isPicAttached()) {
            account.setPicAttached(transfer.isPicAttached());
        }

        return account;
    }

    @Transactional
    public void delete(Account account) {
        accountRepository.deleteById(account.getAccountID());
    }

    public Account getById(long id) {
        return accountRepository.getById(id);
    }

    @Transactional
    public Account getByIdFetchPhones(long id) {
        return accountRepository.getByIdFetchPhones(id);
    }

    public Account getByEmail(String email) {
        return accountRepository.getByEmail(email);
    }

    @Transactional
    public Account getByEmailFetchPhones(String email) {
        return accountRepository.getByEmailFetchPhones(email);
    }

    public SecurityAccount getSecurityAccountByEmail(String email) {
        Account account = accountRepository.getByEmail(email);

        return new SecurityAccount(account.getAccountID(), account.getEmail(), account.getPassword(), account.isEnabled());
    }

    public List<Phone> populateEntityPhones(Account account, List<Phone> transferPhones) {
        for (Phone transferPhone : transferPhones) {
            transferPhone.setAccount(account);
        }

        return transferPhones;
    }

    public List<Phone> populateTransferPhones(String[] homePhones, String[] workPhones) {
        List<Phone> phones = new ArrayList<>();
        addPhones(homePhones, phones, "home");
        addPhones(workPhones, phones, "work");

        return phones;
    }

    private void addPhones(String[] phoneStrings, List<Phone> phones, String type) {
        if (phoneStrings != null) {
            for (String phone : phoneStrings) {
                if (!phone.equals(EMPTY_PHONE_INPUT)) {
                    phones.add(new Phone(phone, type));
                }
            }
        }
    }

    public List<Account> getAccountsListForGroup(Community community) {
        return accountRepository.getAccountsForCommunity(community);
    }

    public boolean isAccountAvailable(String email) {
        return accountRepository.isAccountAvailable(email);
    }

    public Boolean isEmailExist(String email) {
        List<String> existingEmails = accountRepository.getExistingEmails();

        return existingEmails.contains(email);
    }

    @Transactional
    @Secured("ROLE_ADMIN")
    public Account switchAccount(long id) {
        Account account = getById(id);
        account.setEnabled(!account.isEnabled());

        return accountRepository.update(account);
    }

    @Transactional
    @Secured("ROLE_ADMIN")
    public void makeAdmin(long id) {
        Account account = getById(id);

        if (!account.getRole().equals(ADMIN)) {
            account.setRole(ADMIN);
        } else {
            throw new IllegalStateException("Account already have admin permission.");
        }
    }

    @Transactional
    public void updatePassword(String password, Account account) {
        accountRepository.updatePassword(password, account.getAccountID());
    }
}
