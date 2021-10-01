package com.getjavajob.training.okhanzhin.socialnetwork.webapp.controller;

import com.getjavajob.training.okhanzhin.socialnetwork.domain.Account;
import com.getjavajob.training.okhanzhin.socialnetwork.domain.Community;
import com.getjavajob.training.okhanzhin.socialnetwork.domain.request.Request;
import com.getjavajob.training.okhanzhin.socialnetwork.service.AccountService;
import com.getjavajob.training.okhanzhin.socialnetwork.service.CommunityService;
import com.getjavajob.training.okhanzhin.socialnetwork.service.RelationService;
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

@Controller
public class RequestController {
    private static final Logger logger = LoggerFactory.getLogger(RequestController.class);

    private final AccountService accountService;
    private final CommunityService communityService;
    private final RequestService requestService;
    private final RelationService relationService;

    @Autowired
    public RequestController(AccountService accountService, CommunityService communityService,
                             RequestService requestService, RelationService relationService) {
        this.accountService = accountService;
        this.communityService = communityService;
        this.requestService = requestService;
        this.relationService = relationService;
    }

    @RequestMapping(value = "/community/{commId}/follow", method = RequestMethod.GET)
    public String followCommunity(@SessionAttribute(value = "homeAccount") Account creator,
                                  @PathVariable(value = "commId") long recipientID) {
        Community recipient = communityService.getById(recipientID);
        Request request = requestService.createCommunityRequest(creator, recipient);

        logger.info("Creating Community Request: community ID - {}, account ID - {}", recipient.getCommID(), creator.getAccountID());
        logger.info(request.toString());

        return "redirect:/community/" + recipientID;
    }

    @RequestMapping(value = "community/{commId}/unfollow", method = RequestMethod.GET)
    public String unfollowCommunity(@SessionAttribute(value = "homeAccount") Account creator,
                                    @PathVariable(value = "commId") long recipientID) {
        Community recipient = communityService.getById(recipientID);
        Request request = requestService.getCommunityRequest(creator, recipient);
        requestService.deleteRequest(request);

        logger.info("Community request is deleted...");

        return "redirect:/community/" + recipientID;
    }

    @RequestMapping(value = "/account/{accountId}/follow", method = RequestMethod.GET)
    public String followAccount(@SessionAttribute("homeAccount") Account creator,
                                @PathVariable(value = "accountId") long recipientID) {
        Account recipient = accountService.getById(recipientID);
        Request request = requestService.createAccountRequest(creator, recipient);

        if (relationService.isRelationExist(creator, recipient)) {
            relationService.pendRelation(creator, recipient);
        } else {
            relationService.createRelation(creator, recipient);
        }

        logger.info("Creating Relation Request: creator ID - {}, recipient ID - {}", creator.getAccountID(), recipient.getAccountID());
        logger.info(request.toString());

        return "redirect:/account/" + recipientID;
    }

    @RequestMapping(value = "/community/{commId}/acceptRequest", method = RequestMethod.GET)
    public String acceptCommunityRequest(@RequestParam(value = "accountId") long creatorID,
                                         @PathVariable("commId") long recipientID) {
        Account creator = accountService.getById(creatorID);
        Community community = communityService.getById(recipientID);
        Request request = requestService.acceptCommunityRequest(creator, community);
        communityService.createMember(creatorID, recipientID);

        logger.info("Accepting Community Request: community ID - {}, account ID - {}", community.getCommID(), creator.getAccountID());
        logger.info(request.toString());

        return "redirect:/community/" + recipientID;
    }

    @RequestMapping(value = "/community/{commId}/declineRequest", method = RequestMethod.GET)
    public String declineCommunityRequest(@RequestParam(value = "accountId") long creatorID,
                                          @PathVariable("commId") long recipientID) {
        Account creator = accountService.getById(creatorID);
        Community community = communityService.getById(recipientID);
        Request request = requestService.getCommunityRequest(creator, community);
        requestService.deleteRequest(request);

        logger.info("Deleting Community Request: community ID - {}, account ID - {}", community.getCommID(), creator.getAccountID());
        logger.info(request.toString());

        return "redirect:/community/" + recipientID;
    }


    @RequestMapping(value = "/account/acceptPendingRequest", method = RequestMethod.GET)
    public String acceptPendingRequest(@RequestParam(value = "accountId") long creatorID,
                                       @SessionAttribute(value = "homeAccountId") long recipientID) {
        Account creator = accountService.getById(creatorID);
        Account recipient = accountService.getById(recipientID);
        Request request = requestService.acceptAccountRequest(creator, recipient);
        relationService.acceptRelation(creator, recipient);

        logger.info("Accepting Relation Request: creator ID - {}, recipient ID - {}", creator.getAccountID(), recipient.getAccountID());
        logger.info(request.toString());

        return "redirect:/account/" + recipientID + "/friends";
    }

    @RequestMapping(value = "/account/declinePendingRequest", method = RequestMethod.GET)
    public String declinePendingRequest(@RequestParam(value = "accountId") long creatorID,
                                        @SessionAttribute(value = "homeAccount") Account recipient) {
        Account creator = accountService.getById(creatorID);
        Request request = requestService.getAccountRequest(creator, recipient);
        requestService.deleteRequest(request);
        relationService.declineRelation(creator, recipient);

        logger.info("Deleting Relation Request: creator ID - {}, recipient ID - {}", creator.getAccountID(), recipient.getAccountID());
        logger.info(request.toString());

        return "redirect:/account/" + recipient.getAccountID() + "/friends";
    }

    @RequestMapping(value = "account/unfriend", method = RequestMethod.GET)
    public String unfriend(@SessionAttribute("homeAccountId") long homeAccountID,
                           @RequestParam(value = "creatorId") long creatorID,
                           @RequestParam(value = "recipientId") long recipientID) {
        Account creator = accountService.getById(creatorID);
        Account recipient = accountService.getById(recipientID);

        return unfriendProcess(creator, recipient, homeAccountID);
    }

    private String unfriendProcess(Account creator, Account recipient, long homeAccountID) {
        Request request = requestService.getAccountRequest(creator, recipient);

        if (request == null) {
            request = requestService.getAccountRequest(recipient, creator);
        }

        requestService.setRequestUnconfirmed(request);
        relationService.pendRelation(creator, recipient);

        logger.info("Break relationship...");

        return "redirect:/account/" + homeAccountID + "/friends";
    }

    @RequestMapping(value = "account/block", method = RequestMethod.GET)
    public String blockAccount(@SessionAttribute("homeAccount") Account blocker,
                               @SessionAttribute("homeAccountId") long homeAccountId,
                               @RequestParam(value = "accountId") long accountId) {
        Account recipient = accountService.getById(accountId);

        return blockAccountProcess(blocker, recipient, homeAccountId);
    }

    private String blockAccountProcess(Account blocker, Account recipient, long homeAccountID) {
        Request request = requestService.getAccountRequest(blocker, recipient);
        if (request == null) {
            request = requestService.getAccountRequest(recipient, blocker);
        }
        requestService.deleteRequest(request);
        relationService.blockRelation(blocker, recipient);

        logger.info("Block account...");

        return "redirect:/account/" + homeAccountID + "/friends";
    }
}
