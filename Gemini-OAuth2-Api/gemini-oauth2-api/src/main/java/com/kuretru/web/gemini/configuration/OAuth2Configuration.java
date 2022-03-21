package com.kuretru.web.gemini.configuration;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author 呉真(kuretru) <kuretru@gmail.com>
 */
//@Configuration
//@ConfigurationProperties("gemini.oauth2")
@Data
public class OAuth2Configuration {

    @Value("${web-url}")
    private String webUrl;

}
