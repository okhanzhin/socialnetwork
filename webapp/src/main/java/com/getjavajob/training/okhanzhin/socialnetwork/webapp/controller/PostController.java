package com.getjavajob.training.okhanzhin.socialnetwork.webapp.controller;

import com.getjavajob.training.okhanzhin.socialnetwork.domain.Account;
import com.getjavajob.training.okhanzhin.socialnetwork.domain.Community;
import com.getjavajob.training.okhanzhin.socialnetwork.domain.post.AccountPost;
import com.getjavajob.training.okhanzhin.socialnetwork.domain.post.CommunityPost;
import com.getjavajob.training.okhanzhin.socialnetwork.domain.post.Post;
import com.getjavajob.training.okhanzhin.socialnetwork.service.AccountService;
import com.getjavajob.training.okhanzhin.socialnetwork.service.CommunityService;
import com.getjavajob.training.okhanzhin.socialnetwork.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;

@Controller
public class PostController {
    private final PostService postService;
    private final AccountService accountService;
    private final CommunityService communityService;

    @Autowired
    public PostController(PostService postService, AccountService accountService,
                          CommunityService communityService) {
        this.postService = postService;
        this.accountService = accountService;
        this.communityService = communityService;
    }

    @RequestMapping(value = "/account/{accountId}/publish", method = RequestMethod.POST)
    public String publishAccountPost(@SessionAttribute("homeAccount") Account source,
                                     @PathVariable("accountId") long targetID,
                                     @RequestParam("postType") String postType,
                                     @RequestParam("content") String content) {
        Account target = accountService.getById(targetID);
        Post post = new AccountPost(source, target, content);
        postService.publishPost(post);

        return "redirect:/account/" + targetID;
    }

    @RequestMapping(value = "/community/{commId}/publish", method = RequestMethod.POST)
    public String publishGroupPost(@SessionAttribute("homeAccount") Account source,
                                   @PathVariable("commId") long targetID,
                                   @RequestParam("postType") String postType,
                                   @RequestParam("content") String content) {
        Community target = communityService.getById(targetID);
        Post post = new CommunityPost(source, target, content);
        postService.publishPost(post);

        return "redirect:/community/" + targetID;
    }
}
