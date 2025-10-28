package com.tian.usercenter.service;
import java.util.Date;

import com.tian.usercenter.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
class UserServiceTest {
    @Resource
    private UserService userService;

    @Test
    void testAddUser(){
        User user = new User();
        user.setUsername("qwer");
        user.setUserAccount("1234");
        user.setAvatarUrl("https://www.codefather.cn/_next/image?url=%2Fimages%2Flogo.png&w=256&q=75");
        user.setGender(0);
        user.setUserPassword("123456");
        user.setPhone("123");
        user.setEmail("123");
        user.setUserStatus(0);
        user.setCreateTime(new Date());
        user.setUpdateTime(new Date());

        boolean result = userService.save(user);
        System.out.println(user.getId());
        Assertions.assertTrue(result);
    }

    @Test
    void userRegister() {
        String userAccount = "tttt";
        String userPassword = "";
        String checkPassword = "123456";
        long reuslt = userService.userRegister(userAccount, userPassword, checkPassword);
//        Assertions.assertEquals(-1, reuslt);

//        userAccount = "tt";
//        reuslt = userService.userRegister(userAccount, userPassword, checkPassword);
//        Assertions.assertEquals(-1, reuslt);

//        userAccount = "tttt";
//        userPassword = "12345678";
//        reuslt = userService.userRegister(userAccount, userPassword, checkPassword);
//        Assertions.assertEquals(-1, reuslt);

//        userAccount = "tt tt";
//        userPassword = "123456";
//        reuslt = userService.userRegister(userAccount, userPassword, checkPassword);
//        Assertions.assertEquals(-1, reuslt);
//
//        checkPassword = "123456789";
//        reuslt = userService.userRegister(userAccount, userPassword, checkPassword);
//        Assertions.assertEquals(-1, reuslt);
//
//        userAccount = "1234";
//        checkPassword = "123456";
//        reuslt = userService.userRegister(userAccount, userPassword, checkPassword);
//        Assertions.assertEquals(-1, reuslt);

        userAccount = "tttt";
        userPassword = "12345678";
        checkPassword = "12345678";
        reuslt = userService.userRegister(userAccount, userPassword, checkPassword);
        System.out.println(reuslt);
//        Assertions.assertTrue(reuslt > 0);
    }
}