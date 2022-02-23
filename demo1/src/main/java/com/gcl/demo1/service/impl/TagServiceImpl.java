package com.gcl.demo1.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.gcl.demo1.dao.BlogDao;
import com.gcl.demo1.dao.TagDao;
import com.gcl.demo1.entity.Blog;
import com.gcl.demo1.entity.BlogAndTag;
import com.gcl.demo1.entity.Tag;
import com.gcl.demo1.service.TagService;
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
 * @create 2021/12/13
 */
@Service
public class TagServiceImpl implements TagService {

    @Autowired
    private TagDao tagDao;

    @Autowired
    private BlogDao blogDao;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

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
    public List<Tag> listTagTop(int size){
        List<Tag> tags;
        if (!Boolean.TRUE.equals(redisTemplate.hasKey("findAllTag"))){
            tags = tagDao.findAllTag();
            String data = JSONObject.toJSONString(tags);
            dataInRedis("findAllTag", data);
        }
        tags = JSONObject.parseArray(redisTemplate.opsForValue().get("findAllTag"), Tag.class);

        List<Tag> result = new ArrayList<>(size);
        assert tags != null;
        for (Tag tag:tags){
            if (!Boolean.TRUE.equals(redisTemplate.hasKey("findBlogByTag:" + tag.getId()))){
                List<Blog> blogs = blogDao.findBlogByTag(tag.getId());
                dataInRedis("findBlogByTag:" + tag.getId(), JSONObject.toJSONString(blogs));
            }
            List<Blog> blogs = JSON.parseArray(redisTemplate.opsForValue().get("findBlogByTag:"+tag.getId()), Blog.class);
            tag.setBlogs(blogs);
        }
        //排序
        Collections.sort(tags);
        for (Tag tag:tags){
            if (size==0){
                break;
            }
            result.add(tag);
            size--;
        }
        return result;
    }

    @Override
    public List<Tag> listTag(){
        return tagDao.findAllTag();
    }

    @Override
    public List<Tag> listTag(String ids){
        List<Integer> list = convertToList(ids);
        List<Tag> result = new ArrayList<>(list.size());
        for (Integer num:list){
            result.add(tagDao.findAllById(num));
        }
        return result;
    }

    @Override
    public PageInfo<Tag> listTag(int pageNum, int size){
        PageHelper.startPage(pageNum,size);
        List<Tag> tags = tagDao.findAllTag();
        return new PageInfo<>(tags);
    }

    @Override
    public Tag findTagById(int tagId){
        return tagDao.findAllById(tagId);
    }

    @Override
    public Tag findTagByName(String tagName){
        return tagDao.findTagByName(tagName);
    }

    @Override
    @Transactional
    public void insertTag(String tagName){
        tagDao.insertTag(tagName);
    }

    @Override
    @Transactional
    public void updateTag(String tagName,int tagId){
        tagDao.updateTag(tagName, tagId);
    }

    @Override
    @Transactional
    public void deleteTagById(int id){
        tagDao.deleteTagById(id);
    }

    @Override
    public List<BlogAndTag> findRelationByTagId(int tagId){
        return tagDao.findRelationByTagId(tagId);
    }

    private List<Integer> convertToList(String ids) {
        List<Integer> list = new ArrayList<>();
        if (!"".equals(ids) && ids != null) {
            String[] idArray = ids.split(",");
            for (String s : idArray) {
                list.add(Integer.valueOf(s));
            }
        }
        return list;
    }
}
