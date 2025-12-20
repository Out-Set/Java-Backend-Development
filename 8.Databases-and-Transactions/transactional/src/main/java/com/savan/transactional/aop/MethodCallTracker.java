package com.savan.transactional.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class MethodCallTracker {

    @Pointcut("within(com..service.*) || within(com..repo.*)")
    public void logMethodPointCut(){ }

    @Around("logMethodPointCut()")
    public Object logOnMethodCall(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        // Before Method Call
        String name = proceedingJoinPoint.getSignature().getName();
        System.out.println("Method start: "+name);

        // Call Your Method
        Object returnValue = proceedingJoinPoint.proceed();
        // System.out.println("returnValue: "+returnValue);

        // After Method Call
        System.out.println("Method completed: "+name);

        return returnValue;
    }
}
