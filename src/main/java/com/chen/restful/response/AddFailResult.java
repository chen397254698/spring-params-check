package com.chen.restful.response;

import com.chen.restful.constant.ResponseCode;

/**
 * Created by chen on 2017/4/21.
 */
public class AddFailResult extends FailResult {

    public AddFailResult() {
        super(ResponseCode.FAIL_ADD, "添加失败，参数缺失或参数类型错误");
    }

    public static AddFailResult InsWithMsg(String message) {
        return new AddFailResult(message);
    }

    public static AddFailResult INS() {
        return new AddFailResult();
    }

    public AddFailResult(String message) {
        super(ResponseCode.FAIL_ADD, message);
    }
}
