package com.smk.cpp.sts.business.file.dao;

import com.smk.cpp.sts.business.file.entity.SliceEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * TODO
 *
 * @version 1.0
 * @Author: Jin-LiangBo
 * @Date: 2022年03月22日 22:04
 * @Description:
 */
@Mapper
public interface SliceDao {
    
    /**
     * 通过文件id获取分片信息
     * @param fileId 文件id
     * @param status 状态
     * @return java.util.List<com.smk.cpp.sts.business.file.entity.SliceEntity>
     */
    List<SliceEntity> getSliceByFileId(String fileId, Integer status);

    /**
     * 批量保存分片信息
     * @param sliceList 分片列表
     * @return int
     */
    int batchSaveSlice(List<SliceEntity> sliceList);
    
    /**
     * 更新分片信息
     * @param sliceEntity 分片信息
     * @return int
     */
    int updateSlice(SliceEntity sliceEntity);

    /**
     * 根据文件id删除分片信息
     * @param fileId 文件id
     * @return int
     */
    int deleteSliceByFileId(String fileId);
    
}
