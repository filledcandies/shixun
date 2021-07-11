package com.example.myapp.entity;


import java.io.Serializable;

/**
 * 后端返回数据标准
 * @author yuanz
 * @param <T>
 */
public class Result<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 成功标识
     */
    private boolean success;

    /**
     * 失败消息
     */
    private String message;

    /**
     * 返回代码
     */
    private Integer code;

    /**
     * 时间戳
     */
    private Long timestamp;

    /**
     * 存储数据
     */
    private T result;

    public static Integer SUCCESS = 200;
    public static Integer FORBIDDEN = 403;
    public static Integer MOVED = 301;
    public static Integer BADREQUEST = 400;
    public static Integer UNAUTHORIZED = 401;
    public static Integer NOTFOUND = 404;
    public static Integer TIMEOUT = 408;
    public static Integer SERVERERROR = 500;
    public static Integer BADGATEWAY = 502;

    public Result(boolean success, String message, Integer code, T result) {
        this.success = success;
        this.message = message;
        this.code = code;
        this.result = result;
        this.timestamp = System.currentTimeMillis();
    }

    public static <T> Result<T> success(T data) {
        return new Result<>(true, "Success", SUCCESS, data);
    }

    public static <T> Result<T> fail(Integer code, String message, T data) {
        return new Result<>(false, message, code, data);
    }

    public static <T> Result<T> nullData(Integer code, String message) {
        return new Result<>(code.equals(SUCCESS), message, code, null);
    }

    public static <T> Result<T> nullData() {
        return nullData(SUCCESS, null);
    }

    public T get() {
        return result;
    }

    public static <T> Result<Object> successAndFail(boolean success, String successMsg, String failMsg) {
        if (success) {
            return nullData(SUCCESS, successMsg);
        } else {
            return nullData(UNAUTHORIZED, failMsg);
        }
    }

    public Result() {
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }
}
