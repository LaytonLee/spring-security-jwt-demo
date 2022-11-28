package com.layton.demojwt.common.exception;

public class ErrorHandler {

    // ErrorHandler === R 答案：不想破坏R类。
    // 异常的状态码，从枚举中获得
    private Integer status;

    // 异常的消息，写用户看得懂的异常，从枚举中得到
    private String message;

    // 异常的名字
    private String exception;

    /**
     * 对异常处理进行统一封装
     *
     * @param resultCodeEnum 异常枚举
     * @param throwable 出现异常
     * @param message 异常的消息 null
     * @return
     */
    public static ErrorHandler fail(ResultCodeEnum resultCodeEnum, Throwable throwable, String message) {
        ErrorHandler errorHandler = ErrorHandler.fail(resultCodeEnum, throwable);
        errorHandler.setMessage(message);
        return errorHandler;
    }

    /**
     * 对异常枚举进行封装
     *
     * @param resultCodeEnum
     * @param throwable
     * @return
     */
    public static ErrorHandler fail(ResultCodeEnum resultCodeEnum, Throwable throwable) {
        ErrorHandler errorHandler = new ErrorHandler();
        errorHandler.setMessage(resultCodeEnum.getMessage());
        errorHandler.setStatus(resultCodeEnum.getCode());
        errorHandler.setException(throwable.getClass().getName());
        return errorHandler;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getException() {
        return exception;
    }

    public void setException(String exception) {
        this.exception = exception;
    }
}
