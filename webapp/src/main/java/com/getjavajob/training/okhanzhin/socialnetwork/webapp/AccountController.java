package com.getjavajob.training.okhanzhin.socialnetwork.webapp;

import com.getjavajob.training.okhanzhin.socialnetwork.domain.Account;
import com.getjavajob.training.okhanzhin.socialnetwork.domain.Group;
import com.getjavajob.training.okhanzhin.socialnetwork.service.AccountService;
import com.getjavajob.training.okhanzhin.socialnetwork.service.GroupService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import static java.lang.Long.parseLong;

public class AccountController extends HttpServlet {
    private final AccountService accountService = new AccountService();
    private final GroupService groupService = new GroupService();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        long id = parseLong(request.getParameter("id"));
        Account account = accountService.getById(id);
        List<Group> groups = groupService.getGroupsListForAccount(account);

        request.setAttribute("id", account.getAccountID());
        request.setAttribute("account", account);
        request.setAttribute("groupsList", groups);
        request.getRequestDispatcher(request.getContextPath() + "/WEB-INF/jsp/account.jsp").forward(request, response);
    }
}
