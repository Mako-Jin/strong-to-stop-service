package com.smk.cpp.sts.business.file.service;

import com.smk.cpp.sts.business.file.dto.UploadDto;
import com.smk.cpp.sts.business.file.entity.FileEntity;
import com.smk.cpp.sts.business.file.vo.FileStatusVo;

import java.util.List;

/**
 * TODO
 *
 * @version 1.0
 * @Author: Jin-LiangBo
 * @Date: 2022年03月15日 11:06
 * @Description:
 */
public interface FileMgrService {
    /**
     * 保存文件信息
     * @param fileEntity 文件信息
     */
    void saveFile(FileEntity fileEntity);
    
    /**
     * 通过标识符获取文件
     * @param identifier 标识符
     * @return com.smk.cpp.sts.business.file.entity.FileEntity
     */
    FileEntity getFileByIdentifier(String identifier);
    
    /**
     * 通过目录id获取文件信息
     * @param catalogId 目录id
     * @return com.smk.cpp.sts.business.file.entity.FileEntity
     */
    FileEntity getFileByCatalogId(String catalogId);

    /**
     * 初始化文件
     * @param uploadDto 上传参数
     * @return java.lang.String
     */
    String initiateFile (UploadDto uploadDto);
    
    /**
     * 更新文件上传进度
     * @param fileId 文件id
     * @param progress 进度
     */
    void updateProgress(String fileId, double progress);

    /**
     * 获取未完成的文件列表
     * @return java.util.List<com.smk.cpp.sts.business.file.vo.FileStatusVo>
     */
    List<FileStatusVo> getUnCompleteFileList();

    /**
     * 根据文件id查询文件信息
     * @param fileId 文件id
     * @return com.smk.cpp.sts.business.file.entity.FileEntity
     */
    FileEntity getFileByFileId(String fileId);
    
    /**
     * 根据文件id删除文件信息
     * @param fileId 文件id
     */
    void deleteFileByFileId(String fileId);
    
}
