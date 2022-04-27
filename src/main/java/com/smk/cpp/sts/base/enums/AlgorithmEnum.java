package com.smk.cpp.sts.base.enums;

import com.smk.cpp.sts.base.interfaces.IAlgorithmEnum;

/**
 * TODO
 *
 * @version 1.0
 * @Author: Jin-LiangBo
 * @Date: 2021年08月06日 20:32
 * @Description:
 */
public enum AlgorithmEnum implements IAlgorithmEnum {
    /**
     * AES
     */
    SIMPLE_AES("AES"),
    SIMPLE_DES("DES"),
    SIMPLE_MD5("MD5"),
    SIMPLE_SHA("SHA"),
    
    SIMPLE_THREE_DES("DESede"),
    
    SIMPLE_HMAC_MD5("HmacMD5"),
    SIMPLE_HMAC_SHA256("HmacSha256"),
    SIMPLE_HMAC_SHA384("HmacSha384"),
    SIMPLE_HMAC_SHA512("HmacSha512"),
    
    SHA_256("SHA-256"),
    SHA_384("SHA-384"),
    SHA_512("SHA-512"),
    
    PBE_WITH_MD5_AND_DES("PBEWITHMD5andDES"),
    
    PBKDF2_WITH_HMAC_SHA512("PBKDF2WithHmacSHA512"),
    
    RSA("RSA"),
    
    MD5_WITH_RSA("MD5withRSA"),
    ;

    AlgorithmEnum(String algorithmName) {
        this.algorithmName = algorithmName;
    }
    
    /**
     * 算法名称
     */
    private String algorithmName;

    public void setAlgorithmName(String algorithmName) {
        this.algorithmName = algorithmName;
    }

    @Override
    public String getAlgorithmName() {
        return this.algorithmName;
    }
}
