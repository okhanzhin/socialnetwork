package com.getjavajob.training.okhanzhin.socialnetwork.webapp.controller;

import com.getjavajob.training.okhanzhin.socialnetwork.domain.Account;
import com.getjavajob.training.okhanzhin.socialnetwork.domain.Phone;
import com.getjavajob.training.okhanzhin.socialnetwork.domain.dto.AccountTransfer;
import com.getjavajob.training.okhanzhin.socialnetwork.service.AccountService;
import com.getjavajob.training.okhanzhin.socialnetwork.service.PictureService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.support.ByteArrayMultipartFileEditor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Arrays;

import static java.lang.Long.parseLong;
import static java.util.Objects.nonNull;

@Controller
@RequestMapping(value = "/account/{accountId}")
public class UpdateAccountController {
    private static final Logger logger = LoggerFactory.getLogger(UpdateAccountController.class);

    private final AccountService accountService;
    private final PictureService pictureService;

    @Autowired
    public UpdateAccountController(AccountService accountService, PictureService pictureService) {
        this.accountService = accountService;
        this.pictureService = pictureService;
    }

    @InitBinder
    protected void initBinder(ServletRequestDataBinder binder) {
        binder.registerCustomEditor(byte[].class,
                new ByteArrayMultipartFileEditor());
    }

    @RequestMapping(value = "/update", method = RequestMethod.GET)
    public String showUpdateForm(@ModelAttribute("accountTransfer") AccountTransfer transfer,
                                 @ModelAttribute("logXmlFileError") String logXmlFileError,
                                 @ModelAttribute("successNotification") String successNotification,
                                 @SessionAttribute("homeAccountId") long homeAccountId,
                                 @PathVariable String accountId, Model model, HttpServletRequest request) {
        if (parseLong(accountId) == homeAccountId) {
            Account editableAccount = accountService.getByIdFetchPhones(parseLong(accountId));

            logger.info("Starting Home Updating.");

            if (nonNull(transfer)) {
                logger.info("Updating transfer: {}", transfer.toString());
            }

            if (nonNull(transfer.getPhones())) {
                for (Phone phone : transfer.getPhones()) {
                    phone.setAccount(editableAccount);
                }
            }

            if (!successNotification.isEmpty()) {
                request.setAttribute("successNotification", successNotification);
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

            return "homeProfile-edit";
        } else {
            throw new AccessDeniedException("Account " + homeAccountId + " attempted to access foreign edit page");
        }
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST, consumes = "multipart/form-data")
    public String update(@ModelAttribute("accountTransfer") AccountTransfer accountTransfer,
                         @ModelAttribute("editableAccount") Account editableAccount,
                         @PathVariable String accountId,
                         @SessionAttribute(name = "homeAccount") Account homeAccount,
                         @RequestParam(value = "workPhone", required = false) String[] workPhones,
                         @RequestParam(value = "homePhone", required = false) String[] homePhones,
                         @RequestParam(value = "picture", required = false) MultipartFile picture,
                         HttpSession session) throws IOException {
        accountTransfer.setPhones(accountService.populateTransferPhones(homePhones, workPhones));
        setUpTransferPicture(accountTransfer, picture);
        Account updatedAccount = accountService.update(accountTransfer);

        logger.info("Account ID {} has been successfully updated.", accountId);

        if (homeAccount.getAccountID() == parseLong(accountId)) {
            session.setAttribute("homeAccount", updatedAccount);
        }

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

    @RequestMapping(value = "/deletePicture", method = RequestMethod.GET)
    public String deleteAccountPicture(@PathVariable String accountId,
                                       @SessionAttribute("homeAccountId") long homeAccountId,
                                       HttpSession session) {
        long accountID = parseLong(accountId);

        if (accountID == homeAccountId) {
            pictureService.removeAccountPicture(accountID);
            session.setAttribute("homeAccount", accountService.getByIdFetchPhones(accountID));
        }

        return "redirect:/account/" + accountID;
    }
}
