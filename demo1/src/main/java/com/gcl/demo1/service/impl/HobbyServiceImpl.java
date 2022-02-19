package com.gcl.demo1.service.impl;

import com.gcl.demo1.dao.HobbyDao;
import com.gcl.demo1.entity.Hobby;
import com.gcl.demo1.service.HobbyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 小关同学
 * @create 2021/12/16
 */
@Service
public class HobbyServiceImpl implements HobbyService {

    @Autowired
    private HobbyDao hobbyDao;

    @Override
    public List<Hobby> findHobbyByUserId(int userId){
        return hobbyDao.findHobbyByUserId(userId);
    }
}
