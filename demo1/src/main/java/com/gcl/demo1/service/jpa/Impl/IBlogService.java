package com.gcl.demo1.service.jpa.Impl;

import com.gcl.demo1.NotFoundException;
import com.gcl.demo1.dao.jpa.BlogDao;
import com.gcl.demo1.dao.jpa.DayCountDao;
import com.gcl.demo1.entity.jpa.Blog;
import com.gcl.demo1.entity.jpa.Type;
import com.gcl.demo1.service.jpa.BlogService;
import com.gcl.demo1.utils.MarkdownUtils;
import com.gcl.demo1.utils.MyBeanUtils;
import com.gcl.demo1.vo.BlogQuery;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.*;
import java.util.*;

/**
 * @author：小关同学爱吃汉堡
 * @date: 2020/12/14 12:31
 */
@Service
public class IBlogService implements BlogService {
    @Autowired
    private BlogDao blogDao;

    @Autowired
    private DayCountDao dayCountDao;

    @Override
    public Blog getBlog(Long id) {
        return blogDao.findAllById(id);
    }

    @Transactional
    @Override
    public Blog getAndConvert(Long id) {
        Blog blog = blogDao.findAllById(id);
        if (blog == null) {
            throw new NotFoundException("该博客不存在");
        }
        Blog b = new Blog();
        BeanUtils.copyProperties(blog,b);
        String content = b.getContent();
        b.setContent(MarkdownUtils.markdownToHtmlExtensions(content));

        //统计当日浏览次数
        dayCountDao.updateNewestDay(7L);
        //统计该篇博客的浏览次数
        blogDao.updateViews(id);

        return b;
    }


    @Override
    public Page<Blog> listBlog(String type,Pageable pageable, BlogQuery blog) {
        return blogDao.findAll(new Specification<Blog>() {
            @Override
            public Predicate toPredicate(Root<Blog> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
                List<Predicate> predicates = new ArrayList<>();
                //如果不为空字符串且不为空
                if (!"".equals(blog.getTitle()) && blog.getTitle() != null) {
                    //这条语句的作用相当于select * from t_blog where ‘title’ like '%blog.getTitle()%'，中的like语句
                    //'%blog.getTitle()%'将搜索在任何位置包含blog.getTitle()的所有字符串
                    predicates.add(cb.like(root.<String>get("title"), "%"+blog.getTitle()+"%"));
                }
                if (blog.getTypeId() != null) {
                    predicates.add(cb.equal(root.<Type>get("type").get("id"), blog.getTypeId()));
                }
                if (blog.isRecommend()) {
                    predicates.add(cb.equal(root.<Boolean>get("recommend"), blog.isRecommend()));
                }

                //如果是对外展示的就不显示未发布的博客
                if (type.equals("common")){
                    //选择published=1的，即已发布的博客
                    predicates.add(cb.equal(root.get("published"),1));
                }
                //进行查询，相当于where语句
                cq.where(predicates.toArray(new Predicate[predicates.size()]));
                return null;
            }
        },pageable);
    }

    /**
     * 这里出了问题，导致还为草稿的博客也能在页面上显示
     * @param pageable
     * @return
     */
    @Override
    public Page<Blog> listBlog(Pageable pageable) {
        return blogDao.findAllByPublished(pageable);
    }

    @Override
    public Page<Blog> listBlog(Long tagId, Pageable pageable) {
        return blogDao.findAll(new Specification<Blog>() {
            @Override
            public Predicate toPredicate(Root<Blog> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
                Join join = root.join("tags");
                return cb.equal(join.get("id"),tagId);
            }
        },pageable);
    }

    @Override
    public Page<Blog> listBlog(String query, Pageable pageable) {
        return blogDao.findByQuery(query,pageable);
    }

    /**
     * 返回前几页的推荐博客列表
     * @param size 数目
     * @return 返回博客列表
     */
    @Override
    public List<Blog> listRecommendBlogTop(Integer size) {
        //按照更新时间排序
        Sort sort = Sort.by(Sort.Direction.DESC,"createTime");
        Pageable pageable = PageRequest.of(0, size, sort);
        //findTop的目的是啥？
        //返回前几页的推荐的博客内容
        return blogDao.findTop(pageable);
    }

    @Override
    public Map<String, List<Blog>> archiveBlog() {
//        List<String> years = blogDao.findGroupYear();
//        List<String> years = null;
        Map<String, List<Blog>> map = new HashMap<>();
//        for (String year : years) {
//            map.put(year, blogDao.findByYear(year));
//        }
        return map;
    }

    @Override
    public Long countBlog() {
        return blogDao.countByPublished(true);
    }


    @Transactional
    @Override
    public Blog saveBlog(Blog blog) {
        if (blog.getId() == null) {
            blog.setCreateTime(new Date());
            blog.setUpdateTime(new Date());
            blog.setViews(0);
        }
        else {
            blog.setUpdateTime(new Date());
        }
        return blogDao.save(blog);
    }

    @Transactional
    @Override
    public Blog updateBlog(Long id, Blog blog) {
        Blog b = blogDao.findAllById(id);
        if (b == null) {
            throw new NotFoundException("该博客不存在");
        }
        //如果存在就将blog对象里面的值赋给b
        BeanUtils.copyProperties(blog,b, MyBeanUtils.getNullPropertyNames(blog));
        //设置更新的时间日期
        b.setUpdateTime(new Date());
        return blogDao.save(b);
    }

    @Transactional
    @Override
    public void deleteBlog(Long id) {
        blogDao.deleteById(id);
    }

    @Override
    public long allViewCounts(){
        return blogDao.countAllViews();
    }
}
