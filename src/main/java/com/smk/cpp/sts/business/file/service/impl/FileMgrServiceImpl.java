package com.smk.cpp.sts.business.file.service.impl;

import com.smk.cpp.sts.base.enums.ResultStatusEnums;
import com.smk.cpp.sts.base.exception.ServiceException;
import com.smk.cpp.sts.business.file.dao.FileMgrDao;
import com.smk.cpp.sts.business.file.dto.UploadDto;
import com.smk.cpp.sts.business.file.entity.FileEntity;
import com.smk.cpp.sts.business.file.service.CatalogMgrService;
import com.smk.cpp.sts.business.file.service.FileMgrService;
import com.smk.cpp.sts.business.file.service.SliceService;
import com.smk.cpp.sts.business.file.vo.FileStatusVo;
import com.smk.cpp.sts.common.util.FileUtils;
import com.smk.cpp.sts.common.util.SecurityUtils;
import com.smk.cpp.sts.common.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * TODO
 *
 * @version 1.0
 * @Author: Jin-LiangBo
 * @Date: 2022年03月15日 11:06
 * @Description:
 */
@Service
public class FileMgrServiceImpl implements FileMgrService {
    
    @Autowired
    private FileMgrDao fileMgrDao;
    
    @Autowired
    private SliceService sliceService;
    
    @Autowired
    private CatalogMgrService catalogMgrService;
    
    @Override
    public void saveFile(FileEntity fileEntity) {
        fileEntity.setFileId(StringUtil.idGenerate());
        fileMgrDao.createFile(fileEntity);
    }
    
    @Override
    public FileEntity getFileByIdentifier(String identifier) {
        if (!StringUtils.hasText(identifier)) {
            throw new ServiceException(ResultStatusEnums.COMMON_PARAM_CHECK_ERROR);
        }
        return fileMgrDao.getFileByIdentifier(identifier);
    }

    @Override
    public FileEntity getFileByCatalogId(String catalogId) {
        if (!StringUtils.hasText(catalogId)) {
            throw new ServiceException(ResultStatusEnums.COMMON_ID_CANNOT_NULL);
        }
        return fileMgrDao.getFileByCatalogId(catalogId);
    }

    @Override
    public String initiateFile(UploadDto uploadDto) {
        FileEntity file = this.getFileByIdentifier(uploadDto.getIdentifier());
        if (file == null) {
            file = transferToFile(uploadDto);
            String filePath = FileUtils.getUploadPath();
            FileUtils.createDirectory(filePath);
            int fileCount = fileMgrDao.createFile(file);
            if (fileCount < 1) {
                throw new ServiceException(ResultStatusEnums.COMMON_DATA_SAVE_FAILED);
            }
            sliceService.initiateFileSlice(file.getFileId(), uploadDto.getChunkCounts());
        }
        return catalogMgrService.createCatalog(uploadDto, file.getFileId());
    }

    @Override
    public void updateProgress(String fileId, double progress) {
        if (!StringUtils.hasText(fileId)) {
            throw new ServiceException(ResultStatusEnums.COMMON_ID_CANNOT_NULL);
        }
        fileMgrDao.updateProgress(fileId, progress);
    }

    @Override
    public List<FileStatusVo> getUnCompleteFileList() {
        String currentUser = SecurityUtils.getCurrentUserId();
        return fileMgrDao.getUnCompleteFileList(currentUser);
    }

    @Override
    public FileEntity getFileByFileId(String fileId) {
        if (!StringUtils.hasText(fileId)) {
            throw new ServiceException(ResultStatusEnums.COMMON_ID_CANNOT_NULL);
        }
        return fileMgrDao.getFileByFileId(fileId);
    }

    @Override
    public void deleteFileByFileId(String fileId) {
        if (!StringUtils.hasText(fileId)) {
            throw new ServiceException(ResultStatusEnums.COMMON_ID_CANNOT_NULL);
        }
        // 需要判断是否还有目录引用文件
        int count = catalogMgrService.getCountByFileId(fileId);
        if (count == 0) {
            fileMgrDao.deleteFileByFileId(fileId);
            sliceService.deleteSliceByFileId(fileId);
        }
    }

    private FileEntity transferToFile(UploadDto uploadDto) {
        FileEntity fileEntity = new FileEntity();
        fileEntity.setFileId(StringUtil.idGenerate());
        fileEntity.setFilename(StringUtil.idGenerate());
        fileEntity.setFileSize(uploadDto.getTotalSize());
        fileEntity.setFileType(uploadDto.getFileType());
        fileEntity.setIdentifier(uploadDto.getIdentifier());
        return fileEntity;
    }
    
}
