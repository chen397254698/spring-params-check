package com.chen.restful.response;


import com.chen.restful.constant.ResponseCode;
import com.chen.restful.base.BaseResult;

/**
 * 请求成功 网页重定向
 * code 为 2 开头
 * Created by chen on 2017/4/17.
 */
public class SucToRedirResult extends BaseResult {

    private String path;

    public static SucToRedirResult INS(String path) {
        return new SucToRedirResult(path);
    }

    public static SucToRedirResult INS(String message, String path) {
        return new SucToRedirResult(message, path);
    }

    public static SucToRedirResult INS(Integer status, String message, String path) {
        return new SucToRedirResult(status, message, path);
    }

    public SucToRedirResult() {
        super(ResponseCode.SUCCESS_TO_REDIRECT, "请求成功 重定向至新的页面");
    }

    public SucToRedirResult(String message, String path) {
        this(ResponseCode.SUCCESS_TO_REDIRECT, message, path);
    }

    public SucToRedirResult(Integer status, String message, String path) {
        super(status, message);
        this.path = path;
    }

    public SucToRedirResult(String path) {
        this();
        this.path = path;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
