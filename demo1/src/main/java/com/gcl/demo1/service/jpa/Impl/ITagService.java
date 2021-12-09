package com.gcl.demo1.service.jpa.Impl;

import com.gcl.demo1.NotFoundException;
import com.gcl.demo1.dao.jpa.TagDao;
import com.gcl.demo1.entity.jpa.Tag;
import com.gcl.demo1.service.jpa.TagService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @author：小关同学爱吃汉堡
 * @date: 2020/12/13 22:21
 */
@Service
public class ITagService implements TagService {

    @Autowired
    private TagDao tagDao;

    @Transactional
    @Override
    public Tag saveTag(Tag tag) {
        return tagDao.save(tag);
    }

    @Transactional
    @Override
    public Tag getTag(Long id) {
        return tagDao.findAllById(id);
    }

    @Override
    public Tag getTagByName(String name) {
        return tagDao.findAllByName(name);
    }

    @Transactional
    @Override
    public Page<Tag> listTag(Pageable pageable) {
        return tagDao.findAll(pageable);
    }

    @Override
    public List<Tag> listTag() {
        return tagDao.findAll();
    }

    @Override
    public List<Tag> listTagTop(Integer size) {
        //springboot2.2.1（含）以上的版本Sort已经不能再实例化了，构造方法已经是私有的了！
        //可以改用Sort.by获得Sort对象
        //Sort sort = new Sort(Sort.Direction.DESC, "blogs.size");
        Sort sort = Sort.by(Sort.Direction.DESC,"blogs.size");
        //优点：利用构造方法，调用处写死了必须使用new来创建新的对象，对象的控制权在调用处，
        //而调用of方法则把生成对象的控制权保留在了PageRequest类中，
        //后期如果需要扩展则在PageRequest类中进行扩展即可
        //Pageable pageable = new PageRequest(0, size, sort);
        Pageable pageable = PageRequest.of(0, size, sort);
        List<Tag> list = tagDao.findTop(pageable);
        for(Tag tag:list){
            //如果是草稿的话，就把它从集合中删除
            tag.getBlogs().removeIf(blog -> !blog.isPublished());
        }
        return list;
    }


    @Override
    public List<Tag> listTag(String ids) { //1,2,3
        List<Long>list = convertToList(ids);
        return tagDao.findAllById(list);
    }

    private List<Long> convertToList(String ids) {
        List<Long> list = new ArrayList<>();
        if (!"".equals(ids) && ids != null) {
            String[] idArray = ids.split(",");
            for (int i=0; i < idArray.length;i++) {
                list.add(Long.valueOf(idArray[i]));
            }
        }
        return list;
    }


    @Transactional
    @Override
    public Tag updateTag(Long id, Tag tag) {
        Tag t = tagDao.findAllById(id);
        if (t == null) {
            throw new NotFoundException("不存在该标签");
        }
        BeanUtils.copyProperties(tag,t);
        return tagDao.save(t);
    }



    @Transactional
    @Override
    public void deleteTag(Long id) {
        tagDao.deleteById(id);
    }
}
