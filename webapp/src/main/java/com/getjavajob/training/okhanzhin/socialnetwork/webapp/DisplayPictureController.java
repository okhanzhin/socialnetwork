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
import java.util.Objects;

import static java.lang.Long.parseLong;

public class DisplayPictureController extends HttpServlet {
    private AccountService accountService;
    private GroupService groupService;

    @Override
    public void init() throws ServletException {
        ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(getServletContext());
        this.accountService = Objects.requireNonNull(context).getBean(AccountService.class);
        this.groupService = Objects.requireNonNull(context).getBean(GroupService.class);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        byte[] picture;

        if (request.getParameter("id") != null) {
            Account account = accountService.getById(parseLong(request.getParameter("id")));
            picture = account.getPicture();
        } else {
            Group group = groupService.getById(parseLong(request.getParameter("groupId")));
            picture = group.getPicture();
        }

        response.setContentType("image");
        response.getOutputStream().write(picture);
    }
}