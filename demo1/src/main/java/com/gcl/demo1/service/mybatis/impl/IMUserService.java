package com.gcl.demo1.service.mybatis.impl;

import com.gcl.demo1.dao.mybatis.MUserDao;
import com.gcl.demo1.entity.mybatis.User;
import com.gcl.demo1.service.mybatis.MUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author 小关同学
 * @create 2021/12/16
 */
@Service
public class IMUserService implements MUserService {

    @Autowired
    private MUserDao mUserDao;

    @Override
    public User getUser(String username){
        return mUserDao.findUserByUsername(username);
    }
}
