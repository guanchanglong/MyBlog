package com.gcl.demo1.service.mybatis.impl;

import com.gcl.demo1.dao.mybatis.MBlogDao;
import com.gcl.demo1.dao.mybatis.MTagDao;
import com.gcl.demo1.entity.mybatis.Blog;
import com.gcl.demo1.entity.mybatis.Tag;
import com.gcl.demo1.service.mybatis.MTagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

/**
 * @author 小关同学
 * @create 2021/12/13
 */
@Service
public class IMTagService implements MTagService {

    @Autowired
    private MTagDao mTagDao;

    @Autowired
    private MBlogDao mBlogDao;

    @Override
    public List<Tag> listTagTop(int size){
        List<Tag> tags = mTagDao.findAllTag();
        //排序
        Collections.sort(tags);
        for (Tag tag:tags){
            List<Blog> blogs = mBlogDao.findBlogByTag(tag.getId());
            tag.setBlogs(blogs);
            if (size==0){
                break;
            }
            size--;
        }
        return tags;
    }

    //    @Override
    //    public Page<Blog> listBlog(Long tagId, Pageable pageable) {
    //        return blogDao.findAll(new Specification<Blog>() {
    //            @Override
    //            public Predicate toPredicate(Root<Blog> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
    //                Join join = root.join("tags");
    //                return cb.equal(join.get("id"),tagId);
    //            }
    //        },pageable);
    //    }


}
