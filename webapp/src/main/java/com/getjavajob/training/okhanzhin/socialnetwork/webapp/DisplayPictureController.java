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

import static java.lang.Long.parseLong;

public class DisplayPictureController extends HttpServlet {
    private final AccountService accountService = new AccountService();
    private final GroupService groupService = new GroupService();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        byte[] picture;

        if (request.getParameter("id") != null) {
            Account account = accountService.getById(parseLong(request.getParameter("id")));
            picture = account.getPicture();
        } else {
            Group group = groupService.getGroupById(parseLong(request.getParameter("groupId")));
            picture = group.getPicture();
        }

        response.setContentType("image");
        response.getOutputStream().write(picture);
    }
}
