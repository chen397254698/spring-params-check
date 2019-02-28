package com.chen.restful.annotation;


import com.chen.restful.enums.CheckType;

import java.lang.annotation.*;

/**
 * Created by chen on 2017/8/14.
 * 对@RequestBody 注解的对象进行校验，对参数进行非空判断等, 可以重复注解
 */

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Repeatable(Checks.class)
public @interface CheckParam {

    /**
     * 待校验参数,不指定参数则校验所有参数
     * @return
     */
    String propertyName();

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
     * 数字参数范围如：(1,10),(1,10],[1,10]，无穷用*表示:如[1,*]
     * @return
     */
    String range() default "";


    /**
     * 校验类型
     * @return
     */
    CheckType checkType() default CheckType.NOT_NULL;

    /**
     * 触发校验的条件，默认无条件触发
     * @return
     */
    Condition[] conditions() default {};


}
