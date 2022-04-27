package com.smk.cpp.sts.base.exception;

import com.smk.cpp.sts.base.enums.ResultStatusEnums;

/**
 * @Description:
 * @Author: Jin-LiangBo
 * @Date: 2022/4/10 16:34
 */
public class ServiceException extends RuntimeException{

    private int code;

    private String msg;

    public ServiceException(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public ServiceException(String message, int code, String msg) {
        super(message);
    }

    public ServiceException(ResultStatusEnums status) {
        this.code = status.getCode();
        this.msg = status.getMessage();
    }

    public ServiceException(String message) {
        super(message);
    }

    public ServiceException(String message, Throwable cause, int code, String msg) {
        super(message, cause);
    }

    public ServiceException(Throwable cause, int code, String msg) {
        super(cause);
    }

    public ServiceException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, int code, String msg) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

}
