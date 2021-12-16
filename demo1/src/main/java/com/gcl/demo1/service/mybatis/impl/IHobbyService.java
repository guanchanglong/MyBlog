package com.gcl.demo1.service.mybatis.impl;

import com.gcl.demo1.dao.mybatis.MHobbyDao;
import com.gcl.demo1.entity.mybatis.Hobby;
import com.gcl.demo1.service.mybatis.HobbyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 小关同学
 * @create 2021/12/16
 */
@Service
public class IHobbyService implements HobbyService {

    @Autowired
    private MHobbyDao mHobbyDao;

    @Override
    public List<Hobby> findHobbyByUserId(int userId){
        return mHobbyDao.findHobbyByUserId(userId);
    }
}
