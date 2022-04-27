package com.smk.cpp.sts.business.file.service;

import com.smk.cpp.sts.business.file.dto.UploadDto;
import com.smk.cpp.sts.business.file.entity.CatalogEntity;

import java.util.List;

/**
 * TODO
 *
 * @version 1.0
 * @Author: Jin-LiangBo
 * @Date: 2022年04月01日 17:55
 * @Description:
 */
public interface CatalogMgrService {
    
    /**
     * 创建文件夹目录
     * @param catalog 目录信息
     */
    void createDirectory(CatalogEntity catalog);
    
    /**
     * 新增文件
     * @param uploadDto 文件信息
     * @param fileId 文件id
     * @return java.lang.String
     */
    String createCatalog(UploadDto uploadDto, String fileId);

    /**
     * 获取文件目录列表
     * @param catalog 筛选条件
     * @return java.util.List<com.smk.cpp.sts.business.file.entity.CatalogEntity>
     */
    List<CatalogEntity> getCatalogList(CatalogEntity catalog);

    /**
     * 重命名文件夹或文件
     * @param catalog 目录信息
     */
    void renameCatalog(CatalogEntity catalog);
    
    /**
     * 根据目录id删除目录
     * @param catalogId 目录id
     */
    void deleteByCatalogId(String catalogId);

    /**
     * 通过父id和文件id获取目录信息
     * @param fileId 文件id
     * @param parentId 父id
     * @return com.smk.cpp.sts.business.file.entity.CatalogEntity
     */
    CatalogEntity getCatalogByParentIdAndFileId(String fileId, String parentId);
    
    /**
     * 通过目录id获取目录信息
     * @param catalogId 目录id
     * @return com.smk.cpp.sts.business.file.entity.CatalogEntity
     */
    CatalogEntity getCatalogByCatalogId(String catalogId);
    
    /**
     * 根据文件id获取目录关联数
     * @param fileId 文件id
     * @return int
     */
    int getCountByFileId(String fileId);
    
}
