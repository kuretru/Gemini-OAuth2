package com.kuretru.web.gemini;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author 呉真(kuretru) <kuretru@gmail.com>
 */
@SpringBootApplication
@ComponentScan({"com.kuretru.web.gemini", "com.kuretru.api.common"})
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
