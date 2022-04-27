package com.smk.cpp.sts.core.crypto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.util.regex.Pattern;

/**
 * TODO
 *
 * @version 1.0
 * @Author: Jin-LiangBo
 * @Date: 2021年08月26日 21:53
 * @Description:
 */
public class NoCryptPasswordEncoder implements PasswordEncoder {
    
    private static final Logger logger = LoggerFactory.getLogger(NoCryptPasswordEncoder.class);

    /**
     * 4位数字
     */
    private Pattern FOUR_DIGITAL_PATTERN = Pattern.compile("$[./0-9]{4}");

    /**
     * 6位数字
     */
    private Pattern SIX_DIGITAL_PATTERN = Pattern.compile("$[./0-9]{6}");
    
    @Override
    public String encode(CharSequence rawPassword) {
        return String.valueOf(rawPassword);
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        if (rawPassword == null) {
            throw new IllegalArgumentException("rawPassword cannot be null");
        }
        if (encodedPassword == null || encodedPassword.length() == 0) {
            logger.warn("Empty encoded password");
            return false;
        }
        return rawPassword.equals(encodedPassword);
    }

    @Override
    public boolean upgradeEncoding(String encodedPassword) {
        if (encodedPassword == null || encodedPassword.length() == 0) {
            logger.warn("Empty encoded password");
            return false;
        }
        if (!this.FOUR_DIGITAL_PATTERN.matcher(encodedPassword).matches()
            || !this.SIX_DIGITAL_PATTERN.matcher(encodedPassword).matches()) {
            throw new IllegalArgumentException("Encoded password does not look like security code: " 
                    + encodedPassword);
        }
        return true;
    }
    
}
