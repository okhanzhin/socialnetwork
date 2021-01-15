package com.getjavajob.training.okhanzhin.socialnetwork.webapp;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping(value = "/auth")
public class LogOutController {
    private static final int ZERO_AGE = 0;

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String doLogout(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession(false);
        session.removeAttribute("account");
        session.removeAttribute("id");
        session.invalidate();

        if (session == null) {
            System.out.println("Session is null");
        }
        Cookie emailCookie = new Cookie("email", "");
        Cookie passwordCookie = new Cookie("password", "");
        emailCookie.setMaxAge(ZERO_AGE);
        passwordCookie.setMaxAge(ZERO_AGE);
        response.addCookie(emailCookie);
        response.addCookie(passwordCookie);
        return "/";
    }
}