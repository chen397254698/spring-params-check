package com.chen.restful.enums;

public enum ConditionType {


    /**
     * 条件为null,唯一优先于正则生效
     */
    NULL(0),

    /**
     * 非为null
     */
    NOT_NULL(1),


    /**
     * 条件为空，如空字符""
     */
    EMPTY(2),


    /**
     * 条件为非空，如非空字符
     */
    NOT_EMPTY(3);



    int type;

    ConditionType(int type) {
        this.type = type;
    }
}
