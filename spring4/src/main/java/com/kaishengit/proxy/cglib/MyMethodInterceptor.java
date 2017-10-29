package com.kaishengit.proxy.cglib;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

public class MyMethodInterceptor implements MethodInterceptor {
    @Override
    public Object intercept(Object o, Method method,
                            Object[] objects, MethodProxy methodProxy) throws Throwable {
        System.out.println("---------------------------");
        Object result = methodProxy.invokeSuper(o,objects);
        System.out.println("===========================");
        return result;
    }
}
