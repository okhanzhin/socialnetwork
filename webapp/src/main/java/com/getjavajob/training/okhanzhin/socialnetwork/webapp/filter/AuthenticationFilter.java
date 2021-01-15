package com.getjavajob.training.okhanzhin.socialnetwork.webapp.filter;

import com.getjavajob.training.okhanzhin.socialnetwork.domain.Account;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class AuthenticationFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;
                HttpSession session = request.getSession();

        Account account = (Account) session.getAttribute("account");
        String uri = request.getRequestURI();
        System.out.println(uri);

        if (uri.equals("/login") || uri.equals("/") || uri.equals("/register")) {
            if (account != null) {
                response.sendRedirect(request.getContextPath() + "/account/" + account.getAccountID());
            } else {
                if (isEmailCookieExist(request)) {
                    response.sendRedirect(request.getContextPath() + "/account/setup");
                } else {
                    chain.doFilter(req, resp);
                }
            }
        } else {
            if (account != null) {
                chain.doFilter(req, resp);
            } else {
                if (isEmailCookieExist(request)) {
                    response.sendRedirect(request.getContextPath() + "/account/setup");
                } else {
                    response.sendRedirect(request.getContextPath() + "/login");
                }
            }
        }
    }

    @Override
    public void destroy() {

    }

    private boolean isEmailCookieExist(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("email".equals(cookie.getName())) {
                    return true;
                }
            }
        }
        return false;
    }
}