package com.getjavajob.training.okhanzhin.socialnetwork.service;

import com.getjavajob.training.okhanzhin.socialnetwork.domain.Account;
import com.getjavajob.training.okhanzhin.socialnetwork.domain.dto.AccountTransfer;
import com.getjavajob.training.okhanzhin.socialnetwork.domain.dto.SecurityAccount;
import com.getjavajob.training.okhanzhin.socialnetwork.service.utils.TransferEntityConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.InputStream;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@Service
public class XmlMarshallingService {
    private final JAXBContext jaxbContext;
    private final AccountService accountService;
    private final TransferEntityConverter<Account, AccountTransfer> converter;

    @Autowired
    public XmlMarshallingService(AccountService accountService) throws JAXBException {
        this.accountService = accountService;
        this.jaxbContext = JAXBContext.newInstance(AccountTransfer.class);
        this.converter = new TransferEntityConverter<>();
    }

    public File marshallAccount(Account account) throws JAXBException {
        Marshaller marshaller = jaxbContext.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

        AccountTransfer transfer = converter.buildTransfer(account, AccountTransfer.class);

        File xmlFile = new File("account" + account.getAccountID() + ".xml");
        marshaller.marshal(transfer, xmlFile);

        return xmlFile;
    }

    public AccountTransfer unmarshallAccount(InputStream stream) throws JAXBException {
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

        return (AccountTransfer) unmarshaller.unmarshal(stream);
    }

    public String verifyXmlTransfer(AccountTransfer transfer) {
        StringBuilder logXmlFileError = new StringBuilder();
        SecurityAccount securityAccount = accountService.getSecurityAccountByEmail(transfer.getEmail());

        if (isNull(transfer.getEmail())) {
            logXmlFileError.append("Xml file doesn't contains mandatory field - email.");
        } else if (accountService.isEmailExist(transfer.getEmail()) && nonNull(securityAccount)) {
            if (!transfer.getAccountID().equals(securityAccount.getAccountID())) {
                logXmlFileError.append("Email in xml file already occupied by another account.");
            } else if (isNull(transfer.getPhones()) || transfer.getPhones().size() == 0) {
                logXmlFileError.append("Xml file must contain at least one embedded phone.");
            }
        }

        if (logXmlFileError.length() != 0) {
            logXmlFileError.append(" Please edit uploading file.");
        }

        return logXmlFileError.toString();
    }
}
