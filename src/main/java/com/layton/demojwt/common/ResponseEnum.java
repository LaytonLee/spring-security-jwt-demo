package com.layton.demojwt.common;

/**
 * 响应结果枚举类
 */
public enum ResponseEnum {
    SUCCESS(200,"成功!"),

    REQUEST_INVALID_PARAMETER(422, "Invalid Parameters"),
    REQUEST_INVALID_TYPE(415, "Unsupported Media Type"),

    AUTH_UNAUTHORIZED(40100,"未认证"),
    AUTH_USERNAME_OR_PASSWORD_ERR(40101, "用户名或密码错误"),
    AUTH_USERNAME_NOT_EXIST(40102, "用户名不存在"),
    AUTH_TOKEN_EXPIRED(40103, "token已过期"),
    AUTH_TOKEN_VERIFY_FAILED(40104, "token验证失败"),
    AUTH_NO_AUTHORIZATION(40300,"无权限"),

    USER_REG_USERNAME_ALREADY_EXIST(40201, "用户名已存在"),
    USER_REG_USER_PASSWORD_CONFIRM(40202,"密码和确认密码不一致"),


    ORDER_FAIL(601,"订单失败"),
    ORDER_MESSAGE_FAIL(602,"订单发送消息失败") ;

    // 响应编码
    private Integer code;

    // 响应结果信息
    private String message;

    ResponseEnum(Integer code, String message){
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
