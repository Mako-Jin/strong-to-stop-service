package com.smk.cpp.sts.business.file.dao;

import com.smk.cpp.sts.business.file.entity.CatalogEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * TODO
 *
 * @version 1.0
 * @Author: Jin-LiangBo
 * @Date: 2022年04月01日 18:21
 * @Description:
 */
@Mapper
public interface CatalogMgrDao {
    
    /**
     * 根据父id获取目录列表
     * @param catalog 筛选条件
     * @return java.util.List<com.smk.cpp.sts.business.file.entity.CatalogEntity>
     */
    List<CatalogEntity> getCatalogListByParentId(@Param("catalog") CatalogEntity catalog);

    /**
     * 新增目录
     * @param catalog 目录信息
     * @return int
     */
    int createCatalog(CatalogEntity catalog);

    /**
     * 根据文件名获取文件数量
     * @param catalog 筛选条件
     * @return int
     */
    int getFileCountByOriginalFileName(CatalogEntity catalog);

    /**
     * 通过目录id更新目录信息
     * @param catalog 目录信息
     * @return int
     */
    int updateCatalogInfoByCatalogId(CatalogEntity catalog);

    /**
     * 获取所有目录列表
     * @param currentUser 用户id
     * @return java.util.List<com.smk.cpp.sts.business.file.entity.CatalogEntity>
     */
    List<CatalogEntity> getAllCatalog(String currentUser);

    /**
     * 批量删除目录
     * @param catalogIds 目录id列表
     * @param currentUserId 当前用户id
     * @return int
     */
    int batchDeleteByCateLogIds(List<String> catalogIds, String currentUserId);

    /**
     * 根据父id和文件id获取目录信息
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
     * 根据文件id获取目录关联数量
     * @param fileId 文件id
     * @return int
     */
    int getCountByFileId(String fileId);
    
}
