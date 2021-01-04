package com.getjavajob.training.okhanzhin.socialnetwork.webapp;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping(value = "/auth")
public class LogOutController extends HttpServlet {
    private static final int ZERO_AGE = 0;

    @RequestMapping(value = "/logout")
    public String doLogout(HttpServletRequest request, HttpServletResponse response) {
        request.getSession().invalidate();
        request.getSession().removeAttribute("account");
        Cookie emailCookie = new Cookie("email", "");
        Cookie passwordCookie = new Cookie("password", "");
        emailCookie.setMaxAge(ZERO_AGE);
        passwordCookie.setMaxAge(ZERO_AGE);
        response.addCookie(emailCookie);
        response.addCookie(passwordCookie);
        return "redirect:/login";
    }

//    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        request.getSession().invalidate();
//        request.getSession().removeAttribute("account");
//        Cookie emailCookie = new Cookie("email", "");
//        Cookie passwordCookie = new Cookie("password", "");
//        emailCookie.setMaxAge(ZERO_AGE);
//        passwordCookie.setMaxAge(ZERO_AGE);
//        response.addCookie(emailCookie);
//        response.addCookie(passwordCookie);
//        response.sendRedirect(request.getContextPath() + "logIn");
//    }
}