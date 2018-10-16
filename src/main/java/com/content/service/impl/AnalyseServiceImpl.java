package com.content.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.content.BaseContentPO;
import com.content.code.BaseResponse;
import com.content.code.CommonEnum;
import com.content.dao.LoadRecordMapper;
import com.content.model.LoadRecord;
import com.content.model.LoadRecordExample;
import com.content.service.AnalyseService;
import com.content.utils.AnalyzeUtil;
import com.content.utils.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author Shinelon
 * @date 2018/9/30 22:38
 */
@Service("analyseService")
public class AnalyseServiceImpl implements AnalyseService {

    private static final Logger logger = LoggerFactory.getLogger(AnalyseServiceImpl.class);

    @Autowired
    private LoadRecordMapper loadRecordMapper;
    @Autowired
    private FileUtils fileUtils;

    @Override
    public BaseResponse analyseVideo(BaseContentPO baseContentPO) {
        BaseResponse baseResponse = null;
        if(null == baseContentPO.getLink()) {
            baseResponse = new BaseResponse(CommonEnum.ERROR.getCode(),"请求地址为空");
        }
        String analyseUrl = FileUtils.analyseUrl(baseContentPO.getLink());
        logger.info("请求的视频链接地址:"+analyseUrl);
        if(null == analyseUrl) {
            baseResponse = new BaseResponse(CommonEnum.ERROR.getCode());
            return baseResponse;
        }
        String url = null;
        JSONObject jsonObject = null;
        if(baseContentPO.getLink().contains("douyin")) {
            //String playAddr = DouYinDecode.getURI(DouYinDecode.NewUrlDecode(DouYinDecode.urlAnalysisMethod(analyseUrl))).trim();// 有空白符
            //System.out.println("返回后的数据"+playAddr);
            //String cover = DouYinDecode.getCover(DouYinDecode.urlAnalysisMethod(douYinUrl)).trim();// 有空白符
            //url = playAddr;
            System.out.println("======本地接口======");
        }else {
            String result = AnalyzeUtil.analyseVideo(analyseUrl);
            jsonObject = JSONObject.parseObject(result);
            if(jsonObject.getString("retCode").equals("200")) {
                url = jsonObject.getJSONObject("data").getString("video");
                if(null == url | url.equals("")) {
                    return new BaseResponse(CommonEnum.ERROR.getCode(),"该视频暂时不能解析");
                }
                System.out.println("======进入第三方接口======");
        }

            //查看该视频是否已经进行过下载
            LoadRecordExample recordExample = new LoadRecordExample();
            recordExample.createCriteria().andFileUrlEqualTo(url);
            long count = loadRecordMapper.countByExample(recordExample);
            if(count == 0) {
                LoadRecord loadRecord = new LoadRecord();
                loadRecord.setFileUrl(url);
                loadRecord.setCreateTime(new Date());
                loadRecordMapper.insertSelective(loadRecord);
                Integer fileId = loadRecord.getFileId();
                //将视频上传至服务器
                fileUtils.downLoadFileByUrl(url,fileId+"");

            }

            baseResponse = new BaseResponse(CommonEnum.SUCCESS,url);
        }

        return baseResponse;
    }
}
