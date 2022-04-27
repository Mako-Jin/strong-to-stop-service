package com.smk.cpp.sts.common.util;

import com.smk.cpp.sts.base.constant.EncryptConstants;
import com.smk.cpp.sts.base.enums.AlgorithmEnum;
import com.smk.cpp.sts.base.exception.ServiceException;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.AlgorithmParameters;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.Provider;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.EncodedKeySpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.InvalidParameterSpecException;
import java.security.spec.KeySpec;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description:
 * @Author: Jin-LiangBo
 * @Date: 2022/4/10 16:33
 */
public class EncryptUtil {

    private static final Logger logger = LoggerFactory.getLogger(EncryptUtil.class);

    /**
     * @Description: BASE64Util
     * @Author: Jin-LiangBo
     * @Date: 2021/8/6 20:28
     */
    public static class Base64Util{

        /**
         * Base64 编码
         */
        public static String encodeByBase64(byte[] input) {
            return Base64.getEncoder().encodeToString(input);
        }

        /**
         * Base64 编码
         */
        public static byte[] encodeByBase64(String input) {
            return Base64.getEncoder().encode(input.getBytes(StandardCharsets.UTF_8));
        }

        /**
         * Base64 编码
         */
        public static byte[] decodeByBase64(String input) {
            return Base64.getDecoder().decode(input);
        }

        /**
         * Base64URL 编码
         */
        public static String urlEncodeByBase64(String input) {
            return Base64.getUrlEncoder().encodeToString(input.getBytes());
        }

        /**
         * Base64URL 解码
         */
        public static String urlDecodeByBase64(String input) {
            return new String(Base64.getUrlDecoder().decode(input));
        }

    }

    public static class AESUtil{

        private static final Logger logger = LoggerFactory.getLogger(AESUtil.class);

        private static final String AES_ECB_INSTANCE_NAME = "AES/ECB/PKCS5Padding";

        private static final String AES_CBC_INSTANCE_NAME = "AES/CBC/PKCS7Padding";

        private static final int AES_KEY_SIZE = 128;

        /**
         * @Description:
         * @Author: Jin-LiangBo
         * @Date: 2021/8/6 20:46
         */
        public static String generateAesKey() {
            try {
                KeyGenerator keyGenerator = KeyGenerator.getInstance(AlgorithmEnum.SIMPLE_AES.getAlgorithmName());
                keyGenerator.init(AES_KEY_SIZE);
                // 产生密钥
                SecretKey secretKey = keyGenerator.generateKey();
                // 获取密钥
                byte[] keyBytes = secretKey.getEncoded();
                return Base64Util.encodeByBase64(keyBytes);
            } catch (NoSuchAlgorithmException noSuchAlgorithmException) {
                logger.error("generateAESKey ==> {}", noSuchAlgorithmException.getMessage());
                throw new ServiceException("Algorithm is not exist!");
            }
        }

        /**
         * AES加密
         *
         * @param input 待加密明文
         * @param keyName 密钥密钥
         * @return 密文
         * @throws ServiceException 加密异常
         */
        public static String encryptByAes(String input, String keyName) throws ServiceException {
            try {
                byte[] keyBytes = Base64Util.decodeByBase64(keyName);
                // KEY转换
                Key key = new SecretKeySpec(keyBytes, AlgorithmEnum.SIMPLE_AES.getAlgorithmName());
                // 加密
                Cipher cipher = Cipher.getInstance(AES_ECB_INSTANCE_NAME, EncryptConstants.BOUNCY_CASTLE_PROVIDER);
                cipher.init(Cipher.ENCRYPT_MODE, key);
                byte[] result = cipher.doFinal(input.getBytes());
                return Base64Util.encodeByBase64(result);
            } catch (NoSuchAlgorithmException noSuchAlgorithmException) {
                logger.error("encryptByAES ==> {}", noSuchAlgorithmException.getMessage());
                throw new ServiceException("Algorithm is not exist!");
            } catch (InvalidKeyException invalidKeyException) {
                logger.error("encryptByAES ==> {}", invalidKeyException.getMessage());
                throw new ServiceException("key is inValid!");
            } catch (NoSuchPaddingException noSuchPaddingException) {
                logger.error("encryptByAES ==> {}", noSuchPaddingException.getMessage());
                throw new ServiceException("no such Padding!");
            } catch (BadPaddingException badPaddingException) {
                logger.error("encryptByAES ==> {}", badPaddingException.getMessage());
                throw new ServiceException("Padding is not good!");
            } catch (IllegalBlockSizeException illegalBlockSizeException) {
                logger.error("encryptByAES ==> {}", illegalBlockSizeException.getMessage());
                throw new ServiceException("illegal Block Size Exception!");
            } catch (NoSuchProviderException noSuchProviderException) {
                logger.error("encryptByAES ==> {}", noSuchProviderException.getMessage());
                throw new ServiceException("no such Provider!");
            }
        }

