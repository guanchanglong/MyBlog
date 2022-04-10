package com.gcl.demo1.service.impl;

import com.gcl.demo1.dao.PictureDao;
import com.gcl.demo1.entity.Picture;
import com.gcl.demo1.service.PictureService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author 小关同学
 * @Create 2022/4/10 9:51
 */
@Service
public class PictureServiceImpl implements PictureService {

    @Autowired
    private PictureDao pictureDao;

    @Override
    public PageInfo<Picture> findAll(int pageNum, int size){
        PageHelper.startPage(pageNum, size, "date desc");
        List<Picture> list = pictureDao.findAllPicture();
        return new PageInfo<>(list);
    }

    @Override
    public List<Picture> findAllPictureToSort(){
        return pictureDao.findAllPictureToSort();
    }

    @Override
    public Picture findPictureById(int id){
        return pictureDao.findPictureById(id);
    }

    @Override
    public void addPicture(Picture picture){
        pictureDao.addPicture(picture);
    }

    @Override
    public void updatePicture(Picture picture){
        pictureDao.updatePicture(picture);
    }

    @Override
    public void deletePictureById(int id){
        pictureDao.deletePictureById(id);
    }
}
