package com.smk.cpp.sts.core.handler;

import com.smk.cpp.sts.base.enums.ResultStatusEnums;
import com.smk.cpp.sts.base.exception.ServiceException;
import com.smk.cpp.sts.base.model.ResultModel;
import com.smk.cpp.sts.common.util.ResultUtils;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.security.web.authentication.www.NonceExpiredException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @Description:
 * @Author: Jin-LiangBo
 * @Date: 2022/4/10 16:33
 */
@Order(-1)
@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);
    
    @ResponseBody
    @ExceptionHandler(ServiceException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResultModel business(ServiceException e) {
        return ResultUtils.error(e.getCode(), e.getMsg());
    }

    @ResponseBody
    @ExceptionHandler(NonceExpiredException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResultModel nonceExpiredException(NonceExpiredException e) {
        return ResultUtils.error(ResultStatusEnums.NONCE_EXPIRED_EXCEPTION.getCode(),
                e.getMessage());
    }

    @ResponseBody
    @ExceptionHandler({MalformedJwtException.class, SignatureException.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResultModel invalidTokenException(Exception e) {
        return ResultUtils.error(ResultStatusEnums.TOKEN_IS_INVALID.getCode(),
                e.getMessage());
    }

    @ResponseBody
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResultModel exception(Exception e) {
        logger.error("unknown error ==> {}", e);
        return ResultUtils.error(ResultStatusEnums.INTERNAL_SERVER_ERROR);
    }

}
