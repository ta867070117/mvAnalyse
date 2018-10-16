package com.content.controller;

import com.content.BaseContentPO;
import com.content.code.BaseResponse;
import com.content.code.CommonEnum;
import com.content.service.AnalyseService;
import com.content.service.LoadRecordService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Shinelon
 * @date 2018/9/30 22:36
 */
@RequestMapping("/")
@RestController
public class AnalyseController {

    private static final Logger logger = LoggerFactory.getLogger(AnalyseController.class);

    @Autowired
    private AnalyseService analyseService;
    @Autowired
    private LoadRecordService loadRecordService;

    @RequestMapping("/analyse")
    public BaseResponse analyseVideo(BaseContentPO baseContentPO) {

        return analyseService.analyseVideo(baseContentPO);
    }

    /**
     * 从本地服务器获取视频
     * @param baseContentPO
     * @return
     */
    @ResponseBody
    @RequestMapping("/getVideoName")
    public Object getLoadFile(BaseContentPO baseContentPO) {
        if(null == baseContentPO.getLink()) {
            return new BaseResponse<>(CommonEnum.ERROR.getCode(),"请求链接不能为空");
        }
        Integer result = loadRecordService.getLoadFile(baseContentPO);
        if(null == result) {
            return new BaseResponse(CommonEnum.SUCCESS,-1);
        }
        return new BaseResponse(CommonEnum.SUCCESS,result);
    }

}
