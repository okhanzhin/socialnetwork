package com.getjavajob.training.okhanzhin.socialnetwork.webapp.controller;

import com.getjavajob.training.okhanzhin.socialnetwork.domain.Account;
import com.getjavajob.training.okhanzhin.socialnetwork.service.AccountService;
import com.getjavajob.training.okhanzhin.socialnetwork.service.RelationService;
import com.getjavajob.training.okhanzhin.socialnetwork.service.RequestService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttribute;

@Controller
@RequestMapping(value = "account/{accountId}")
public class FriendsController {
    private final AccountService accountService;
    private final RequestService requestService;
    private final RelationService relationService;

    public FriendsController(AccountService accountService, RequestService requestService,
                             RelationService relationService) {
        this.accountService = accountService;
        this.requestService = requestService;
        this.relationService = relationService;
    }

    @RequestMapping(value = "/friends", method = RequestMethod.GET)
    public String getFriendsAndRequests(@PathVariable("accountId") long accountID,
                                        @SessionAttribute(value = "homeAccount") Account homeAccount,
                                        @SessionAttribute(value = "homeAccountId") long homeAccountID, Model model) {
        Account currentAccount;
        boolean isHomeAccount = accountID == homeAccountID;

        if (isHomeAccount) {
            currentAccount = homeAccount;
        } else {
            currentAccount = accountService.getById(accountID);
            model.addAttribute("userAccount", currentAccount);
        }

        model.addAttribute("friends", relationService.getAccountFriends(currentAccount));
        model.addAttribute("pendingAccounts", requestService.getPendingAccounts(currentAccount));
        model.addAttribute("outgoingAccounts", requestService.getOutgoingAccounts(currentAccount));
        model.addAttribute("acceptedRequests", requestService.getAcceptedRequests(currentAccount));

        return (isHomeAccount) ? "homeProfile-friends" : "userProfile-friends";
    }
}
