package com.smk.cpp.sts.business.file.dao;

import com.smk.cpp.sts.business.file.entity.StorageEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * TODO
 *
 * @version 1.0
 * @Author: Jin-LiangBo
 * @Date: 2022年03月22日 22:15
 * @Description:
 */
@Mapper
public interface StorageDao {

    /**
     * 获取当前用户仓储大侠
     * @param userId 用户id
     * @return com.smk.cpp.sts.business.file.entity.StorageEntity
     */
    StorageEntity getStorageByUserId(String userId);
    
}
