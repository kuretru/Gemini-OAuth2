package com.kuretru.web.gemini.configuration;

import com.kuretru.microservices.web.manager.AccessTokenManager;
import com.kuretru.microservices.web.manager.impl.InMemoryTokenAccessManagerImpl;
import com.kuretru.web.gemini.aspect.SimpleAuthorizationAspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author 呉真(kuretru) <kuretru@gmail.com>
 */
@Configuration
public class ApiCommonUtilsConfiguration {

    @Bean
    public AccessTokenManager accessTokenManager() {
        return new InMemoryTokenAccessManagerImpl();
    }

    @Bean
    @Autowired
    public SimpleAuthorizationAspect authorizationAspect(AccessTokenManager accessTokenManager) {
        return new SimpleAuthorizationAspect(accessTokenManager);
    }

}
