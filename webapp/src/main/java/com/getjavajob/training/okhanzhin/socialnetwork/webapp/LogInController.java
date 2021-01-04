package com.getjavajob.training.okhanzhin.socialnetwork.webapp;

import com.getjavajob.training.okhanzhin.socialnetwork.domain.Account;
import com.getjavajob.training.okhanzhin.socialnetwork.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller
public class LogInController extends HttpServlet {
    private static final int COOKIE_AGE = 7 * 24 * 60 * 60;

    private final AccountService accountService;

    @Autowired
    public LogInController(AccountService accountService) {
        this.accountService = accountService;
    }

    @RequestMapping(value = "/")
    public String index() {
        return "redirect:/auth/login";
    }

    @RequestMapping(value = "/auth/login", method = RequestMethod.GET)
    public ModelAndView showLogInForm() {
        return new ModelAndView("login");
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String logIn(@RequestParam("email") String email,
                        @RequestParam("password") String password,
                        @RequestParam(value = "rememberLogPass", required = false) String rememberLogPas,
                        HttpServletRequest request, HttpServletResponse response) {

        HttpSession session = request.getSession();
        Account account = accountService.getByEmail(email);

        if (rememberLogPas != null && rememberLogPas.equals("true")) {
            Cookie emailCookie = new Cookie("email", account.getEmail());
            Cookie passwordCookie = new Cookie("password", account.getPassword());
            emailCookie.setMaxAge(COOKIE_AGE);
            passwordCookie.setMaxAge(COOKIE_AGE);
            response.addCookie(emailCookie);
            response.addCookie(passwordCookie);
        }

        if (account.getPassword().equals(password)) {
            session.setAttribute("account", account);
            return "redirect:/account/" + account.getAccountID();
        } else {
            request.setAttribute("logPasError", "Email/Password is invalid.");
            return "login";
        }
    }
}
