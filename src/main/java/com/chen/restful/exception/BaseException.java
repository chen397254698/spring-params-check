package com.chen.restful.exception;

import com.chen.restful.constant.ResponseCode;

/**
 * Created by chen on 2017/8/7.
 */
public class BaseException extends RuntimeException {

    private Integer status;

    public static BaseException INS(Integer status, String message) {
        return new BaseException(status, message);
    }

    public static BaseException INS(String message) {
        return new BaseException(ResponseCode.ERROR, message);
    }

    public BaseException(Integer status, String message) {
        super(message, new Throwable(message));
        this.status = status;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
