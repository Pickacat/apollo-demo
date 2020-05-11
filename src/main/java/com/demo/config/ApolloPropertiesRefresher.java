package com.demo.config;

import com.ctrip.framework.apollo.Config;
import com.ctrip.framework.apollo.ConfigChangeListener;
import com.ctrip.framework.apollo.ConfigService;
import com.ctrip.framework.apollo.model.ConfigChange;
import com.ctrip.framework.apollo.model.ConfigChangeEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.environment.EnvironmentChangeEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import javax.annotation.PostConstruct;
import java.util.Iterator;
import java.util.List;

public class ApolloPropertiesRefresher implements ApplicationContextAware, ConfigChangeListener {
    private static final Logger logger = LoggerFactory.getLogger(ApolloPropertiesRefresher.class);
    private ApplicationContext applicationContext;
    @Value("${apollo.bootstrap.namespaces}")
    private List<String> namespaces;

    public ApolloPropertiesRefresher() {
    }

    @PostConstruct
    public void init() {
        if (this.namespaces != null && !this.namespaces.isEmpty()) {
            Iterator var1 = this.namespaces.iterator();

            while(var1.hasNext()) {
                String namespace = (String)var1.next();
                Config config = ConfigService.getConfig(namespace);
                config.addChangeListener(this);
            }
        }

    }

    public void onChange(ConfigChangeEvent changeEvent) {
        Iterator var2 = changeEvent.changedKeys().iterator();

        while(var2.hasNext()) {
            String key = (String)var2.next();
            ConfigChange change = changeEvent.getChange(key);
            logger.info("Apollo change - {}", change.toString());
        }

        this.refreshProperties(changeEvent);
    }

    private void refreshProperties(ConfigChangeEvent changeEvent) {
        logger.info("Refreshing properties!");
        this.applicationContext.publishEvent(new EnvironmentChangeEvent(changeEvent.changedKeys()));
        logger.info("Properties refreshed!");
    }

    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}

