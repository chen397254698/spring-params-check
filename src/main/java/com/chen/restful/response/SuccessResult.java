package com.chen.restful.response;


import com.chen.restful.base.BaseResult;
import com.chen.restful.constant.ResponseCode;

/**
 * 请求成功
 * code 为 2 开头
 * Created by chen on 2017/4/17.
 */
public class SuccessResult extends BaseResult {

    private Object result;

    public static SuccessResult InsWithResult(Object result) {
        return new SuccessResult(result);
    }

    public static SuccessResult INS() {
        return new SuccessResult();
    }

    public static SuccessResult INS(Integer status, String message) {
        return new SuccessResult(status, message);
    }

    public static SuccessResult INS(Integer status, String message, Object result) {
        return new SuccessResult(status, message, result);
    }

    public SuccessResult() {
        super(ResponseCode.SUCCESS, "成功");
    }

    public SuccessResult(Integer status, String message) {
        super(status, message);
    }

    public SuccessResult(Object result) {
        this();
        this.result = result;
    }

    public SuccessResult(Integer status, String message, Object result) {
        super(status, message);
        this.result = result;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }

}
