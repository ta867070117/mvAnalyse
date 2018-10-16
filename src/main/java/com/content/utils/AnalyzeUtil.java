package com.content.utils;

import cn.hutool.http.Header;
import cn.hutool.http.HttpRequest;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.content.HttpUtils;
import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * @author Shinelon
 * @date 2018/9/30 22:04
 */
public class AnalyzeUtil {

    private static final Logger logger = LoggerFactory.getLogger(AnalyzeUtil.class);

    private static String analyzeUrl = "http://service.iiilab.com/video/download";

    private static String clientSecretKey = "0e3580fb77b76f294168b548ecf830c4";

    private static String client = "de888ddf9cde6748";

    /**
     * 解析视频地址
     * @param link
     * @return
     */
    public static String analyseVideo(String link) {
        Long timeStamp = System.currentTimeMillis();

        /**
         * 进行md5加密
         */
        String md5Hex = DigestUtils.md5Hex(link + timeStamp + clientSecretKey);
        /**
         * 接口请求参数
         */
        Map paramMap = new HashMap();
        paramMap.put("link",link);
        paramMap.put("timestamp",timeStamp+"");
        paramMap.put("sign",md5Hex);
        paramMap.put("client",client);

        String result = HttpUtils.sendPost(analyzeUrl, paramMap);
        logger.info("请求视频解析返回结果"+result);

        return result;
    }

}
