package com.getjavajob.training.okhanzhin.socialnetwork.webapp;

import com.getjavajob.training.okhanzhin.socialnetwork.domain.Account;
import com.getjavajob.training.okhanzhin.socialnetwork.domain.Group;
import com.getjavajob.training.okhanzhin.socialnetwork.service.AccountService;
import com.getjavajob.training.okhanzhin.socialnetwork.service.GroupService;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

import static java.util.Objects.*;

public class SearchController extends HttpServlet {
    private AccountService accountService;
    private GroupService groupService;

    @Override
    public void init() throws ServletException {
        ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(getServletContext());
        accountService = requireNonNull(context).getBean(AccountService.class);
        groupService = requireNonNull(context).getBean(GroupService.class);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String value = request.getParameter("value");
        int currentPage = Integer.parseInt(request.getParameter("currentPage"));
        String tab = request.getParameter("tab");

        if (tab == null) {
            tab = "accounts";
        }

        List<Account> accounts = accountService.searchForAccounts(value, currentPage);
        List<Group> groups = groupService.searchForGroups(value, currentPage);

        accounts.forEach(System.out::println);
        groups.forEach(System.out::println);

        request.setAttribute("value", value);
        request.setAttribute("accounts",accounts);
        request.setAttribute("accountNumOfPages", accountService.getNumberOfPages(value));
        request.setAttribute("groups", groups);
        request.setAttribute("groupsNumOfPages", groupService.getNumberOfPages(value));
        request.setAttribute("currentPage", currentPage);
        request.setAttribute("activeTab", tab);

        request.getRequestDispatcher(request.getContextPath() + "/WEB-INF/jsp/searchResults.jsp").forward(request, response);
    }
}
