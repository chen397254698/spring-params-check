
package com.chen.restful.response;

import com.chen.restful.constant.ResponseCode;
import com.chen.restful.base.BaseResult;

/**
 * 请求异常，用户不可见
 * code 为 4 开头
 * Created by chen on 2017/4/17.
 */
public class PermissonFailResult extends BaseResult {

    public PermissonFailResult() {
        super(ResponseCode.ERROR, "没有操作权限");
    }

    public PermissonFailResult(String message) {
        super(ResponseCode.ERROR, message);
    }


    public PermissonFailResult(Integer status, String message) {
        super(status, message);
    }
}
