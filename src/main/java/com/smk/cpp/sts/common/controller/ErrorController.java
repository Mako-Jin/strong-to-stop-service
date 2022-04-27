package com.smk.cpp.sts.common.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * TODO
 *
 * @version 1.0
 * @Author: Jin-LiangBo
 * @Date: 2022年01月12日 10:39
 * @Description:
 */
@RestController
public class ErrorController {
    
    private static final Logger logger = LoggerFactory.getLogger(ErrorController.class);
    
    @GetMapping("/error")
    public void error(Exception e) {
        logger.error(e.getMessage());
        return;
    }
    
}
