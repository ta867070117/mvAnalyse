package com.content.service;

import com.content.BaseContentPO;
import com.content.code.BaseResponse;

/**
 * @author Shinelon
 * @date 2018/9/9 18:32
 */
public interface VideoUserService {
    BaseResponse insertUser(BaseContentPO baseContentPO);

    Integer getAttendNum(String openid);

    BaseResponse getAnalyseTimes(BaseContentPO baseContentPO);

    BaseResponse getUserInfo(BaseContentPO baseContentPO);
}
