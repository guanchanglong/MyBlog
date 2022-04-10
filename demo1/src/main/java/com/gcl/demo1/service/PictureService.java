package com.gcl.demo1.service;

import com.gcl.demo1.entity.Picture;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * @Author 小关同学
 * @Create 2022/4/10 9:51
 */
public interface PictureService {
    PageInfo<Picture> findAll(int pageNum, int size);

    List<Picture> findAllPictureToSort();

    Picture findPictureById(int id);

    void addPicture(Picture picture);

    void updatePicture(Picture picture);

    void deletePictureById(int id);
}
