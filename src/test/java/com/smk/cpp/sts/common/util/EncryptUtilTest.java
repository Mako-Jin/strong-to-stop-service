package com.smk.cpp.sts.common.util;

import com.smk.cpp.sts.base.constant.FileConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.Key;
import java.security.interfaces.RSAPrivateKey;
import java.util.Map;

/**
 * TODO
 *
 * @version 1.0
 * @Author: Jin-LiangBo
 * @Date: 2021年08月16日 19:15
 * @Description:
 */
public class EncryptUtilTest {

    private static final Logger logger = LoggerFactory.getLogger(EncryptUtilTest.class);
    
    public static class RSAUtil {
        
        public static void main(String[] args) throws Exception {
            logger.info(System.getProperty("user.dir"));
            Map<String, Key> keyMap = EncryptUtil.RSAUtil.initKey();
//        String publicKey = RSAUtil.getPublicKey(keyMap);
//        String privateKey = RSAUtil.getPrivateKey(keyMap);

            String publicKey = EncryptUtil.RSAUtil.getPublicKey(FileConstants.PUBLIC_KEY_PATH);
            String privateKey = EncryptUtil.RSAUtil.getPrivateKey(FileConstants.PRIVATE_KEY_PATH);

            RSAPrivateKey rsaPrivateKey = EncryptUtil.loadPrivateKeyByStr(privateKey);

            logger.info(String.valueOf(keyMap));
            logger.info("-----------------------------------");
            logger.info(publicKey);
            logger.info("-----------------------------------");
            logger.info(privateKey);
            logger.info("-----------------------------------");
            byte[] encryptByPrivateKey = EncryptUtil.RSAUtil.encryptByPrivateKey("123456".getBytes(),privateKey);
            byte[] encryptByPublicKey = EncryptUtil.RSAUtil.encryptByPublicKey("515322", publicKey);
            logger.info(EncryptUtil.Base64Util.encodeByBase64(encryptByPrivateKey));
            logger.info("-----------------------------------");
            logger.info("public ==> " + EncryptUtil.Base64Util.encodeByBase64(encryptByPublicKey));
            logger.info("-----------------------------------");
            String sign = EncryptUtil.RSAUtil.sign(encryptByPrivateKey,privateKey);
            logger.info(sign);
            logger.info("-----------------------------------");
            boolean verify = EncryptUtil.RSAUtil.verify(encryptByPrivateKey,publicKey,sign);
            logger.info(String.valueOf(verify));
            logger.info("-----------------------------------");
            byte[] decryptByPublicKey = EncryptUtil.RSAUtil.decryptByPublicKey(encryptByPrivateKey,publicKey);
            byte[] decryptByPrivateKey = EncryptUtil.RSAUtil.decryptByPrivateKey(encryptByPublicKey,privateKey);
            logger.info(new String(decryptByPublicKey));
            logger.info("-----------------------------------");
            logger.info(new String(decryptByPrivateKey));
        }
    }

    public static class PBKDF2Util {
        
        public static void main(String[] args) {
            byte[] bytes = EncryptUtil.PBKDF2Util.encryptByPBKDF2(
                    "515322".toCharArray(),
                    StringUtil.HexEncodeTool.hexStringToByteArray("f96948d89fee4bb899f1f9a1934fcb90"),
                    500, 1000);
            logger.info(StringUtil.HexEncodeTool.byteArrayToHexString(bytes));
        }
    }
    
}
