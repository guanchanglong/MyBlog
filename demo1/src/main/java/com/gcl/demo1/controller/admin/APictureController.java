package com.gcl.demo1.controller.admin;

import com.gcl.demo1.entity.Picture;
import com.gcl.demo1.service.PictureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Date;

/**
 * @Author 小关同学
 * @Create 2022/4/10 9:57
 */
@Controller
@RequestMapping("/admin/picture")
public class APictureController {

    @Autowired
    private PictureService pictureService;

    @GetMapping("/list")
    //以createTime(更新时间)来排序
    public String blogs(@RequestParam(value = "pageNum",defaultValue = "1") int pageNum,
                        @RequestParam(value = "size",defaultValue = "8") int size,
                        Model model) {
        //获得page对象
        model.addAttribute("page", pictureService.findAll(pageNum, size));
        return "admin/picture";
    }

    @GetMapping("/input")
    public String input(int id, Model model){

        if (id==0){
            model.addAttribute("picture", new Picture());
        }else{
            Picture picture = pictureService.findPictureById(id);
            picture.setDate(picture.getDate().replaceAll(" 00:00:00", ""));
            model.addAttribute("picture", picture);
        }
        return "admin/picture-input";
    }

    /**
     * 提交感悟到本地保存
     * @param picture
     * @param attributes
     * @return
     */
    @PostMapping("/pictureAddOrUpdate")
    public String post(Picture picture,
                       RedirectAttributes attributes) {
        if (picture.getId() == 0) {
            picture.setCreateTime(new Date());
            pictureService.addPicture(picture);
        } else {
            if (picture.getDate().isEmpty()){
                picture.setDate(null);
            }
            pictureService.updatePicture(picture);
        }
        attributes.addFlashAttribute("message", "操作成功");
        return "redirect:/admin/picture/list";
    }


    /**
     *
     * @param id
     * @param attributes
     * @return
     */
    @GetMapping("/{id}/delete")
    public String delete(@PathVariable int id, RedirectAttributes attributes) {
        pictureService.deletePictureById(id);
        attributes.addFlashAttribute("message", "删除成功");
        return "redirect:/admin/picture/list";
    }
}
