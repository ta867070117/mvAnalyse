package com.content.service.impl;

import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSONObject;
import com.content.BaseContentPO;
import com.content.code.BaseResponse;
import com.content.code.CommonEnum;
import com.content.dao.LoadRecordMapper;
import com.content.model.LoadRecord;
import com.content.model.LoadRecordExample;
import com.content.service.AnalyseService;
import com.content.utils.AnalyzeUtil;
import com.content.utils.App;
import com.content.utils.FileUtils;
import com.content.utils.HttpClientUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.Date;
import java.util.List;

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
        if(null == baseContentPO.getLink() || "".equals(baseContentPO.getLink())) {
            System.out.println("======请求的链接地址为空======");
            return new BaseResponse(CommonEnum.ERROR.getCode(),"请求地址为空");
        }
        if(!baseContentPO.getLink().contains("http")) {
            return new BaseResponse(CommonEnum.ERROR.getCode(),"视频链接地址不正确");
        }
        System.out.println("======用户传入的视频链接======"+baseContentPO.getLink());
        //去除中文 仅保留视频链接
        String analyseUrl = FileUtils.analyseUrl(baseContentPO.getLink());
        if(null == analyseUrl || "".equals(analyseUrl)) {
            return new BaseResponse(CommonEnum.ERROR.getCode(),"视频地址不能为空");
        }

        String url = null;
        JSONObject jsonObject = null;
        if(baseContentPO.getLink().contains("douyin")) {
            System.out.println("======进入抖音解析本地接口======解析地址:"+analyseUrl);
            String douyin = App.douyin(analyseUrl);
            System.out.println("======本地解析后无水印视频链接地址======"+douyin);
            if(null == douyin) {
                System.out.println("======视频出错地址======"+analyseUrl);
                return new BaseResponse(CommonEnum.ERROR.getCode(),"联系微信：Take5127 该视频不可解析");
            }
            url = douyin;
            //此处进行抖音下载的操作
            downLoadDouYin(douyin);

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
        }
        if(null == url || "".equals(url)) {
            return new BaseResponse(CommonEnum.ERROR.getCode(),"该视频暂时不能解析");
        }
        if(!baseContentPO.getLink().contains("douyin")) {
            //查看该视频是否已经进行过下载
            LoadRecordExample recordExample = new LoadRecordExample();
            recordExample.createCriteria().andFileUrlEqualTo(url);
            List<LoadRecord> loadRecords = loadRecordMapper.selectByExample(recordExample);
            Integer fileId = null;
            if(null != loadRecords && loadRecords.size() > 0) {
                fileId = loadRecords.get(0).getFileId();
            }else {
                LoadRecord loadRecord = new LoadRecord();
                loadRecord.setFileUrl(url);
                loadRecord.setCreateTime(new Date());
                loadRecordMapper.insertSelective(loadRecord);
                fileId = loadRecord.getFileId();
            }
            //将视频上传至服务器
            fileUtils.downLoadFileByUrl(url,fileId+"");
        }

        baseResponse = new BaseResponse(CommonEnum.SUCCESS,url);

        return baseResponse;
    }

    public void downLoadDouYin(String url) {
        LoadRecordExample recordExample = new LoadRecordExample();
        recordExample.createCriteria().andFileUrlEqualTo(url);
        List<LoadRecord> loadRecords = loadRecordMapper.selectByExample(recordExample);
        Integer fileId = null;
        if(null != loadRecords && loadRecords.size() > 0) {
            fileId = loadRecords.get(0).getFileId();
        }else {
            LoadRecord loadRecord = new LoadRecord();
            loadRecord.setFileUrl(url);
            loadRecord.setCreateTime(new Date());
            loadRecordMapper.insertSelective(loadRecord);
            fileId = loadRecord.getFileId();
        }
        String fileName = fileId+".mp4";
        String property = System.getProperty("catalina.home")+"/webapps";
        File saveFile = new File(property+("/ROOT/upload/") + fileName);
        if (!saveFile.getParentFile().exists()) {
            saveFile.getParentFile().mkdirs();
        }
        System.out.println("输出的视频链接地址===="+saveFile);
        int times = 1;
        //开始抖音视频的解析下载
        try {
            HttpClientUtils.getInstance().download(url, saveFile+"",
                    new HttpClientUtils.HttpClientDownLoadProgress() {
                        @Override
                        public void onProgress(int progress) {
                            if(progress == 100) {
                                System.out.println("======视频下载完成======");
                            }
                        }});
        } catch (Exception e) {
            System.out.println("=======下载都是视频文件时出现异常=======重试次数"+times);
            if(times < 3) {
                HttpClientUtils.getInstance().download(url, saveFile+"",
                        new HttpClientUtils.HttpClientDownLoadProgress() {
                            @Override
                            public void onProgress(int progress) {
                                if(progress == 100) {
                                    System.out.println("======视频下载完成======");
                                }
                            }});
            }
            times++;
        }
    }
}
