package com.getjavajob.training.okhanzhin.socialnetwork.webapp;

import com.getjavajob.training.okhanzhin.socialnetwork.domain.Account;
import com.getjavajob.training.okhanzhin.socialnetwork.domain.Phone;
import com.getjavajob.training.okhanzhin.socialnetwork.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.*;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@MultipartConfig
@Controller
@RequestMapping(value = "/account")
public class RegisterController extends HttpServlet {
    private final AccountService accountService;

    @Autowired
    public RegisterController(AccountService accountService) {
        this.accountService = accountService;
    }

    @RequestMapping(value = "/new", method = RequestMethod.GET)
    public String showSignUpForm(Model model) {
        model.addAttribute("account", new Account());
        return "register";
    }

    @RequestMapping(value = "/new", method = RequestMethod.POST)
    public String signUp(@ModelAttribute(value = "account") Account formAccount,
                         @RequestParam("homePhone") String homePhone,
                         @RequestParam("workPhone") String workPhone,
                         @RequestParam("picture") MultipartFile picture,
                         HttpSession session) throws IOException {
        if (formAccount.getEmail().length() == 0 || formAccount.getPassword().length() == 0) {
            return "register";
        } else {
            Account account = new Account(formAccount.getSurname(),
                    formAccount.getName(),
                    formAccount.getEmail(),
                    formAccount.getPassword());

            account.setMiddlename(formAccount.getMiddlename());
            account.setDateOfBirth(formAccount.getDateOfBirth());
            account.setSkype(formAccount.getSkype());
            account.setIcq(formAccount.getIcq());
            account.setHomeAddress(formAccount.getHomeAddress());
            account.setWorkAddress(formAccount.getWorkAddress());
            account.setAddInfo(formAccount.getAddInfo());
            account.setRole(formAccount.getRole());
            account.setPicture(picture.getBytes());

            List<Phone> phones = new ArrayList<>();
            phones.add(new Phone(homePhone, "home"));
            phones.add(new Phone(workPhone, "work"));
            account.setPhones(phones);

            Account returnedAccount = accountService.createAccount(account);
            long id = returnedAccount.getAccountID();
            account.setAccountID(id);

            accountService.updateAccount(account);

            session.setAttribute("id", id);
            session.setAttribute("account", returnedAccount);

            return "redirect:/account/" + account.getAccountID();
        }
    }
}