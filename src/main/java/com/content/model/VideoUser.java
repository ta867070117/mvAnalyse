package com.content.model;

import java.io.Serializable;
import java.util.Date;

/**
 * @author 
 */
public class VideoUser implements Serializable {
    private Integer userId;

    /**
     * 用户id
     */
    private String openId;

    /**
     * 用户昵称
     */
    private String nickName;

    /**
     * 所在城市
     */
    private String city;

    /**
     * 用户头像
     */
    private String avatarurl;

    /**
     * 用户性别
     */
    private String gender;

    /**
     * 更新日期
     */
    private Date createTime;

    private Integer attendNum;

    public Integer getAttendNum() {
        return attendNum;
    }

    public void setAttendNum(Integer attendNum) {
        this.attendNum = attendNum;
    }

    private static final long serialVersionUID = 1L;

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

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAvatarurl() {
        return avatarurl;
    }

    public void setAvatarurl(String avatarurl) {
        this.avatarurl = avatarurl;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        VideoUser other = (VideoUser) that;
        return (this.getUserId() == null ? other.getUserId() == null : this.getUserId().equals(other.getUserId()))
            && (this.getOpenId() == null ? other.getOpenId() == null : this.getOpenId().equals(other.getOpenId()))
            && (this.getNickName() == null ? other.getNickName() == null : this.getNickName().equals(other.getNickName()))
            && (this.getCity() == null ? other.getCity() == null : this.getCity().equals(other.getCity()))
            && (this.getAvatarurl() == null ? other.getAvatarurl() == null : this.getAvatarurl().equals(other.getAvatarurl()))
            && (this.getGender() == null ? other.getGender() == null : this.getGender().equals(other.getGender()))
            && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getUserId() == null) ? 0 : getUserId().hashCode());
        result = prime * result + ((getOpenId() == null) ? 0 : getOpenId().hashCode());
        result = prime * result + ((getNickName() == null) ? 0 : getNickName().hashCode());
        result = prime * result + ((getCity() == null) ? 0 : getCity().hashCode());
        result = prime * result + ((getAvatarurl() == null) ? 0 : getAvatarurl().hashCode());
        result = prime * result + ((getGender() == null) ? 0 : getGender().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        return result;
    }

    @Override
    public String toString() {
        return "VideoUser{" +
                "userId=" + userId +
                ", openId='" + openId + '\'' +
                ", nickName='" + nickName + '\'' +
                ", city='" + city + '\'' +
                ", avatarurl='" + avatarurl + '\'' +
                ", gender='" + gender + '\'' +
                ", createTime=" + createTime +
                ", attendNum=" + attendNum +
                '}';
    }
}