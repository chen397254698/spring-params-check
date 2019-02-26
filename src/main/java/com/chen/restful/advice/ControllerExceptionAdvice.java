package com.chen.restful.advice;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import com.chen.restful.base.BaseResult;
import com.chen.restful.constant.ResponseCode;
import com.chen.restful.exception.BaseException;
import com.chen.restful.response.ErrorResult;

/**
 * controller 异常捕获统一处理
 *
 * @author chen
 * @date 2017/10/30
 */
@ControllerAdvice
public class ControllerExceptionAdvice {

    /**
     * 系统异常捕捉处理
     *
     * @param e
     * @return BaseResult
     */
    @ResponseBody
    @ExceptionHandler(value = Exception.class)
    public BaseResult exceptionHandler(Exception e) {

        e.printStackTrace();

        String cause = "";

        if (e.getCause() != null) {
            cause = e.getCause().getMessage();
        }

        StackTraceElement[] stackTrace = e.getStackTrace();

        String des = "";

        String fileName = stackTrace[0].getFileName();
        String methodName = stackTrace[0].getMethodName();
        int lineNumber = stackTrace[0].getLineNumber();

        des = "错误发生在文件：" + fileName + " ,方法名为： " + methodName + " ,行数为： " + lineNumber;

        return ErrorResult.INS(ResponseCode.ERROR, "exception: " + e.toString()
                + ", message: " + e.getMessage()
                + ", cause: " + cause
                + "错误描述： " + des);
    }

    /**
     * 业务异常捕捉处理
     *
     * @param e
     * @return BaseResult
     */
    @ResponseBody
    @ExceptionHandler(value = BaseException.class)
    public BaseResult exceptionHandler(BaseException e) {

        e.printStackTrace();

        return ErrorResult.INS(e);
    }
}
