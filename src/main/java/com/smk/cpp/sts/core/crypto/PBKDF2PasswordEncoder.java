/***********************************************************************/
/**         Copyright (C) 2020-2030 西安三码客软件科技有限公司         */
/**                      All rights reserved                           */
/***********************************************************************/

package com.smk.cpp.sts.core.crypto;

import com.smk.cpp.sts.common.util.EncryptUtil;
import com.smk.cpp.sts.common.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * 功能描述:
 *
 * @ClassName: EurekaPasswordEncoder
 * @Author: Mr.Jin-晋
 * @Date: 2021-01-03 19:11
 * @Version: V1.0
 * @Dedscription:
 */
@Component
public class PBKDF2PasswordEncoder implements PasswordEncoder {

    private static final Logger logger = LoggerFactory.getLogger(PBKDF2PasswordEncoder.class);

    private String salt = "f96948d89fee4bb899f1f9a1934fcb90";

    private int iterationNum = 500;

    private int keyLength = 1000;

    @Override
    public String encode(CharSequence rawPassword) {
        if (rawPassword == null) {
            throw new IllegalArgumentException("rawPassword cannot be null");
        }
        byte[] bytes = EncryptUtil.PBKDF2Util.encryptByPBKDF2(
                rawPassword.toString().toCharArray(),
                StringUtil.HexEncodeTool.hexStringToByteArray(salt), 
                iterationNum, keyLength);
        return StringUtil.HexEncodeTool.byteArrayToHexString(bytes);
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        if (!StringUtils.hasText(encodedPassword) || !StringUtils.hasText(rawPassword)) {
            return false;
        }
        return encodedPassword.equals(rawPassword);
    }

    @Override
    public boolean upgradeEncoding(String encodedPassword) {
        return false;
    }

}
