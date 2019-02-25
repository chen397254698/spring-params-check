package com.chen.restful.annotation;


import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface MultiChecks {

    /**
     * 多个带校验参数
     * @return
     */
    CheckParams[] value();

}
