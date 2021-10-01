package com.getjavajob.training.okhanzhin.socialnetwork.webapp.controller;

import com.getjavajob.training.okhanzhin.socialnetwork.domain.Account;
import com.getjavajob.training.okhanzhin.socialnetwork.domain.dto.PostView;
import com.getjavajob.training.okhanzhin.socialnetwork.service.AccountService;
import com.getjavajob.training.okhanzhin.socialnetwork.service.CommunityService;
import com.getjavajob.training.okhanzhin.socialnetwork.service.PostService;
import com.getjavajob.training.okhanzhin.socialnetwork.service.RelationService;
import com.getjavajob.training.okhanzhin.socialnetwork.service.RequestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping(value = "/account")
public class AccountController {
    private static final Logger logger = LoggerFactory.getLogger(AccountController.class);
    private static final String ADMIN = "ADMIN";

    private final AccountService accountService;
    private final CommunityService communityService;
    private final RequestService requestService;
    private final PostService postService;
    private final RelationService relationService;

    @Autowired
    public AccountController(AccountService accountService, CommunityService communityService,
                             RequestService requestService, PostService postService,
                             RelationService relationService) {
        this.accountService = accountService;
        this.communityService = communityService;
        this.requestService = requestService;
        this.postService = postService;
        this.relationService = relationService;
    }

    @RequestMapping(value = "/{accountId}", method = RequestMethod.GET)
    public ModelAndView showProfile(@PathVariable("accountId") long pathAccountId,
                                    @SessionAttribute(name = "homeAccount") Account homeAccount,
                                    @SessionAttribute(name = "homeAccountId") long homeAccountID,
                                    HttpSession session) {
        if (session.getAttribute("chatRoom") != null) {
            session.removeAttribute("chatRoom");
        }
        //remove group Properties from session if exists.
        if (session.getAttribute("activeCommunity") != null) {
            session.removeAttribute("activeCommunity");
            session.removeAttribute("commPermissions");
        }

        if (homeAccountID == pathAccountId) {
            logger.info("Session account: {}", homeAccount.toString());

            return configureBasicModelAndView(
                    "homeProfile-info", "homeAccount", homeAccount);
        } else {
            Account userAccount = accountService.getByIdFetchPhones(pathAccountId);
            logger.info("User account role: {}", userAccount.getRole());
            if (homeAccount.getRole().equals(ADMIN) || userAccount.isEnabled()) {
                ModelAndView modelAndView = configureBasicModelAndView(
                        "userProfile-info", "userAccount", userAccount);

                return setupForeignAccountFlags(modelAndView, homeAccount, userAccount);
            } else {
                return new ModelAndView("/templates/profile-locked");
            }
        }
    }

    @RequestMapping(value = "/{accountId}/communities", method = RequestMethod.GET)
    public ModelAndView showAccountCommunities(@PathVariable("accountId") long pathAccountId,
                                               @SessionAttribute(name = "homeAccountId") long homeAccountID,
                                               @SessionAttribute(name = "homeAccount") Account homeAccount) {
        if (homeAccountID == pathAccountId) {
            ModelAndView modelAndView = configureBasicModelAndView(
                    "homeProfile-communities", "homeAccount", homeAccount);
            modelAndView.addObject("commList", communityService.getAccountCommunities(homeAccount));

            return modelAndView;
        } else {
            Account userAccount = accountService.getByIdFetchPhones(pathAccountId);
            if (homeAccount.getRole().equals(ADMIN) || userAccount.isEnabled()) {
                ModelAndView modelAndView = configureBasicModelAndView(
                        "userProfile-communities", "userAccount", userAccount);
                modelAndView.addObject("commList", communityService.getAccountCommunities(userAccount));

                return setupForeignAccountFlags(modelAndView, homeAccount, userAccount);
            } else {
                return new ModelAndView("/templates/profile-locked");
            }
        }
    }

    private ModelAndView configureBasicModelAndView(String viewName, String attributeName, Account account) {
        ModelAndView modelAndView = new ModelAndView(viewName);
        List<PostView> accountPosts = postService.getAccountPosts(account);
        modelAndView.addObject(attributeName, account);
        modelAndView.addObject(attributeName + "Id", account.getAccountID());
        modelAndView.addObject("accountPosts", accountPosts);

        return modelAndView;
    }

    private ModelAndView setupForeignAccountFlags(ModelAndView modelAndView, Account homeAccount, Account userAccount) {
        List<Account> homeAccountFriends = relationService.getAccountFriends(homeAccount);
        List<Account> homeAccountOutgoings = requestService.getOutgoingAccounts(homeAccount);
        modelAndView.addObject("isUnpointed", !homeAccountFriends.contains(userAccount) &&
                !homeAccountOutgoings.contains(userAccount));
        modelAndView.addObject("isBlocked", relationService.getAccountBlockedUsers(homeAccount).contains(userAccount));

        return modelAndView;
    }
}
