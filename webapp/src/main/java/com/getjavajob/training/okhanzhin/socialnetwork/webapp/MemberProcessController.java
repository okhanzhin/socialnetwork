package com.getjavajob.training.okhanzhin.socialnetwork.webapp;

import com.getjavajob.training.okhanzhin.socialnetwork.domain.Account;
import com.getjavajob.training.okhanzhin.socialnetwork.domain.Group;
import com.getjavajob.training.okhanzhin.socialnetwork.domain.Request;
import com.getjavajob.training.okhanzhin.socialnetwork.service.AccountService;
import com.getjavajob.training.okhanzhin.socialnetwork.service.GroupService;
import com.getjavajob.training.okhanzhin.socialnetwork.service.RequestService;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

public class MemberProcessController extends HttpServlet {
    private AccountService accountService;
    private GroupService groupService;
    private RequestService requestService;

    @Override
    public void init() throws ServletException {
        ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(getServletContext());
        this.accountService = Objects.requireNonNull(context).getBean(AccountService.class);
        this.groupService = Objects.requireNonNull(context).getBean(GroupService.class);
        this.requestService = Objects.requireNonNull(context).getBean(RequestService.class);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        long groupId = Long.parseLong(request.getParameter("groupId"));
        long accountId = Long.parseLong(request.getParameter("accountId"));
        String memberOperation = request.getParameter("operation");

        Group group = groupService.getById(groupId);
        Account account = accountService.getById(accountId);
        Request requestObj = requestService.getByCreatorRecipientID(accountId, groupId);

        switch (memberOperation) {
            case "delete":
                groupService.deleteMemberFromGroup(account, group);
                requestService.setRequestUnconfirmed(requestObj);
                break;
            case "makeModerator":
                groupService.setGroupModerator(account, group);
                break;
            case "makeUser":
                groupService.setGroupUser(account, group);
                break;
        }

        response.sendRedirect(request.getContextPath() + "/group?id=" + groupId);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }
}
