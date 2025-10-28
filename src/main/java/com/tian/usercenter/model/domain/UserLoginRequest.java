package com.tian.usercenter.model.domain;

import lombok.Data;

import java.io.Serializable;

/**
 * 用户登录请求体
 */
@Data
public class UserLoginRequest implements Serializable {
    private static final long serialVersionUID = 7377981574938871300L;
    private String userAccount;
    private String userPassword;
}
