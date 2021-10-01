package com.getjavajob.training.okhanzhin.socialnetwork.webapp.controller;

import com.getjavajob.training.okhanzhin.socialnetwork.domain.Account;
import com.getjavajob.training.okhanzhin.socialnetwork.service.AccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

import static java.lang.Long.parseLong;

@Controller
@RequestMapping(value = "/account")
public class ChangePasswordController {
    private static final Logger logger = LoggerFactory.getLogger(ChangePasswordController.class);
    private static final String SUCCESS_MESSAGE = "Password has been successfully updated";

    private final AccountService accountService;
    private final BCryptPasswordEncoder encoder;

    @Autowired
    public ChangePasswordController(AccountService accountService) {
        this.accountService = accountService;
        this.encoder = new BCryptPasswordEncoder(12);
    }

    @RequestMapping(value = "/{accountId}/changePassword", method = RequestMethod.POST)
    public String changePassword(@PathVariable String accountId,
                                 @SessionAttribute(name = "homeAccount") Account homeAccount,
                                 @RequestParam(value = "new-password") String newPassword,
                                 HttpSession session, RedirectAttributes redirectAttributes) {
        accountService.updatePassword(encoder.encode(newPassword), homeAccount);
        session.setAttribute("homeAccount", accountService.getByIdFetchPhones(parseLong(accountId)));
        redirectAttributes.addFlashAttribute("successNotification", SUCCESS_MESSAGE);
        logger.info(SUCCESS_MESSAGE + "for account ID - {}, email - {}",
                homeAccount.getAccountID(), homeAccount.getEmail());

        return "redirect:/account/" + accountId + "/update";
    }

    @RequestMapping(value = "/isStoredPasswordCorrect", method = RequestMethod.GET)
    @ResponseBody
    public boolean isStoredPasswordCorrect(@SessionAttribute("homeAccount") Account homeAccount,
                                           @RequestParam(value = "storedPassword") String storedPassword) {
        return encoder.matches(storedPassword, homeAccount.getPassword());
    }
}
