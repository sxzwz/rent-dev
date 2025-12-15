package com.rent.pojo.api;

/**
 * 响应基类
 * @param <T>
 */
public class Result<T> {
    private Integer code; //响应状态码
    private String message; //响应消息


    public Result() {
    }

    public Result(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    /**
     * 获取
     * @return code
     */
    public Integer getCode() {
        return code;
    }

    /**
     * 设置
     * @param code
     */
    public void setCode(Integer code) {
        this.code = code;
    }

    /**
     * 获取
     * @return message
     */
    public String getMessage() {
        return message;
    }

    /**
     * 设置
     * @param message
     */
    public void setMessage(String message) {
        this.message = message;
    }

    public String toString() {
        return "Result{code = " + code + ", message = " + message + "}";
    }
}
