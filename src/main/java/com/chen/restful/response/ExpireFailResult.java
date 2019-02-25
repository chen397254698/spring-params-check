package com.chen.restful.response;

import com.chen.restful.constant.ResponseCode;

/**
 * Created by chen on 2017/4/21.
 */
public class ExpireFailResult extends FailResult {

    public ExpireFailResult() {
        this("请求失败，参数超过有效期，请重新提交");
    }

    public ExpireFailResult(String message) {
        super(ResponseCode.FAIL_PARAM_EXPIRE, message);
    }

    public static ExpireFailResult InsWithParam(String paramName) {
        return new ExpireFailResult("参数:" + paramName + "， 已过期，请重新提交");
    }
}
