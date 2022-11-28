package com.layton.demojwt.common.exception;

public class UserNotExistException extends RuntimeException {

    private Integer code;
    private String message;

    public UserNotExistException() {
        this.code = ResultCodeEnum.USER_NOT_EXIST.getCode();
        this.message = ResultCodeEnum.USER_NOT_EXIST.getMessage();
    }

    public UserNotExistException(ResultCodeEnum resultCodeEnum) {
        this.code = resultCodeEnum.getCode();
        this.message = resultCodeEnum.getMessage();
    }

    public UserNotExistException(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
