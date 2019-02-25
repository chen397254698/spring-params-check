package com.chen.restful.response;


import com.chen.restful.constant.ResponseCode;

/**
 * Created by chen on 2017/4/21.
 */
public class MissParamFailResult extends FailResult {

    public MissParamFailResult() {
        this("请求失败，参数缺失或参数类型错误");
    }

    public MissParamFailResult(String message) {
        super(ResponseCode.FAIL_MISS_PARAM, message);
    }

    public static MissParamFailResult InsWithParam(String paramName) {
        return new MissParamFailResult("缺少参数:" + paramName);
    }
}
