package com.smk.cpp.sts.business.file.service;

import com.smk.cpp.sts.business.file.entity.StorageEntity;

/**
 * TODO
 *
 * @version 1.0
 * @Author: Jin-LiangBo
 * @Date: 2022年03月21日 17:45
 * @Description:
 */
public interface StorageService {
    
    /**
     * 查看仓库信息
     * @return com.smk.cpp.sts.business.file.entity.StorageEntity
     */
    StorageEntity viewStorage();

    /**
     * 分配存储空间
     * @param storageEntity 仓库信息
     */
    void allocateStorage(StorageEntity storageEntity);

    /**
     * 释放存储空间
     * @param storageId 仓库id
     */
    void freeUpStorage(String storageId);

    /**
     * 升级存储空间
     * @param storageEntity 仓库信息
     */
    void upgradeStorage(StorageEntity storageEntity);

    /**
     * 检查存储空间
     * @param fileSize 文件大小
     * @return boolean
     */
    boolean checkStorage(Long fileSize);

    /**
     * 更新存储的大小
     * @param storageEntity 仓库信息
     */
    void updateStorage(StorageEntity storageEntity);
    
}
