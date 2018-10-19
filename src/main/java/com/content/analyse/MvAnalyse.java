package com.content.analyse;

import com.content.code.BaseResponse;
import com.content.code.CommonEnum;
import com.content.dao.LoadRecordMapper;
import com.content.model.LoadRecord;
import com.content.model.LoadRecordExample;
import com.content.utils.App;
import com.content.utils.HttpClientUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.Date;

/**
 * 该类统一处理各类不同视频
 */
@Component
public class MvAnalyse {

    @Autowired
    private LoadRecordMapper loadRecordMapper;

    /**
     * 处理抖音视频解析
     * @param url
     * @return
     */
    public BaseResponse analyseDouYin(String url) {
        String analyseUrl = App.douyin(url);
        System.out.println("======本地解析后无水印视频链接地址======"+analyseUrl);
        if(null == analyseUrl) {
            System.out.println("======视频出错地址======"+url);
            return new BaseResponse(CommonEnum.ERROR.getCode(),"联系微信：Take5127 该视频不可解析");
        }
        url = analyseUrl;
        //此处进行抖音下载的操作
        downLoadDouYin(analyseUrl);

        return null;
    }

    public void downLoadDouYin(String url) {
        LoadRecordExample recordExample = new LoadRecordExample();
        recordExample.createCriteria().andFileUrlEqualTo(url);
        long count = loadRecordMapper.countByExample(recordExample);
        if(count == 0) {
            LoadRecord loadRecord = new LoadRecord();
            loadRecord.setFileUrl(url);
            loadRecord.setCreateTime(new Date());
            loadRecordMapper.insertSelective(loadRecord);
            Integer fileId = loadRecord.getFileId();
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
                    HttpClientUtils.getInstance().download(url, "C:\\Users\\wanglei\\Desktop\\3.mp4",
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

}
