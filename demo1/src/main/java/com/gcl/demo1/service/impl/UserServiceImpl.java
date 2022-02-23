package com.gcl.demo1.service.impl;

import com.gcl.demo1.dao.UserDao;
import com.gcl.demo1.entity.User;
import com.gcl.demo1.service.UserService;
import com.gcl.demo1.utils.JSONToStringUtils;
import com.gcl.demo1.utils.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * @author 小关同学
 * @create 2021/12/16
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    /**
     * 保存字符串数据到Redis中
     * @param key 键
     * @param data 待保存的数据
     */
    private void dataInRedis(String key, String data){
        redisTemplate.opsForValue().set(key, data, 24, TimeUnit.HOURS);
    }


    @Override
    public User getUserByNickname(String username){
        //看hasKey源码可知，hasKey方法返回一个对象(Boolean对象)包装器，但在if条件内使用它隐式将其取消装箱（即，将调用结果转换为原始值）
        //如果由于某种原因该hasKey方法可能返回null，则会出现错误。为了安全起见，可以按以下步骤检查密钥是否存在：
        //Boolean.TRUE.equals(redisTemplate.hasKey(XXXX)
        if (!Boolean.TRUE.equals(redisTemplate.hasKey("adminUser"))){ //若该值不存在Redis中，就往Redis中存储该值
            User user = userDao.findUserByNickname(username);
            user.setPassword(null);     //设置密码为空
            user.setBlogs(null);
            String data = JSONToStringUtils.JSONToString(user);
            dataInRedis("adminUser", data);
        }
        //从Redis中获取用户信息
        User user = User.stringToUser(redisTemplate.opsForValue().get("adminUser"));
        user.setPassword(null);
        user.setBlogs(null);
        return user;
    }



    @Override
    public User login(String username,String password){
        return userDao.login(username, MD5Utils.code(password));
    }
}