        /**
         * AES解密
         *
         * @param data           //密文，被加密的数据
         * @param key            //秘钥
         * @param iv             //偏移量
         * @param encodingFormat //解密后的结果需要进行的编码
         * @return String
         */
        public static String decryptWechat(String data, String key, String iv, String encodingFormat)
                throws ServiceException {
            //被加密的数据
            byte[] dataByte = Base64Util.decodeByBase64(data);
            //加密秘钥
            byte[] keyByte = Base64Util.decodeByBase64(key);
            //偏移量
            byte[] ivByte = Base64Util.decodeByBase64(iv);
            try {
                Cipher cipher = Cipher.getInstance(AES_CBC_INSTANCE_NAME);
                SecretKeySpec spec = new SecretKeySpec(keyByte, AlgorithmEnum.SIMPLE_AES.getAlgorithmName());
                AlgorithmParameters parameters = AlgorithmParameters.getInstance(AlgorithmEnum.SIMPLE_AES.getAlgorithmName());
                parameters.init(new IvParameterSpec(ivByte));
                cipher.init(Cipher.DECRYPT_MODE, spec, parameters);// 初始化
                byte[] resultByte = cipher.doFinal(dataByte);
                if (null != resultByte && resultByte.length > 0) {
                    return new String(resultByte, encodingFormat);
                }
                return null;
            } catch (NoSuchAlgorithmException noSuchAlgorithmException) {
                logger.error("decryptByAES ==> {}", noSuchAlgorithmException.getMessage());
                throw new ServiceException("Algorithm is not exist!");
            } catch (InvalidKeyException invalidKeyException) {
                logger.error("decryptByAES ==> {}", invalidKeyException.getMessage());
                throw new ServiceException("key is inValid!");
            } catch (NoSuchPaddingException noSuchPaddingException) {
                logger.error("decryptByAES ==> {}", noSuchPaddingException.getMessage());
                throw new ServiceException("no such Padding!");
            } catch (BadPaddingException badPaddingException) {
                logger.error("decryptByAES ==> {}", badPaddingException.getMessage());
                throw new ServiceException("Padding is not good!");
            } catch (UnsupportedEncodingException unsupportedEncodingException) {
                logger.error("decryptByAES ==> {}", unsupportedEncodingException.getMessage());
                throw new ServiceException("unsupported Encoding Exception!");
            } catch (InvalidAlgorithmParameterException invalidAlgorithmParameterException) {
                logger.error("decryptByAES ==> {}", invalidAlgorithmParameterException.getMessage());
                throw new ServiceException("invalid Algorithm Parameter Exception!");
            } catch (InvalidParameterSpecException invalidParameterSpecException) {
                logger.error("decryptByAES ==> {}", invalidParameterSpecException.getMessage());
                throw new ServiceException("invalid Parameter Spec Exception!");
            } catch (IllegalBlockSizeException illegalBlockSizeException) {
                logger.error("encryptByAES ==> {}", illegalBlockSizeException.getMessage());
                throw new ServiceException("illegal Block Size Exception!");
            }
        }

        /**
         * AES解密
         *
         * @param input 待解密明文
         * @param keyName 密钥密钥
         * @return 密文
         * @throws ServiceException AES解密异常
         */
        public static String decryptByAes(String input, String keyName) throws ServiceException {
            try {
                byte[] keyBytes = Base64Util.decodeByBase64(keyName);
                Key key = new SecretKeySpec(keyBytes, AlgorithmEnum.SIMPLE_AES.getAlgorithmName());
                Cipher cipher = Cipher.getInstance(AES_ECB_INSTANCE_NAME, EncryptConstants.BOUNCY_CASTLE_PROVIDER);
                cipher.init(Cipher.DECRYPT_MODE, key);
                byte[] result = cipher.doFinal(Base64Util.decodeByBase64(input));
                return new String(result);
            } catch (NoSuchAlgorithmException noSuchAlgorithmException) {
                logger.error("decryptByAES ==> {}", noSuchAlgorithmException.getMessage());
                throw new ServiceException("Algorithm is not exist!");
            } catch (InvalidKeyException invalidKeyException) {
                logger.error("decryptByAES ==> {}", invalidKeyException.getMessage());
                throw new ServiceException("key is inValid!");
            } catch (NoSuchPaddingException noSuchPaddingException) {
                logger.error("decryptByAES ==> {}", noSuchPaddingException.getMessage());
                throw new ServiceException("no such Padding!");
            } catch (BadPaddingException badPaddingException) {
                logger.error("decryptByAES ==> {}", badPaddingException.getMessage());
                throw new ServiceException("Padding is not good!");
            } catch (IllegalBlockSizeException illegalBlockSizeException) {
                logger.error("decryptByAES ==> {}", illegalBlockSizeException.getMessage());
                throw new ServiceException("illegal Block Size Exception!");
            } catch (NoSuchProviderException noSuchProviderException) {
                logger.error("decryptByAES ==> {}", noSuchProviderException.getMessage());
                throw new ServiceException("no such Provider!");
            }
        }

    }

    public static class PBKDF2Util {

        public static byte[] encryptByPBKDF2(char[] password, byte[] salt, int iterationNum, int keyLength)
                throws SecurityException {
            try {
                PBEKeySpec pbeKeySpec = new PBEKeySpec(password, salt, iterationNum, keyLength);
                SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance(
                        AlgorithmEnum.PBKDF2_WITH_HMAC_SHA512.getAlgorithmName());
                byte[] bytes = secretKeyFactory.generateSecret(pbeKeySpec).getEncoded();
                return bytes;
            } catch (NoSuchAlgorithmException noSuchAlgorithmException) {
                logger.error(noSuchAlgorithmException.getMessage());
                throw new ServiceException("Algorithm is not exist !!!");
            } catch (InvalidKeySpecException invalidKeySpecException) {
                logger.error(invalidKeySpecException.getMessage());
                throw new ServiceException("key is invalid");
            }
        }
    }

    public static class RSAUtil {

        private static final String PUBLIC_KEY = "RSAPublicKey";
        private static final String PRIVATE_KEY = "RSAPrivateKey";
        private static final int RSA_ENCRYPT_LENGTH = 2048;

        private static byte[] decryptBASE64(String key) {
            return Base64Util.decodeByBase64(key);
        }

        private static String encryptBASE64(byte[] bytes) {
            return Base64Util.encodeByBase64(bytes);
        }

        /**
         * 用私钥对信息生成数字签名
         *
         * @param data       加密数据
         * @param privateKey 私钥
         * @return
         * @throws Exception
         */
        public static String sign(byte[] data, String privateKey) throws SecurityException {
            try {
                // 解密由base64编码的私钥
                byte[] keyBytes = decryptBASE64(privateKey);
                // 构造PKCS8EncodedKeySpec对象
                PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
                // KEY_ALGORITHM 指定的加密算法
                KeyFactory keyFactory = KeyFactory.getInstance(AlgorithmEnum.RSA.getAlgorithmName());
                // 取私钥匙对象
                PrivateKey priKey = keyFactory.generatePrivate(pkcs8KeySpec);
                // 用私钥对信息生成数字签名
                Signature signature = Signature.getInstance(AlgorithmEnum.MD5_WITH_RSA.getAlgorithmName());
                signature.initSign(priKey);
                signature.update(data);
                return encryptBASE64(signature.sign());
            } catch (NoSuchAlgorithmException noSuchAlgorithmException) {
                logger.error("sign ==> {}", noSuchAlgorithmException.getMessage());
                throw new SecurityException("Algorithm is not exist!");
            } catch (InvalidKeySpecException invalidKeySpecException) {
                logger.error("sign ==> {}", invalidKeySpecException.getMessage());
                throw new SecurityException("invalid Key Spec Exception!");
            } catch (InvalidKeyException invalidKeyException) {
                logger.error("sign ==> {}", invalidKeyException.getMessage());
                throw new SecurityException("key is inValid!");
            } catch (SignatureException signatureException) {
                logger.error("sign ==> {}", signatureException.getMessage());
                throw new SecurityException("signature exception!");
            }
        }

