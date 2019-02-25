package com.chen.restful.response;


import com.chen.restful.constant.ResponseCode;
import com.chen.restful.base.BaseResult;

/**
 * 请求异常，用户不可见
 * code 为 4 开头
 * Created by chen on 2017/4/17.
 */
public class ErrorResult extends BaseResult {

    public static ErrorResult InsWithMsg(String msg) {
        return new ErrorResult(ResponseCode.ERROR, msg);
    }

    public static ErrorResult INS(Integer status, String msg) {
        return new ErrorResult(status, msg);
    }

    public static ErrorResult INS(Exception e) {
        return new ErrorResult(ResponseCode.ERROR, e.getMessage());
    }

    public static ErrorResult INS() {
        return new ErrorResult();
    }

    public ErrorResult() {
        super(ResponseCode.ERROR, "请求异常");
    }

    public ErrorResult(String message) {
        super(ResponseCode.ERROR, message);
    }

    public ErrorResult(Integer status, String message) {
        super(status, message);
    }
}
