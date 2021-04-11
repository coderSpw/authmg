package com.spw.authmg.configuration;

import com.spw.authmg.core.http.RespResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.ValidationException;
import java.util.Optional;

/**
 *  统一异常处理
 *  @author spw
 *  @date 2021/02/15
 */
@Slf4j
@ControllerAdvice
public class UnifyExceptionHandler {
    @ResponseBody
    @ExceptionHandler(Exception.class)
    public RespResult exceptionHandler(Exception e) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        //参数匹配异常处理
        BindingResult bindingResult = null;
        if (e instanceof BindException) {
            bindingResult = ((BindException) e).getBindingResult();
        } else if (e instanceof MethodArgumentNotValidException) {
            bindingResult = ((MethodArgumentNotValidException) e).getBindingResult();
        }
        if (Optional.ofNullable(bindingResult).isPresent() && bindingResult.hasErrors()) {
            return RespResult.failResult(bindingResult.getAllErrors().get(0).getDefaultMessage());
        }
        log.error("method:{} ===> error: {}", request.getMethod(), e.getMessage());
        return  RespResult.failResult(e.getMessage());
    }
}
