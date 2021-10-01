package com.getjavajob.training.okhanzhin.socialnetwork.webapp.controller;

import com.getjavajob.training.okhanzhin.socialnetwork.domain.Account;
import com.getjavajob.training.okhanzhin.socialnetwork.domain.Community;
import com.getjavajob.training.okhanzhin.socialnetwork.domain.dto.SearchEntry;
import com.getjavajob.training.okhanzhin.socialnetwork.service.SearchService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class SearchController {
    private static final String ACCOUNT_TAB = "accounts";
    private static final String COMMUNITY_TAB = "communities";
    private static final int START_PAGE_NUMBER = 1;
    private static final Logger logger = LoggerFactory.getLogger(SearchController.class);

    private final SearchService searchService;

    @Autowired
    public SearchController(SearchService searchService) {
        this.searchService = searchService;
    }

    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public ModelAndView showSearchPage(@RequestParam(value = "value") String value) {
        List<Account> accounts = searchService.searchForAccounts(value, START_PAGE_NUMBER);
        List<Community> communities = searchService.searchForCommunities(value, START_PAGE_NUMBER);

        ModelAndView modelAndView = new ModelAndView("homeProfile-search");

        modelAndView.addObject("value", value);
        modelAndView.addObject("accounts", accounts);
        modelAndView.addObject("accountNumOfPages",
                searchService.getNumberOfPagesForAccountEntries(value));
        modelAndView.addObject("communities", communities);
        modelAndView.addObject("commNumOfPages",
                searchService.getNumberOfPagesForCommEntries(value));

        if (accounts.size() != 0) {
            modelAndView.addObject("activeTab", ACCOUNT_TAB);
        } else if (communities.size() != 0) {
            modelAndView.addObject("activeTab", COMMUNITY_TAB);
        } else {
            return new ModelAndView("/templates/no-search-results");
        }

        return modelAndView;
    }

    @RequestMapping(value = "/searchAccounts", method = RequestMethod.GET)
    @ResponseBody
    public List<Account> searchAccounts(@RequestParam(value = "value") String value,
                                        @RequestParam(value = "currentPage") int currentPage) {
        logger.debug("Accounts search : {}", value);

        return searchService.searchForAccounts(value, currentPage);
    }

    @RequestMapping(value = "/searchCommunities", method = RequestMethod.GET)
    @ResponseBody
    public List<Community> searchCommunities(@RequestParam(value = "value") String value,
                                             @RequestParam(value = "currentPage") int currentPage) {
        logger.debug("Communities search : {}", value);

        return searchService.searchForCommunities(value, currentPage);
    }

    @RequestMapping(value = "/search-ajax", method = RequestMethod.GET)
    @ResponseBody
    public List<SearchEntry> searchAjax(@RequestParam(value = "value") String value) {
        logger.debug("Autocomplete search : {}", value);
        return searchService.getSearchEntries(value);
    }
}
