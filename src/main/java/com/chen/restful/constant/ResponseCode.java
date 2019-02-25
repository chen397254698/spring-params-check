package com.chen.restful.constant;

/**
 * 2 开头为成功， 3开头为用户可见的失败， 4，系统错误导致异常，用户不可见。
 * @author chen
 * @date 2017/4/17
 */
public class ResponseCode {

    /**请求成功*/
    public static final Integer SUCCESS = 200;

    /**请求成功 去到下一步*/
    public static final Integer SUCCESS_TO_NEXT = 201;

    /**批量请求中部分成功*/
    public static final Integer SUCCESS_NOT_ALL = 202;

    /**流程请求中结果成功，部分步骤失败*/
    public static final Integer SUCCESS_RESULT_FAIL_STEP = 203;

    /**请求成功 重定向至新的页面*/
    public static final Integer SUCCESS_TO_REDIRECT = 204;



    /**请求失败*/
    public static final Integer FAIL = 300;

    /**添加数据失败，一般是参数缺失或者参数错误*/
    public static final Integer FAIL_ADD = 301;

    /**参数缺失错误*/
    public static final Integer FAIL_MISS_PARAM = 303;

    /**NULL错误*/
    public static final Integer FAIL_NULL = 304;

    /**参数匹配错误*/
    public static final Integer FAIL_WRONG_PARAM = 305;

    /**发送短信失败*/
    public static final Integer FAIL_SEND_SMS = 306;

    /**参数过期*/
    public static final Integer FAIL_PARAM_EXPIRE = 307;




    /**请求错误*/
    public static final Integer ERROR = 400;

    /**未登录下无法操作*/
    public static final Integer ERROR_UNLOGIN = 401;

    /**NULL异常*/
    public static final Integer ERROR_NULL = 402;

    /**参数缺失或者参数异常*/
    public static final Integer ERROR_MISS_PARAM = 403;

    /**不支持的类型异常*/
    public static final Integer ERROR_NOT_SUPPORT = 404;

    /**没有操作权限*/
    public static final Integer ERROR_PERMISSION_DENY = 405;

    /**参数错误*/
    public static final Integer ERROR_WRONG_PARAM = 406;
}
