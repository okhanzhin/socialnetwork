package com.getjavajob.training.okhanzhin.socialnetwork.webapp;

import com.getjavajob.training.okhanzhin.socialnetwork.domain.Account;
import com.getjavajob.training.okhanzhin.socialnetwork.service.AccountService;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@MultipartConfig
public class RegisterController extends HttpServlet {
    private final AccountService accountService = new AccountService();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher(request.getContextPath() + "/WEB-INF/jsp/register.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        if (email.length() == 0 || password.length() == 0) {
            response.sendRedirect(request.getContextPath() + "/register");
        } else {
            Account account = new Account(request.getParameter("surname"),
                    request.getParameter("name"),
                    request.getParameter("email"),
                    request.getParameter("password"));

            account.setMiddlename(request.getParameter("middlename"));
            String stringDate = request.getParameter("dateOfBirth");
            if (stringDate.length() != 0) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                LocalDate date = LocalDate.parse(stringDate, formatter);
                account.setDateOfBirth(date);
            } else {
                account.setDateOfBirth(null);
            }
            account.setHomePhone(request.getParameter("homePhone"));
            account.setWorkPhone(request.getParameter("workPhone"));
            account.setSkype(request.getParameter("skype"));
            account.setIcq(request.getParameter("icq"));
            account.setHomeAddress(request.getParameter("homeAddress"));
            account.setWorkAddress(request.getParameter("workAddress"));
            account.setAddInfo(request.getParameter("addInfo"));
            account.setRole(request.getParameter("role"));
            Part filePart = request.getPart("picture");
            account.setPicture(getByteArrayFromPart(filePart));

            Account returnedAccount = accountService.createAccount(account);
            long id = returnedAccount.getAccountID();
            account.setAccountID(id);
            accountService.updateAccount(account);

            request.getSession().setAttribute("id", id);
            request.getSession().setAttribute("account", returnedAccount);

            response.sendRedirect(request.getContextPath() + "/account?id=" + id);
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
