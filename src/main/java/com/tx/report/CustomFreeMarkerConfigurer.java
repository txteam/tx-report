package com.tx.report;

import com.tx.report.directive.ConditionDirective;
import freemarker.template.Configuration;
import freemarker.template.TemplateException;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;

@Component
public class CustomFreeMarkerConfigurer implements ApplicationContextAware {
    private ApplicationContext applicationContext;

    @Autowired
    private Configuration configuration;

    @Autowired
    private ConditionDirective conditionDirective;

    @PostConstruct //在项目启动时执行方法
    public void setSharedVariable() throws IOException, TemplateException {
        // 将标签perm注册到配置文件
        configuration.setSharedVariable("condition", conditionDirective);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
