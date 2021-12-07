package com.gcl.demo1.service;


import com.gcl.demo1.entity.User;

/**
 * @author：小关同学爱吃汉堡
 * @date: 2020/12/10 21:09
 */
public interface UserService {
    User checkUser(String username, String password);

    User getUser();
}
