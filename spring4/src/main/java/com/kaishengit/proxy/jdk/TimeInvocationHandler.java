package com.kaishengit.proxy.jdk;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class TimeInvocationHandler implements InvocationHandler {

    private Object object;
    public TimeInvocationHandler(Object object) {
        this.object = object;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        try {
            long startTime = System.currentTimeMillis();
            Object result = method.invoke(object, args);
            long endTime = System.currentTimeMillis();

            String className = object.getClass().getName();
            String methodName = method.getName();
            System.out.println(className + "." + methodName + " -> " + (endTime - startTime) + "ms");
            return result;
        } catch (Exception ex) {
            //....
        } finally {
            //
        }
        return null;
    }
}
