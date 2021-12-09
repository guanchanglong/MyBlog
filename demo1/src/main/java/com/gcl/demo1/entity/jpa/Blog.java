package com.gcl.demo1.entity.jpa;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author：小关同学爱吃汉堡
 * @date: 2020/12/8 22:53
 */
@Data
@Entity
@Table(name = "t_blog")
public class Blog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //博客的id
    private Long id;
    //博客的标题
    private String title;

    @Basic(fetch = FetchType.LAZY)
    @Lob
    //博文内容
    private String content;
    //博客的封面图片
    private String firstPicture;
    //博客的标签
    private String flag;
    //博客的浏览人数
    private Integer views;
    //博客是否开启赞赏
    private boolean appreciation;
    //博客是否开启分享
    private boolean shareStatement;
    //博客是否开启评论
    private boolean commentable;
    //博客是否发布
    private boolean published;
    //博客是否开启推荐
    private boolean recommend;
    //创建时间
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;
    //更新时间
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateTime;


    //1、直接将@Transient注解直接加在year属性上，数据库不会创建year字段，上述查询也无法获取值；
    //
    //2、将@Transient注解加在year属性的get方法上，数据库会创建year字段，上述查询可以获取值；
    //
    //3、将@Transient注解加在year属性的set方法上，数据库不会创建year字段，上述查询可以获取值；

    @Transient  //该注解的意思是,该属性并非一个到数据库表的字段的映射。
    private String year;

    public String getYear() {
        return year;
    }

    @Transient
    public void setYear(String year) {
        this.year = year;
    }

    //博客类型
    //多对一注解（多表查询）
    @ManyToOne
    private Type type;

    //多对多
    //CascadeType.PERSIST级联新增
    //级联操作，操作一个对象的同时操作他的关联对象
    //需要区分操作主体
    //需要在操作主体的实体类上，添加级联属性（需要添加到多表映射关系的注解上）
    //cascade（配置级联）
    @ManyToMany(cascade = {CascadeType.PERSIST})
    private List<Tag> tags = new ArrayList<>();

    //多个Blog对应一个User
    //用户
    @ManyToOne
    private User user;

    @OneToMany(mappedBy = "blog")
    private List<Comment> comments = new ArrayList<>();

    //该注解的作用就是用来区分并指出不属于@Table表中的字段
    @Transient
    private String tagIds;

    private String description;

    public Blog(){
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getFirstPicture() {
        return firstPicture;
    }

    public void setFirstPicture(String firstPicture) {
        this.firstPicture = firstPicture;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public Integer getViews() {
        return views;
    }

    public void setViews(Integer views) {
        this.views = views;
    }

    public boolean isAppreciation() {
        return appreciation;
    }

    public void setAppreciation(boolean appreciation) {
        this.appreciation = appreciation;
    }

    public boolean isShareStatement() {
        return shareStatement;
    }

    public void setShareStatement(boolean shareStatement) {
        this.shareStatement = shareStatement;
    }

    public boolean isCommentable() {
        return commentable;
    }

    public void setCommentable(boolean commentable) {
        this.commentable = commentable;
    }

    public boolean isPublished() {
        return published;
    }

    public void setPublished(boolean published) {
        this.published = published;
    }

    public boolean isRecommend() {
        return recommend;
    }

    public void setRecommend(boolean recommend) {
        this.recommend = recommend;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public String getTagIds() {
        return tagIds;
    }

    public void setTagIds(String tagIds) {
        this.tagIds = tagIds;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void init() {
        this.tagIds = tagsToIds(this.getTags());
    }

    //1,2,3
    private String tagsToIds(List<Tag> tags) {
        if (!tags.isEmpty()) {
            StringBuffer ids = new StringBuffer();
            boolean flag = false;
            for (Tag tag : tags) {
                if (flag) {
                    ids.append(",");
                } else {
                    flag = true;
                }
                ids.append(tag.getId());
            }
            return ids.toString();
        } else {
            return tagIds;
        }
    }

    /**
     * 字符串转Blog对象
     * @param param 待转换的字符串
     * @return Blog对象
     */
    public static Blog stringToBlog(String param){
        JSONObject json = JSONObject.parseObject(param);
         return JSON.toJavaObject(json,Blog.class);
    }

    @Override
    public String toString() {
        return "Blog{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", firstPicture='" + firstPicture + '\'' +
                ", flag='" + flag + '\'' +
                ", views=" + views +
                ", appreciation=" + appreciation +
                ", shareStatement=" + shareStatement +
                ", commentable=" + commentable +
                ", published=" + published +
                ", recommend=" + recommend +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", type=" + type +
                ", tags=" + tags +
                ", user=" + user +
                ", comments=" + comments +
                ", tagIds='" + tagIds + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
