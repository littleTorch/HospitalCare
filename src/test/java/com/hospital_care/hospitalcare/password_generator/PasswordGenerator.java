package com.hospital_care.hospitalcare.password_generator;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PasswordGenerator {

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * 用来生成加密后的密码，用于手动填入数据库
     */
    @Test
    public void getEncodePassword() {
        String encode = passwordEncoder.encode("123456");
        System.out.println("生成的密码为：" + encode);
    }

    @Test
    public void isPassWordTrue(){
        boolean matches = passwordEncoder.matches("123456", "$2a$10$WK3SjM1S0j5rSh1.3u2iruLWVxoUiX1Ve76cBAMO9S2rre4/mqJzS");
        System.out.println("密码正确？"+matches);
    }

}
