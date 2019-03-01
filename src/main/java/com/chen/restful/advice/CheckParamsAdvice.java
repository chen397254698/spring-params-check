package com.chen.restful.advice;


import com.chen.restful.annotation.Condition;
import com.chen.restful.enums.ConditionType;
import com.sun.istack.internal.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdvice;
import com.chen.restful.annotation.CheckParam;
import com.chen.restful.annotation.CheckParams;
import com.chen.restful.annotation.CheckRule;
import com.chen.restful.constant.ResponseCode;
import com.chen.restful.enums.CheckType;
import com.chen.restful.exception.BaseException;
import com.chen.restful.util.BeanUtil;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by chen on 2017/8/14.
 */

@Slf4j
@ControllerAdvice
public class CheckParamsAdvice implements RequestBodyAdvice {


    public boolean supports(MethodParameter methodParameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
        return true;
    }

    public Object handleEmptyBody(Object body, HttpInputMessage inputMessage, MethodParameter parameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
        return body;
    }


    public HttpInputMessage beforeBodyRead(HttpInputMessage inputMessage, MethodParameter parameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) throws IOException {
        return inputMessage;
    }


    public Object afterBodyRead(Object body, HttpInputMessage inputMessage, MethodParameter parameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {

        handleCheckParams(body, parameter);

        handleCheckParam(body, parameter);

        return body;
    }

    private void handleCheckParam(Object body, MethodParameter parameter) {

        CheckParam[] methodAnnotations = parameter.getMethod().getAnnotationsByType(CheckParam.class);
        if (methodAnnotations != null && methodAnnotations.length > 0) {
            for (CheckParam methodAnnotation : methodAnnotations) {

                Condition[] conditions = methodAnnotation.conditions();

                //判断参数校验的执行条件是否满足
                if (!handleConditions(body, conditions)) continue;

                String propertyName = methodAnnotation.propertyName();

                if (propertyName.length() > 0) {

                    Object property;

                    CheckType checkType = methodAnnotation.checkType();

                    String description = methodAnnotation.description();

                    String regExpression = methodAnnotation.regEx();

                    String range = methodAnnotation.range();

                    try {
                        property = BeanUtil.getProperty(body, propertyName);
                    } catch (Exception e) {
                        e.printStackTrace();
                        throw BaseException.INS(ResponseCode.FAIL_MISS_PARAM, "指定校验属性'" + propertyName + "'不存在，请开后台发人员检查指定校验的参数名是否有误");
                    }

                    if (checkType != CheckType.NULL_ABLE && property == null) {
                        throw BaseException.INS(ResponseCode.FAIL_MISS_PARAM, "需要校验的参数'" + propertyName + "'不存在，参数'" + propertyName + "'描述：" + description + ",请提交参数后再试");
                    } else if (property != null && (regExpression.length() > 0 || range.length() > 0)) {

                        if (range.length() > 0) {
                            if (!checkRange(range, property, propertyName, true)) {
                                throw BaseException.INS(ResponseCode.FAIL_WRONG_PARAM, "需要校验的参数'" + propertyName + "'不在区间：" + range + "内，参数'" + propertyName + "'描述：" + description + ",请提交参数后再试");
                            }
                        }

                        if (regExpression.length() > 0) {

                            Pattern pattern = Pattern.compile(regExpression);
                            Matcher matcher = pattern.matcher(property.toString());
                            boolean matches = matcher.matches();

                            if (matches) {
                                continue;
                            } else {
                                throw BaseException.INS(ResponseCode.FAIL_WRONG_PARAM, "需要校验的参数'" + propertyName + "'不符合校验正则表达式" + regExpression + "，参数'" + propertyName + "'描述：" + description + ",请提交参数后再试");
                            }
                        }

                    }

                    switch (checkType) {
                        case NOT_EMPTY:
                            if (property instanceof List) {
                                if (((List) property).size() <= 0) {
                                    throw BaseException.INS(ResponseCode.FAIL_MISS_PARAM, propertyName + "列表长度不能为0");
                                }
                            }
                        case NOT_EMPTY_SIMPLE:
                            if (property instanceof String) {
                                if (((String) property).length() <= 0) {
                                    throw BaseException.INS(ResponseCode.FAIL_MISS_PARAM, propertyName + "不能为空字符");
                                }
                            }
                        default:
                    }
                }
            }
        }

    }

    //处理复杂注解
    private void handleCheckParams(Object body, MethodParameter parameter) {

        CheckParams[] methodAnnotations = parameter.getMethod().getAnnotationsByType(CheckParams.class);

        if (methodAnnotations != null && methodAnnotations.length > 0) {

            for (CheckParams methodAnnotation : methodAnnotations) {

                Condition[] conditions = methodAnnotation.conditions();

                //判断参数校验的执行条件是否满足
                if (!handleConditions(body, conditions)) continue;

                Set<String> checkParamsNameSet = new HashSet<>();

                Class checkPropertiesBean = methodAnnotation.paramsBean();


                if (!checkPropertiesBean.equals(Void.class)) {

                    List<String> propertiesName = BeanUtil.getFieldName(checkPropertiesBean);

                    checkParamsNameSet.addAll(propertiesName);
                }

                String[] propertiesName = methodAnnotation.propertiesName();

                checkParamsNameSet.addAll(Arrays.asList(propertiesName));


                if (checkPropertiesBean.equals(Void.class) && propertiesName.length <= 0) {

                    List<String> allPropertiesName = BeanUtil.getFieldName(body.getClass());

                    checkParamsNameSet.addAll(allPropertiesName);

                }

                String[] exceptPropertiesName = methodAnnotation.exceptPropertiesName();

                List<String> exceptPropertiesNameList = new ArrayList<>(Arrays.asList(exceptPropertiesName));

                checkParamsNameSet.removeAll(exceptPropertiesNameList);

                if (checkParamsNameSet.size() > 0) {

                    for (String propertyName : checkParamsNameSet) {

                        Object property;

                        Field field = null;

                        CheckRule annotation = null;

                        if (!checkPropertiesBean.equals(Void.class)) {
                            try {
                                field = checkPropertiesBean.getDeclaredField(propertyName);
                            } catch (NoSuchFieldException e) {
                                e.printStackTrace();
                            }
                        }

                        if (field != null) {

                            annotation = field.getAnnotation(CheckRule.class);

                        }

                        if (annotation == null) {

                            try {
                                field = body.getClass().getDeclaredField(propertyName);
                            } catch (NoSuchFieldException e) {
                                e.printStackTrace();
                            }

                            if (field != null) {

                                annotation = field.getAnnotation(CheckRule.class);

                            }

                        }

                        CheckType checkType;

                        if (field != null) {

                            if (annotation != null) {

                                String regEx = annotation.regEx();
                                checkType = annotation.checkType();
                                String description = annotation.description();

                                try {
                                    property = BeanUtil.getProperty(body, propertyName);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                    throw BaseException.INS(ResponseCode.FAIL_MISS_PARAM, "指定校验属性'" + propertyName + "'不存在，请开后台发人员检查指定校验的参数名是否有误");
                                }

                                if (checkType != CheckType.NULL_ABLE && property == null) {
                                    throw BaseException.INS(ResponseCode.FAIL_MISS_PARAM, "需要校验的参数'" + propertyName + "'不存在，参数'" + propertyName + "'描述：" + description + ",请提交参数后再试");
                                } else if (property != null && regEx.length() > 0) {
                                    Pattern pattern = Pattern.compile(regEx);
                                    Matcher matcher = pattern.matcher(property.toString());
                                    boolean matches = matcher.matches();
                                    if (matches) {
                                        continue;
                                    } else {
                                        throw BaseException.INS(ResponseCode.FAIL_MISS_PARAM, "需要校验的参数'" + propertyName + "'不符合校验正则表达式" + regEx + "，参数'" + propertyName + "'描述：" + description + ",请提交参数后再试");
                                    }
                                }
                            }
                        }

                        try {
                            property = BeanUtil.getProperty(body, propertyName);
                        } catch (Exception e) {
                            e.printStackTrace();
                            throw BaseException.INS(ResponseCode.FAIL_MISS_PARAM, "指定校验属性'" + propertyName + "'不存在，请开发人员检查指定校验的参数名是否有误");
                        }

                        checkType = methodAnnotation.checkType();
                        switch (checkType) {
                            case NOT_EMPTY:
                                if (property instanceof List) {
                                    if (((List) property).size() <= 0) {
                                        throw BaseException.INS(ResponseCode.FAIL_MISS_PARAM, propertyName + "列表长度不能为0");
                                    }
                                }
                            case NOT_EMPTY_SIMPLE:
                                if (property instanceof String) {
                                    if (((String) property).length() <= 0) {
                                        throw BaseException.INS(ResponseCode.FAIL_MISS_PARAM, propertyName + "不能为空字符");
                                    }
                                }
                            case NOT_NULL:
                                if (property == null) {
                                    throw BaseException.INS(ResponseCode.FAIL_MISS_PARAM, "需要校验的参数'" + propertyName + "'不存在，请提交参数后再试");
                                }
                                break;
                            default:
                        }
                    }
                }
            }
        }
    }

    private boolean handleConditions(Object body, Condition[] conditions) {

        for (Condition condition : conditions) {

            Object property = null;

            String key = condition.key();
            String regEx = condition.regEx();
            ConditionType conditionType = condition.conditionType();
            String range = condition.range();

            try {
                property = BeanUtil.getProperty(body, key);
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (property == null) {
                if (conditionType != ConditionType.NULL) return false;
            } else if (Condition.BLANK_REGEX.equals(regEx)) {
                if (conditionType == ConditionType.NULL) {
                    return false;
                } else if (conditionType == ConditionType.EMPTY) {
                    if (!((property instanceof String && ((String) property).length() == 0)
                            || (property instanceof List && ((List) property).size() <= 0))) {
                        return false;
                    }
                } else if (conditionType == ConditionType.NOT_EMPTY) {
                    if (((property instanceof String && ((String) property).length() == 0)
                            || (property instanceof List && ((List) property).size() <= 0))) {
                        return false;
                    }
                }
            } else {
                Pattern pattern = Pattern.compile(regEx);
                Matcher matcher = pattern.matcher(property.toString());
                boolean matches = matcher.matches();
                if (!matches) {
                    return false;
                }
            }

            if (range.length() > 0 && property != null) {
                if (!checkRange(range, property, key, false)) {
                    return false;
                }
            }

        }

        return true;
    }

    public boolean checkRange(@NotNull String range, @NotNull Object property, String propertyName, boolean throwExce) {

        Float value = null;

        try {
            value = Float.parseFloat(property.toString().trim());

        } catch (NumberFormatException e) {
            e.printStackTrace();
            if (throwExce) {
                throw BaseException.INS(ResponseCode.ERROR_WRONG_PARAM, "判断rang参数:" + propertyName + "不是数字参数，无法做范围判断，请开后台发人员检查指定校验的参数是否有误");
            }else {
                return false;
            }
        }

        if (range.length() < 3) {
            throw BaseException.INS(ResponseCode.ERROR_WRONG_PARAM, "rang参数指定有误，请开后台发人员检查指定校验的rang是否有误");
        }

        String rangePattern = "^([\\[\\(])([0-9]*|[\\*]?),([0-9]*|[\\*]?)([\\]\\)])$";

        Pattern pattern = Pattern.compile(rangePattern);
        Matcher matcher = pattern.matcher(range.replaceAll(" ", ""));

        if (!matcher.matches()) {
            throw BaseException.INS(ResponseCode.ERROR_WRONG_PARAM, "rang参数指定有误，请开后台发人员检查指定校验的rang是否有误");
        } else {

            String start = "[";
            String end = "]";

            String left = null;
            String right = null;

            if (matcher.groupCount() > 0) {
                start = matcher.group(1);
                left = matcher.group(2) != null ? matcher.group(2) : null;
                right = matcher.group(3) != null ? matcher.group(3) : null;
                end = matcher.group(4);
            }

            if (left != null) {
                try {
                    float l = Float.parseFloat(left);

                    if ("[".equals(start)) {
                        if (value < l) return false;
                    } else {
                        if (value <= l) return false;
                    }
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
            }

            if (right != null) {
                try {
                    float r = Float.parseFloat(right);

                    if ("]".equals(end)) {
                        if (value > r) return false;
                    } else {
                        if (value >= r) return false;
                    }
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
            }

        }

        return true;

    }

}
