package com.getjavajob.training.okhanzhin.socialnetwork.webapp;

import com.getjavajob.training.okhanzhin.socialnetwork.domain.Account;
import com.getjavajob.training.okhanzhin.socialnetwork.domain.Group;
import com.getjavajob.training.okhanzhin.socialnetwork.service.GroupService;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

@MultipartConfig
public class CreateGroupController extends HttpServlet {
    private final GroupService groupService = new GroupService();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher(request.getContextPath() + "/WEB-INF/jsp/createGroup.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String groupName = request.getParameter("groupName");

        if (groupName.length() == 0) {
            response.sendRedirect(request.getContextPath() + "/createGroup");
        } else {
            Group group = new Group(groupName);
            group.setGroupDescription(request.getParameter("description"));
            Part filePart = request.getPart("picture");
            group.setPicture(getByteArrayFromPart(filePart));

            Group returnedGroup = groupService.createGroup(group);
            long groupID = returnedGroup.getGroupID();
            group.setGroupID(groupID);
            groupService.updateGroup(group);
            groupService.createGroupOwner((Account) request.getSession().getAttribute("account"), group);
            response.sendRedirect(request.getContextPath() + "/group?id=" + groupID);
        }
    }

    private byte[] getByteArrayFromPart(Part filePart) throws IOException {
        try (InputStream fileContent = filePart.getInputStream();
             ByteArrayOutputStream buffer = new ByteArrayOutputStream()) {
            int nRead;
            byte[] data = new byte[65535];
            while ((nRead = fileContent.read(data, 0, data.length)) != -1) {
                buffer.write(data, 0, nRead);
            }
            return buffer.toByteArray();
        }
    }
}
