package com.gcl.demo1.service.impl;

import com.gcl.demo1.NotFoundException;
import com.gcl.demo1.dao.*;
import com.gcl.demo1.entity.*;
import com.gcl.demo1.service.BlogService;
import com.gcl.demo1.utils.MarkdownUtils;
import com.gcl.demo1.utils.MyBeanUtils;
import com.gcl.demo1.vo.BlogQuery;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @author 小关同学
 * @create 2021/12/9
 */
@Service
public class BlogServiceImpl implements BlogService {

    @Autowired
    private BlogDao blogDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private TypeDao typeDao;

    @Autowired
    private DayCountDao dayCountDao;

    @Autowired
    private TagDao tagDao;

    @Autowired
    private CommentDao commentDao;

    @Override
    public PageInfo<Blog> listBlog(int pageNum, int size, int id, String type, String content, BlogQuery blogQuery) {
        List<Blog> blogs = new ArrayList<>();
        //按照博客创建时间倒序排序
        PageHelper.startPage(pageNum,size,"create_time desc");
        switch (type){
            case "tag":
                blogs = blogDao.findBlogByTag(id);
                break;
            case "type":
                blogs = blogDao.findBlogByTypeId(id);
                break;
            case "published":
                blogs = blogDao.findAllByPublished();
                break;
            case "search":
                blogs = blogDao.findBlogByContent("%"+content+"%");
                break;
            case "findAll":
                blogs = blogDao.findAll();
                break;
            case "searchInAdmin":
                String title = blogQuery.getTitle();
                title = "%"+title+"%";
                blogQuery.setTitle(title);
                blogs = blogDao.findBlogByBlogQuery(blogQuery);
                break;
        }
        for (Blog blog:blogs){
            //设置博客的标签信息
            blog.setTags(tagDao.findTagByBlogId(blog.getId()));
            //设置博客的作者
            blog.setUser(userDao.findUserById(blog.getUserId()));
            //设置博客的类型
            blog.setType(typeDao.findTypeById(blog.getTypeId()));
        }
        return new PageInfo<>(blogs);
    }

    @Override
    @Transactional
    public Blog getAndConvert(int id) {
        //找到对应的博客
        Blog blog = blogDao.findBlogById(id);
        //若博客不为空，继续接下来的步骤
        if (blog == null){
            throw new NotFoundException("该博客不存在");
        }
        //找到博客对应的标签信息
        List<Tag> tags = tagDao.findTagByBlogId(id);
        //找到博客的作者
        User user = userDao.findUserById(blog.getUserId());
        //设置博客的标签
        blog.setTags(tags);
        //设置博客的作者
        blog.setUser(user);
        String content = blog.getContent();
        //将博客内容转换为html的格式
        blog.setContent(MarkdownUtils.markdownToHtmlExtensions(content));
        //统计当日浏览次数
        dayCountDao.updateNewestDay(7);
        //统计该篇博客的浏览次数
        blogDao.updateViews(id);
        return blog;
    }

    @Override
    public Blog findBlogByBlogId(int blogId){
        //找到对应的博客
        Blog blog = blogDao.findBlogById(blogId);
        //找到博客对应的标签信息
        List<Tag> tags = tagDao.findTagByBlogId(blogId);
        //找到博客的作者
        User user = userDao.findUserById(blog.getUserId());
        //找到博客对应的类型
        Type type = typeDao.findTypeById(blog.getTypeId());
        //设置博客的标签
        blog.setTags(tags);
        //设置博客的作者
        blog.setUser(user);
        //设置博客的类型
        blog.setType(type);
        return blog;
    }

    /**
     * 返回前几页的推荐博客列表
     * @param size 数目
     * @return 返回博客列表
     */
    @Override
    public List<Blog> listRecommendBlogTop(int size){
        //倒序排序
        PageHelper.startPage(1,size,"create_time desc");
        List<Blog> blogs = blogDao.findRecommendBlog();
        return new PageInfo<>(blogs).getList();
    }

    @Override
    public Map<String,List<Blog>> archiveBlog(){
        List<Blog> years = blogDao.findGroupYear();
        Map<String,List<Blog>> map = new HashMap<>();
        for (Blog year:years){
            List<Blog> blogs = blogDao.findByYear(year.getYear());
            if (blogs.size() > 0){
                map.put(year.getYear(), blogs);
            }
        }
        return map;
    }

    @Override
    public int countBlog(){
        return blogDao.findAllByPublished().size();
    }

    @Override
    @Transactional  //使用事务
    public void addBlog(Blog blog){
        if (blog.getId()==0){   //博客进行新增
            blog.setCreateTime(new Date());
            blog.setUpdateTime(new Date());
            blog.setViews(0);
            //保存博客的内容
            //这里在MyBatis里面使用了获取新增主键的语句，所以后面能获取到新增数据的主键
            blogDao.insertBlog(blog);
            int blogId = blog.getId();
            //保存标签的关系
            for (Tag tag:blog.getTags()){
                tagDao.saveTagRelation(blogId, tag.getId());
            }
        }
    }

    /**
     * 更新博客内容
     * 这里使用程序逻辑来管理中间表
     * @param blogId 待更新的博客id
     * @param blog 博客内容
     */
    @Override
    @Transactional  //使用事务
    public void updateBlog(int blogId, Blog blog){
        Blog b = blogDao.findBlogById(blogId);
        if (b==null){
            throw new NotFoundException("该博客不存在");
        }
        //如果存在就将blog对象里面的值赋给b
        BeanUtils.copyProperties(blog, b, MyBeanUtils.getNullPropertyNames(blog));
        //设置更新的时间日期
        b.setUpdateTime(new Date());
        //更新博客信息
        blogDao.updateBlog(b);

        //标签-博客中间表依照相应的博客id整体更新
        List<BlogAndTag> oldBlogAndTags = tagDao.findTagIdByBlogId(b.getId());
        //待修改的标签
        List<Tag> tags = b.getTags();
        //新的标签数
        int i = 0;
        for (;i < tags.size();i++){
            if (i < oldBlogAndTags.size()){ //更新的标签数小于或等于原有的标签数，执行修改操作
                tagDao.updateTagRelationById(tags.get(i).getId(),oldBlogAndTags.get(i).getId());
            }else{  //更新的标签数超过原有的标签数，执行插入操作
                tagDao.saveTagRelation(b.getId(),tags.get(i).getId());
            }
        }
        //更新的标签数小于数据库中原有的标签数，执行删除操作
        for (;i<oldBlogAndTags.size();i++){
            tagDao.deleteTagRelationById(oldBlogAndTags.get(i).getId());
        }
    }

    @Override
    @Transactional  //使用事务
    public void deleteBlog(int blogId){
        //新增和更新博客内容不用在意博客的评论，但是删除需要在意博客的评论
        //删除博客的内容
        //删除操作使用了整理主键的语句，所以删除时间会有点长，当数据越多，删除数据所花的时间越长
        //至于为什么要使用整理主键的语句，是因为为了新增博客时能获取到最新的主键
        blogDao.deleteBlogById(blogId);
        //删除博客和标签的关系
        tagDao.deleteTagRelationByBlogId(blogId);
        //删除博客下相应的评论
        commentDao.deleteCommentByBlogId(blogId);
    }

    @Override
    public List<Blog> findBlogByTypeId(int typeId){
        return blogDao.findBlogByTypeIdByAdmin(typeId);
    }
}
