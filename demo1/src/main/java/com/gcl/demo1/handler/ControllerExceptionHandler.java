package com.gcl.demo1.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class ControllerExceptionHandler {

    //获取异常作为日志记录下来
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    // @ExceptionHandler这个注解表明它是可以进行异常处理的，Exception.class表明只要是Exception这个级别下的都有效
    @ExceptionHandler(Exception.class)
    public ModelAndView exceptionHandler(HttpServletRequest request,Exception e,Model model) throws Exception {
        //如果存在这个异常就让SpringBoot抛出
        if (AnnotationUtils.findAnnotation(e.getClass(), ResponseStatus.class)!=null){
            throw e;
        }

        logger.error("Request URL : {},Exception : {}",request.getRequestURI(),e);


        model.addAttribute("url",request.getRequestURI());
        model.addAttribute("exception",e);

        //返回到error页面去
        return new ModelAndView("errors/error","model",model);
    }
}
