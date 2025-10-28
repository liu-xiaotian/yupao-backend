package com.tian.usercenter.common;

/**
 * 错误码
 */
public enum ErrorCode {
    SUCCESS(0, "success", ""),
    PARAMS_ERROR(4000, "请求参数错误", ""),
    NULL_ERROR(4001, "请求参数为空", ""),
    NO_LOGIN(40100, "未登录", ""),
    NO_AUTH(40101, "无权限", ""),
    SYSTEM_ERROR(5000, "系统内部异常", "");

    private final int code;
    /**
     * 状态码信息
     */
    private final String message;
    /**
     * 状态码描述(详情)
     */
    private final String description;

    ErrorCode(int code, String message, String description){
        this.code = code;
        this.message = message;
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public String getMessage() {
        return message;
    }

    public int getCode() {
        return code;
    }
}
