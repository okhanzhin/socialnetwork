package com.getjavajob.training.okhanzhin.socialnetwork.webapp.controller;

import com.getjavajob.training.okhanzhin.socialnetwork.domain.Account;
import com.getjavajob.training.okhanzhin.socialnetwork.domain.Community;
import com.getjavajob.training.okhanzhin.socialnetwork.domain.Member;
import com.getjavajob.training.okhanzhin.socialnetwork.service.AccountService;
import com.getjavajob.training.okhanzhin.socialnetwork.service.CommunityService;
import com.getjavajob.training.okhanzhin.socialnetwork.service.PostService;
import com.getjavajob.training.okhanzhin.socialnetwork.service.RequestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/community")
public class CommunityController {
    private static final Logger logger = LoggerFactory.getLogger(CommunityController.class);

    private final AccountService accountService;
    private final CommunityService communityService;
    private final RequestService requestService;
    private final PostService postService;

    @Autowired
    public CommunityController(AccountService accountService,
                               CommunityService communityService,
                               RequestService requestService,
                               PostService postService) {
        this.accountService = accountService;
        this.communityService = communityService;
        this.requestService = requestService;
        this.postService = postService;
    }

    @RequestMapping(value = "/{commId}", method = RequestMethod.GET)
    public String showCommunityInfo(@PathVariable("commId") long commId,
                                    @SessionAttribute("homeAccount") Account homeAccount,
                                    Model model, HttpSession session) {
        Community activeCommunity = communityService.getById(commId);
        Map<String, Object> commPermissions = communityService.configureCommunityPermissions(homeAccount, activeCommunity);

        model.addAttribute("commPosts", postService.getCommunityPosts(activeCommunity));
        session.setAttribute("activeCommunity", activeCommunity);
        session.setAttribute("commPermissions", commPermissions);
        logger.info("Community ID {} visit by account ID {} with status {}.",
                commId, homeAccount.getAccountID(), commPermissions.get("visitorStatus"));

        return "community-info";
    }

    @RequestMapping(value = "/{commId}/members", method = RequestMethod.GET)
    public ModelAndView showCommunityMembers(@PathVariable("commId") long commId,
                                             @SessionAttribute(value = "activeCommunity") Community activeCommunity) {
        Member groupOwner = communityService.getOwner(activeCommunity);

        List<Account> memberAccounts = accountService.getAccountsListForGroup(activeCommunity);
        List<Member> moderatorMembers = communityService.getListOfModerators(commId);
        List<Account> moderatorAccounts = communityService.convertMembersToAccounts(moderatorMembers);

        ModelAndView modelAndView = new ModelAndView("community-members");
        modelAndView.addObject("ownerId", groupOwner.getAccount().getAccountID());
        modelAndView.addObject("accountsList", memberAccounts);
        modelAndView.addObject("moderatorsList", moderatorAccounts);

        return modelAndView;
    }

    @RequestMapping(value = "/{commId}/requests", method = RequestMethod.GET)
    public ModelAndView showCommunityRequests(@PathVariable("commId") long commId,
                                              @SessionAttribute(value = "activeCommunity") Community activeCommunity) {
        List<Account> unconfirmedAccounts = requestService.getRequestingAccounts(activeCommunity);

        ModelAndView modelAndView = new ModelAndView("community-requests");
        modelAndView.addObject("unconfirmedList", unconfirmedAccounts);

        return modelAndView;
    }
}
