package com.demo.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApolloAutoConfig {
    public ApolloAutoConfig() {
    }

    @Bean
    @ConditionalOnProperty(
            value = {"apollo.bootstrap.enabled"},
            havingValue = "true"
    )
    public ApolloPropertiesRefresher apolloPropertiesRefresher() {
        return new ApolloPropertiesRefresher();
    }
}
