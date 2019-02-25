package com.chen.restful.response;


import com.chen.restful.constant.ResponseCode;

/**
 * Created by chen on 2017/4/21.
 */
public class UpdateFailResult extends FailResult {

    public static UpdateFailResult INS() {
        return new UpdateFailResult();
    }

    public static UpdateFailResult InsWithMsg(String msg) {
        return new UpdateFailResult(msg);
    }

    public UpdateFailResult() {
        super(ResponseCode.FAIL_ADD, "更新失败，参数错误或者没有指定的对象");
    }

    public UpdateFailResult(String message) {
        super(ResponseCode.FAIL_ADD, message);
    }
}
