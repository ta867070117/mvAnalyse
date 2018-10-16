package com.content.controller;

import com.content.BaseContentPO;
import com.content.code.BaseResponse;
import com.content.service.VideoUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Shinelon
 * @date 2018/9/30 22:01
 */
@RequestMapping("/")
@RestController
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private VideoUserService videoUserService;
    /**
     * 获取该用户下载次数
     * @param baseContentPO
     * @return
     */
    @RequestMapping("/getAnalyseTimes")
    public BaseResponse getAnalyseTimes(BaseContentPO baseContentPO) {
        return videoUserService.getAnalyseTimes(baseContentPO);
    }

}
