package com.content.service.impl;

import com.content.BaseContentPO;
import com.content.dao.LoadRecordMapper;
import com.content.model.LoadRecord;
import com.content.model.LoadRecordExample;
import com.content.service.LoadRecordService;
import com.content.utils.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.List;


/**
 * @author Shinelon
 * @date 2018/9/8 21:11
 */
@Service("loadRecordService")
public class LoadServiceImpl implements LoadRecordService {

    @Autowired
    private FileUtils fileUtils;

    @Autowired
    private LoadRecordMapper loadRecordMapper;

    @Async
    @Override
    public void downLoadFile(String link) throws Exception {

        LoadRecordExample recordExample = new LoadRecordExample();
        link = link.trim();
        recordExample.createCriteria().andFileUrlEqualTo(link);
        long count = loadRecordMapper.countByExample(recordExample);
        if(count == 0) {
            LoadRecord loadRecord = new LoadRecord();
            loadRecord.setFileUrl(link);
            loadRecord.setCreateTime(new Date());
            int result = loadRecordMapper.insertSelective(loadRecord);
            if(result == 0) {
                throw new Exception("插入地址失败");
            }

            fileUtils.downLoadFileByUrl(link,loadRecord.getFileId()+"");
        }else {
            System.out.println("======该地址已经存在======");
        }
    }

    /**
     * 查询该链接对应的文件名
     * @param baseContentPO
     * @return
     */
    @Override
    public Integer getLoadFile(BaseContentPO baseContentPO) {
        LoadRecordExample example = new LoadRecordExample();
        example.createCriteria().andFileUrlEqualTo(baseContentPO.getLink());
        List<LoadRecord> recordList = loadRecordMapper.selectByExample(example);
        if(null != recordList && recordList.size() > 0) {
            return recordList.get(0).getFileId();
        }
        return null;
    }
}
