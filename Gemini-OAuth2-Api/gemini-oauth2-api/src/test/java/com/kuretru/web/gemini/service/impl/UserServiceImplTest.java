package com.kuretru.web.gemini.service.impl;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author 呉真(kuretru) <kuretru@gmail.com>
 */
@SpringBootTest
class UserServiceImplTest {

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImplTest(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Test
    void generatePassword() {
        String raw = "123456";
        assertEquals(68, passwordEncoder.encode(raw).length());
    }

}
