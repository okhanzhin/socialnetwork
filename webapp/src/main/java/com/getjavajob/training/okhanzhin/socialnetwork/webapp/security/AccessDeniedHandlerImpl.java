package com.getjavajob.training.okhanzhin.socialnetwork.webapp.security;

import com.getjavajob.training.okhanzhin.socialnetwork.domain.Account;
import com.getjavajob.training.okhanzhin.socialnetwork.service.AccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class AccessDeniedHandlerImpl implements AccessDeniedHandler {
    private static final Logger logger = LoggerFactory.getLogger(AccessDeniedHandlerImpl.class);

    private final AccountService accountService;

    @Autowired
    public AccessDeniedHandlerImpl(AccountService accountService) {
        this.accountService = accountService;
    }

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException e)
            throws IOException, ServletException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Account account = accountService.getByEmail(authentication.getName());

        logger.warn("Account: {} {} with ID {} attempted to access the protected URL: {}",
                account.getSurname(), account.getName(), account.getAccountID(), request.getRequestURI());

        logger.info(request.getContextPath());

        request.getRequestDispatcher("/WEB-INF/jsp/templates/access-denied.jsp").forward(request, response);
    }
}