        /**
         * 校验数字签名
         *
         * @param data      加密数据
         * @param publicKey 公钥
         * @param sign      数字签名
         * @return 校验成功返回true 失败返回false
         * @throws Exception
         */
        public static boolean verify(byte[] data, String publicKey, String sign) throws SecurityException {
            try {
                // 解密由base64编码的公钥
                byte[] keyBytes = decryptBASE64(publicKey);
                // 构造X509EncodedKeySpec对象
                X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
                // KEY_ALGORITHM 指定的加密算法
                KeyFactory keyFactory = KeyFactory.getInstance(AlgorithmEnum.RSA.getAlgorithmName());
                // 取公钥匙对象
                PublicKey pubKey = keyFactory.generatePublic(keySpec);
                Signature signature = Signature.getInstance(AlgorithmEnum.MD5_WITH_RSA.getAlgorithmName());
                signature.initVerify(pubKey);
                signature.update(data);
                // 验证签名是否正常
                return signature.verify(decryptBASE64(sign));
            } catch (NoSuchAlgorithmException noSuchAlgorithmException) {
                logger.error("verify ==> {}", noSuchAlgorithmException.getMessage());
                throw new SecurityException("Algorithm is not exist!");
            } catch (InvalidKeySpecException invalidKeySpecException) {
                logger.error("verify ==> {}", invalidKeySpecException.getMessage());
                throw new SecurityException("invalid Key Spec Exception!");
            } catch (InvalidKeyException invalidKeyException) {
                logger.error("verify ==> {}", invalidKeyException.getMessage());
                throw new SecurityException("key is inValid!");
            } catch (SignatureException signatureException) {
                logger.error("verify ==> {}", signatureException.getMessage());
                throw new SecurityException("signature exception!");
            }
        }

        /**
         * 解密<br>
         * 用私钥解密
         *
         * @param data
         * @param key
         * @return
         * @throws Exception
         */
        public static byte[] decryptByPrivateKey(byte[] data, String key) throws SecurityException {
            try{
                // 对密钥解密
                byte[] keyBytes = decryptBASE64(key);
                // 取得私钥
                PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
                KeyFactory keyFactory = KeyFactory.getInstance(AlgorithmEnum.RSA.getAlgorithmName());
                Key privateKey = keyFactory.generatePrivate(pkcs8KeySpec);
                // 对数据解密
                Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
                cipher.init(Cipher.DECRYPT_MODE, privateKey);
                return cipher.doFinal(data);
            } catch (NoSuchAlgorithmException noSuchAlgorithmException) {
                logger.error("decryptByPrivateKey ==> {}", noSuchAlgorithmException.getMessage());
                throw new SecurityException("Algorithm is not exist!");
            } catch (NoSuchPaddingException noSuchPaddingException) {
                logger.error("decryptByPrivateKey ==> {}", noSuchPaddingException.getMessage());
                throw new SecurityException("no such Padding!");
            } catch (IllegalBlockSizeException illegalBlockSizeException) {
                logger.error("decryptByPrivateKey ==> {}", illegalBlockSizeException.getMessage());
                throw new SecurityException("key is inValid!");
            } catch (BadPaddingException badPaddingException) {
                logger.error("decryptByPrivateKey ==> {}", badPaddingException.getMessage());
                throw new SecurityException("Padding is bad!");
            } catch (InvalidKeySpecException invalidKeySpecException) {
                logger.error("decryptByPrivateKey ==> {}", invalidKeySpecException.getMessage());
                throw new SecurityException("invalid Key Spec Exception!");
            } catch (InvalidKeyException invalidKeyException) {
                logger.error("decryptByPrivateKey ==> {}", invalidKeyException.getMessage());
                throw new SecurityException("key is inValid!");
            }
        }

        /**
         * 解密<br>
         * 用私钥解密
         *
         * @param data
         * @param key
         * @return
         * @throws Exception
         */
        public static byte[] decryptByPrivateKey(String data, String key) {
            return decryptByPrivateKey(decryptBASE64(data),key);
        }

        /**
         * 解密<br>
         * 用公钥解密
         *
         * @param data
         * @param key
         * @return
         * @throws SecurityException
         */
        public static byte[] decryptByPublicKey(byte[] data, String key) throws SecurityException {
            try{
                // 对密钥解密
                byte[] keyBytes = decryptBASE64(key);
                // 取得公钥
                X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
                KeyFactory keyFactory = KeyFactory.getInstance(AlgorithmEnum.RSA.getAlgorithmName());
                Key publicKey = keyFactory.generatePublic(x509KeySpec);
                // 对数据解密
                Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
                cipher.init(Cipher.DECRYPT_MODE, publicKey);
                return cipher.doFinal(data);
            } catch (NoSuchAlgorithmException noSuchAlgorithmException) {
                logger.error("decryptByPublicKey ==> {}", noSuchAlgorithmException.getMessage());
                throw new SecurityException("Algorithm is not exist!");
            } catch (NoSuchPaddingException noSuchPaddingException) {
                logger.error("decryptByPublicKey ==> {}", noSuchPaddingException.getMessage());
                throw new SecurityException("no such Padding!");
            } catch (IllegalBlockSizeException illegalBlockSizeException) {
                logger.error("decryptByPublicKey ==> {}", illegalBlockSizeException.getMessage());
                throw new SecurityException("key is inValid!");
            } catch (BadPaddingException badPaddingException) {
                logger.error("decryptByPublicKey ==> {}", badPaddingException.getMessage());
                throw new SecurityException("Padding is bad!");
            } catch (InvalidKeySpecException invalidKeySpecException) {
                logger.error("decryptByPublicKey ==> {}", invalidKeySpecException.getMessage());
                throw new SecurityException("invalid Key Spec Exception!");
            } catch (InvalidKeyException invalidKeyException) {
                logger.error("decryptByPublicKey ==> {}", invalidKeyException.getMessage());
                throw new SecurityException("key is inValid!");
            }
        }

