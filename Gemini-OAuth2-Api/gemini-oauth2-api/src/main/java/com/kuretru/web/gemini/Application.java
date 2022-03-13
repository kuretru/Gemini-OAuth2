package com.kuretru.web.gemini;

import com.kuretru.api.common.manager.AccessTokenManager;
import com.kuretru.api.common.manager.impl.InMemoryTokenAccessManagerImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/**
 * @author 呉真(kuretru) <kuretru@gmail.com>
 */
@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public static AccessTokenManager accessTokenManager() {
        return new InMemoryTokenAccessManagerImpl();
    }

}
