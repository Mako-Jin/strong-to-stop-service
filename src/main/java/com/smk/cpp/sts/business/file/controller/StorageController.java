package com.smk.cpp.sts.business.file.controller;

import com.smk.cpp.sts.base.model.ResultModel;
import com.smk.cpp.sts.business.file.entity.StorageEntity;
import com.smk.cpp.sts.business.file.service.StorageService;
import com.smk.cpp.sts.common.util.ResultUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * TODO
 *
 * @version 1.0
 * @Author: Jin-LiangBo
 * @Date: 2022年03月25日 19:02
 * @Description:
 */
@RestController
@RequestMapping("/storage/v1/")
public class StorageController {
    
    @Autowired
    private StorageService storageService;
    
    /**
     * 查看有权限的仓库列表
     */
    
    /**
     * 查看单个存储
     */
    @GetMapping("viewStorage")
    public ResultModel<StorageEntity> viewStorage() {
        StorageEntity storageEntity = storageService.viewStorage();
        return ResultUtils.success(storageEntity);
    }

    /**
     * 分配存储空间
     */
    @PostMapping("allocateStorage")
    public ResultModel<String> allocateStorage(@RequestBody StorageEntity storageEntity) {
        storageService.allocateStorage(storageEntity);
        return ResultUtils.success();
    }

    /**
     * 升级存储空间
     */
    @PutMapping("upgradeStorage")
    public ResultModel<String> upgradeStorage(@RequestBody StorageEntity storageEntity) {
        storageService.upgradeStorage(storageEntity);
        return ResultUtils.success();
    }

    /**
     * 释放存储空间
     */
    @DeleteMapping("freeUpStorage/{storageId}")
    public ResultModel<String> freeUpStorage(@PathVariable String storageId) {
        storageService.freeUpStorage(storageId);
        return ResultUtils.success();
    }

}
