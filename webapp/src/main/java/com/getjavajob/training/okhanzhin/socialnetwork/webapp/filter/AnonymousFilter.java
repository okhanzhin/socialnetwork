package com.getjavajob.training.okhanzhin.socialnetwork.webapp.filter;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class AnonymousFilter extends GenericFilterBean {
    private static final Set<String> anonymousUrls = new HashSet<>(Arrays.asList("/", "/account/new"));

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain filterChain)
            throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;

        if (isAnonymousUrl(request) && isAuthenticated()) {
            response.sendRedirect("/account/" + request.getSession().getAttribute("homeAccountId"));
        } else {
            filterChain.doFilter(req, resp);
        }
    }

    private boolean isAnonymousUrl(HttpServletRequest request) {
        return anonymousUrls.contains(request.getRequestURI());
    }

    private boolean isAuthenticated() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        return (authentication != null) && authentication.isAuthenticated();
    }
}
