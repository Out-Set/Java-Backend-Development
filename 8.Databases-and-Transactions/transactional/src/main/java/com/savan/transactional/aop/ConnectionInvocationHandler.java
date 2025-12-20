package com.savan.transactional.aop;

import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.sql.Connection;

/*
1. Create an invocation handler for dynamic proxy creation.
2. Print the method name is being used.
3. Call the close() / commit() / rollback() manually
 */

public class ConnectionInvocationHandler implements InvocationHandler {

    @Autowired
    private Connection connection;

    public ConnectionInvocationHandler(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if(method.getName().contains("commit") ||
           method.getName().contains("rollback") ||
           method.getName().contains("close")
        ) {
            System.out.println("Connection trace: "+method.toGenericString());
        }
        return method.invoke(connection,  args);
    }
}
