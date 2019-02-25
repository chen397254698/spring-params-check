package com.chen.restful.request;

import com.chen.restful.base.BaseRequest;
import lombok.Data;

/**
 * @Author: chen
 * @Date: 2019/2/22 10:48
 */
@Data
public class StringIdRequest extends BaseRequest {

    private String id;
}
