package com.smk.cpp.sts.common.util;

import com.smk.cpp.sts.base.enums.ResultStatusEnums;
import com.smk.cpp.sts.base.exception.ServiceException;
import com.smk.cpp.sts.base.model.ResultModel;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;

/**
 * @Description:
 * @Author: Jin-LiangBo
 * @Date: 2022/4/10 16:35
 */
public class ResultUtils {

    /**
     * 状态返回
     */
    public static ResultModel<String> success(){
        ResultModel<String> vo = new ResultModel<>();
        vo.setSuccess(true);
        vo.setCode(ResultStatusEnums.SUCCESS_STATUS.getCode());
        vo.setMsg(MessageUtils.getMessage(ResultStatusEnums.SUCCESS_STATUS.getMessage()));
        return vo;
    }

    /**
     * 返回单个对象
     * @param item
     * @param <T>
     * @return
     */
    public static <T> ResultModel<T> success(T item){
        ResultModel<T> vo = new ResultModel<>();
        vo.setSuccess(true);
        vo.setCode(ResultStatusEnums.SUCCESS_STATUS.getCode());
        vo.setMsg(MessageUtils.getMessage(ResultStatusEnums.SUCCESS_STATUS.getMessage()));
        if (item != null){
            vo.setData(Collections.singletonList(item));
        }
        return vo;
    }

    /**
     * 返回单个对象
     * @param item
     * @param <T>
     * @return
     */
    public static <T> ResultModel<T> success(T item, ResultStatusEnums status){
        ResultModel<T> vo = new ResultModel<>();
        vo.setSuccess(true);
        vo.setCode(status.getCode());
        vo.setMsg(MessageUtils.getMessage(status.getMessage()));
        if (item != null){
            vo.setData(Collections.singletonList(item));
        }
        return vo;
    }

    /**
     * 返回list
     * @param items
     * @param <T>
     * @return
     */
    public static <T> ResultModel<T> success(List<T> items){
        ResultModel<T> vo = new ResultModel<>();
        vo.setCode(ResultStatusEnums.SUCCESS_STATUS.getCode());
        vo.setMsg(MessageUtils.getMessage(ResultStatusEnums.SUCCESS_STATUS.getMessage()));
        vo.setSuccess(true);
        if (!CollectionUtils.isEmpty(items)){
            vo.setData(items);
        } 
        return vo;
    }

    public static <T> ResultModel<T> error(Integer code, String message) {
        return error(new ServiceException(code, message));
    }

    public static <T> ResultModel<T> error(ResultStatusEnums error) {
        return error(new ServiceException(error));
    }

    public static <T> ResultModel<T> error(ServiceException e){
        ResultModel<T> vo = new ResultModel<>();
        vo.setCode(e.getCode());
        vo.setMsg(MessageUtils.getMessage(e.getMsg()));
        vo.setSuccess(false);
        return vo;
    }

}
