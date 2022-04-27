package com.smk.cpp.sts.business.file.service.impl;

import com.smk.cpp.sts.base.constant.FileConstants;
import com.smk.cpp.sts.base.enums.ResultStatusEnums;
import com.smk.cpp.sts.base.exception.ServiceException;
import com.smk.cpp.sts.business.file.dao.CatalogMgrDao;
import com.smk.cpp.sts.business.file.dto.UploadDto;
import com.smk.cpp.sts.business.file.entity.CatalogEntity;
import com.smk.cpp.sts.business.file.service.CatalogMgrService;
import com.smk.cpp.sts.common.util.FileUtils;
import com.smk.cpp.sts.common.util.SecurityUtils;
import com.smk.cpp.sts.common.util.StringUtil;
import com.smk.cpp.sts.common.util.TreeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * TODO
 *
 * @version 1.0
 * @Author: Jin-LiangBo
 * @Date: 2022年04月01日 17:56
 * @Description:
 */
@Service
public class CatalogMgrServiceImpl implements CatalogMgrService {

    @Autowired
    private CatalogMgrDao catalogMgrDao;

    /**
     * 创建文件夹
     */
    @Override
    public void createDirectory(CatalogEntity catalog) {
        catalog.setCatalogId(StringUtil.idGenerate());
        catalog.setParentId(!StringUtils.hasText(catalog.getParentId()) ?
             FileConstants.FILE_TOP_PARENT_ID : catalog.getParentId());
        setRelativePath(catalog);
        setOriginalFilename(catalog);
        catalog.setIsDirectory(FileConstants.CATALOG_IS_DIRECTORY);
        int count = catalogMgrDao.createCatalog(catalog);
        if (count < 1) {
            throw new ServiceException(ResultStatusEnums.COMMON_DATA_SAVE_FAILED);
        }
    }

    @Override
    public String createCatalog(UploadDto uploadDto, String fileId) {
        CatalogEntity catalog = this.getCatalogByParentIdAndFileId(fileId, 
                uploadDto.getParentId());
        if (catalog != null) {
            catalog.setOriginalFilename(uploadDto.getOriginalFilename());
            this.renameCatalog(catalog);
            return catalog.getCatalogId();
        }
        catalog = setCatalog(uploadDto, fileId);
        setRelativePath(catalog);
        setOriginalFilename(catalog);
        int count = catalogMgrDao.createCatalog(catalog);
        if (count < 1) {
            throw new ServiceException(ResultStatusEnums.COMMON_DATA_SAVE_FAILED);
        }
        return catalog.getCatalogId();
    }

    /**
     * 获取文件列表
     */
    @Override
    public List<CatalogEntity> getCatalogList(CatalogEntity catalog) {
        if (!StringUtils.hasText(catalog.getParentId())) {
            catalog.setParentId(FileConstants.FILE_TOP_PARENT_ID);
        }
        String currentUser = SecurityUtils.getCurrentUserId();
        catalog.setCreateUser(currentUser);
        return catalogMgrDao.getCatalogListByParentId(catalog);
    }

    /**
     * 重命名文件夹或文件
     */
    @Override
    public void renameCatalog(CatalogEntity catalog) {
        if (!StringUtils.hasText(catalog.getCatalogId())) {
            throw new ServiceException(ResultStatusEnums.COMMON_ID_CANNOT_NULL);
        }
        setOriginalFilename(catalog);
        int resultCount = catalogMgrDao.updateCatalogInfoByCatalogId(catalog);
        if (resultCount < 1) {
            throw new ServiceException(ResultStatusEnums.COMMON_DATA_UPDATE_FAILED);
        }
    }

    @Override
    public void deleteByCatalogId(String catalogId) {
        if (!StringUtils.hasText(catalogId)) {
            throw new ServiceException(ResultStatusEnums.COMMON_ID_CANNOT_NULL);
        }
        String currentUser = SecurityUtils.getCurrentUserId();
        List<CatalogEntity> catalogLists = catalogMgrDao.getAllCatalog(currentUser);
        List<String> catalogIds = TreeUtils.getSubTreeNodeIds(catalogLists, catalogId);
        catalogIds.add(catalogId);
        String currentUserId = SecurityUtils.getCurrentUserId();
        int resultCount = catalogMgrDao.batchDeleteByCateLogIds(catalogIds, currentUserId);
        if (resultCount < 1) {
            throw new ServiceException(ResultStatusEnums.COMMON_DATA_DELETE_FAILED);
        }
    }

    @Override
    public CatalogEntity getCatalogByParentIdAndFileId(String fileId, String parentId) {
        return catalogMgrDao.getCatalogByParentIdAndFileId(fileId, parentId);
    }

    @Override
    public CatalogEntity getCatalogByCatalogId(String catalogId) {
        if (!StringUtils.hasText(catalogId)) {
            throw new ServiceException(ResultStatusEnums.COMMON_ID_CANNOT_NULL);
        }
        return catalogMgrDao.getCatalogByCatalogId(catalogId);
    }

    @Override
    public int getCountByFileId(String fileId) {
        if (!StringUtils.hasText(fileId)) {
            return 0;
        }
        return catalogMgrDao.getCountByFileId(fileId);
    }

    private CatalogEntity setCatalog(UploadDto uploadDto, String fileId) {
        CatalogEntity catalogEntity = new CatalogEntity();
        catalogEntity.setCatalogId(StringUtil.idGenerate());
        catalogEntity.setParentId(uploadDto.getParentId());
        catalogEntity.setFileId(fileId);
        catalogEntity.setFileSize(uploadDto.getTotalSize());
        catalogEntity.setFileType(uploadDto.getFileType());
        catalogEntity.setOriginalFilename(uploadDto.getOriginalFilename());
        return catalogEntity;
    }

    /**
     * 根据父id设置相对路径
     */
    private void setRelativePath (CatalogEntity catalog) {
        if (!StringUtils.hasText(catalog.getParentId())
                || FileConstants.FILE_TOP_PARENT_ID.equals(catalog.getParentId())) {
            catalog.setRelativePath(FileConstants.FILE_TOP_PARENT_ID);
        } else {
            CatalogEntity directory = catalogMgrDao.getCatalogByCatalogId(catalog.getParentId());
            catalog.setRelativePath(directory.getRelativePath());
        }
    }

    /**
     * 文件或文件夹重命名，自动命名为：name（n）
     */
    private void setOriginalFilename (CatalogEntity catalog) {
        int sameNameCount = catalogMgrDao.getFileCountByOriginalFileName(catalog);
        if (sameNameCount > 0) {
            catalog.setOriginalFilename(
                FileUtils.sameNameRename(catalog.getOriginalFilename(), sameNameCount)
            );
        }
    }
}
