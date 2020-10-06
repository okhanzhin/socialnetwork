package com.getjavajob.training.okhanzhin.socialnetwork.webapp;

import com.getjavajob.training.okhanzhin.socialnetwork.domain.Request;
import com.getjavajob.training.okhanzhin.socialnetwork.service.AccountService;
import com.getjavajob.training.okhanzhin.socialnetwork.service.GroupService;
import com.getjavajob.training.okhanzhin.socialnetwork.service.RequestService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static java.lang.Byte.parseByte;
import static java.lang.Long.parseLong;

public class RequestController extends HttpServlet {
    private final AccountService accountService = new AccountService();
    private final GroupService groupService = new GroupService();
    private final RequestService requestService = new RequestService();

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
            requestObj = new Request(creatorID, recipientID, requestType, requestStatus);
        } else {
            requestObj = requestService.getByCreatorRecipientID(creatorID, recipientID);
        }
        System.out.println(requestObj);

        switch (operation) {
            case "create":
                System.out.println("Created " + requestObj);
                if (requestType.equals("user")) {
                    requestService.createRelationRequest(requestObj);
                    accountService.createRelation(creatorID, recipientID);
                    System.out.println("Creating Relation Request...");
                } else {
                    requestService.createGroupRequest(requestObj);
                    System.out.println("Creating Group Request...");
                }
                break;
            case "accept":
                requestObj.setRequestStatus(requestStatus);
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
