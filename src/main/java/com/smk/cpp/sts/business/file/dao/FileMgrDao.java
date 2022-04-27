package com.smk.cpp.sts.business.file.dao;

import com.smk.cpp.sts.business.file.entity.FileEntity;
import com.smk.cpp.sts.business.file.vo.FileStatusVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * TODO
 *
 * @version 1.0
 * @Author: Jin-LiangBo
 * @Date: 2022年03月15日 11:07
 * @Description:
 */
@Mapper
public interface FileMgrDao {
    
    /**
     * 新增文件信息
     * @param fileEntity 文件信息
     * @return int
     */
    int createFile(FileEntity fileEntity);
    
    /**
     * 根据文件标识符获取文件信息
     * @param identifier 文件标识符
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
     * 根据文件id获取文件信息
     * @param fileId 文件id
     * @return com.smk.cpp.sts.business.file.entity.FileEntity
     */
    FileEntity getFileByFileId(String fileId);
    
    /**
     * 更新文件上传进度
     * @param fileId 文件id
     * @param progress 进度
     * @return int
     */
    int updateProgress(String fileId, double progress);

    /**
     * 获取未完成文件列表
     * @param currentUser 当前用户
     * @return java.util.List<com.smk.cpp.sts.business.file.vo.FileStatusVo>
     */
    List<FileStatusVo> getUnCompleteFileList(String currentUser);
    
    /**
     * 根据文件id删除文件信息
     * @param fileId 文件id
     * @return java.util.List<com.smk.cpp.sts.business.file.vo.FileStatusVo>
     */
    List<FileStatusVo> deleteFileByFileId(String fileId);
    
}
