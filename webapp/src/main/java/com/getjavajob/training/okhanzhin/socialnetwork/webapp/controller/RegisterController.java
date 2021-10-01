package com.getjavajob.training.okhanzhin.socialnetwork.webapp.controller;

import com.getjavajob.training.okhanzhin.socialnetwork.domain.Account;
import com.getjavajob.training.okhanzhin.socialnetwork.domain.dto.AccountTransfer;
import com.getjavajob.training.okhanzhin.socialnetwork.service.AccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.support.ByteArrayMultipartFileEditor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Controller
@RequestMapping(value = "/account")
public class RegisterController {
    private static final Logger logger = LoggerFactory.getLogger(RegisterController.class);
    private static final String EMAIL = "email";
    private static final String MESSAGE = "message";

    private final AccountService accountService;
    private final BCryptPasswordEncoder encoder;

    @Autowired
    public RegisterController(AccountService accountService) {
        this.accountService = accountService;
        this.encoder = new BCryptPasswordEncoder(12);
    }

    @InitBinder
    protected void initBinder(ServletRequestDataBinder binder) {
        binder.registerCustomEditor(byte[].class,
                new ByteArrayMultipartFileEditor());
    }

    @RequestMapping(value = "/new", method = RequestMethod.GET)
    public ModelAndView showSignUpForm() {
        return new ModelAndView( "register", "accountTransfer", new AccountTransfer());
    }

    @RequestMapping(value = "/new", method = RequestMethod.POST, consumes = "multipart/form-data")
    public String signUp(@ModelAttribute("account") AccountTransfer accountTransfer,
                         @RequestParam(value = "homePhone", required = false) String homePhone,
                         @RequestParam(value = "workPhone", required = false) String workPhone,
                         @RequestParam(value = "picture", required = false) MultipartFile picture,
                         HttpServletRequest request,
                         HttpSession session) throws IOException {
        if (accountTransfer.getEmail().length() == 0 || accountTransfer.getPassword().length() == 0) {
            return "register";
        } else {
            accountTransfer.setPhones(accountService.populateTransferPhones(new String[]{homePhone}, new String[]{workPhone}));

            if (!picture.isEmpty()) {
                accountTransfer.setPicAttached(true);
                accountTransfer.setPicture(picture.getBytes());
            } else {
                accountTransfer.setPicAttached(false);
            }

            String hashedPassword = encoder.encode(accountTransfer.getPassword());
            accountTransfer.setPassword(hashedPassword);
            Account storedAccount = accountService.create(accountTransfer);

            session.setAttribute("homeAccountId", storedAccount.getAccountID());
            session.setAttribute("homeAccount", storedAccount);
            request.setAttribute(MESSAGE, "Successful registration. Please login.");
            request.setAttribute(EMAIL, storedAccount.getEmail());

            return "login";
        }
    }


    @RequestMapping(value = "/isEmailExist", method = RequestMethod.GET)
    @ResponseBody
    public boolean isEmailExist(@RequestParam("email") String email) {
        return accountService.isEmailExist(email);
    }
}