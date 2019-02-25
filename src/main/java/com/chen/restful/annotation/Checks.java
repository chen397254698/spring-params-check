package com.chen.restful.annotation;


import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface Checks {

    /**
     * 多个带校验参数
     * @return
     */
    CheckParam[] value();

}
