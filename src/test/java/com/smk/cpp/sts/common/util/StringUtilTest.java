package com.smk.cpp.sts.common.util;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * TODO
 *
 * @version 1.0
 * @Author: Jin-LiangBo
 * @Date: 2022年02月07日 19:57
 * @Description:
 */
public class StringUtilTest {

    private static final Logger logger = LoggerFactory.getLogger(StringUtilTest.class);
    
    @Test
    public void testIdGenerate() {
        logger.info(StringUtil.idGenerate());
    }
    
    
}
