package com.gcl.demo1.service;

import com.gcl.demo1.entity.Hobby;

import java.util.List;

/**
 * @author 小关同学
 * @create 2021/12/16
 */
public interface HobbyService {

    List<Hobby> findHobbyByUserId(int userId);

    void findHobbyByUserIdToUpdateRedis(int userId);
}
