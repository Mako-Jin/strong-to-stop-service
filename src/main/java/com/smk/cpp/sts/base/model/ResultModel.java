package com.smk.cpp.sts.base.model;

import com.smk.cpp.sts.base.enums.ResultStatusEnums;

import java.util.List;

/**
 * @Description:
 * @Author: Jin-LiangBo
 * @Date: 2022/4/10 16:35
 */
public class ResultModel<T> {

    private Boolean isSuccess;

    private Integer code;

    private String msg;

    private List<T> data;

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    public ResultModel(){}

    public ResultModel(Boolean isSuccess, Integer code, String msg) {
        this.isSuccess = isSuccess;
        this.code = code;
        this.msg = msg;
    }

    public ResultModel(Boolean isSuccess, ResultStatusEnums resultStatus) {
        this.isSuccess = isSuccess;
        this.code = resultStatus.getCode();
        this.msg = resultStatus.getMessage();
    }

    public ResultModel(Boolean isSuccess, Integer code, String msg, List<T> data) {
        this.isSuccess = isSuccess;
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public Boolean getSuccess() {
        return isSuccess;
    }

    public void setSuccess(Boolean success) {
        isSuccess = success;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}