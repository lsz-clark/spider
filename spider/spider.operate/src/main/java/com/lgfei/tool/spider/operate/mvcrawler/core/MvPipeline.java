package com.lgfei.tool.spider.operate.mvcrawler.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

public class MvPipeline implements Pipeline
{
    private static Logger logger = LoggerFactory.getLogger(MvPipeline.class);
    
    @Override
    public void process(ResultItems arg0, Task arg1)
    {
        logger.debug("进入管道...");
    }
    
}
