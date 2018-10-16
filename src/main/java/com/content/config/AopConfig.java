package com.content.config;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;

/**
 * @author Shinelon
 * @date 2018/9/9 8:43
 */
@Service(value = "aspectBean")
@Aspect
public class AopConfig {

    public static final Logger logger = LoggerFactory.getLogger(AopConfig.class);
    /**
     * 必须为final String类型的,注解里要使用的变量只能是静态常量类型的
     */
    public static final String EDP="execution(* com.content.*.*(..))";


    /**
     * 声明环绕通知
     * @param pjp
     * @return
     * @throws Throwable
     */
    @Around(EDP)
    public Object doAround(ProceedingJoinPoint pjp) throws Throwable {
        long begin = getTime();
        Object o = pjp.proceed();
        long end = getTime();
        String date = getDate();
        logger.info("\n======"+date+"======进入方法:"+pjp.getSignature().getName()+"=====耗费时间:"+(end-begin));
        return o;
    }


    /**
     * 获取时间戳
     * @return
     */
    public long getTime() {
        long start = System.currentTimeMillis();
        return start;
    }

    /**
     * 获取时间
     * @return
     */
    public String getDate() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return df.format(System.currentTimeMillis());
    }
}
