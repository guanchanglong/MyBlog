package com.gcl.demo1.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;


/**
 * @author 小关同学
 * @create 2021/11/1
 */
//切面注解
@Aspect
//开启组件扫描
@Component
public class LogAspect {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    //声明切面
    //execution()里面的内容来规定我这个注解来拦截哪些类,
    //* com.web.*.*(..)的意思是拦截com.web包下的所有方法
    @Pointcut("execution(* com.gcl.demo1.controller.*.*(..))")
    public void log(){ }

    //传递切面log()，表明这个doBefore()方法会在log()这个切面之前执行
    @Before("log()")
    public void doBefore(JoinPoint joinPoint){
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        assert attributes != null;
        HttpServletRequest request = attributes.getRequest();
        //获取url、ip、classMethod和args
        String url = request.getRequestURI();
        String ip = request.getRemoteAddr();
        String classMethod = joinPoint.getSignature().getDeclaringTypeName()+"."+joinPoint.getSignature().getName();
        Object[] args = joinPoint.getArgs();
        RequestLog requestLog = new RequestLog(url,ip,classMethod,args);
        logger.info("Request : {}",requestLog);
    }

    //该方法在log()这个切面之后执行
    @After("log()")
    public void doAfter(){
        //logger.info("-------doAfter---------");
    }

    //returning = "result"为返回的内容，pointcut为切面内容
    @AfterReturning(returning = "result",pointcut = "log()")
    //定义一个Object类型来接受返回的类型
    public void doAfterReturn(Object result){
        logger.info("Result：{}",result);
    }

    //定义一个内部类来方便接受请求url、访问者的ip、调用的方法classMethod和传递的参数args
    private class RequestLog{
        private String url;
        private String ip;
        private String classMethod;
        //因为传递的参数可能会有点多，所以我们使用对象数组来储存请求的参数
        private Object[] args;

        public RequestLog(String url, String ip, String classMethod, Object[] args) {
            this.url = url;
            this.ip = ip;
            this.classMethod = classMethod;
            this.args = args;
        }

        @Override
        public String toString() {
            return "{" +
                    "url='" + url + '\'' +
                    ", ip='" + ip + '\'' +
                    ", classMethod='" + classMethod + '\'' +
                    ", args=" + Arrays.toString(args) +
                    '}';
        }
    }
}
