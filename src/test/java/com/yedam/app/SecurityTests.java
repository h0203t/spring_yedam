package com.yedam.app;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootTest
public class SecurityTests {

    @Autowired
    PasswordEncoder passwordEncoder;

    @Test
    public void passwordEncoder() {
        String pwd = "1234";

        String encPwd = passwordEncoder.encode(pwd);

        System.out.printf("변환된 값 : \n%s\n", encPwd);

        boolean result = passwordEncoder.matches(pwd, encPwd);
        System.out.printf("비교된 값 : " + result);
    }
}
