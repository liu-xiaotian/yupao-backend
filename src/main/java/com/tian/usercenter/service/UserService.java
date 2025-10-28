package com.tian.usercenter.service;

import com.tian.usercenter.model.User;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpServletRequest;

/**
* @author Tian
* @description 针对表【user】的数据库操作Service
* @createDate 2025-10-11 16:15:07
*/
public interface UserService extends IService<User> {
    /**
     * 用户注册
     * @param userAccount
     * @param userPassword
     * @param checkPassword
     * @return
     */
    long userRegister(String userAccount, String userPassword, String checkPassword);

    /**
     * 用户登录
     * @param userAccount
     * @param userPassword
     * @return
     */
    User userLogin(String userAccount, String userPassword, HttpServletRequest request);

    /**
     * 脱敏
     * @param originUser
     * @return
     */
    User getSafetyUser(User originUser);
    /**
     * 用户注销
     * @param request
     * @return
     */
    int userLayout(HttpServletRequest request);
}
