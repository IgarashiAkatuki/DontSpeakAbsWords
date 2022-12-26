package com.project.common.exceptions;


import com.project.common.response.ErrorInfo;
import com.project.common.response.ResponseStatusCode;
import com.project.common.response.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletRequest;
import java.util.List;

// 开启全局异常处理
@ControllerAdvice
@Slf4j
public class ExceptionsHandler {


    // 处理参数校验异常
    @ResponseBody
    @ExceptionHandler(BindException.class)
    public Result bindExceptionHandler(ServletRequest request, BindException exception){

        String s = exception.toString();
        List<FieldError> fieldErrors = exception.getFieldErrors();
        String defaultMessage = fieldErrors.get(0).getDefaultMessage();

        log.warn("=> "+s);
        log.warn("=> "+defaultMessage);

        return Result.error(new ErrorInfo(ResponseStatusCode.INVALID_PARAMETER.getResultCode(), defaultMessage));
    }

    // 处理空指针异常
    @ResponseBody
    @ExceptionHandler(NullPointerException.class)
    public void NullPointerExceptionHandler(ServletRequest request,NullPointerException exception){

        String message = exception.toString();
        log.error("=> "+message);
    }

    // 处理全局所有异常
    @ResponseBody
    @ExceptionHandler(RuntimeException.class)
    public void GlobalExceptionHandler(RuntimeException e){
        e.printStackTrace();
        log.error("=> "+e.toString());
    }
}
