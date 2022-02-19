package com.gcl.demo1.entity;

/**
 * @Author 小关同学
 * @Create 2022/2/11 13:18
 */
public class BlogAndTag implements Comparable<BlogAndTag>{
    private int id;
    private int blogId;
    private int newTagId;
    private int tagId;

    public BlogAndTag() {
    }

    public BlogAndTag(int blogId, int tagId) {
        this.blogId = blogId;
        this.tagId = tagId;
    }

    public BlogAndTag(int blogId, int newTagId, int tagId) {
        this.blogId = blogId;
        this.newTagId = newTagId;
        this.tagId = tagId;
    }

    public BlogAndTag(int id, int blogId, int newTagId, int tagId) {
        this.id = id;
        this.blogId = blogId;
        this.newTagId = newTagId;
        this.tagId = tagId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getBlogId() {
        return blogId;
    }

    public void setBlogId(int blogId) {
        this.blogId = blogId;
    }

    public int getNewTagId() {
        return newTagId;
    }

    public void setNewTagId(int newTagId) {
        this.newTagId = newTagId;
    }

    public int getTagId() {
        return tagId;
    }

    public void setTagId(int tagId) {
        this.tagId = tagId;
    }

    @Override
    public int compareTo(BlogAndTag o) {
        //从小到大排序
        return tagId - o.getTagId();
    }
}