        /**
         * 解密<br>
         * 用公钥解密
         *
         * @param data
         * @param key
         * @return
         * @throws SecurityException
         */
        public static byte[] decryptByPublicKey(String data, String key) {
            return decryptByPublicKey(decryptBASE64(data),key);
        }

        /**
         * 加密<br>
         * 用公钥加密
         *
         * @param data
         * @param key
         * @return
         * @throws Exception
         */
        public static byte[] encryptByPublicKey(String data, String key) throws SecurityException {
            try{
                // 对公钥解密
                byte[] keyBytes = decryptBASE64(key);
                // 取得公钥
                X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
                KeyFactory keyFactory = KeyFactory.getInstance(AlgorithmEnum.RSA.getAlgorithmName());
                Key publicKey = keyFactory.generatePublic(x509KeySpec);
                // 对数据加密
                Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
                cipher.init(Cipher.ENCRYPT_MODE, publicKey);
                return cipher.doFinal(data.getBytes());
            } catch (NoSuchAlgorithmException noSuchAlgorithmException) {
                logger.error("encryptByPublicKey ==> {}", noSuchAlgorithmException.getMessage());
                throw new SecurityException("Algorithm is not exist!");
            } catch (NoSuchPaddingException noSuchPaddingException) {
                logger.error("encryptByPublicKey ==> {}", noSuchPaddingException.getMessage());
                throw new SecurityException("no such Padding!");
            } catch (IllegalBlockSizeException illegalBlockSizeException) {
                logger.error("encryptByPublicKey ==> {}", illegalBlockSizeException.getMessage());
                throw new SecurityException("key is inValid!");
            } catch (BadPaddingException badPaddingException) {
                logger.error("encryptByPublicKey ==> {}", badPaddingException.getMessage());
                throw new SecurityException("Padding is bad!");
            } catch (InvalidKeySpecException invalidKeySpecException) {
                logger.error("encryptByPublicKey ==> {}", invalidKeySpecException.getMessage());
                throw new SecurityException("invalid Key Spec Exception!");
            } catch (InvalidKeyException invalidKeyException) {
                logger.error("encryptByPublicKey ==> {}", invalidKeyException.getMessage());
                throw new SecurityException("key is inValid!");
            }
        }

        /**
         * 加密<br>
         * 用私钥加密
         *
         * @param data
         * @param key
         * @return
         * @throws Exception
         */
        public static byte[] encryptByPrivateKey(byte[] data, String key) throws SecurityException {
            try {
                // 对密钥解密
                byte[] keyBytes = decryptBASE64(key);
                // 取得私钥
                PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
                KeyFactory keyFactory = KeyFactory.getInstance(AlgorithmEnum.RSA.getAlgorithmName());
                Key privateKey = keyFactory.generatePrivate(pkcs8KeySpec);
                // 对数据加密
                Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
                cipher.init(Cipher.ENCRYPT_MODE, privateKey);
                return cipher.doFinal(data);
            } catch (NoSuchAlgorithmException noSuchAlgorithmException) {
                logger.error("encryptByPrivateKey ==> {}", noSuchAlgorithmException.getMessage());
                throw new SecurityException("Algorithm is not exist!");
            } catch (NoSuchPaddingException noSuchPaddingException) {
                logger.error("encryptByPrivateKey ==> {}", noSuchPaddingException.getMessage());
                throw new SecurityException("no such Padding!");
            } catch (IllegalBlockSizeException illegalBlockSizeException) {
                logger.error("encryptByPrivateKey ==> {}", illegalBlockSizeException.getMessage());
                throw new SecurityException("key is inValid!");
            } catch (BadPaddingException badPaddingException) {
                logger.error("encryptByPrivateKey ==> {}", badPaddingException.getMessage());
                throw new SecurityException("Padding is bad!");
            } catch (InvalidKeySpecException invalidKeySpecException) {
                logger.error("encryptByPrivateKey ==> {}", invalidKeySpecException.getMessage());
                throw new SecurityException("invalid Key Spec Exception!");
            } catch (InvalidKeyException invalidKeyException) {
                logger.error("encryptByPrivateKey ==> {}", invalidKeyException.getMessage());
                throw new SecurityException("key is inValid!");
            }
        }

        /**
         * 取得私钥
         *
         * @param keyMap
         * @return
         */
        public static String getPrivateKey(Map<String, Key> keyMap) {
            Key key = keyMap.get(PRIVATE_KEY);
            return encryptBASE64(key.getEncoded());
        }

        /**
         * 从私钥文件取得私钥
         *
         * @param path 私钥文件路径
         * @return
         */
        public static String getPrivateKey(String path) {
            String privateKey = FileUtils.loadKey(path);
            return privateKey.substring(27, privateKey.length() - 25);
        }

        /**
         * 取得公钥
         *
         * @param keyMap
         * @return
         */
        public static String getPublicKey(Map<String, Key> keyMap) {
            Key key = keyMap.get(PUBLIC_KEY);
            return encryptBASE64(key.getEncoded());
        }

        /**
         * 从公钥文件取得公钥
         *
         * @param path 公钥文件路径
         * @return
         */
        public static String getPublicKey(String path) {
            String publicKey = FileUtils.loadKey(path);
            return publicKey.substring(26, publicKey.length() - 24);
        }

        /**
         * 初始化密钥
         *
         * @return
         * @throws Exception
         */
        public static Map<String, Key> initKey() throws SecurityException {
            try {
                KeyPairGenerator keyPairGen = KeyPairGenerator
                        .getInstance(AlgorithmEnum.RSA.getAlgorithmName());
                keyPairGen.initialize(RSA_ENCRYPT_LENGTH);
                KeyPair keyPair = keyPairGen.generateKeyPair();
                Map<String, Key> keyMap = new HashMap(2);
                keyMap.put(PUBLIC_KEY, keyPair.getPublic());// 公钥
                keyMap.put(PRIVATE_KEY, keyPair.getPrivate());// 私钥
                return keyMap;
            } catch (NoSuchAlgorithmException noSuchAlgorithmException) {
                logger.error("initKey ==> {}", noSuchAlgorithmException.getMessage());
                throw new SecurityException("Algorithm is not exist!");
            }
        }
    }

