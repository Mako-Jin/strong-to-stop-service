package com.smk.cpp.sts.common.util;

import java.util.Objects;
import java.util.UUID;

/**
 * @Description:
 * @Author: Jin-LiangBo
 * @Date: 2022/4/10 16:34
 */
public class StringUtil {

    /**
     * 生成id
     */
    public static String idGenerate() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    /**
     * 字节数组最大长度
     */
    public static final int MAX_BYTE_ARRAY_LENGTH = 1024;

    /**
     *  十六进制最大长度
     */
    public static final int MAX_HEX_LENGTH = MAX_BYTE_ARRAY_LENGTH * 2;

    /**
     * 字符串的十六进制操作类
     */
    public static class HexEncodeTool {

        /**
         * 字节数组转换成十六进制字符串
         *
         * @param bytes 需要转换的字节数组，不能为空，最大长度1024
         */
        public static String byteArrayToHexString (byte[] bytes) throws NullPointerException{
            Objects.requireNonNull(bytes);
            return HexUtil.encode(bytes, false);
        }

        /**
         * 十六进制字符串转换成字节数组
         * @param hexString 需要转换的十六进制字符串，不能为空，长度不能超过2048
         */
        public static byte[] hexStringToByteArray (String hexString) throws NullPointerException{
            Objects.requireNonNull(hexString);
            return HexUtil.decode(hexString);
        }

    }

}
