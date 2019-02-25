package com.chen.restful.base;

import lombok.Data;

/**
 * Created by chen on 2017/4/20.
 */

@Data
public class BaseRequest {

    private Integer version = 1;

    /**
     * 设置来源端
     * 0:未知   1:来自安卓客户端  2：ios客户端   3:微信网页  4:QQ网页   5:新浪网页
     */
    private Integer clientType = 0;
}
