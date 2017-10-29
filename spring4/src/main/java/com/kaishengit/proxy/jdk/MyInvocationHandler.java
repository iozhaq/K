package com.kaishengit.proxy.jdk;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class MyInvocationHandler implements InvocationHandler {

    private Object target;
    public MyInvocationHandler(Object target) {
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method,
                         Object[] args) throws Throwable {

        System.out.println(">>>>>>>>>>>>>>");
        Object result = method.invoke(target,args);//代表目标对象方法的执行
        System.out.println("<<<<<<<<<<<<<<<");

        return result;
    }
}
