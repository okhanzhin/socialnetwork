package com.getjavajob.training.okhanzhin.socialnetwork.webapp;

import com.getjavajob.training.okhanzhin.socialnetwork.domain.Account;
import com.getjavajob.training.okhanzhin.socialnetwork.domain.Group;
import com.getjavajob.training.okhanzhin.socialnetwork.service.AccountService;
import com.getjavajob.training.okhanzhin.socialnetwork.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServlet;
import java.util.List;

import static java.lang.Long.parseLong;

@Controller
@RequestMapping(value = "/account")
public class AccountController extends HttpServlet {
    private final AccountService accountService;
    private final GroupService groupService;

    @Autowired
    public AccountController(AccountService accountService, GroupService groupService) {
        this.accountService = accountService;
        this.groupService = groupService;
    }

    @RequestMapping(value = "/{accountId}", method = RequestMethod.GET)
    public ModelAndView showProfile(@PathVariable("accountId") long accountId) {
        Account account = accountService.getById(accountId);
        List<Group> groups = groupService.getGroupsListForAccount(account);

        ModelAndView model = new ModelAndView("profile");
        model.addObject("profileAccountId", account.getAccountID());
        model.addObject("profileAccount", account);
        model.addObject("groupList", groups);

        return model;
    }
}
