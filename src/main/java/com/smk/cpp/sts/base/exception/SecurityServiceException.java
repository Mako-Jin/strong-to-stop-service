package com.smk.cpp.sts.base.exception;

import com.smk.cpp.sts.base.enums.ResultStatusEnums;
import org.springframework.security.core.AuthenticationException;

/**
 * TODO
 *
 * @version 1.0
 * @Author: Jin-LiangBo
 * @Date: 2021年09月01日 15:30
 * @Description:
 */
public class SecurityServiceException extends AuthenticationException {

    private int code;

    private String msg;

    public SecurityServiceException(int code, String msg) {
        super(msg);
        this.code = code;
        this.msg = msg;
    }

    public SecurityServiceException(String message, int code, String msg) {
        super(message);
    }

    public SecurityServiceException(ResultStatusEnums status) {
        super(status.getMessage());
        this.code = status.getCode();
        this.msg = status.getMessage();
    }
    
    public SecurityServiceException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public SecurityServiceException(String msg) {
        super(msg);
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
