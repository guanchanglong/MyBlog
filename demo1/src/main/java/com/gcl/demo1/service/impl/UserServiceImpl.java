package com.gcl.demo1.service.impl;

import com.gcl.demo1.dao.UserDao;
import com.gcl.demo1.entity.User;
import com.gcl.demo1.service.UserService;
import com.gcl.demo1.utils.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author 小关同学
 * @create 2021/12/16
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public User getUser(String username){
        return userDao.findUserByNickname(username);
    }

    @Override
    public User login(String username,String password){
        return userDao.login(username, MD5Utils.code(password));
    }
}
