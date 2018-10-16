package com.content.code;

import java.io.Serializable;

/**
 * @description:
 * @author: fengxx
 * @version: 1.0
 * @modified:
 * @dete:Create in 2017/12/28 16:04
 */
public class BaseResponse<T> implements Serializable {

    private static final long serialVersionUID = -7820178383963287725L;
    protected String code;
    protected String message;
    protected T data;

    public BaseResponse(){
        this.code= CommonEnum.SUCCESS.getCode();
        this.message=CommonEnum.SUCCESS.getCname();
    }

    public BaseResponse(T data){
        this();
        this.data=data;
    }
    public BaseResponse(String code, String message){
        this.code=code;
        this.message=message;
    }
    public BaseResponse(String code, String message, T data){
        this(code,message);
        this.data = data;
    }

    public BaseResponse(CommonEnum commonEnum){
        this.code = commonEnum.getCode();
        this.message = commonEnum.getCname();
    }
    public BaseResponse(CommonEnum commonEnum, T data){
        this(commonEnum);
        this.data=data;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}

