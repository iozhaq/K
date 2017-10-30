package com.kaishengit.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class AopAdvice {

    @Pointcut("execution(* com.kaishengit.service..*.*(..))")
    public void pointcut() {}

    @Before("pointcut()")
    public void beforeAdvice() {
        System.out.println("前置通知");
    }

    @AfterReturning(pointcut = "pointcut()",returning = "result")
    public void afterReturning(Object result) {
        System.out.println("后置通知 ->" + result);
    }

    @AfterThrowing(pointcut = "pointcut()",throwing = "ex")
    public void afterThrowing(Exception ex) {
        System.out.println("异常通知 -> " + ex.getMessage());
    }

    @After("pointcut()")
    public void after() {
        System.out.println("最终通知");
    }

   // @Around("pointcut()")
    public Object aroundAdvice(ProceedingJoinPoint joinPoint) {
        Object result = null;
        try {
            System.out.println("--------");
            result = joinPoint.proceed();//目标对象的方法执行
            System.out.println("=============");
        } catch (Throwable throwable) {
            System.out.println(">>>>>>");
        } finally {

        }
        return result;
    }

}
