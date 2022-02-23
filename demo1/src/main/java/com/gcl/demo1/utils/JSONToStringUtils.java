package com.gcl.demo1.utils;

import com.alibaba.fastjson.JSON;

/**
 * @author 小关同学
 * @create 2021/10/16
 * JSON对象和String类型的相互转换类
 */
public class JSONToStringUtils {

    /**
     * 将数据库查询到的对象信息转化为字符串
     * @param param JSON对象信息
     * @return 字符串
     */
    public static String JSONToString(Object param){
        return JSON.toJSONString(param);
    }

//    public static void main(String[] args) {
//        User user = new User();
//        user.setId(1L);
//        user.setAboutMe("我是xxx");
//        user.setEmail("506921079@qq.com");
//        user.setUsername("小关");
//        String str = JSONToString(user);
//
//        JSONObject userJson = JSONObject.parseObject(str);
//        User user1 = JSON.toJavaObject(userJson,User.class);
//
//        String string = user1.toString();
//        System.out.println(string);
//        System.out.println(user.toString());
//    }
}
