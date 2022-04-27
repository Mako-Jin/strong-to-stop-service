package com.smk.cpp.sts.business.file.service.impl;

import com.smk.cpp.sts.business.file.dao.StorageDao;
import com.smk.cpp.sts.business.file.entity.StorageEntity;
import com.smk.cpp.sts.business.file.service.StorageService;
import com.smk.cpp.sts.common.util.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * TODO
 *
 * @version 1.0
 * @Author: Jin-LiangBo
 * @Date: 2022年03月21日 17:45
 * @Description:
 */
@Service
public class StorageServiceImpl implements StorageService {
    
    private static final Logger logger = LoggerFactory.getLogger(StorageServiceImpl.class);
    
    @Autowired
    private StorageDao storageDao;

    @Override
    public StorageEntity viewStorage() {
        // 需要检查权限，只能查看自己的。
        String userId = SecurityUtils.getCurrentUserId();
        return storageDao.getStorageByUserId(userId);
    }

    /**
     * 分配存储空间
     */
    @Override
    public void allocateStorage(StorageEntity storageEntity) {
        
    }

    /**
     * 释放存储空间
     */
    @Override
    public void freeUpStorage(String storageId) {

    }

    /**
     * 升级存储空间
     */
    @Override
    public void upgradeStorage(StorageEntity storageEntity) {

    }

    /**
     * 检查
     */
    @Override
    public boolean checkStorage(Long fileSize) {
        if (fileSize == null) {
            return true;
        }
        String userId = SecurityUtils.getCurrentUserId();
        StorageEntity storageEntity = storageDao.getStorageByUserId(userId);
        if (fileSize + storageEntity.getUsedStorageSize() < storageEntity.getTotalStorageSize()) {
            return true;
        }
        return false;
    }

    /**
     * 更新存储空间
     */
    @Override
    public void updateStorage(StorageEntity storageEntity) {
    }

}
