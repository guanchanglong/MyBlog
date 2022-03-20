package com.gcl.demo1.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.gcl.demo1.NotFoundException;
import com.gcl.demo1.dao.*;
import com.gcl.demo1.entity.*;
import com.gcl.demo1.service.BlogService;
import com.gcl.demo1.service.UserService;
import com.gcl.demo1.utils.MarkdownUtils;
import com.gcl.demo1.utils.MyBeanUtils;
import com.gcl.demo1.vo.BlogQuery;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.support.atomic.RedisAtomicLong;
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
    private UserService userService;

    @Autowired
    private TypeDao typeDao;

    @Autowired
    private TagDao tagDao;

    @Autowired
    private CommentDao commentDao;

    @Autowired
    private RedisTemplate<String,String> redisTemplate;

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
    public PageInfo<Blog> listBlog(int pageNum, int size, int id, String type, String content, BlogQuery blogQuery) {
        Page<Blog> blogs;
        String data = "";
        PageInfo<Blog> result;
        List<Blog> blogList = new ArrayList<>();
        JSONObject json;
        switch (type){
            case "tag":
                if (!Boolean.TRUE.equals(redisTemplate.hasKey("tagBlogPage:" + pageNum + ";tagId:" + id))){
                    //按照博客创建时间倒序排序
                    PageHelper.startPage(pageNum, size,"create_time desc");
                    blogs = blogDao.findBlogByTag(id);
                    dataInRedis("tagBlogPage:" + pageNum + ";tagId:" + id, JSONObject.toJSONString(blogs.toPageInfo()));
                }
                data = redisTemplate.opsForValue().get("tagBlogPage:" + pageNum + ";tagId:" + id);
                json = JSON.parseObject(data);
                blogList = JSON.parseArray(json.getString("list"), Blog.class);
                break;
            case "type":
                if (!Boolean.TRUE.equals(redisTemplate.hasKey("typeBlogPage:" + pageNum + ";typeId:" + id))){
                    //按照博客创建时间倒序排序
                    PageHelper.startPage(pageNum, size,"create_time desc");
                    blogs = blogDao.findBlogByTypeId(id);
                    dataInRedis("typeBlogPage:" + pageNum + ";typeId:" + id, JSONObject.toJSONString(blogs.toPageInfo()));
                }
                data = redisTemplate.opsForValue().get("typeBlogPage:" + pageNum + ";typeId:" + id);
                json = JSON.parseObject(data);
                blogList = JSON.parseArray(json.getString("list"), Blog.class);
                break;
            case "published":
                //redis判断数据是否存储到内存中
                if (!Boolean.TRUE.equals(redisTemplate.hasKey("publishedBlogPage:" + pageNum))){
                    //按照博客创建时间倒序排序
                    PageHelper.startPage(pageNum, size,"create_time desc");
                    blogs = blogDao.findAllByPublished();
                    dataInRedis("publishedBlogPage:" + pageNum, JSONObject.toJSONString(blogs.toPageInfo()));
                }
                data = redisTemplate.opsForValue().get("publishedBlogPage:" + pageNum);
                json = JSON.parseObject(data);
                blogList = JSON.parseArray(json.getString("list"), Blog.class);
                break;
            case "search":              //搜索不用从Redis中取值，不然会很难受
                PageHelper.startPage(pageNum, size,"create_time desc");
                blogs = blogDao.findBlogByContent("%"+content+"%");
                result = new PageInfo<>(blogs);
                return result;
            case "findAll":             //管理员这里也不用从Redis中取值
                PageHelper.startPage(pageNum, size,"create_time desc");
                blogs = blogDao.findAll();
                result = new PageInfo<>(blogs);
                return result;
            case "searchInAdmin":       //管理员这里也不用从Redis中取值
                PageHelper.startPage(pageNum, size,"create_time desc");
                String title = blogQuery.getTitle();
                title = "%"+title+"%";
                blogQuery.setTitle(title);
                blogs = blogDao.findBlogByBlogQuery(blogQuery);
                result = new PageInfo<>(blogs);
                return result;
        }

        result = JSON.parseObject(data, PageInfo.class);
        assert result != null;
        result.setList(blogList);

        for (Blog blog: result.getList()){

            //如果不存在的话就刷新
            if (!Boolean.TRUE.equals(redisTemplate.hasKey("updateViews:" + blog.getId()))){
                dataInRedis("updateViews:" + blog.getId(), String.valueOf(blog.getViews()));
            }

            if (!Boolean.TRUE.equals(redisTemplate.hasKey("blogLikeCount:"+blog.getId()))){
                redisTemplate.opsForValue().set("blogLikeCount:"+blog.getId(), String.valueOf(blog.getLikeCount()));
            }

            if (!Boolean.TRUE.equals(redisTemplate.hasKey("blogUnLikeCount:"+blog.getId()))){
                redisTemplate.opsForValue().set("blogUnLikeCount:"+blog.getId(), String.valueOf(blog.getUnLikeCount()));
            }

            if (!Boolean.TRUE.equals(redisTemplate.hasKey("findTagByBlogId:" + blog.getId()))){
                List<Tag> tags = tagDao.findTagByBlogId(blog.getId());
                dataInRedis("findTagByBlogId:" + blog.getId(), JSONObject.toJSONString(tags));
            }

            if (!Boolean.TRUE.equals(redisTemplate.hasKey("findTypeById:" + blog.getTypeId()))){
                Type blogType = typeDao.findTypeById(blog.getTypeId());
                dataInRedis("findTypeById:" + blog.getTypeId(), JSONObject.toJSONString(blogType));
            }
            List<Tag> tags = JSON.parseArray(redisTemplate.opsForValue().get("findTagByBlogId:" + blog.getId()), Tag.class);
            Type blogType = JSON.parseObject(redisTemplate.opsForValue().get("findTypeById:" + blog.getTypeId()), Type.class);
            //设置博客的标签信息
            blog.setTags(tags);
            //设置博客的作者
            blog.setUser(userService.getUserByNickname("小关同学"));
            //设置博客的类型
            blog.setType(blogType);
            //设置最新的浏览人数
            blog.setViews(Integer.valueOf(Objects.requireNonNull(redisTemplate.opsForValue().get("updateViews:" + blog.getId()))));
            //设置最新的点赞数据
            blog.setLikeCount(Integer.valueOf(Objects.requireNonNull(redisTemplate.opsForValue().get("blogLikeCount:" + blog.getId()))));
        }
        return result;
    }





    public PageInfo<Blog> listBlogToUpdateRedis(int pageNum, int size, int id, String type) {
        Page<Blog> blogs = new Page<>();
        switch (type){
            case "tag":
                //按照博客创建时间倒序排序
                PageHelper.startPage(pageNum, size,"create_time desc");
                blogs = blogDao.findBlogByTag(id);
                redisTemplate.opsForValue().set("tagBlogPage:" + pageNum + ";tagId:" + id, JSONObject.toJSONString(blogs.toPageInfo()));
                break;
            case "type":
                //按照博客创建时间倒序排序
                PageHelper.startPage(pageNum, size,"create_time desc");
                blogs = blogDao.findBlogByTypeId(id);
                redisTemplate.opsForValue().set("typeBlogPage:" + pageNum + ";typeId:" + id, JSONObject.toJSONString(blogs.toPageInfo()));
                break;
            case "published":
                //按照博客创建时间倒序排序
                PageHelper.startPage(pageNum, size,"create_time desc");
                blogs = blogDao.findAllByPublished();
                redisTemplate.opsForValue().set("publishedBlogPage:" + pageNum, JSONObject.toJSONString(blogs.toPageInfo()));
                break;
        }

        for (Blog blog: blogs){

            List<Tag> tags = tagDao.findTagByBlogId(blog.getId());
            redisTemplate.opsForValue().set("findTagByBlogId:" + blog.getId(), JSONObject.toJSONString(tags));

            Type blogType = typeDao.findTypeById(blog.getTypeId());
            redisTemplate.opsForValue().set("findTypeById:" + blog.getTypeId(), JSONObject.toJSONString(blogType));
        }

        return blogs.toPageInfo();
    }



    @Override
    public PageInfo<Blog> listBlogAdmin(int pageNum, int size, int id, String type, String content, BlogQuery blogQuery) {
        List<Blog> blogs = new ArrayList<>();
        //按照博客创建时间倒序排序
        PageHelper.startPage(pageNum,size,"create_time desc");
        switch (type){
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
    public Blog getAndConvert(int id) {
        Blog blog;
        List<Tag> tags;
        User user;
        if (!Boolean.TRUE.equals(redisTemplate.hasKey("findBlogById:" + id))){
            //找到对应的博客
            blog = blogDao.findBlogById(id);
            dataInRedis("findBlogById:" + id, JSONObject.toJSONString(blog));
        }
        blog = JSON.parseObject(redisTemplate.opsForValue().get("findBlogById:" + id), Blog.class);
        //若博客不为空，继续接下来的步骤
        if (blog == null){
            throw new NotFoundException("该博客不存在");
        }

        if (!Boolean.TRUE.equals(redisTemplate.hasKey("findTagByBlogId:" + id))){
            //找到博客对应的标签信息
            tags = tagDao.findTagByBlogId(id);
            dataInRedis("findTagByBlogId:" + id, JSONObject.toJSONString(tags));
        }
        tags = JSON.parseArray(redisTemplate.opsForValue().get("findTagByBlogId:" + id), Tag.class);

        if (!Boolean.TRUE.equals(redisTemplate.hasKey("findUserById:" + blog.getUserId()))){
            //找到博客的作者
            user = userDao.findUserById(blog.getUserId());
            dataInRedis("findUserById:" + blog.getUserId(), JSONObject.toJSONString(user));
        }
        user = JSON.parseObject(redisTemplate.opsForValue().get("findUserById:" + blog.getUserId()), User.class);

        //设置博客的标签
        blog.setTags(tags);
        //设置博客的作者
        blog.setUser(user);
        String content = blog.getContent();
        //将博客内容转换为html的格式
        blog.setContent(MarkdownUtils.markdownToHtmlExtensions(content));
        //对当天的浏览数据进行自增
        RedisAtomicLong entityIdCounter = new RedisAtomicLong("newestDayCount", Objects.requireNonNull(redisTemplate.getConnectionFactory()));
        entityIdCounter.getAndIncrement();

        //设置点赞数据
        blog.setLikeCount(Integer.valueOf(Objects.requireNonNull(redisTemplate.opsForValue().get("blogLikeCount:" + blog.getId()))));
        //设置点踩数据
        blog.setUnLikeCount(Integer.valueOf(Objects.requireNonNull(redisTemplate.opsForValue().get("blogUnLikeCount:"+blog.getId()))));

        //若该篇博客在Redis中还没存储它的值，则往redis中存储它的浏览数值，下次直接从redis中取出来
        if (!Boolean.TRUE.equals(redisTemplate.hasKey("updateViews:" + id))){
            dataInRedis("updateViews:" + id, String.valueOf(blog.getViews() + 1));
        }else{  //如果不是第一次被浏览，则进行自增操作并重新往博客对象设置浏览数据
            entityIdCounter = new RedisAtomicLong("updateViews:" + id, redisTemplate.getConnectionFactory());
            long views = entityIdCounter.incrementAndGet();
            blog.setViews(Math.toIntExact(views));
        }
        return blog;
    }





    @Override
    public void getAndConvertToUpdateRedis(int id) {
        Blog blog = blogDao.findBlogById(id);
        redisTemplate.opsForValue().set("findBlogById:" + id, JSONObject.toJSONString(blog));

        //找到博客对应的标签信息
        List<Tag> tags = tagDao.findTagByBlogId(id);
        redisTemplate.opsForValue().set("findTagByBlogId:" + id, JSONObject.toJSONString(tags));

        //找到博客的作者
        User user = userDao.findUserById(blog.getUserId());
        redisTemplate.opsForValue().set("findUserById:" + blog.getUserId(), JSONObject.toJSONString(user));
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
        if (!Boolean.TRUE.equals(redisTemplate.hasKey("listRecommendBlogTop:" + size))){
            //倒序排序
            PageHelper.startPage(1, size,"create_time desc");
            List<Blog> blogs = blogDao.findRecommendBlog();
            dataInRedis("listRecommendBlogTop:" + size, JSONObject.toJSONString(blogs));
        }
        List<Blog> blogs = JSON.parseArray(redisTemplate.opsForValue().get("listRecommendBlogTop:" + size), Blog.class);
        return new PageInfo<>(blogs).getList();
    }


    @Override
    public void listRecommendBlogTopToUpdateRedis(int size){
        //倒序排序
        PageHelper.startPage(1, size,"create_time desc");
        List<Blog> blogs = blogDao.findRecommendBlog();
        redisTemplate.opsForValue().set("listRecommendBlogTop:" + size, JSONObject.toJSONString(blogs));
    }


    @Override
    public Map<String,List<Blog>> archiveBlog(){
        if (!Boolean.TRUE.equals(redisTemplate.hasKey("archiveBlog"))){ //若该值不存在Redis中，就往Redis中存储该值
            List<Blog> years = blogDao.findGroupYear();
            Map<String,List<Blog>> map = new HashMap<>();
            for (Blog year:years){
                List<Blog> blogs = blogDao.findByYear(year.getYear());
                if (blogs.size() > 0){
                    map.put(year.getYear(), blogs);
                }
            }
            String data = JSONObject.toJSONString(map);
            dataInRedis("archiveBlog", data);
        }
        JSON json = JSONObject.parseObject(redisTemplate.opsForValue().get("archiveBlog"));
        Map<String, List<Blog>> archiveBlog = JSON.toJavaObject(json, Map.class);
        return archiveBlog;
    }


    @Override
    public void archiveBlogToUpdateRedis(){
        List<Blog> years = blogDao.findGroupYear();
        Map<String,List<Blog>> map = new HashMap<>();
        for (Blog year: years){
            List<Blog> blogs = blogDao.findByYear(year.getYear());
            if (blogs.size() > 0){
                map.put(year.getYear(), blogs);
            }
        }
        redisTemplate.opsForValue().set("archiveBlog", JSONObject.toJSONString(map));
    }



    @Override
    public int countBlog(){
        if (!Boolean.TRUE.equals(redisTemplate.hasKey("countBlog"))){
            String data = JSONObject.toJSONString(blogDao.findAllByPublished().size());
            dataInRedis("countBlog", data);
        }
        Integer blogCount = JSONObject.parseObject(redisTemplate.opsForValue().get("countBlog"), Integer.class);
        assert blogCount != null;
        return blogCount;
    }

    @Override
    public void countBlogToUpdateRedis(){
        redisTemplate.opsForValue().set("countBlog", JSONObject.toJSONString(blogDao.findAllByPublished().size()));
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

    @Override
    public void updateBlogOfViewsById(int blogId, int views){
        blogDao.updateBlogOfViewsById(blogId, views);
    }

    @Override
    public List<Blog> findAll(){
        return blogDao.findAll();
    }

    @Override
    @Transactional
    public void updateBlogOfLikeCountById(int blogId, int likeCount){
        blogDao.updateBlogOfLikeCountById(blogId, likeCount);
    }

    @Override
    @Transactional
    public void updateBlogOfUnLikeCountById(int blogId, int unLikeCount){
        blogDao.updateBlogOfUnLikeCountById(blogId, unLikeCount);
    }
}
