package com.getjavajob.training.okhanzhin.socialnetwork.webapp.security;

import com.getjavajob.training.okhanzhin.socialnetwork.domain.Account;
import com.getjavajob.training.okhanzhin.socialnetwork.service.AccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Component
public class AuthenticationSuccessHandlerImpl implements AuthenticationSuccessHandler {
    private static final Logger logger = LoggerFactory.getLogger(AuthenticationSuccessHandlerImpl.class);

    private final AccountService accountService;

    @Autowired
    public AuthenticationSuccessHandlerImpl(AccountService accountService) {
        this.accountService = accountService;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException {
        User principal = (User) authentication.getPrincipal();
        HttpSession session = request.getSession();
        Account homeAccount = accountService.getByEmailFetchPhones(principal.getUsername());

        session.setAttribute("homeAccount", homeAccount);
        session.setAttribute("homeAccountId", homeAccount.getAccountID());

        logger.info("Account ID {} is successfully authorized.", homeAccount.getAccountID());

        response.sendRedirect("/account/" + homeAccount.getAccountID());
    }
}