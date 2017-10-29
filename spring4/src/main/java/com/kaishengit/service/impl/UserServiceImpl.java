package com.kaishengit.service.impl;

import com.kaishengit.dao.UserDao;
import com.kaishengit.service.UserService;

import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

public class UserServiceImpl implements UserService {
    @Override
    public void save() {
       /* if(1==1) {
            throw new RuntimeException("这是故意引发的异常");
        }*/
        System.out.println("userservice save");
    }

    @Override
    public void update() {
        System.out.println("userservice update");
    }

    @Override
    public int count() {
        System.out.println("userService count");
        return 100;
    }

    /*private UserDao userDao;

    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public void save() {
        System.out.println(">>>>>>>>>>>>>>>>>");
        userDao.save();
        System.out.println("<<<<<<<<<<<<<<<<<");
    }*/
}
