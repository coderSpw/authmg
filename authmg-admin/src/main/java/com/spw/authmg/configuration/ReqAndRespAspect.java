package com.spw.authmg.configuration;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

/**
 *  aop 请求日志处理
 *  @author spw
 *  @date 2021/02/15
 */
@Slf4j
@Aspect
@Component
public class ReqAndRespAspect {

    /**
     * 切入点
     * 修饰符 返回值类型 包名 类名 方法名 （请求参数）
     */
    @Pointcut("execution(public * com.spw.authmg.controller.*.*(..))")
    public void webLog() {}

    /**
     * 请求拦截
     * @param joinPoint
     */
    @Before("webLog()")
    public void doBefore(JoinPoint joinPoint) {
        /*MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();*/
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        //获取请求
        assert attributes != null;
        HttpServletRequest request = attributes.getRequest();
        //日志打印请求信息
        log.info("method: {} ====> args: {}", request.getMethod(), Arrays.toString(joinPoint.getArgs()));
    }

    /**
     * 响应正常拦截
     * @param ret
     */
    @AfterReturning(pointcut = "webLog()", returning = "ret")
    public void doAfterReturning(Object ret) {
       ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        assert attributes != null;
        HttpServletRequest request = attributes.getRequest();
        log.info("method: {} ====> response: {}", request.getMethod(), JSON.toJSONString(ret));
    }


}
