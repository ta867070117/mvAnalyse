package com.content.code;

/**
 * @description:
 * @author: fengxx
 * @version: 1.0
 * @modified:
 * @dete:Create in 2017/12/28 16:37
 */
public enum CommonEnum {
    /**
     * 系统代码
     */
    SUCCESS("0001","操作成功",""),
    ERROR("0002","操作失败",""),
    Limit("0003","解析上限",""),
    OtherError("1001","抖音接口错误","");

    private String code;
    private String cname;
    private String ename;
    private CommonEnum(String code, String cname, String ename){
        this.code=code;
        this.cname=cname;
        this.ename=ename;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    public String getEname() {
        return ename;
    }

    public void setEname(String ename) {
        this.ename = ename;
    }

}
