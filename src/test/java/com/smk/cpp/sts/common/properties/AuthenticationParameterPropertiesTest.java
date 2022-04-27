package com.smk.cpp.sts.common.properties;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * TODO
 *
 * @version 1.0
 * @Author: Jin-LiangBo
 * @Date: 2021年09月01日 9:35
 * @Description:
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class AuthenticationParameterPropertiesTest {
    
    private static final Logger logger = LoggerFactory.getLogger(AuthenticationParameterPropertiesTest.class);
    
    @Autowired
    private AuthenticationParameterProperties authenticationParameterProperties;
    
    @Test
    public void testGetProperties () {
        logger.info(authenticationParameterProperties.getParam().toString());    
    }
    
}
