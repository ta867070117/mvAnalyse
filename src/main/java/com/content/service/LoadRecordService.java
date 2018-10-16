package com.content.service;

import com.content.BaseContentPO;
import com.content.model.LoadRecord;

/**
 * @author Shinelon
 * @date 2018/9/8 21:11
 */
public interface LoadRecordService {
    void downLoadFile(String link) throws Exception;

    Integer getLoadFile(BaseContentPO baseContentPO);
}
