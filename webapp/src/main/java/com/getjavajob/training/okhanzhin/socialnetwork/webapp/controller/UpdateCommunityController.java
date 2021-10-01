package com.getjavajob.training.okhanzhin.socialnetwork.webapp.controller;

import com.getjavajob.training.okhanzhin.socialnetwork.domain.Community;
import com.getjavajob.training.okhanzhin.socialnetwork.domain.dto.CommunityTransfer;
import com.getjavajob.training.okhanzhin.socialnetwork.service.CommunityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.support.ByteArrayMultipartFileEditor;

import javax.servlet.http.HttpSession;
import java.io.IOException;

@Controller
@RequestMapping(value = "/community/{commId}")
public class UpdateCommunityController {
    private static final Logger logger = LoggerFactory.getLogger(UpdateCommunityController.class);

    private final CommunityService communityService;

    @Autowired
    public UpdateCommunityController(CommunityService communityService) {
        this.communityService = communityService;
    }

    @InitBinder
    protected void initBinder(ServletRequestDataBinder binder) {
        binder.registerCustomEditor(byte[].class,
                new ByteArrayMultipartFileEditor());
    }

    @RequestMapping(value = "/update", method = RequestMethod.GET)
    public String showUpdateForm(@PathVariable String commId,
                                 @SessionAttribute(name = "activeCommunity") Community activeCommunity, Model model) {
        model.addAttribute("communityTransfer", communityService.getTransfer(activeCommunity));
        model.addAttribute("editableCommunity", activeCommunity);

        return "community-edit";
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST, consumes = "multipart/form-data")
    public String update(@ModelAttribute("communityTransfer") CommunityTransfer communityTransfer,
                         @ModelAttribute("editableCommunity") Community editedCommunity,
                         @PathVariable String commId,
                         @RequestParam(value = "picture", required = false) MultipartFile picture,
                         HttpSession session) throws IOException {
        setUpTransferPicture(communityTransfer, picture);
        Community updatedCommunity = communityService.update(communityTransfer);
        logger.info("Community ID {} has been successfully updated.", commId);

        session.setAttribute("activeCommunity", updatedCommunity);

        return "redirect:/community/" + updatedCommunity.getCommID();
    }

    private void setUpTransferPicture(CommunityTransfer communityTransfer, MultipartFile picture) throws IOException {
        if (picture.getBytes().length != 0) {
            communityTransfer.setPicAttached(true);
            communityTransfer.setPicture(picture.getBytes());
        } else {
            communityTransfer.setPicAttached(false);
        }
    }
}
