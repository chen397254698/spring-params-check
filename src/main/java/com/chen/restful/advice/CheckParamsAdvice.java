package com.chen.restful.advice;


import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageConverter;
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

                String propertyName = methodAnnotation.propertyName();

                if (propertyName.length() > 0) {

                    Object property;

                    CheckType checkType = methodAnnotation.checkType();

                    String description = methodAnnotation.description();

                    String regExpression = methodAnnotation.regEx();

                    try {
                        property = BeanUtil.getProperty(body, propertyName);
                    } catch (Exception e) {
                        e.printStackTrace();
                        throw BaseException.INS(ResponseCode.ERROR_WRONG_PARAM, "指定校验属性'" + propertyName + "'不存在，请开发人员检查指定校验的参数名是否有误");
                    }

                    if (checkType != CheckType.NULL_ABLE && property == null) {
                        throw BaseException.INS(ResponseCode.ERROR_MISS_PARAM, "需要校验的参数'" + propertyName + "'不存在，参数'" + propertyName + "'描述：" + description + ",请提交参数后再试");
                    } else if (property != null && regExpression.length() > 0) {
                        Pattern pattern = Pattern.compile(regExpression);
                        Matcher matcher = pattern.matcher(property.toString());
                        boolean matches = matcher.matches();

                        if (matches) {
                            continue;
                        } else {
                            throw BaseException.INS(ResponseCode.ERROR_MISS_PARAM, "需要校验的参数'" + propertyName + "'不符合校验正则表达式" + regExpression + "，参数'" + propertyName + "'描述：" + description + ",请提交参数后再试");
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

                List<String> exceptPropertiesNameList = new ArrayList<>();

                String[] exceptPropertiesName = methodAnnotation.exceptPropertiesName();

                exceptPropertiesNameList.addAll(Arrays.asList(exceptPropertiesName));

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
                                    throw BaseException.INS(ResponseCode.FAIL_MISS_PARAM, "指定校验属性'" + propertyName + "'不存在，请开发人员检查指定校验的参数名是否有误");
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

}
