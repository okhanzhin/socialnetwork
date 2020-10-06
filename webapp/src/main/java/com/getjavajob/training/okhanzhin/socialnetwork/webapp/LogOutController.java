package com.getjavajob.training.okhanzhin.socialnetwork.webapp;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LogOutController extends HttpServlet {
    private static final int ZERO_AGE = 0;

    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getSession().invalidate();
        request.getSession().removeAttribute("account");
        Cookie emailCookie = new Cookie("email", "");
        Cookie passwordCookie = new Cookie("password", "");
        emailCookie.setMaxAge(ZERO_AGE);
        passwordCookie.setMaxAge(ZERO_AGE);
        response.addCookie(emailCookie);
        response.addCookie(passwordCookie);
        response.sendRedirect(request.getContextPath() + "logIn");
    }
}