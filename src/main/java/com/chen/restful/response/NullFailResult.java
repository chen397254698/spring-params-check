package com.chen.restful.response;


import com.chen.restful.constant.ResponseCode;

/**
 * Created by chen on 2017/4/21.
 */
public class NullFailResult extends FailResult {

    public NullFailResult() {
        this("请求结果不存在，参数缺失或参数类型错误");
    }

    public NullFailResult(String message) {
        super(ResponseCode.FAIL_NULL, message);
    }

    public static NullFailResult InsWithParam(String resultName) {
        return new NullFailResult("请求的" + resultName + "不存在，参数错误或者获取ID不存在");
    }
}
