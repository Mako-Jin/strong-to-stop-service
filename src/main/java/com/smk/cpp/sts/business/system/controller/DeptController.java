package com.smk.cpp.sts.business.system.controller;

import com.smk.cpp.sts.business.system.service.DeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * TODO
 *
 * @version 1.0
 * @Author: Jin-LiangBo
 * @Date: 2022年01月12日 10:07
 * @Description:
 */
@RestController
@RequestMapping("/deptMgr/v1/")
public class DeptController {
    
    @Autowired
    private DeptService deptService;
    
}
