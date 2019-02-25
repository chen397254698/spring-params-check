package com.chen.restful.annotation;


import com.chen.restful.enums.CheckType;

import java.lang.annotation.*;

/**
 * Created by chen on 2017/8/14.
 * 只支持简单对象，对复杂对象注解会出错
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface CheckRule {

    /**
     * 正则表达式，校验规则
     * @return
     */
    String regEx() default "";

    /**
     * 参数描述
     * @return
     */
    String description() default "";

    /**
     * 校验类型
     * @return
     */
    CheckType checkType() default CheckType.NOT_NULL;
}
