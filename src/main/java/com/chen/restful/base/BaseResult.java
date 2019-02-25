package com.chen.restful.base;

import lombok.Data;

/**
 * 接口请求返回的基类
 * status 分为三类  200  300  400
 * 2 开头为成功， 3开头为用户可见的失败， 4，系统错误导致异常，用户不可见。
 * <p>
 * Created by chen on 2017/4/17.
 */

@Data
public class BaseResult {

    protected Integer status;

    protected String message;

    public BaseResult(Integer status, String message) {
        this.status = status;
        this.message = message;
    }

    public boolean isSuccess() {
        return status != null && status >= 200 && status < 300;
    }
}
