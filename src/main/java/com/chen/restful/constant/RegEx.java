package com.chen.restful.constant;

/**
 * @Author: chen
 * @Date: 2019/2/28 17:23
 */
public class RegEx {

    /**
     * 11为手机号
     */
    public static final String MOBILE = "1[3-9][0-9]{9}";

    public static final String NUMBER = "\\d*";


    /**
     * 指定位数的数字，正则表达式
     *
     * @param length 数字位数
     * @return
     */
    public static String NUMBERS(Integer length) {
        return "\\d{" + length + "}";
    }
}
