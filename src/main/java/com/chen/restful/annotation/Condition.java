package com.chen.restful.annotation;

import com.chen.restful.enums.CheckType;
import com.chen.restful.enums.ConditionType;

import java.lang.annotation.*;

/**
 *
 * 条件组织方式为AND,需要OR逻辑需要分开多个注解实现
 *
 * @Author: chen
 * @Date: 2019/2/27 10:54
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.ANNOTATION_TYPE)
@Inherited
public @interface Condition {

    String BLANK_REGEX = "<^&*^&_blank_regex_*^%>";

    /**
     * 判断条件的参数名，只能指定简单对象。否则不生效
     *
     * @return
     */
    String key() default "";

    /**
     * 判断条件的值，正则表达式，校验规则，正则优先。只有当regEx 的值为 BLANK_REGEX 时优先校验conditionType；
     *
     * @return
     */
    String regEx() default BLANK_REGEX;

    /**
     * 数字参数范围如：(1,10),(1,10],[1,10]，无穷用*,或空字符表示:如[1,*]，[1,]
     * @return
     */
    String range() default "";

    /**
     * 条件校验类型，判断条件在正则之后
     * @return
     */
    ConditionType conditionType() default ConditionType.NOT_NULL;



}
