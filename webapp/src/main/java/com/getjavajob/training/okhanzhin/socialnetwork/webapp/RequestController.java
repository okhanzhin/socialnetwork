package com.getjavajob.training.okhanzhin.socialnetwork.webapp;

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

import static com.getjavajob.training.okhanzhin.socialnetwork.domain.Request.Status.fromValue;
import static java.lang.Byte.parseByte;
import static java.lang.Long.parseLong;

public class RequestController extends HttpServlet {
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
        long creatorID = parseLong(request.getParameter("creatorId"));
        long recipientID;
        if (request.getParameter("accountId") != null) {
            recipientID = parseLong(request.getParameter("accountId"));
        } else {
            recipientID = parseLong(request.getParameter("groupId"));
        }
        String requestType = request.getParameter("reqType");
        byte requestStatus = parseByte(request.getParameter("reqStatus"));

        String operation = request.getParameter("operation");
        Request requestObj;

        if (operation.equals("create")) {
            requestObj = new Request(creatorID, recipientID, requestType, fromValue(requestStatus));
        } else {
            requestObj = requestService.getByCreatorRecipientID(creatorID, recipientID);
        }
        System.out.println(requestObj);

        switch (operation) {
            case "create":
                System.out.println("Created " + requestObj);
                requestService.createRequest(requestObj);
                if (requestType.equals("user")) {
                    accountService.createRelation(creatorID, recipientID);
                    System.out.println("Creating Relation Request...");
                } else {
                    System.out.println("Creating Group Request...");
                }
                break;
            case "accept":
                requestObj.setStatus(fromValue(requestStatus));
                requestService.acceptRequest(requestObj);
                if (requestType.equals("user")) {
                    accountService.acceptRelation(creatorID, recipientID);
                } else {
                    groupService.createGroupMember(creatorID, recipientID);
                }
                System.out.println("Accepting Request...");
                break;
            case "decline":
                requestService.deleteRequest(requestObj);
                if (requestType.equals("user")) {
                    accountService.declineRelation(creatorID, recipientID);
                }
                System.out.println("Deleting Request...");
                break;
            case "unfriend":
                requestService.setRequestUnconfirmed(requestObj);
                accountService.deleteRelation(creatorID, recipientID);
                System.out.println("Break relationship...");
                break;
        }

        if (requestType.equals("user")) {
            response.sendRedirect(request.getContextPath() + "/account?id=" + recipientID);
        } else {
            response.sendRedirect(request.getContextPath() + "/group?id=" + recipientID);
        }
    }
}
