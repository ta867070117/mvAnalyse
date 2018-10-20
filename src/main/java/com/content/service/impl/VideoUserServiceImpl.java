package com.content.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.content.BaseContentPO;
import com.content.code.BaseResponse;
import com.content.code.CommonEnum;
import com.content.controller.AnalyseController;
import com.content.dao.VideoUserMapper;
import com.content.model.VideoUser;
import com.content.model.VideoUserExample;
import com.content.service.OperateLogService;
import com.content.service.VideoUserService;
import com.content.utils.WeChatUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author Shinelon
 * @date 2018/9/9 18:32
 */
@Service("videoUserService")
public class VideoUserServiceImpl implements VideoUserService {

    private static final Logger logger = LoggerFactory.getLogger(VideoUserServiceImpl.class);

    @Autowired
    private VideoUserMapper videoUserMapper;
    @Autowired
    private OperateLogService operateLogService;

    @Override
    @Async
    public BaseResponse insertUser(BaseContentPO baseContentPO) {

        BaseResponse baseResponse = null;

        VideoUser videoUser = new VideoUser();
        videoUser.setAvatarurl(baseContentPO.getAvatarUrl());
        videoUser.setCity(baseContentPO.getCity());
        videoUser.setNickName(baseContentPO.getNickName());
        videoUser.setCreateTime(new Date());
        videoUser.setUserId(null);
        videoUser.setGender(baseContentPO.getGender());
        videoUser.setOpenId(baseContentPO.getOpenId());

        //查询该用户是否存在
        VideoUserExample videoUserExample = new VideoUserExample();
        videoUserExample.createCriteria().andOpenIdEqualTo(baseContentPO.getOpenId());
        long count = videoUserMapper.countByExample(videoUserExample);
        if(count == 0) {
            videoUserMapper.insert(videoUser);
        }else {
            videoUserMapper.updateAttendNum(baseContentPO.getOpenId());
        }


        return baseResponse;
    }

    /**
     * 判断解析次数
     * @param openid
     * @return
     */
    @Override
    public Integer getAttendNum(String openid) {
        VideoUserExample videoUserExample = new VideoUserExample();
        videoUserExample.createCriteria().andOpenIdEqualTo(openid);
        List<VideoUser> videoUsers = videoUserMapper.selectByExample(videoUserExample);
        if(null == videoUsers || videoUsers.size() == 0) {
            return 0;
        }
        return videoUsers.get(0).getAttendNum();
    }

    /**
     * 根据参数调用不同的方法
     * @param baseContentPO
     * @return
     */
    @Override
    public BaseResponse getAnalyseTimes(BaseContentPO baseContentPO) {
        //短视频去水印小助手
        if(null == baseContentPO.getProgramType()) {
            System.out.println("\n\n======短视频去水印小助手======");
            baseContentPO.setAppid("wxc67f59c5abda3db2");
            baseContentPO.setSecret("9345cea7f8a41ea451f067777556650e");
            //短视频去水印哟
        }else if(baseContentPO.getProgramType().equals("1")) {
            baseContentPO.setAppid("wx9ed3917a3b770fda");
            baseContentPO.setSecret("372602d8f856e348189b40db187dc604");
            //视频去水印助手
        }else if(baseContentPO.getProgramType().equals("0")) {
            baseContentPO.setAppid("wxf44b109eabbe6648");
            baseContentPO.setSecret("d60e78cf2df210087113c58a695fb702");
            //短视频去水印小助手
        }else {
            System.out.println("======短视频去水印小助手======");
            baseContentPO.setAppid("wxc67f59c5abda3db2");
            baseContentPO.setSecret("9345cea7f8a41ea451f067777556650e");
        }
        return getAnalyse(baseContentPO);
    }

    /**
     * 处理分析工具类
     * @param baseContentPO
     * @return
     */
    public BaseResponse getAnalyse(BaseContentPO baseContentPO) {
        BaseResponse baseResponse = null;
        String appid= baseContentPO.getAppid();
        String secret = baseContentPO.getSecret();
        String userInfo = WeChatUtil.getUserInfo(baseContentPO.getCode(), appid, secret);
        JSONObject object = JSONObject.parseObject(userInfo);
        if(null != object.getString("errcode")) {
            logger.info("======获取用户openId出现错误======");
            return new BaseResponse(CommonEnum.Limit.getCode(),"已上限请联系作者微信：Take5127");
        }

        String openid = object.getString("openid");
        logger.info("请求的openId"+openid);
        baseContentPO.setOpenId(openid);
        /**
         * 异步插入处理日志
         */
        if(null != openid && !"".equals(openid)) {
            operateLogService.insertLog(openid);
        }

        /**
         * 异步处理用户信息
         */

        try {
            insertUser(baseContentPO);
        } catch (Exception e) {
            baseContentPO.setNickName("带表情的昵称");
            insertUser(baseContentPO);
            logger.info("=======出现带表情的昵称======");
        }

        Integer attendNum = getAttendNum(openid);
        if(attendNum > 3) {
            System.out.println("======该用户已经达到解析次数======"+openid);
            baseResponse = new BaseResponse(CommonEnum.Limit.getCode(),"已上限请联系作者微信：Take5127");
        }else {
            baseResponse = new BaseResponse(CommonEnum.SUCCESS.getCode());
        }
        return baseResponse;
    }

    @Override
    public BaseResponse getUserInfo(BaseContentPO baseContentPO) {
        System.out.println("======开始获取用户详细信息======");
        if(null == baseContentPO.getOpenId()) {
            return new BaseResponse(CommonEnum.ERROR.getCode(),"openId不能为空");
        }
        VideoUserExample videoUserExample = new VideoUserExample();
        videoUserExample.createCriteria().andOpenIdEqualTo(baseContentPO.getOpenId());
        List<VideoUser> videoUsers = videoUserMapper.selectByExample(videoUserExample);
        if(null != videoUsers && videoUsers.size() > 0) {
            return new BaseResponse(CommonEnum.SUCCESS,videoUsers.get(0));
        }else {
            return new BaseResponse(CommonEnum.ERROR.getCode(),"该用户不存在");
        }
    }
}
