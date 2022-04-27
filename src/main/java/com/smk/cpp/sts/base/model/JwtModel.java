package com.smk.cpp.sts.base.model;

import com.smk.cpp.sts.base.interfaces.IAlgorithmEnum;

import java.util.Map;

/**
 * @Description:
 * @Author: Jin-LiangBo
 * @Date: 2022/4/10 16:38
 */
public class JwtModel {
    
    public class Header {
        /**
         * 用于保护JWS的加密算法
         */
        private String alg;
        /**
         * 用于保护JWS的公钥
         */
        private String jwk;
        /**
         * JWT、JWS、JWT
         */
        private String type;
        
        
        
    }
    
    private Map<String, String> header;
    
    private Map<String, String> payload;
    
    private IAlgorithmEnum algorithmEnum;

    public Map<String, String> getHeader() {
        return header;
    }

    public void setHeader(Map<String, String> header) {
        this.header = header;
    }

    public Map<String, String> getPayload() {
        return payload;
    }

    public void setPayload(Map<String, String> payload) {
        this.payload = payload;
    }

    public IAlgorithmEnum getAlgorithmEnum() {
        return algorithmEnum;
    }

    public void setAlgorithmEnum(IAlgorithmEnum algorithmEnum) {
        this.algorithmEnum = algorithmEnum;
    }
}
