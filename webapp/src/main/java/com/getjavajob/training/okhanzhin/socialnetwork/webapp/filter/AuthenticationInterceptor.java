package com.getjavajob.training.okhanzhin.socialnetwork.webapp.filter;

import com.getjavajob.training.okhanzhin.socialnetwork.domain.Account;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Arrays;

public class AuthenticationInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("Start interception!");
        HttpSession session = request.getSession();
        Account account = (Account) session.getAttribute("account");
        String uri = request.getRequestURI();
        System.out.println(uri);

        if (uri.equals("/login") || uri.equals("/") || uri.equals("/account/new")) {
            System.out.println("ULR is login or register");
            if (account != null) {
                System.out.println("Session contains account");
                response.sendRedirect(request.getContextPath() + "/account/" + account.getAccountID());
            } else {
                System.out.println("Session is empty");
                if (isEmailCookieExist(request)) {
                    System.out.println("Cookie Exist");
                    response.sendRedirect(request.getContextPath() + "/account/setup");
                } else {
                    System.out.println("Cookie Empty");
                    return true;
                }
            }
        } else {
            System.out.println("ULR is not login or register");
            if (account != null) {
                System.out.println("Session contains account");
                return true;
            } else {
                System.out.println("Session is empty");
                if (isEmailCookieExist(request)) {
                    response.sendRedirect(request.getContextPath() + "/account/setup");
                } else {
                    response.sendRedirect(request.getContextPath() + "/login");
                }
            }
        }
        return false;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }

    private boolean isEmailCookieExist(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        System.out.println(Arrays.toString(cookies));

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
