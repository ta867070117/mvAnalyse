package com.content.utils;

import cn.hutool.core.io.FileUtil;
import cn.hutool.http.HttpUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.io.File;

/**
 * @author Shinelon
 * @date 2018/9/8 18:28
 */
@Component
public class FileUtils {

    private static final Logger logger = LoggerFactory.getLogger(FileUtils.class);

    /**
     * 根据url链接下载文件
     * @param url
     */
    @Async
    public void downLoadFileByUrl(String url,String fileName) {
        System.out.println("======开始上传视频文件======");
        //将视频保存到服务器目录
        String property = System.getProperty("catalina.home")+"/webapps";
        File saveFile = new File(property+("/ROOT/upload/") + fileName);
        if (!saveFile.getParentFile().exists()) {
            saveFile.getParentFile().mkdirs();
        }
        long size = HttpUtil.downloadFile(url, FileUtil.file(saveFile+".mp4"));
        logger.info("视频下载完成 视频大小:"+size);

    }

    /**
     * 对视频链接进行解析
     * @param link
     * @return
     */
    public static String analyseUrl(String link) {
        String analyseUrl = null;
        if(null != link && link != "") {
            link.trim();
        }else {
            return null;
        }
        //处理快手
        if(link.contains("gifshow")) {
            return analyseUrl = link;
        }else if(link.contains("douyin")) {
            analyseUrl = subString(link);
        }else if(link.contains("weishi")) {
            link = link + "/";
            analyseUrl = subString(link);
        }else {
            link = link + "/";
            analyseUrl = subString(link);
        }

        return analyseUrl;
    }

    /**
     * 处理字符串
     * @param link
     * @return
     */
    public static String subString(String link) {
        if(null == link || link.equals("")) {
            return null;
        }
        StringBuffer buffer = new StringBuffer(link);
        int startIndex = buffer.indexOf("http");
        int endIndex = buffer.lastIndexOf("/");

        String substring = buffer.substring(startIndex, endIndex);
        logger.info("去掉多余的参数后的url:"+substring);
        link = substring;
        return link;
    }
}
