package com.chen.restful.annotation;


import com.chen.restful.enums.CheckType;

import java.lang.annotation.*;

/**
 * Created by chen on 2017/8/14.
 * 对@RequestBody 注解的对象进行简单校验，对参数进行非空判断等, 可以重复注解
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Repeatable(MultiChecks.class)
public @interface CheckParams {

    /**
     * 待校验参数,所有都不指定参数则校验所有参数
     * @return
     */
    Class paramsBean() default Void.class;


    /**
     * 待校验参数,所有都不指定参数则校验所有参数
     * @return
     */
    String[] propertiesName() default {};


    /**
     * 校验除了(不包括)指定验参数参数外的参数
     * @return
     */
    String[] exceptPropertiesName() default {};

    /**
     * 校验类型
     * @return
     */
    CheckType checkType() default CheckType.NOT_NULL;
}
