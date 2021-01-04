package com.getjavajob.training.okhanzhin.socialnetwork.webapp.filter;

import com.getjavajob.training.okhanzhin.socialnetwork.domain.Account;
import com.getjavajob.training.okhanzhin.socialnetwork.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;


public class AuthenticationInterceptor implements HandlerInterceptor {
    private final AccountService accountService;

    @Autowired
    public AuthenticationInterceptor(AccountService accountService) {
        this.accountService = accountService;
    }

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
                account = getAccountFromCookies(request);
                if (account != null) {
                    session.setAttribute("account", account);
                    session.setAttribute("id", account.getAccountID());
                    response.sendRedirect(request.getContextPath() + uri);
                } else {
                    return true;
                }
            }
        } else {
            if (account != null) {
                return true;
            } else {
                account = getAccountFromCookies(request);
                if (account != null) {
                    session.setAttribute("account", account);
                    session.setAttribute("id", account.getAccountID());
                    response.sendRedirect(request.getContextPath() + uri);
                } else {
                    response.sendRedirect(request.getContextPath() + "/login");
                }
            }
        }

        return false;
    }

    private Account getAccountFromCookies(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        String userMail = null;
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("email".equals((cookie.getName()))) {
                    userMail = cookie.getValue();
                }
            }
        }

        return accountService.getByEmail(userMail);
    }

/*    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;
        HttpSession session = request.getSession();

        Account account = (Account) session.getAttribute("account");
        String uri = request.getRequestURI();
        System.out.println(uri);
        if (uri.equals("/logIn") || uri.equals("/") || uri.equals("/register")) {
            if (account != null) {
                response.sendRedirect(request.getContextPath() + "/account?id=" + account.getAccountID());
            } else {
                account = getAccountFromCookies(request);
                if (account != null) {
                    session.setAttribute("account", account);
                    session.setAttribute("id", account.getAccountID());
                    response.sendRedirect(request.getContextPath() + uri);
                } else {
                    chain.doFilter(req, resp);
                }
            }
        } else {
            if (account != null) {
                chain.doFilter(req, resp);
            } else {
                account = getAccountFromCookies(request);
                if (account != null) {
                    session.setAttribute("account", account);
                    session.setAttribute("id", account.getAccountID());
                    response.sendRedirect(request.getContextPath() + uri);
                } else {
                    response.sendRedirect(request.getContextPath() + "/logIn");
                }
            }
        }
    }*/
}
