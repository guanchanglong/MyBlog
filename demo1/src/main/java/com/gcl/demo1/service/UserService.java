package com.gcl.demo1.service;

import com.gcl.demo1.entity.User;

/**
 * @author 小关同学
 * @create 2021/12/16
 */
public interface UserService {

    User getUserByNickname(String nickname);

    User login(String username,String password);

}
