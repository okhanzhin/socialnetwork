package com.getjavajob.training.okhanzhin.socialnetwork.webapp.controller;

import com.getjavajob.training.okhanzhin.socialnetwork.domain.Account;
import com.getjavajob.training.okhanzhin.socialnetwork.domain.Phone;
import com.getjavajob.training.okhanzhin.socialnetwork.domain.dto.AccountTransfer;
import com.getjavajob.training.okhanzhin.socialnetwork.service.AccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.support.ByteArrayMultipartFileEditor;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

import static java.lang.Long.parseLong;
import static java.util.Objects.nonNull;

@Controller
public class AdminFunctionController {
    private static final Logger logger = LoggerFactory.getLogger(AdminFunctionController.class);

    private final AccountService accountService;

    @Autowired
    public AdminFunctionController(AccountService accountService) {
        this.accountService = accountService;
    }

    @InitBinder
    protected void initBinder(ServletRequestDataBinder binder) {
        binder.registerCustomEditor(byte[].class,
                new ByteArrayMultipartFileEditor());
    }

    @RequestMapping(value = "/admin/{accountId}/update", method = RequestMethod.GET)
    public String showForeignUpdateForm(@ModelAttribute("accountTransfer") AccountTransfer transfer,
                                        @ModelAttribute("logXmlFileError") String logXmlFileError,
                                        @PathVariable String accountId, Model model, HttpServletRequest request) {
        Account editableAccount = accountService.getByIdFetchPhones(parseLong(accountId));
        logger.info("Starting Foreign Updating.");

        if (!editableAccount.getRole().equals("ADMIN")) {
            if (nonNull(transfer)) {
                logger.info("Updating transfer: {}", transfer.toString());
            }

            if (nonNull(transfer.getPhones())) {
                for (Phone phone : transfer.getPhones()) {
                    phone.setAccount(editableAccount);
                }
            }

            if (nonNull(transfer.getAccountID())) {
                model.addAttribute("accountTransfer", transfer);
            } else {
                if (!logXmlFileError.isEmpty()) {
                    logger.error("Xml-file errors: {}", logXmlFileError);
                    request.setAttribute("logXmlFileError", logXmlFileError);
                }
                model.addAttribute("accountTransfer", accountService.getTransfer(editableAccount));
            }
            model.addAttribute("editableAccount", editableAccount);
        } else {
            return "templates/access-denied";
        }

        return "userProfile-edit";
    }

    @RequestMapping(value = "/admin/{accountId}/update", method = RequestMethod.POST, consumes = "multipart/form-data")
    public String updateForeign(@ModelAttribute("accountTransfer") AccountTransfer accountTransfer,
                                @ModelAttribute("editableAccount") Account editableAccount,
                                @PathVariable String accountId,
                                @RequestParam(value = "workPhone", required = false) String[] workPhones,
                                @RequestParam(value = "homePhone", required = false) String[] homePhones,
                                @RequestParam(value = "picture", required = false) MultipartFile picture) throws IOException {
        accountTransfer.setPhones(accountService.populateTransferPhones(homePhones, workPhones));
        setUpTransferPicture(accountTransfer, picture);
        Account updatedAccount = accountService.update(accountTransfer);
        logger.info("Account ID {} has been successfully updated.", accountId);


        return "redirect:/account/" + updatedAccount.getAccountID();
    }

    private void setUpTransferPicture(AccountTransfer accountTransfer, MultipartFile picture) throws IOException {
        if (picture.getBytes().length != 0) {
            accountTransfer.setPicAttached(true);
            accountTransfer.setPicture(picture.getBytes());
        } else {
            accountTransfer.setPicAttached(false);
        }
    }

    @RequestMapping(value = "/account/{accountId}/switch", method = RequestMethod.GET)
    public String switchAccount(@PathVariable("accountId") String pathAccountId) {
        Account account = accountService.switchAccount(parseLong(pathAccountId));

        if (account.isEnabled()) {
            logger.info("Account with Id {} successfully unlocked.", pathAccountId);
        } else {
            logger.info("Account with Id {} successfully locked.", pathAccountId);
        }

        return "redirect:/account/" + pathAccountId;
    }

    @RequestMapping(value = "/account/{accountId}/makeAdmin", method = RequestMethod.GET)
    public String makeAdmin(@PathVariable("accountId") String pathAccountId) {
        accountService.makeAdmin(parseLong(pathAccountId));

        return "redirect:/account/" + pathAccountId;
    }
}
