package com.chen.restful.base;


import lombok.Data;

/**
 * Created by chen on 2017/4/20.
 */

@Data
public class BasePageRequest extends BaseRequest{

    private int page = 1; //页码

    private int pageSize = 20; //每页记录数

    private String orderName; //排序名

    private String orderRule; //排序规则
}
