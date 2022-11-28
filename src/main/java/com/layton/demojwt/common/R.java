package com.layton.demojwt.common;

public class R {
    // 返回的编码
    private Integer code;

    // 返回的信息
    private String message;

    // 返回的数据
    private Object data;


    // 调用过程中，保持一种调用。直接用类去调用。
    private R() {

    }

    /**
     * 成功返回
     * @param data
     * @param message
     * @return
     */
    public static R success(Object data, String message) {
        R r = new R();
        r.setCode(ResponseEnum.SUCCESS.getCode());
        r.setData(data);
        r.setMessage(message == null ? ResponseEnum.SUCCESS.getMessage() : message);
        return r;
    }

    /**
     * 成功返回
     * @param data
     * @return
     */
    public static R success(Object data) {
        return success(data, null);
    }

    /**
     *
     * @param code
     * @param message
     * @return
     */
    public static R fail(Integer code, String message) {
        R r = new R();
        r.setCode(code);
        r.setData(null);
        r.setMessage(message);
        return r;
    }

    /**
     *
     * @param responseEnum
     * @return
     */
    public static R fail(ResponseEnum responseEnum) {
        R r = new R();
        r.setCode(responseEnum.getCode());
        r.setData(null);
        r.setMessage(responseEnum.getMessage());
        return r;
    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public Object getData() {
        return data;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
