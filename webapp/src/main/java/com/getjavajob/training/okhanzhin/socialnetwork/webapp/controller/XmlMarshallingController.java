package com.getjavajob.training.okhanzhin.socialnetwork.webapp.controller;

import com.getjavajob.training.okhanzhin.socialnetwork.domain.Account;
import com.getjavajob.training.okhanzhin.socialnetwork.domain.dto.AccountTransfer;
import com.getjavajob.training.okhanzhin.socialnetwork.domain.picture.Picture;
import com.getjavajob.training.okhanzhin.socialnetwork.service.AccountService;
import com.getjavajob.training.okhanzhin.socialnetwork.service.PictureService;
import com.getjavajob.training.okhanzhin.socialnetwork.service.XmlMarshallingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXBException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import static java.lang.Long.parseLong;

@Controller
@RequestMapping(value = "/account/{accountId}")
public class XmlMarshallingController {
    private static final String APPLICATION_XML = "application/xml";
    private static final String MULTIPART_FORM_DATA = "multipart/form-data";
    private static final Logger logger = LoggerFactory.getLogger(XmlMarshallingController.class);

    private final AccountService accountService;
    private final PictureService pictureService;
    private final XmlMarshallingService xmlMarshallingService;

    @Autowired
    public XmlMarshallingController(AccountService accountService,
                                    PictureService pictureService,
                                    XmlMarshallingService xmlMarshallingService) {
        this.accountService = accountService;
        this.pictureService = pictureService;
        this.xmlMarshallingService = xmlMarshallingService;
    }

    @RequestMapping(value = "/downloadXml", method = RequestMethod.GET, produces = APPLICATION_XML)
    @ResponseBody
    public void downloadXmlAccount(@PathVariable String accountId, @SessionAttribute(name = "homeAccount") Account homeAccount,
                                   HttpServletResponse response) throws JAXBException, IOException {
        File file;
        if (homeAccount.getAccountID() == parseLong(accountId)) {
            file = xmlMarshallingService.marshallAccount(homeAccount);
        } else {
            Account account = accountService.getByIdFetchPhones(parseLong(accountId));
            file = xmlMarshallingService.marshallAccount(account);
        }

        response.setContentType(APPLICATION_XML);
        response.setHeader("Content-Disposition", "attachment; filename=" + file.getName());
        response.setHeader("Content-Length", String.valueOf(file.length()));
        FileCopyUtils.copy(new FileInputStream(file), response.getOutputStream());
    }

    @RequestMapping(value = "/uploadXml", method = RequestMethod.POST, consumes = MULTIPART_FORM_DATA)
    public String uploadXmlAccount(@RequestParam(value = "xmlFile", required = false) MultipartFile xmlAccount,
                                   @PathVariable long accountId,
                                   @SessionAttribute("homeAccountId") long homeAccountId,
                                   RedirectAttributes redirectAttributes) throws JAXBException, IOException {
        logger.debug("Going to upload Xml-form account ID {} to update page.", accountId);

        String logXmlFileError;
        String redirectUrl = getRedirectUrl(homeAccountId, accountId);

        if (xmlAccount.isEmpty()) {
            logXmlFileError = "Bad xml file uploaded.";
            redirectAttributes.addFlashAttribute("logXmlFileError", logXmlFileError);

            return redirectUrl;
        }

        AccountTransfer transfer = xmlMarshallingService.unmarshallAccount(xmlAccount.getInputStream());
        transfer.setAccountID(accountId);
        logXmlFileError = xmlMarshallingService.verifyXmlTransfer(transfer);

        if (logXmlFileError.isEmpty()) {
            Picture picture = pictureService.getAccountPicture(transfer.getAccountID());
            transfer.setPicture(picture.getContent());

            redirectAttributes.addFlashAttribute("accountTransfer", transfer);

            return getRedirectUrl(homeAccountId, transfer.getAccountID());
        } else {
            redirectAttributes.addFlashAttribute("logXmlFileError", logXmlFileError);

            return redirectUrl;
        }
    }

    private String getRedirectUrl(long homeAccountId, long accountId) {
        String redirectUrl;

        if (homeAccountId == accountId) {
            redirectUrl = "redirect:/account/" + accountId + "/update";
        } else {
            redirectUrl = "redirect:/admin/" + accountId + "/updateForeign";
        }

        return redirectUrl;
    }
}
