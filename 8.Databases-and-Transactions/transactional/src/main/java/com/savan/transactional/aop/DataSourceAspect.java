package com.savan.transactional.aop;

import com.mysql.cj.jdbc.ConnectionImpl;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.lang.reflect.Proxy;
import java.sql.Connection;

@Component
@Aspect
public class DataSourceAspect {

    @Around("target(javax.sql.DataSource)")
    public Object loadDataSourceConnectionInfo(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        String name = proceedingJoinPoint.getSignature().getName();
        System.out.println("Method start: "+name);

        // @Transactional will call getConnection() internally and
        // the call will go through our method as we are targeting the DataSource obj
        Object returnValue = proceedingJoinPoint.proceed();
        System.out.println("DataSource tracker: "+returnValue);

        // Passing the captured connectionImpl object here to create a proxy out of this,
        // So that now all calls to the object will pass through our invocationHandler
        if(returnValue instanceof Connection) {
            return Proxy.newProxyInstance(
                    ConnectionImpl.class.getClassLoader(),
                    new Class[] {Connection.class}, new ConnectionInvocationHandler((Connection) returnValue)
            );
        }
        System.out.println("Method completed: "+name);
        return returnValue;
    }
}
