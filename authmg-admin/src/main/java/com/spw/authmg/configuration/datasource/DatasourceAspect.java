package com.spw.authmg.configuration.datasource;

import com.spw.authmg.constant.Constants;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.PriorityOrdered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Optional;

/**
 * @Description 数据源切换 aop
 * @Author spw
 * @Date 2021/3/28
 */
@Order(-1)
@Slf4j
@Aspect
@Component
public class DatasourceAspect {
    /**
     * 拦截 mapper包下及带有datasource注解文件
     * 多数据源与@transactional事务冲突，只能通过在前一层拦截
     * @param joinPoint
     */
    @Before(value = "execution(* com.spw.authmg.service.*.*(..))" +
            " || @annotation(com.spw.authmg.configuration.datasource.DataSource)")
    public void before(JoinPoint joinPoint) {
        log.info("数据源拦截 ===> begin...");
        //获取类上的注解
        DataSource clazzDatasource = (DataSource) joinPoint.getSignature().getDeclaringType().getAnnotation(DataSource.class);
        //获取方法上注解
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method method = methodSignature.getMethod();
        DataSource methodDatasource = method.getAnnotation(DataSource.class);
        //获取方法名称
        String methodName = method.getName();
        /*
            根据方法名前缀自动切换数据源
            insert、save、update、delete  -> master
            find、query、count、select -> slave
         */
        if (methodName.contains(Constants.MethodRule.INSERT) || methodName.contains(Constants.MethodRule.SAVE) || methodName.contains(Constants.MethodRule.UPDATE)
                || methodName.contains(Constants.MethodRule.DELETE)) {
            DatasourceContextHolder.setDatasource(Constants.DatasourceType.MASTER);
        } else if (methodName.contains(Constants.MethodRule.FIND) || methodName.contains(Constants.MethodRule.QUERY)
                || methodName.contains(Constants.MethodRule.COUNT) || methodName.contains(Constants.MethodRule.SELECT)) {
            DatasourceContextHolder.setDatasource(Constants.DatasourceType.SLAVE);
        } else {
            DatasourceContextHolder.setDatasource(Constants.DatasourceType.MASTER);
        }
        //根据类和方法上的注解切换数据源
        if (Optional.ofNullable(clazzDatasource).isPresent()) {
            DatasourceContextHolder.setDatasource(clazzDatasource.value());
        }
        if (Optional.ofNullable(methodDatasource).isPresent()) {
            DatasourceContextHolder.setDatasource(methodDatasource.value());
        }
        log.info("数据源拦截 ===> end...");
    }

}
