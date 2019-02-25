package com.chen.restful.response;


import com.chen.restful.constant.ResponseCode;
import com.chen.restful.base.BaseResult;

/**
 * 请求失败，用户可见
 * code 为 3 开头
 * Created by chen on 2017/4/17.
 */
public class FailResult extends BaseResult {

    public FailResult() {
        super(ResponseCode.FAIL, "请求失败");
    }

    public FailResult(String message) {
        super(ResponseCode.FAIL, message);
    }

    public FailResult(Integer status, String message) {
        super(status, message);
    }

    public static FailResult INS() {
        return new FailResult();
    }

    public static FailResult INS(Integer status, String message) {
        return new FailResult(status, message);
    }

    public static FailResult InsWithMsg(String msg) {
        return new FailResult(msg);
    }
}
