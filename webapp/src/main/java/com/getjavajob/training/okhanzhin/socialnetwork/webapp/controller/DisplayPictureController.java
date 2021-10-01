package com.getjavajob.training.okhanzhin.socialnetwork.webapp.controller;

import com.getjavajob.training.okhanzhin.socialnetwork.domain.picture.Picture;
import com.getjavajob.training.okhanzhin.socialnetwork.service.PictureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static java.lang.Long.*;

@Controller
public class DisplayPictureController {
    private final PictureService pictureService;

    @Autowired
    public DisplayPictureController(PictureService pictureService) {
        this.pictureService = pictureService;
    }

    @RequestMapping(value = "/displayPicture", method = RequestMethod.GET)
    @ResponseBody
    public void displayPicture(@RequestParam(value = "accId", required = false) String accountID,
                               @RequestParam(value = "commId", required = false) String communityID,
                               HttpServletResponse response) throws IOException {
        Picture picture;

        if (accountID != null) {
            picture = pictureService.getAccountPicture(parseLong(accountID));
        } else {
            picture = pictureService.getCommunityPicture(parseLong(communityID));
        }

        response.setContentType("image");
        response.getOutputStream().write(picture.getContent());
    }
}