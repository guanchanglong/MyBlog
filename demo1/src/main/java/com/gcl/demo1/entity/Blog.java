package com.gcl.demo1.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author 小关同学
 * @create 2021/12/7
 */
public class Blog implements Comparable<Blog>, Serializable {
    //博客的id
    private int id;
    //博客的标题
    private String title;
    //博文内容
    private String content;
    //博客的封面图片
    private String firstPicture;
    //博客的原创(0)、转载(1)和翻译(2)标识
    private int flag;
    //博客的浏览人数
    private int views;
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
    private Date createTime;
    //更新时间
    private Date updateTime;
    //博客作者
    private int userId;
    //博客类型
    private int typeId;

    private String tagIds;
    //博客简介
    private String description;
    //点赞数
    private int likeCount;
    //踩数
    private int unLikeCount;

    private String year;

    private User user;
    //类型
    //多对一
    //一个类型对应着多篇文章
    private Type type;
    //评论
    //一对多
    //一篇文章对应多个评论
    private List<Comment> comments = new ArrayList<>();
    //标签
    //多对多
    //多个标签对应着多篇文章
    private List<Tag> tags = new ArrayList<>();


    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public int getViews() {
        return views;
    }

    public void setViews(int views) {
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

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
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

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
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

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    public void init() {
        this.tagIds = tagsToIds(this.getTags());
    }

    public int getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(int likeCount) {
        this.likeCount = likeCount;
    }

    public int getUnLikeCount() {
        return unLikeCount;
    }

    public void setUnLikeCount(int unLikeCount) {
        this.unLikeCount = unLikeCount;
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
                ", userId=" + userId +
                ", typeId=" + typeId +
                ", tagIds='" + tagIds + '\'' +
                ", description='" + description + '\'' +
                ", year='" + year + '\'' +
                '}';
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

    @Override
    public int compareTo(Blog blog) {
        return (int)(createTime.getTime()-blog.getCreateTime().getTime());
    }
}
