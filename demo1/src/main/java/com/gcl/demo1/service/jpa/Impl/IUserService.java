package com.gcl.demo1.service.jpa.Impl;

import com.gcl.demo1.dao.jpa.UserDao;
import com.gcl.demo1.entity.jpa.User;
import com.gcl.demo1.service.jpa.UserService;
import com.gcl.demo1.utils.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author：小关同学爱吃汉堡
 * @date: 2020/12/10 21:10
 */
@Service
public class IUserService implements UserService {

    @Autowired
    private UserDao userDao;


    @Override
    public User checkUser(String username, String password) {
        User user = userDao.findByUsernameAndPassword(username, MD5Utils.code(password));
        return user;
    }

    @Override
    public User getUser(){
        return userDao.findAllByUsername("关昌隆");
    }
}
