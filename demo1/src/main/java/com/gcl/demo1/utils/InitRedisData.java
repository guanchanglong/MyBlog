package com.gcl.demo1.utils;

import com.gcl.demo1.entity.User;
import com.gcl.demo1.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

/**
 * @author 小关同学
 * @create 2021/10/16
 * Redis值初始化类
 */
@Component
public class InitRedisData {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    private UserService userService;

    /**
     * 保存字符串数据到Redis中
     * @param key 键
     * @param data 待保存的数据
     */
    private void dataInRedis(String key, String data){
        redisTemplate.opsForValue().set(key,data);
    }


    /**
     * 将关于我页面需要的数据存入Redis中
     */
    public String initAboutShow(){
        //看hasKey源码可知，hasKey方法返回一个对象(Boolean对象)包装器，但在if条件内使用它隐式将其取消装箱（即，将调用结果转换为原始值）
        //如果由于某种原因该hasKey方法可能返回null，则会出现错误。为了安全起见，可以按以下步骤检查密钥是否存在：
        //Boolean.TRUE.equals(redisTemplate.hasKey(XXXX)
        if (!Boolean.TRUE.equals(redisTemplate.hasKey("user:" + 1))){ //若该值不存在Redis中，就往Redis中存储该值
            User user = userService.getUser("小关同学");
            user.setPassword(null);     //设置密码为空
            user.setBlogs(null);
            String data = JSONToStringUtils.JSONToString(user);
            dataInRedis("user:" + user.getId(), data);
            System.out.println("Redis中没有值，往Redis里面存值");
        }
        //因为用户信息暂时就我一个，是固定的，所以就写死了
        return "user:" + 1;
    }
}
