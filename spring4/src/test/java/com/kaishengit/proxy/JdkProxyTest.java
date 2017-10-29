package com.kaishengit.proxy;

import com.kaishengit.proxy.jdk.MyInvocationHandler;
import com.kaishengit.proxy.jdk.TimeInvocationHandler;
import com.kaishengit.service.UserService;
import com.kaishengit.service.impl.UserServiceImpl;
import org.junit.Test;

import java.lang.reflect.Proxy;

public class JdkProxyTest {

    @Test
    public void proxy() {
        Dell dell = new Dell();
        MyInvocationHandler invocationHandler = new MyInvocationHandler(dell);

        //产生的代理类需要接口指向代理类
        Sales sales = (Sales) Proxy.newProxyInstance(dell.getClass().getClassLoader(),
                dell.getClass().getInterfaces(),invocationHandler);
        sales.salePc();
    }

    @Test
    public void userServiceProxy() {
        UserServiceImpl userServiceImpl = new UserServiceImpl();
        MyInvocationHandler invocationHandler = new MyInvocationHandler(userServiceImpl);

        UserService userService = (UserService) Proxy.newProxyInstance(userServiceImpl.getClass().getClassLoader(),
                userServiceImpl.getClass().getInterfaces(),invocationHandler);

        userService.save();
        userService.update();
    }

    @Test
    public void time() {
        UserServiceImpl userServiceImpl = new UserServiceImpl();
        TimeInvocationHandler timeInvocationHandler = new TimeInvocationHandler(userServiceImpl);

        UserService userService = (UserService) Proxy.newProxyInstance(userServiceImpl.getClass().getClassLoader(),
                userServiceImpl.getClass().getInterfaces(),timeInvocationHandler);

        userService.save();
        userService.update();


    }

}
