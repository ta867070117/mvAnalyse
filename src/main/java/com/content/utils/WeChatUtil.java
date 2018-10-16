package com.content.utils;

import com.content.HttpUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Shinelon
 * @date 2018/10/1 0:53
 */
public class WeChatUtil {

    private static String userUrl = "https://api.weixin.qq.com/sns/jscode2session";

    /**
     * 获取用户信息
     * @param code
     * @return
     */
    public static String getUserInfo(String code,String appid,String secret) {
        Map paraMap = new HashMap();
        paraMap.put("appid",appid);
        paraMap.put("secret",secret);
        paraMap.put("grant_type","authorization_code");
        paraMap.put("js_code",code);
        String sendGet = HttpUtils.sendGet(userUrl, paraMap);
        System.out.println("返回的用户信息结果"+sendGet);
        return sendGet;

    }

}
