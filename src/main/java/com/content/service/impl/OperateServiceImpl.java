package com.content.service.impl;

import com.content.dao.OperateLogMapper;
import com.content.model.OperateLog;
import com.content.service.OperateLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author Shinelon
 * @date 2018/10/4 23:56
 */
@Service("operateLogService")
public class OperateServiceImpl implements OperateLogService {

    @Autowired
    private OperateLogMapper operateLogMapper;

    @Async
    @Override
    public void insertLog(String openid) {
        try {
            OperateLog log = new OperateLog();
            log.setOpenId(openid);
            log.setCreateTime(new Date());
            operateLogMapper.insert(log);
        } catch (Exception e) {
            System.out.println("插入解析日志时用户OPENID为空");
        }
    }
}
