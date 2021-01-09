package com.getjavajob.training.okhanzhin.socialnetwork.webapp.filter;

import com.getjavajob.training.okhanzhin.socialnetwork.domain.Account;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class AuthenticationInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("Start interception!");
        HttpSession session = request.getSession();
        Account account = (Account) session.getAttribute("account");
        String uri = request.getRequestURI();
        System.out.println(uri);

        if (uri.equals("/login") || uri.equals("/") || uri.equals("/account/new")) {
            if (account != null) {
                response.sendRedirect(request.getContextPath() + "/account/" + account.getAccountID());
            } else {
                if (isEmailCookieExist(request)) {
                    response.sendRedirect(request.getContextPath() + "/account/setup");
                } else {
                    return true;
                }
            }
        } else {
            if (account != null) {
                return true;
            } else {
                if (isEmailCookieExist(request)) {
                    response.sendRedirect(request.getContextPath() + "/account/setup");
                } else {
                    response.sendRedirect(request.getContextPath() + "/login");
                }
            }
        }
        return false;
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
