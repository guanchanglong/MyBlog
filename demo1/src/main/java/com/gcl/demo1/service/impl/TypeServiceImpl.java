package com.gcl.demo1.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.gcl.demo1.dao.BlogDao;
import com.gcl.demo1.dao.TypeDao;
import com.gcl.demo1.entity.Blog;
import com.gcl.demo1.entity.Type;
import com.gcl.demo1.service.TypeService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author 小关同学
 * @create 2021/12/11
 */
@Service
public class TypeServiceImpl implements TypeService {

    @Autowired
    private TypeDao typeDao;

    @Autowired
    private BlogDao blogDao;

    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    /**
     * 保存字符串数据到Redis中
     * @param key 键
     * @param data 待保存的数据
     */
    private void dataInRedis(String key, String data){
        //设置每个存储的数据的有效时间为24小时
        redisTemplate.opsForValue().set(key, data, 24, TimeUnit.HOURS);
    }

    @Override
    public List<Type> listTypeTop(int size){
        List<Type> types;
        if (!Boolean.TRUE.equals(redisTemplate.hasKey("findAllType"))){
            types = typeDao.findAllType();
            String data = JSONObject.toJSONString(types);
            dataInRedis("findAllType", data);
        }
        types = JSONObject.parseArray(redisTemplate.opsForValue().get("findAllType"), Type.class);

        List<Type> result = new ArrayList<>(size);
        assert types != null;
        for (Type type: types){
            if (!Boolean.TRUE.equals(redisTemplate.hasKey("findBlogByTypeId:" + type.getId()))){
                List<Blog> blogs = blogDao.findBlogByTypeId(type.getId());
                dataInRedis("findBlogByTypeId:" + type.getId(), JSONObject.toJSONString(blogs));
            }
            List<Blog> blogs = JSON.parseArray(redisTemplate.opsForValue().get("findBlogByTypeId:" + type.getId()), Blog.class);
            type.setBlogs(blogs);
        }
        //排序
        Collections.sort(types);
        for (Type type:types){
            if (size==0){
                break;
            }
            result.add(type);
            size--;
        }
        return result;
    }

    @Override
    public List<Type> listType(){
        return typeDao.findAllType();
    }

    @Override
    public Type findTypeById(int id){
        return typeDao.findTypeById(id);
    }

    @Override
    public PageInfo<Type> listType(int pageNum, int size){
        PageHelper.startPage(pageNum,size);
        List<Type> types = typeDao.findAllType();
        return new PageInfo<>(types);
    }

    @Override
    public Type findTypeByName(String name){
        return typeDao.findAllByName(name);
    }

    @Override
    @Transactional
    public void insertType(String typeName){
        typeDao.insertType(typeName);
    }

    @Override
    @Transactional
    public void updateTypeById(String typeName, int typeId){
        typeDao.updateTypeById(typeName, typeId);
    }

    @Override
    @Transactional
    public void deleteTypeById(int typeId){
        typeDao.deleteTypeById(typeId);
    }
}
