package com.chen.restful.response;


import com.chen.restful.constant.ResponseCode;

/**
 * Created by chen on 2017/4/21.
 */
public class WrongParamFailResult extends FailResult {

    public WrongParamFailResult() {
        this("请求失败，参数不匹配导致错误");
    }

    public WrongParamFailResult(String message) {
        super(ResponseCode.FAIL_WRONG_PARAM, message);
    }

}
