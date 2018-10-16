package com.content;

import com.alibaba.fastjson.JSONObject;
import com.content.PO.BasePO;
import com.content.code.BaseResponse;
import com.content.code.CommonEnum;
import com.content.service.LoadRecordService;
import com.content.service.UserService;
import com.content.service.VideoUserService;
import com.content.utils.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.apache.commons.codec.digest.DigestUtils;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
/**
 * Description
 * Author: wanglei
 * Version:1.0
 * Create Data Time: 14:25 2018/8/25
 */
@Controller
@RequestMapping("/")
public class ConentController {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private UserService userService;
    @Resource(name = "loadRecordService")
    private LoadRecordService loadRecordService;
    @Resource(name = "videoUserService")
    private VideoUserService videoUserService;

    /**
     * 收集用户openId
     * @param baseContentPO
     * @return
     */
    @RequestMapping("/query")
    @ResponseBody
    public Object testSearch(BaseContentPO baseContentPO) {

        if(null == baseContentPO.getCode()) {
            return "谢谢";
        }

        String url = "https://api.weixin.qq.com/sns/jscode2session";
        Map pathMap = new HashMap();
        pathMap.put("appid","wxba46db1070eb7e90");
        pathMap.put("secret","7c050825a0aa98b1da86edb80a4933c0");
        pathMap.put("grant_type","authorization_code");
        pathMap.put("js_code",baseContentPO.getCode());
        String sendGet = HttpUtils.sendGet(url, pathMap);

        JSONObject object = JSONObject.parseObject(sendGet);
        String openid = object.getString("openid");

        int result = userService.getExitOpenId(openid);
        if(result > 0) {
            return null;
        }
        BasePO basePO = new BasePO();
        basePO.setOpenId(openid);
        userService.addUser(basePO);


        return "hello world";
    }

    /**
     * 校验微信签名
     */
    @RequestMapping("/check")
    @ResponseBody
    public Object checkWebChat(BaseContentPO baseContentPO) {
        /**
         * 校验方式
         */


        return baseContentPO.getEchostr();

    }

    @ResponseBody
    @RequestMapping("/video")
    public Object getVideo(BaseContentPO baseContentPO) {
        if(null == baseContentPO.getOpenId()) {
            return new BaseResponse<>(CommonEnum.ERROR.getCode(),"openId不能为空");
        }
        if(null == baseContentPO.getLink()) {
            return new BaseResponse<>(CommonEnum.ERROR.getCode(),"链接地址不能为空");
        }

        //对链接进行判断
        String link = baseContentPO.getLink();

        if(baseContentPO.getLink().contains("weishi.qq")) {
            link = link+"/";
        }

        String trim = link.trim();
        StringBuffer buffer = new StringBuffer(trim);
        int startIndex = buffer.indexOf("http");
        int endIndex = buffer.lastIndexOf("/");
        String substring = buffer.substring(startIndex, endIndex+1);

        link = substring;
        //增加下载视频记录表,间隔10秒的不能重复调用
        if(baseContentPO.getLink().contains("gifshow")) {
            link = baseContentPO.getLink();
        }

        String url = "http://service.iiilab.com/video/download";

        String clientSecretKey = "0e3580fb77b76f294168b548ecf830c4";
        String client = "de888ddf9cde6748";
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
        //发送请求
        String post = HttpUtils.sendPost(url, paramMap);

        JSONObject jsonObject = (JSONObject) JSONObject.parseObject(post).getJSONObject("data");
        String code = JSONObject.parseObject(post).getString("retCode");

        StringBuffer stringBuffer = null;
        if(code.equals("200")) {
            String video = jsonObject.getString("video");
            stringBuffer = new StringBuffer();
            stringBuffer.append(video);
            stringBuffer.insert(4,"s");
        }
        String message = JSONObject.parseObject(post).getString("retDesc");
        System.out.println(post);

        //异步下载文件
        try {
            loadRecordService.downLoadFile(stringBuffer.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }


        if(code.equals("200")) {
            return new BaseResponse(CommonEnum.SUCCESS.getCode(),message,stringBuffer);
        }else {
            return new BaseResponse(CommonEnum.ERROR.getCode(),message,stringBuffer);
        }

    }

    /**
     * 添加去水印用户基础信息
     * @param baseContentPO
     * @return
     */
    @ResponseBody
    @RequestMapping("/addVideoUser")
    public Object addPhotoUser(BaseContentPO baseContentPO) {
        if(null == baseContentPO.getCode()) {
            return "谢谢";
        }

        String url = "https://api.weixin.qq.com/sns/jscode2session";
        Map pathMap = new HashMap();
        pathMap.put("appid","wxc67f59c5abda3db2");
        pathMap.put("secret","6cc5b7113b4454190fa6baa40acb929b");
        pathMap.put("grant_type","authorization_code");
        pathMap.put("js_code",baseContentPO.getCode());
        String sendGet = HttpUtils.sendGet(url, pathMap);

        JSONObject object = JSONObject.parseObject(sendGet);
        String openid = object.getString("openid");

        baseContentPO.setOpenId(openid);
        videoUserService.insertUser(baseContentPO);

        //判断该用户解析次数
        Integer attendNum = videoUserService.getAttendNum(openid);
        System.out.println(attendNum+"参与数量");
        if(attendNum > 15) {
            return new BaseResponse<>(CommonEnum.Limit);
        }else {
            return new BaseResponse<>(CommonEnum.SUCCESS);
        }

    }

    /**
     * 添加登录日志
     */
    @ResponseBody
    @RequestMapping("/loginLog")
    public Object addLog(BaseContentPO baseContentPO) {
        if(null == baseContentPO.getOpenId()) {
            return new BaseResponse<>(CommonEnum.ERROR.getCode(),"openId不能为空");
        }



        return "哈哈咯";
    }

    @ResponseBody
    @RequestMapping("/down/video")
    public Object downLoadFile(BaseContentPO baseContentPO) {
        if(null == baseContentPO.getLink()) {
            return new BaseResponse<>(CommonEnum.ERROR.getCode(),"请求链接不能为空");
        }
        try {
            loadRecordService.downLoadFile(baseContentPO.getLink());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "文件下载完成";
    };



}
