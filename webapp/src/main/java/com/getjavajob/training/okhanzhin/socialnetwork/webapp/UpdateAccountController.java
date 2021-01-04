package com.getjavajob.training.okhanzhin.socialnetwork.webapp;

import com.getjavajob.training.okhanzhin.socialnetwork.domain.Account;
import com.getjavajob.training.okhanzhin.socialnetwork.domain.Phone;
import com.getjavajob.training.okhanzhin.socialnetwork.service.AccountService;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

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
import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.requireNonNull;

@MultipartConfig
public class UpdateAccountController extends HttpServlet {
    private static final String EMPTY_PHONE_INPUT = "";
    private AccountService accountService;
    private Account account;

    @Override
    public void init() throws ServletException {
        ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(getServletContext());
        this.accountService = requireNonNull(context).getBean(AccountService.class);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        account = (Account) request.getSession().getAttribute("account");
        request.setAttribute("account", account);
        request.getRequestDispatcher(request.getContextPath() + "/WEB-INF/jsp/updateAccount.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        long id = Long.parseLong(request.getParameter("id"));
        account.setAccountID(id);
        account.setSurname(request.getParameter("surname"));
        account.setName(request.getParameter("name"));
        account.setMiddlename(request.getParameter("middlename"));
        account.setPassword(request.getParameter("password"));
        String stringDate = request.getParameter("dateOfBirth");
        if (stringDate.length() != 0) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate date = LocalDate.parse(stringDate, formatter);
            account.setDateOfBirth(date);
        } else {
            account.setDateOfBirth(null);
        }
        account.setSkype(request.getParameter("skype"));
        account.setIcq(request.getParameter("icq"));
        account.setHomeAddress(request.getParameter("homeAddress"));
        account.setWorkAddress(request.getParameter("workAddress"));
        account.setAddInfo(request.getParameter("addInfo"));
        account.setRole(request.getParameter("role"));
        Part filePart = request.getPart("picture");
        account.setPicture(getByteArrayFromPart(filePart));
        populateAccountPhones(request, id);

        accountService.updateAccount(account);
        request.getSession().setAttribute("account", account);
        response.sendRedirect(request.getContextPath() + "/account?id=" + account.getAccountID());
    }

    private void populateAccountPhones(HttpServletRequest request, long id) {
        List<Phone> phones = new ArrayList<>();
        if (request.getParameterValues("workPhone[]") != null) {
            for (String workPhone : request.getParameterValues("workPhone[]")) {
                if (!workPhone.equals(EMPTY_PHONE_INPUT)) {
                    phones.add(new Phone(id, workPhone, "work"));
                }
            }
        }
        if (request.getParameterValues("homePhone[]") != null) {
            for (String homePhone : request.getParameterValues("homePhone[]")) {
                if (!homePhone.equals(EMPTY_PHONE_INPUT)) {
                    phones.add(new Phone(id, homePhone, "home"));
                }
            }
        }

        account.setPhones(phones);
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
