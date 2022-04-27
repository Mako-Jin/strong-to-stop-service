package com.smk.cpp.sts.business.file.service;

import com.smk.cpp.sts.business.file.dto.UploadDto;
import com.smk.cpp.sts.business.file.entity.SliceEntity;

import java.util.List;

/**
 * TODO
 *
 * @version 1.0
 * @Author: Jin-LiangBo
 * @Date: 2022年03月22日 16:56
 * @Description:
 */
public interface SliceService {
    
    /**
     * 设置分片
     * @param sliceEntity 分片信息
     */
    void setSlice(SliceEntity sliceEntity);
    
    /**
     * 更新切片状态
     * @param uploadDto
     */
    void updateSliceStatus(UploadDto uploadDto);
    
    /**
     * 通过文件id获取分片列表
     * @param fileId 文件id
     * @return java.util.List<com.smk.cpp.sts.business.file.entity.SliceEntity>
     */
    List<SliceEntity> getSliceByFileId(String fileId);
    
    /**
     * 通过文件id获取分片列表
     * @param fileId 文件id
     * @param status 分片状态
     * @return java.util.List<com.smk.cpp.sts.business.file.entity.SliceEntity>
     */
    List<SliceEntity> getSliceByFileId(String fileId, int status);
    
    /**
     * 保存分片信息
     * @param sliceEntity 分片信息
     */
    void saveSlice(SliceEntity sliceEntity);

    /**
     * 初始化文件分片
     * @param fileId
     * @param chunkCount
     */
    void initiateFileSlice(String fileId, int chunkCount);
    
    /**
     * 根据文件id删除分片
     * @param fileId 文件id
     */
    void deleteSliceByFileId(String fileId);
    
}
