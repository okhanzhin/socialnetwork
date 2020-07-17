package com.getjavajob.training.okhanzhin.socialnetwork.webapp;

import com.getjavajob.training.okhanzhin.socialnetwork.domain.Account;
import com.getjavajob.training.okhanzhin.socialnetwork.service.AccountService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class AccountInfoServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        StringBuilder builder = new StringBuilder("<html>\n" +
                " <head>\n" +
                "  <meta charset=\"utf-8\">\n" +
                "  <title>Accounts Info</title>\n" +
                " </head>\n" +
                " <body>");

        builder.append("<style>")
                .append("table {")
                .append("width: 100%")
                .append("height: 200px;")
                .append("border: 1px solid black;")
                .append("margin: auto;").append("}")
                .append("td {")
                .append("text-align: center;")
                .append("}")
                .append("</style> ");

        builder.append("<table border=\"2\" cellpadding=\"25\" cellspacing=\"0\">")
                .append("<tr>")
                .append("<th>Full name</th>")
                .append("<th colspan=\"2\">Phone</th>")
                .append("<th>Address</th>").append("</tr>");

        AccountService accountService = new AccountService();
        List<Account> accountList = accountService.getAllAccountsList();

        try {
            for (Account account : accountList) {
                builder.append("<tr>")
                        .append("<th>").append(account.getName()).append(" ").append(account.getSurname()).append("</th>")
                        .append("<th>").append(account.getHomePhone()).append("</th>")
                        .append("<th>").append(account.getWorkPhone()).append("</th>")
                        .append("<th>").append(account.getHomeAddress()).append("</th>").append("</tr>");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        builder.append("</table></body></html>");

        PrintWriter pw = response.getWriter();
        pw.print(builder.toString());
    }
}
