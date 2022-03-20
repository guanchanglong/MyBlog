package com.gcl.demo1.controller.admin;

import com.gcl.demo1.entity.TimeLine;
import com.gcl.demo1.entity.User;
import com.gcl.demo1.service.TimeLineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.Date;

/**
 * @Author 小关同学
 * @Create 2022/3/20 15:54
 */
@Controller
@RequestMapping("/admin/timeLine")
public class TimeLineController {

    @Autowired
    private TimeLineService timeLineService;

    @GetMapping("/list")
    //以createTime(更新时间)来排序
    public String blogs(@RequestParam(value = "pageNum",defaultValue = "1") int pageNum,
                        @RequestParam(value = "size",defaultValue = "8") int size,
                        Model model) {
        //获得page对象
        model.addAttribute("page", timeLineService.findAllTimeLine(pageNum, size));
        return "admin/timeLines";
    }

    @GetMapping("/input")
    public String input(int id, Model model){
        if (id==0){
            model.addAttribute("timeLine", new TimeLine());
        }else{
            TimeLine timeLine = timeLineService.findTimeLineById(id);
            model.addAttribute("timeLine", timeLine);
        }
        return "admin/timeLine-input";
    }

    /**
     * 提交感悟到本地保存
     * @param timeLine
     * @param attributes
     * @param session
     * @return
     */
    @PostMapping("/timeLineAddOrUpdate")
    public String post(TimeLine timeLine,
                       RedirectAttributes attributes,
                       HttpSession session) {
        User user = (User) session.getAttribute("user");
        timeLine.setUserId(user.getId());
        timeLine.setPublicTime(new Date());
        timeLine.setContent(timeLine.getContent().replaceAll("../../", "../"));
        if (timeLine.getId() == 0) {
            timeLineService.insertTimeLine(timeLine);
        } else {
            timeLineService.updateTimeLineById(timeLine);
        }
        attributes.addFlashAttribute("message", "操作成功");
        return "redirect:/admin/timeLine/list";
    }


    /**
     * 删除博客
     * @param id
     * @param attributes
     * @return
     */
    @GetMapping("/{id}/delete")
    public String delete(@PathVariable int id, RedirectAttributes attributes) {
        System.out.println(id);
        timeLineService.deleteTimeLineById(id);
        attributes.addFlashAttribute("message", "删除成功");
        return "redirect:/admin/timeLine/list";
    }
}
