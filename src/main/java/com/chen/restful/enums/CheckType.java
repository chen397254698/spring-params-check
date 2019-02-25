package com.chen.restful.enums;

public enum CheckType {


    /**
     * 可为空，只在正则表达式校验时有效
     */
    NULL_ABLE(0),

    /**
     * 非为null
     */
    NOT_NULL(1),

    /**
     * 非空字符，非空整形等简单对象，可为空列表
     */
    NOT_EMPTY_SIMPLE(2),

    /**
     * 所有参数非空。
     */
    NOT_EMPTY(3);


    int type;

    CheckType(int type) {
        this.type = type;
    }
}
