package com.getjavajob.training.okhanzhin.socialnetwork.webapp.controller;

import com.getjavajob.training.okhanzhin.socialnetwork.domain.Account;
import com.getjavajob.training.okhanzhin.socialnetwork.domain.Community;
import com.getjavajob.training.okhanzhin.socialnetwork.domain.request.Request;
import com.getjavajob.training.okhanzhin.socialnetwork.service.AccountService;
import com.getjavajob.training.okhanzhin.socialnetwork.service.CommunityService;
import com.getjavajob.training.okhanzhin.socialnetwork.service.RequestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;

import static java.lang.Long.*;

@Controller
@RequestMapping(value = "/community")
public class MemberProcessController {
    private static final Logger logger = LoggerFactory.getLogger(MemberProcessController.class);

    private final AccountService accountService;
    private final CommunityService communityService;
    private final RequestService requestService;

    @Autowired
    public MemberProcessController(AccountService accountService,
                                   CommunityService communityService,
                                   RequestService requestService) {
        this.accountService = accountService;
        this.communityService = communityService;
        this.requestService = requestService;
    }

    @RequestMapping(value = "/{commId}/deleteUser", method = RequestMethod.GET)
    public String deleteUser(@PathVariable(value = "commId") long commID,
                             @SessionAttribute("homeAccount") Account homeAccount,
                             @SessionAttribute("homeAccountId") long homeAccountID,
                             @RequestParam(value = "accountId", required = false) String accountID) {
        Request request;
        Community community = communityService.getById(commID);
        if (accountID != null) {
            Account numberAccount = accountService.getById(parseLong(accountID));
            request = requestService.getCommunityRequest(numberAccount, community);
            communityService.deleteMemberFromCommunity(numberAccount.getAccountID(), commID);
        } else {
            request = requestService.getCommunityRequest(homeAccount, community);
            communityService.deleteMemberFromCommunity(homeAccountID, commID);
        }

        requestService.setRequestUnconfirmed(request);

        return "redirect:/community/" + commID;
    }

    @RequestMapping(value = "/{commId}/makeModerator", method = RequestMethod.GET)
    public String makeModerator(@PathVariable(value = "commId") long commID,
                                @RequestParam(value = "accountId") long accountID) {
        communityService.setModerator(accountID, commID);
        logger.info("Account ID {} - moderator of Community ID {}", accountID, commID);

        return "redirect:/community/" + commID;
    }

    @RequestMapping(value = "/{commId}/makeUser", method = RequestMethod.GET)
    public String makeUser(@PathVariable(value = "commId") long commID,
                           @RequestParam(value = "accountId") long accountID) {
        communityService.setUser(accountID, commID);
        logger.info("Account ID {} - user of Community ID {}", accountID, commID);

        return "redirect:/community/" + commID;
    }
}