    public static class HmacUtil {

        /**
         * 初始化HMAC密钥
         */
        public static String initMacKey(String algorithmName) throws SecurityException {
            try {
                KeyGenerator keyGenerator = KeyGenerator.getInstance(algorithmName);
                SecretKey secretKey = keyGenerator.generateKey();
                return Base64Util.encodeByBase64(secretKey.getEncoded());
            } catch (NoSuchAlgorithmException noSuchAlgorithmException) {
                logger.error("HMAC_MD5 Algorithm is not exist !!!");
                throw new SecurityException("Algorithm is not exist !!!");
            }
        }

        /**
         * HMAC加密  ：主要方法
         *
         * @param input 待加密文本
         * @param key 密钥别名
         * @return 消息摘要（Base64编码）
         */
        public static String encryptByHMAC(byte[] input, String key) throws SecurityException {

            SecretKey secretKey = new SecretKeySpec(Base64Util.decodeByBase64(key),
                    AlgorithmEnum.SIMPLE_HMAC_MD5.getAlgorithmName());
            try {
                Mac mac = Mac.getInstance(secretKey.getAlgorithm());
                mac.init(secretKey);
                return new String(mac.doFinal(input));
            } catch (NoSuchAlgorithmException noSuchAlgorithmException) {
                logger.error("HMAC_MD5 Algorithm is not exist !!!");
                throw new SecurityException("Algorithm is not exist !!!");
            } catch (InvalidKeyException invalidKeyException) {
                logger.error("key ==> {} is in valid", key);
                throw new SecurityException("key is invalid");
            }
        }

