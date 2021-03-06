package com.getjavajob.training.okhanzhin.socialnetwork.webapp;

import com.getjavajob.training.okhanzhin.socialnetwork.domain.Account;
import com.getjavajob.training.okhanzhin.socialnetwork.domain.Request;
import com.getjavajob.training.okhanzhin.socialnetwork.service.AccountService;
import com.getjavajob.training.okhanzhin.socialnetwork.service.RequestService;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

public class FriendsController extends HttpServlet {
    private AccountService accountService;
    private RequestService requestService;

    @Override
    public void init() throws ServletException {
        ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(getServletContext());
        this.accountService = Objects.requireNonNull(context).getBean(AccountService.class);
        this.requestService = Objects.requireNonNull(context).getBean(RequestService.class);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        long sessionAccountID = (long) session.getAttribute("id");
        long pageAccountID = Long.parseLong(request.getParameter("id"));
        Account pageAccount = accountService.getById(pageAccountID);

        List<Account> friends = accountService.getAccountFriends(pageAccount);
        List<Request> pendingRequests = requestService.getPendingRequests(pageAccount);
        List<Account> pendingAccounts = accountService.getPendingRequestAccounts(pageAccount);
        List<Request> outgoingRequests = requestService.getOutgoingRequests(pageAccount);
        List<Account> outgoingAccounts = accountService.getOutgoingRequestAccounts(pageAccount);
        List<Request> acceptedRequests = requestService.getAcceptedRequests(pageAccount);

        request.setAttribute("visitorID", sessionAccountID);
        request.setAttribute("pageAccountID", pageAccountID);
        boolean isAbleToModify = false;
        if (sessionAccountID == pageAccountID) {
            isAbleToModify = true;
        }
        request.setAttribute("isAbleToModify", isAbleToModify);
        request.setAttribute("friends", friends);
        request.setAttribute("pendingRequests", pendingRequests);
        request.setAttribute("pendingAccounts", pendingAccounts);
        request.setAttribute("outgoingRequests", outgoingRequests);
        request.setAttribute("outgoingAccounts", outgoingAccounts);
        request.setAttribute("acceptedRequests", acceptedRequests);

        request.getRequestDispatcher(request.getContextPath() + "/WEB-INF/jsp/friends.jsp").forward(request, response);
    }
}
