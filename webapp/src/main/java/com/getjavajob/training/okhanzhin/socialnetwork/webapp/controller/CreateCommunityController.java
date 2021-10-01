package com.getjavajob.training.okhanzhin.socialnetwork.webapp.controller;

import com.getjavajob.training.okhanzhin.socialnetwork.domain.Account;
import com.getjavajob.training.okhanzhin.socialnetwork.domain.Community;
import com.getjavajob.training.okhanzhin.socialnetwork.domain.dto.CommunityTransfer;
import com.getjavajob.training.okhanzhin.socialnetwork.service.CommunityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.support.ByteArrayMultipartFileEditor;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;

@Controller
@RequestMapping(value = "/community")
public class CreateCommunityController {
    private static final Logger logger = LoggerFactory.getLogger(CreateCommunityController.class);

    private final CommunityService communityService;

    @Autowired
    public CreateCommunityController(CommunityService communityService) {
        this.communityService = communityService;
    }

    @InitBinder
    protected void initBinder(ServletRequestDataBinder binder) {
        binder.registerCustomEditor(byte[].class,
                new ByteArrayMultipartFileEditor());
    }

    @RequestMapping(value = "/new", method = RequestMethod.GET)
    public ModelAndView showCreateCommunityForm() {
        return new ModelAndView("homeProfile-create-community", "communityTransfer", new CommunityTransfer());
    }

    @RequestMapping(value = "/new", method = RequestMethod.POST, consumes = "multipart/form-data")
    public String createCommunity(@ModelAttribute("community") CommunityTransfer communityTransfer,
                                  @SessionAttribute("homeAccount") Account commOwner,
                                  @RequestParam(value = "picture", required = false) MultipartFile picture) throws IOException {
        if (communityTransfer.getCommunityName() == null) {
            return "homeProfile-create-community";
        } else {
            if (!picture.isEmpty()) {
                communityTransfer.setPicAttached(true);
                communityTransfer.setPicture(picture.getBytes());
            } else {
                communityTransfer.setPicAttached(false);
            }

            Community storedCommunity = communityService.create(communityTransfer);
            communityService.createOwner(commOwner, storedCommunity);
            logger.info("Community ID {} has been created, owner ID {}.",
                    storedCommunity.getCommID(), commOwner.getAccountID());

            return "redirect:/community/" + storedCommunity.getCommID();
        }
    }
}
