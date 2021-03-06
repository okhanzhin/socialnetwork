package com.getjavajob.training.okhanzhin.socialnetwork.webapp;

import com.getjavajob.training.okhanzhin.socialnetwork.domain.Account;
import com.getjavajob.training.okhanzhin.socialnetwork.domain.Group;
import com.getjavajob.training.okhanzhin.socialnetwork.domain.Member;
import com.getjavajob.training.okhanzhin.socialnetwork.service.AccountService;
import com.getjavajob.training.okhanzhin.socialnetwork.service.GroupService;
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

import static java.lang.Long.parseLong;

public class GroupController extends HttpServlet {
    private AccountService accountService;
    private GroupService groupService;

    @Override
    public void init() throws ServletException {
        ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(getServletContext());
        this.accountService = Objects.requireNonNull(context).getBean(AccountService.class);
        this.groupService = Objects.requireNonNull(context).getBean(GroupService.class);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        long groupID = parseLong(request.getParameter("id"));
        long accountID = (long) session.getAttribute("id");
        Group group = groupService.getById(groupID);
        Account visitorAccount = (Account) session.getAttribute("account");
        Member visitorMember = groupService.getMemberByAccountId(accountID);
        Member owner = groupService.getGroupOwner(group);

        List<Account> accounts = accountService.getAccountsListForGroup(group);
        List<Account> unconfirmedRequestAccounts = accountService.getUnconfirmedRequestAccountsForGroup(group);
        List<Member> moderatorMembers = groupService.getListOfModerators(groupID);
        List<Account> moderatorAccounts = groupService.convertMembersToAccounts(moderatorMembers);

        request.setAttribute("isAbleToModify", groupService.isAbleToModify(accountID, groupID));
        request.setAttribute("isSubscribed", unconfirmedRequestAccounts.contains(visitorAccount));
        request.setAttribute("isMember", accounts.contains(visitorAccount));
        if (visitorMember != null) {
            request.setAttribute("visitorStatus", visitorMember.getMemberStatus().getStatus());
        }
        request.setAttribute("visitorID", accountID);
        request.setAttribute("ownerID", owner.getAccountID());
        request.setAttribute("group", group);
        request.setAttribute("accountsList", accounts);
        request.setAttribute("unconfirmedList", unconfirmedRequestAccounts);
        request.setAttribute("moderatorsList", moderatorAccounts);

        request.getRequestDispatcher(request.getContextPath() + "/WEB-INF/jsp/group.jsp").forward(request, response);
    }
}
