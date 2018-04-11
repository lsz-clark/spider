package com.lgfei.tool.spider.operate.mvcrawler.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/mvcrawler")
public class PageController
{
    private static final Logger LOG = LoggerFactory.getLogger(PageController.class);
    
    @RequestMapping(value = "/mainpage", method = RequestMethod.GET)
    public String mainPage()
    {
        LOG.debug("PageController.mainPage begin");
        // some code
        LOG.debug("PageController.mainPage end");
        return "mvcrawler/main";
    }
    
    @RequestMapping(value = "/westpage", method = RequestMethod.GET)
    public String westPage()
    {
        LOG.debug("PageController.westPage begin");
        // some code
        LOG.debug("PageController.westPage end");
        return "mvcrawler/west";
    }
    
    @RequestMapping(value = "/northpage", method = RequestMethod.GET)
    public String northPage()
    {
        LOG.debug("PageController.northPage begin");
        // some code
        LOG.debug("PageController.northPage end");
        return "mvcrawler/north";
    }
    
    @RequestMapping(value = "/southpage", method = RequestMethod.GET)
    public String southPage()
    {
        LOG.debug("PageController.southPage begin");
        // some code
        LOG.debug("PageController.southPage end");
        return "mvcrawler/south";
    }
    
    @RequestMapping(value = "/task/listpage", method = RequestMethod.GET)
    public String taskListPage()
    {
        return "mvcrawler/tasklist";
    }
    
    @RequestMapping(value = "/website/listpage", method = RequestMethod.GET)
    public String websiteListPage()
    {
        return "mvcrawler/websitelist";
    }
    
    @RequestMapping(value = "/mv/listpage", method = RequestMethod.GET)
    public String mvListPage()
    {
        return "mvcrawler/mvlist";
    }
}