        /**
         * HmacSHA256 加密算法
         * @param input 要加密的数据
         * @param key 秘钥
         */
        public static String HmacSHA256(byte[] input, String key) throws SecurityException {
            SecretKeySpec secretKey = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8),
                    AlgorithmEnum.SIMPLE_HMAC_SHA256.getAlgorithmName());
            try {
                Mac sha256HMAC = Mac.getInstance(secretKey.getAlgorithm());
                sha256HMAC.init(secretKey);
                byte[] array = sha256HMAC.doFinal(input);
                StringBuilder stringBuilder = new StringBuilder();
                for (byte item : array) {
                    stringBuilder.append(Integer.toHexString((item & 0xFF) | 0x100), 1, 3);
                }
                return stringBuilder.toString().toUpperCase();
            } catch (NoSuchAlgorithmException e) {
                logger.error("HMAC_MD5_SHA256 Algorithm is not exist !!!");
                throw new SecurityException("Algorithm is not exist !!!");
            } catch (InvalidKeyException e) {
                logger.error("key ==> {} is in valid", key);
                throw new SecurityException("key is invalid");
            }
        }
    }
    
    public static class MD5Util {
        /**
         * @param input input 待加密明文
         * @return 密文
         * @throws SecurityException
         */
        public static String encryptByMD5 (String input) throws SecurityException {
            BigInteger bigInteger = null;
            try {
                MessageDigest md = MessageDigest.getInstance(AlgorithmEnum.SIMPLE_MD5.getAlgorithmName());
                md.update(input.getBytes(StandardCharsets.UTF_8));
                bigInteger = new BigInteger(md.digest());
            } catch (NoSuchAlgorithmException e) {
                logger.error("MD5 Algorithm is not exist !!!");
                throw new SecurityException("Algorithm Not Exist");
            }
            return bigInteger.toString(16);
        }

        /**
         * @param input input 待加密明文
         * @param salt salt 加密盐
         * @param hashIterations hashIterations 遍历次数
         * @return 密文
         * @throws SecurityException
         */
        public static String encryptByMD5 (String input, String salt, int hashIterations) throws SecurityException {
            BigInteger bigInteger = null;
            try {
                MessageDigest md = MessageDigest.getInstance(AlgorithmEnum.SIMPLE_MD5.getAlgorithmName());
                md.update(salt.getBytes(StandardCharsets.UTF_8));
                byte[] hashed = md.digest(input.getBytes(StandardCharsets.UTF_8));
                int iterations = hashIterations - 1;
                for(int i = 0; i < iterations; ++i) {
                    md.reset();
                    hashed = md.digest(hashed);
                }
                bigInteger = new BigInteger(md.digest());
            } catch (NoSuchAlgorithmException e) {
                logger.error("MD5 Algorithm is not exist !!!");
                throw new SecurityException("Algorithm Not Exist");
            }
            return bigInteger.toString(16);
        }
    }
    
    public static class DESUtil {
        
        private static final String DES_INSTANCE_NAME = "DES/ECB/PKCS5Padding";

        private static final String DESEDE_INSTANCE_NAME = "DESede/ECB/PKCS5Padding";

        private static final int THREE_DES_KEY_SIZE = 168;

        /**
         *
         * @param input
         * @param keyName
         * @return
         * @throws SecurityException
         */
        public static String encryptByDES(String input, String keyName) throws SecurityException {
            if (StringUtils.hasText(input) || StringUtils.hasText(keyName)) {
                throw new SecurityException("param must not be null");
            }
            try {
                // 加密
                Cipher cipher = Cipher.getInstance(DES_INSTANCE_NAME);
                cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(keyName.getBytes(StandardCharsets.UTF_8),
                        AlgorithmEnum.SIMPLE_DES.getAlgorithmName()));
                byte[] result = cipher.doFinal(input.getBytes(StandardCharsets.UTF_8));
                return Base64Util.encodeByBase64(result);
            } catch (NoSuchAlgorithmException noSuchAlgorithmException) {
                logger.error("encryptByDES ==> {}", noSuchAlgorithmException.getMessage());
                throw new SecurityException("Algorithm is not exist!");
            } catch (InvalidKeyException invalidKeyException) {
                logger.error("encryptByDES ==> {}", invalidKeyException.getMessage());
                throw new SecurityException("key is inValid!");
            } catch (NoSuchPaddingException noSuchPaddingException) {
                logger.error("encryptByDES ==> {}", noSuchPaddingException.getMessage());
                throw new SecurityException("no such Padding!");
            } catch (BadPaddingException badPaddingException) {
                logger.error("encryptByDES ==> {}", badPaddingException.getMessage());
                throw new SecurityException("Padding is not good!");
            } catch (IllegalBlockSizeException illegalBlockSizeException) {
                logger.error("encryptByDES ==> {}", illegalBlockSizeException.getMessage());
                throw new SecurityException("illegal Block Size Exception！");
            }
        }

        /**
         * des解密
         * @param input
         * @param keyName
         * @return
         * @throws SecurityException
         */

        public static String decryptByDES(String input, String keyName) throws SecurityException {
            try {
                Cipher cipher = Cipher.getInstance(DES_INSTANCE_NAME);
                cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(keyName.getBytes(StandardCharsets.UTF_8),
                        AlgorithmEnum.SIMPLE_DES.getAlgorithmName()));
                byte[] result = cipher.doFinal(Base64Util.decodeByBase64(input));
                return new String(result, StandardCharsets.UTF_8);
            } catch (NoSuchAlgorithmException noSuchAlgorithmException) {
                logger.error("decryptByDES ==> {}", noSuchAlgorithmException.getMessage());
                throw new SecurityException("Algorithm is not exist!");
            } catch (InvalidKeyException invalidKeyException) {
                logger.error("decryptByDES ==> {}", invalidKeyException.getMessage());
                throw new SecurityException("key is inValid!");
            } catch (NoSuchPaddingException noSuchPaddingException) {
                logger.error("decryptByDES ==> {}", noSuchPaddingException.getMessage());
                throw new SecurityException("no such Padding!");
            } catch (BadPaddingException badPaddingException) {
                logger.error("decryptByDES ==> {}", badPaddingException.getMessage());
                throw new SecurityException("Padding is not good!");
            } catch (IllegalBlockSizeException illegalBlockSizeException) {
                logger.error("decryptByDES ==> {}", illegalBlockSizeException.getMessage());
                throw new SecurityException("illegal Block Size Exception!");
            }
        }

        /**
         * @return
         * @throws NoSuchAlgorithmException
         */
        public static Key getThreeDESKey() throws SecurityException {
            try {
                KeyGenerator keyGenerator = KeyGenerator.getInstance(AlgorithmEnum.SIMPLE_THREE_DES.getAlgorithmName());
                keyGenerator.init(THREE_DES_KEY_SIZE);
                return keyGenerator.generateKey();
            } catch (NoSuchAlgorithmException noSuchAlgorithmException) {
                logger.error("getThreeDESKey ==> {}", noSuchAlgorithmException.getMessage());
                throw new SecurityException("Algorithm is not exist!");
            }
        }

        /**
         * 已知密钥串，获取3DES密钥
         * @param key
         * @return
         * @throws Exception
         */
        public static Key getThreeDESKey(String key) throws NoSuchAlgorithmException, InvalidKeyException,
                InvalidKeySpecException {
            Provider provider = new BouncyCastleProvider();
            KeySpec keySpec = new DESedeKeySpec(Base64.getDecoder().decode(key.getBytes(StandardCharsets.UTF_8)));
            SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance(
                    AlgorithmEnum.SIMPLE_THREE_DES.getAlgorithmName(), provider);
            SecretKey secretKey = secretKeyFactory.generateSecret(keySpec);
            return secretKey;
        }

        /**
         * 3DES加密
         * @param input
         * @param key
         * @return
         */
        public static String encryptByThreeDES(String input, Key key) throws SecurityException {
            try {
                Cipher cipher = Cipher.getInstance(DESEDE_INSTANCE_NAME);
                cipher.init(Cipher.ENCRYPT_MODE, key);
                byte[] result = cipher.doFinal(input.getBytes(StandardCharsets.UTF_8));
                return Base64Util.encodeByBase64(result);
            } catch (NoSuchAlgorithmException noSuchAlgorithmException) {
                logger.error("encryptBy3DES ==> {}", noSuchAlgorithmException.getMessage());
                throw new SecurityException("Algorithm is not exist!");
            } catch (InvalidKeyException invalidKeyException) {
                logger.error("encryptBy3DES ==> {}", invalidKeyException.getMessage());
                throw new SecurityException("key is inValid!");
            } catch (NoSuchPaddingException noSuchPaddingException) {
                logger.error("encryptBy3DES ==> {}", noSuchPaddingException.getMessage());
                throw new SecurityException("no such Padding!");
            } catch (BadPaddingException badPaddingException) {
                logger.error("encryptBy3DES ==> {}", badPaddingException.getMessage());
                throw new SecurityException("Padding is not good!");
            } catch (IllegalBlockSizeException illegalBlockSizeException) {
                logger.error("encryptBy3DES ==> {}", illegalBlockSizeException.getMessage());
                throw new SecurityException("key is inValid!");
            }
        }

        /**
         * 3DES加密
         * @param input
         * @param keyName
         * @return
         */
        public static String encryptByThreeDES(String input, String keyName) throws SecurityException {
            try {
                Key key = getThreeDESKey(keyName);
                return encryptByThreeDES(input, key);
            } catch (NoSuchAlgorithmException noSuchAlgorithmException) {
                logger.error("encryptBy3DES ==> {}", noSuchAlgorithmException.getMessage());
                throw new SecurityException("Algorithm is not exist!");
            } catch (InvalidKeyException invalidKeyException) {
                logger.error("encryptBy3DES ==> {}", invalidKeyException.getMessage());
                throw new SecurityException("key is inValid!");
            } catch (InvalidKeySpecException invalidKeySpecException) {
                logger.error("encryptBy3DES ==> {}", invalidKeySpecException.getMessage());
                throw new SecurityException("invalid Key Spec Exception!");
            }
        }

        /**
         * 3DES解密
         * @param input
         * @param keyName
         * @return
         * @throws SecurityException
         */
        public static String decryptByThreeDES(String input, String keyName) throws SecurityException {
            try {
                Key key = getThreeDESKey(keyName);
                return decryptByThreeDES(input, key);
            } catch (NoSuchAlgorithmException noSuchAlgorithmException) {
                logger.error("decryptBy3DES ==> {}", noSuchAlgorithmException.getMessage());
                throw new SecurityException("Algorithm is not exist!");
            } catch (InvalidKeyException invalidKeyException) {
                logger.error("decryptBy3DES ==> {}", invalidKeyException.getMessage());
                throw new SecurityException("key is inValid!");
            } catch (InvalidKeySpecException invalidKeySpecException) {
                logger.error("decryptBy3DES ==> {}", invalidKeySpecException.getMessage());
                throw new SecurityException("invalid Key Spec Exception!");
            }
        }

        /**
         * 3DES解密
         * @param input
         * @param keyName
         * @return
         * @throws SecurityException
         */
        public static String decryptByThreeDES(String input, Key keyName) throws SecurityException {
            try {
                Cipher cipher = Cipher.getInstance(DESEDE_INSTANCE_NAME);
                cipher.init(Cipher.DECRYPT_MODE, keyName);
                byte[] result = cipher.doFinal(Base64Util.decodeByBase64(input));
                return new String(result);
            } catch (NoSuchAlgorithmException noSuchAlgorithmException) {
                logger.error("decryptBy3DES ==> {}", noSuchAlgorithmException.getMessage());
                throw new SecurityException("Algorithm is not exist!");
            } catch (InvalidKeyException invalidKeyException) {
                logger.error("decryptBy3DES ==> {}", invalidKeyException.getMessage());
                throw new SecurityException("key is inValid!");
            } catch (NoSuchPaddingException noSuchPaddingException) {
                logger.error("decryptBy3DES ==> {}", noSuchPaddingException.getMessage());
                throw new SecurityException("no such Padding!");
            } catch (BadPaddingException badPaddingException) {
                logger.error("decryptBy3DES ==> {}", badPaddingException.getMessage());
                throw new SecurityException("Padding is not good!");
            } catch (IllegalBlockSizeException illegalBlockSizeException) {
                logger.error("decryptBy3DES ==> {}", illegalBlockSizeException.getMessage());
                throw new SecurityException("key is inValid!");
            }
        }
    }

    /**
     * @title: SHAUtil
     * @Version: V-0.0.1
     * @description: 安全哈希算法（Secure Hash Algorithm）
     * 主要适用于数字签名标准（Digital Signature Standard DSS）里面定义的数字签名算法
     */
    public static class SHAUtil {
        /**
         * 简单sha1加密 --- 方法禁用
         * @param input input 待加密明文
         * @return 密文
         * @throws SecurityException
         */
        public static String encryptBySHA (String input) throws SecurityException {
            BigInteger bigInteger = null;
            try {
                MessageDigest md = MessageDigest.getInstance(AlgorithmEnum.SIMPLE_SHA.getAlgorithmName());
                md.update(input.getBytes(StandardCharsets.UTF_8));
                bigInteger = new BigInteger(md.digest());
            } catch (NoSuchAlgorithmException e) {
                logger.error("SHA Algorithm is not exist !!!");
                throw new SecurityException("Algorithm Not Exist");
            }
            return bigInteger.toString(32);
        }

        /**
         * SHA-256
         *
         * @param input 待加密明文
         * @return 密文
         * @throws SecurityException
         */
        public static String encryptBySHA256(String input) throws SecurityException {
            MessageDigest md = null;
            try {
                md = MessageDigest.getInstance(AlgorithmEnum.SHA_256.getAlgorithmName());
            } catch (NoSuchAlgorithmException noSuchAlgorithmException) {
                logger.error("SHA-256 Algorithm is not exist !!!");
                throw new SecurityException("Algorithm Not Exist");
            }
            md.update(input.getBytes(StandardCharsets.UTF_8)); // Change this to "UTF-16" if needed
            byte[] digest = md.digest();
            BigInteger bigInt = new BigInteger(1, digest);
            return bigInt.toString(16);
        }

    }
    
    public static class PBEUtil {
        private static Key getKey(String keyName) throws InvalidKeySpecException, NoSuchAlgorithmException {
            PBEKeySpec pbeKeySpec = new PBEKeySpec(keyName.toCharArray());
            SecretKeyFactory factory = SecretKeyFactory.getInstance(AlgorithmEnum.PBE_WITH_MD5_AND_DES.getAlgorithmName());
            return factory.generateSecret(pbeKeySpec);
        }

        /**
         *
         * @param input
         * @param salt
         * @param iterationCount
         * @param keyName
         * @return
         * @throws SecurityException
         */
        public static String encryptByPBE(String input, byte[] salt, int iterationCount, String keyName)
                throws SecurityException {
            try {
                Key key = getKey(keyName);
                PBEParameterSpec pbeParameterSpac = new PBEParameterSpec(salt, iterationCount);//参数：盐和迭代次数
                Cipher cipher = Cipher.getInstance(AlgorithmEnum.PBE_WITH_MD5_AND_DES.getAlgorithmName());
                cipher.init(Cipher.ENCRYPT_MODE, key, pbeParameterSpac);
                byte[] result = cipher.doFinal(input.getBytes());
                return StringUtil.HexEncodeTool.byteArrayToHexString(result);
            } catch (InvalidKeySpecException invalidKeySpecException) {
                logger.error("encryptByPBE ==> {}", invalidKeySpecException.getMessage());
                throw new SecurityException("invalid Key Spec Exception!");
            } catch (NoSuchAlgorithmException noSuchAlgorithmException) {
                logger.error("encryptByPBE ==> {}", noSuchAlgorithmException.getMessage());
                throw new SecurityException("Algorithm is not exist!");
            } catch (InvalidKeyException invalidKeyException) {
                logger.error("encryptByPBE ==> {}", invalidKeyException.getMessage());
                throw new SecurityException("key is inValid!");
            } catch (NoSuchPaddingException noSuchPaddingException) {
                logger.error("encryptByPBE ==> {}", noSuchPaddingException.getMessage());
                throw new SecurityException("no such Padding!");
            } catch (BadPaddingException badPaddingException) {
                logger.error("encryptByPBE ==> {}", badPaddingException.getMessage());
                throw new SecurityException("Padding is not good!");
            } catch (IllegalBlockSizeException illegalBlockSizeException) {
                logger.error("encryptByPBE ==> {}", illegalBlockSizeException.getMessage());
                throw new SecurityException("key is inValid!");
            } catch (InvalidAlgorithmParameterException invalidAlgorithmParameterException) {
                logger.error("encryptByPBE ==> {}", invalidAlgorithmParameterException.getMessage());
                throw new SecurityException("Algorithm Parameter is inValid!");
            }
        }

        /**
         *
         * @param input
         * @param salt
         * @param iterationCount
         * @param keyName
         * @return String
         */
        public static String decryptByPBE(String input, byte[] salt, int iterationCount, String keyName)
                throws SecurityException {
            try {
                Key key = getKey(keyName);
                PBEParameterSpec pbeParameterSpac = new PBEParameterSpec(salt, iterationCount); // 参数：盐和迭代次数
                Cipher cipher = Cipher.getInstance(AlgorithmEnum.PBE_WITH_MD5_AND_DES.getAlgorithmName());
                cipher.init(Cipher.DECRYPT_MODE, key, pbeParameterSpac);
                byte[] result = cipher.doFinal(StringUtil.HexEncodeTool.hexStringToByteArray(input));
                return new String(result);
            } catch (InvalidKeySpecException invalidKeySpecException) {
                logger.error("decryptByPBE ==> {}", invalidKeySpecException.getMessage());
                throw new SecurityException("invalid Key Spec Exception!");
            } catch (NoSuchAlgorithmException noSuchAlgorithmException) {
                logger.error("decryptByPBE ==> {}", noSuchAlgorithmException.getMessage());
                throw new SecurityException("Algorithm is not exist!");
            } catch (InvalidKeyException invalidKeyException) {
                logger.error("decryptByPBE ==> {}", invalidKeyException.getMessage());
                throw new SecurityException("key is inValid!");
            } catch (NoSuchPaddingException noSuchPaddingException) {
                logger.error("decryptByPBE ==> {}", noSuchPaddingException.getMessage());
                throw new SecurityException("no such Padding!");
            } catch (BadPaddingException badPaddingException) {
                logger.error("decryptByPBE ==> {}", badPaddingException.getMessage());
                throw new SecurityException("Padding is not good!");
            } catch (IllegalBlockSizeException illegalBlockSizeException) {
                logger.error("decryptByPBE ==> {}", illegalBlockSizeException.getMessage());
                throw new SecurityException("illegal Block Size Exception!");
            } catch (InvalidAlgorithmParameterException invalidAlgorithmParameterException) {
                logger.error("decryptByPBE ==> {}", invalidAlgorithmParameterException.getMessage());
                throw new SecurityException("Algorithm Parameter is inValid!");
            }
        }
    }

    public static PublicKey getPublicKey(byte[] keyBytes, String algorithm) {
        PublicKey publicKey = null;
        try {
            KeyFactory kf = KeyFactory.getInstance(algorithm);
            EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
            publicKey = kf.generatePublic(keySpec);
        } catch (NoSuchAlgorithmException noSuchAlgorithmException) {
            logger.error("getPublicKey ==> {}", noSuchAlgorithmException.getMessage());
            throw new SecurityException("Algorithm is not exist!");
        } catch (InvalidKeySpecException invalidKeySpecException) {
            logger.error("getPublicKey ==> {}", invalidKeySpecException.getMessage());
            throw new SecurityException("invalid Key Spec Exception!");
        }
        return publicKey;
    }

    public static PrivateKey getPrivateKey(byte[] keyBytes, String algorithm) {
        PrivateKey privateKey;
        try {
            KeyFactory kf = KeyFactory.getInstance(algorithm);
            EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
            privateKey = kf.generatePrivate(keySpec);
        } catch (NoSuchAlgorithmException noSuchAlgorithmException) {
            logger.error("getPublicKey ==> {}", noSuchAlgorithmException.getMessage());
            throw new SecurityException("Algorithm is not exist!");
        } catch (InvalidKeySpecException invalidKeySpecException) {
            logger.error("getPublicKey ==> {}", invalidKeySpecException.getMessage());
            throw new SecurityException("invalid Key Spec Exception!");
        }
        return privateKey;
    }

    public static RSAPrivateKey loadPrivateKeyByStr(String privateKeyStr) throws SecurityException { 
        try { 
            byte[] buffer = Base64Util.decodeByBase64(privateKeyStr);
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(buffer);
            KeyFactory keyFactory = KeyFactory.getInstance(AlgorithmEnum.RSA.getAlgorithmName());
            return (RSAPrivateKey) keyFactory.generatePrivate(keySpec);
        } catch (NoSuchAlgorithmException noSuchAlgorithmException) {
            logger.error("getPrivateKey ==> {}", noSuchAlgorithmException.getMessage());
            throw new SecurityException("Algorithm is not exist!");
        } catch (InvalidKeySpecException invalidKeySpecException) {
            logger.error("getPrivateKey ==> {}", invalidKeySpecException.getMessage());
            throw new SecurityException("invalid Key Spec Exception!");
        }
    }

    public static RSAPublicKey loadPublicKeyByStr(String publicKeyStr) throws SecurityException {
        try {
            byte[] buffer = Base64Util.decodeByBase64(publicKeyStr);
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(buffer);
            KeyFactory keyFactory = KeyFactory.getInstance(AlgorithmEnum.RSA.getAlgorithmName());
            return (RSAPublicKey) keyFactory.generatePublic(keySpec);
        } catch (NoSuchAlgorithmException noSuchAlgorithmException) {
            logger.error("getPublicKey ==> {}", noSuchAlgorithmException.getMessage());
            throw new SecurityException("Algorithm is not exist!");
        } catch (InvalidKeySpecException invalidKeySpecException) {
            logger.error("getPublicKey ==> {}", invalidKeySpecException.getMessage());
            throw new SecurityException("invalid Key Spec Exception!");
        }
    }

}
