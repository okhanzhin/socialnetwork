package com.getjavajob.training.okhanzhin.socialnetwork.webapp.filter;

import com.getjavajob.training.okhanzhin.socialnetwork.service.AccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Component
public class AccountAvailableValidationFilter extends GenericFilterBean {
    private static final Logger logger = LoggerFactory.getLogger(AccountAvailableValidationFilter.class);
    private static final Set<String> ignoredUrls = new HashSet<>(Collections.singletonList("/displayPicture"));
    private static final String ANONYMOUS_USER = "anonymousUser";

    private final AccountService accountService;

    @Autowired
    public AccountAvailableValidationFilter(AccountService accountService) {
        this.accountService = accountService;
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain filterChain)
            throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        logger.info(request.getRequestURI());

        if (authentication != null && !isIgnoredUrl(request)) {
            boolean isAccountAvailable = true;

            if (!authentication.getName().equals(ANONYMOUS_USER)) {
                isAccountAvailable = accountService.isAccountAvailable(authentication.getName());
            }

            logger.info("Is account Available: " + isAccountAvailable);

            if (isAccountAvailable) {
                filterChain.doFilter(req, resp);
            } else {
                logger.warn("Blocked account {} attempted to login.", authentication.getName());

                response.sendRedirect("/logout");
            }
        } else {
            filterChain.doFilter(req, resp);
        }
    }

    private boolean isIgnoredUrl(HttpServletRequest request) {
        String uri = request.getRequestURI();

        return (uri.endsWith("png") || uri.endsWith("css") ||
                uri.endsWith("jpg") || uri.endsWith("js") ||
                ignoredUrls.contains(uri));
    }
}
