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
        System.out.println("userservice save");
    }

    @Override
    public void update() {
        System.out.println("userservice update");
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
