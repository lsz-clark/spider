package com.lgfei.tool.spider.operate.mvcrawler.scheduler;

import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.lgfei.tool.spider.common.cache.service.RedisService;
import com.lgfei.tool.spider.operate.mvcrawler.model.vo.MvInfoVO;
import com.lgfei.tool.spider.operate.mvcrawler.service.MvInfoService;

/**
 * 定时任务
 * <功能详细描述>
 * 
 * @author  Lgfei
 * @version  [版本号, 2017年11月11日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
@Component
public class MvCrawlerTimer
{
    private static Logger logger = LoggerFactory.getLogger(MvCrawlerTimer.class);
    
    @Autowired
    private MvInfoService mvInfoService;
    
    @Autowired
    private RedisService redisService;
    
    /**
     * 每天凌晨2点触发
     * <功能详细描述>
     * @see [类、类#方法、类#成员]
     */
    //@Scheduled(fixedRate = 60 * 1000)
    @Scheduled(cron = "0 0 2 * * ?")
    public void input()
    {
        Set<String> keys = null;
        try
        {
            keys = redisService.keys("mv:*");
            if (CollectionUtils.isEmpty(keys))
            {
                logger.debug("未发现缓存数据");
                return;
            }
            // 逐条将redis的数据同步至DB
            for (String cacheKey : keys)
            {
                MvInfoVO mvInfo = redisService.get(cacheKey, MvInfoVO.class);
                mvInfoService.insert(mvInfo);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            if (!CollectionUtils.isEmpty(keys))
            {
                redisService.mdel(keys);
            }
        }
    }
}
