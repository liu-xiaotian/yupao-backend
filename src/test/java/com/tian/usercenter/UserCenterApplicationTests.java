package com.tian.usercenter;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.DigestUtils;

@SpringBootTest
class UserCenterApplicationTests {

    @Test
    void contextLoads() {
    }

    @Test
    void testDigest(){
        String newPassword = DigestUtils.md5DigestAsHex(("123" + "tt").getBytes());
        System.out.println(newPassword);
    }
}
