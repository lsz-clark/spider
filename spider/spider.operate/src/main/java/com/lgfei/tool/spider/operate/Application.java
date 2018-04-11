package com.lgfei.tool.spider.operate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.lgfei.tool.spider.common.web.servlet.RequestAcceptorServlet;
import com.lgfei.tool.spider.operate.dispatcher.OperateRequsetAcceptor;
import com.lgfei.tool.spider.operate.listener.MyApplicationEventListener;
import com.lgfei.tool.spider.operate.listener.PropertiesListener;

/**
 * SpringBoot应用启动类
 * <功能详细描述>
 * 
 * @author  lgfei
 * @version  [版本号, 2017年8月1日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
@EnableConfigurationProperties
@EnableScheduling
@SpringBootApplication
@ComponentScan("com.lgfei.tool.spider")
public class Application
{
    private static final Logger LOG = LoggerFactory.getLogger(Application.class);
    
    @Bean
    public ServletRegistrationBean servletRegistration()
    {
        ServletRegistrationBean servlet =
            new ServletRegistrationBean(new RequestAcceptorServlet(new OperateRequsetAcceptor()), "*.do");
        servlet.setLoadOnStartup(1);
        return servlet;
    }
    
    /**
     * Spring内置tomcat服务启动入口
     * <功能详细描述>
     * @param args 参数
     * @see [类、类#方法、类#成员]
     */
    public static void main(String[] args)
    {
        LOG.info("开始启动");
        SpringApplication app = new SpringApplication(Application.class);
        // 添加监听器
        app.addListeners(new MyApplicationEventListener());
        app.addListeners(new PropertiesListener("project-config.properties,mvcrawler-config.properties"));
        // 启动应用
        app.run(args);
        LOG.info("启动成功");
    }
    
}
