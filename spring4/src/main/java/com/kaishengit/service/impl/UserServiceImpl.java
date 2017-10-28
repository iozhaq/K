package com.kaishengit.service.impl;

import com.kaishengit.dao.UserDao;
import com.kaishengit.service.UserService;

import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

public class UserServiceImpl implements UserService {

    private Integer id;
    private String name;
    private List<String> nameList;
    private Set<UserDao> userDaoSet;
    private Map<String,UserDao> userDaoMap;
    private Properties properties;

    private UserDao userDao;

    public UserServiceImpl() {
    }

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setNameList(List<String> nameList) {
        this.nameList = nameList;
    }

    public void setUserDaoSet(Set<UserDao> userDaoSet) {
        this.userDaoSet = userDaoSet;
    }

    public void setUserDaoMap(Map<String, UserDao> userDaoMap) {
        this.userDaoMap = userDaoMap;
    }

    public void setProperties(Properties properties) {
        this.properties = properties;
    }

    @Override
    public void save() {
        System.out.println(">>>>>>>>>>>>>>>>>");
        userDao.save();
        System.out.println("<<<<<<<<<<<<<<<<<");
    }
}
