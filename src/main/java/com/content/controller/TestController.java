package com.content.controller;

import com.content.utils.FileUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Shinelon
 * @date 2018/9/30 22:24
 */
@RequestMapping("/")
@RestController
public class TestController {


    @RequestMapping("/test")
    public void hk() {
        String property = System.getProperty("catalina.home");
        System.out.println("===tomcat目录" + property);
    }
}
