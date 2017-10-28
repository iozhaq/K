package com.kaishengit.spring;

import com.kaishengit.dao.UserDao;
import com.kaishengit.service.UserService;
import com.kaishengit.service.impl.UserServiceImpl;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class AccessSpring {

    @Test
    public void hello() {
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");

        UserServiceImpl userService = (UserServiceImpl) context.getBean("userService");
        userService.save();


       /* UserDao userDao = (UserDao) context.getBean("userDao");
        UserDao userDao2 = (UserDao) context.getBean("userDao");

        System.out.println(userDao == userDao2);*/
    }

}
