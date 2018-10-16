package com.content.model;

import java.io.Serializable;
import java.util.Date;

/**
 * Description
 * Author: wanglei
 * Version:1.0
 * Create Data Time: 14:15 2018/8/30
 */
public class User implements Serializable {
    private Integer userId;

    private String openId;

    private Date createTime;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
