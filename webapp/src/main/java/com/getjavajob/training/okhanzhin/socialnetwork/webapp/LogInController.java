package com.getjavajob.training.okhanzhin.socialnetwork.webapp;

import com.getjavajob.training.okhanzhin.socialnetwork.domain.Account;
import com.getjavajob.training.okhanzhin.socialnetwork.service.AccountService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;

public class LogInController extends HttpServlet {
    private static final int COOKIE_AGE = 7 * 24 * 60 * 60;
    private final AccountService service = new AccountService();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        Account account = service.getByEmail(email);
        System.out.println(account.toString());

        if (request.getParameter("rememberLogPass") != null && request.getParameter("rememberLogPass").equals("true")) {
            Cookie emailCookie = new Cookie("email", account.getEmail());
            Cookie passwordCookie = new Cookie("password", account.getPassword());
            emailCookie.setMaxAge(COOKIE_AGE);
            passwordCookie.setMaxAge(COOKIE_AGE);
            response.addCookie(emailCookie);
            response.addCookie(passwordCookie);
        }

        if (account.getPassword().equals(password)) {
            long id = account.getAccountID();
            HttpSession session = request.getSession();
            session.setAttribute("id", id);
            session.setAttribute("account", account);
            response.sendRedirect(request.getContextPath() + "/account?id=" + id);
        } else {
            RequestDispatcher dispatcher = request.getRequestDispatcher(request.getContextPath() + "/WEB-INF/jsp/logIn.jsp");
            PrintWriter failedLogIn = response.getWriter();
            failedLogIn.println("<font color=red>Email/Password is invalid.</font>");
            dispatcher.include(request, response);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher(request.getContextPath() + "/WEB-INF/jsp/logIn.jsp").forward(request, response);
    }
}
