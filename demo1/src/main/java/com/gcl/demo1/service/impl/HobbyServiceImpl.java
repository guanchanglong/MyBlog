package com.gcl.demo1.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.gcl.demo1.dao.HobbyDao;
import com.gcl.demo1.entity.Hobby;
import com.gcl.demo1.service.HobbyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
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

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    /**
     * 保存字符串数据到Redis中
     * @param key 键
     * @param data 待保存的数据
     */
    private void dataInRedis(String key, String data){
        //设置每个存储的数据的有效时间为24小时
        redisTemplate.opsForValue().set(key, data);
    }

    @Override
    public List<Hobby> findHobbyByUserId(int userId){
        if (!Boolean.TRUE.equals(redisTemplate.hasKey("findHobbyByUserId:" + userId))){
            List<Hobby> hobbies = hobbyDao.findHobbyByUserId(userId);
            String data = JSONObject.toJSONString(hobbies);
            dataInRedis("findHobbyByUserId:" + userId, data);
        }
        List<Hobby> hobbies = JSONObject.parseArray(redisTemplate.opsForValue().get("findHobbyByUserId:" + userId), Hobby.class);
        return hobbies;
    }


    @Override
    public void findHobbyByUserIdToUpdateRedis(int userId){
        List<Hobby> hobbies = hobbyDao.findHobbyByUserId(userId);
        redisTemplate.opsForValue().set("findHobbyByUserId:" + userId, JSONObject.toJSONString(hobbies));
    }
}
