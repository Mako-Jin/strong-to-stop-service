package com.smk.cpp.sts.business.file.service.impl;

import com.smk.cpp.sts.base.enums.ResultStatusEnums;
import com.smk.cpp.sts.base.exception.ServiceException;
import com.smk.cpp.sts.business.file.dao.SliceDao;
import com.smk.cpp.sts.business.file.dto.UploadDto;
import com.smk.cpp.sts.business.file.entity.SliceEntity;
import com.smk.cpp.sts.business.file.service.SliceService;
import com.smk.cpp.sts.common.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * TODO
 *
 * @version 1.0
 * @Author: Jin-LiangBo
 * @Date: 2022年03月22日 17:31
 * @Description:
 */
@Service
public class SliceServiceImpl implements SliceService {
    
    @Autowired
    private SliceDao sliceDao;
    
    @Override
    public void setSlice(SliceEntity sliceEntity) {
        
    }

    @Override
    public void updateSliceStatus(UploadDto uploadDto) {
        SliceEntity sliceEntity = new SliceEntity();
        sliceEntity.setStatus(3);
        sliceEntity.setFileId(uploadDto.getFileId());
        sliceEntity.setChunkNumber(uploadDto.getCurrentChunk());
        sliceEntity.setIdentifier(uploadDto.getIdentifier());
        sliceEntity.setChunkSize(uploadDto.getCurrentChunkSize());
        sliceDao.updateSlice(sliceEntity);
    }

    @Override
    public List<SliceEntity> getSliceByFileId(String fileId) {
        if (!StringUtils.hasText(fileId)) {
            throw new ServiceException(ResultStatusEnums.COMMON_ID_CANNOT_NULL);
        }
        return  sliceDao.getSliceByFileId(fileId, null);
    }

    @Override
    public List<SliceEntity> getSliceByFileId(String fileId, int status) {
        if (!StringUtils.hasText(fileId)) {
            throw new ServiceException(ResultStatusEnums.COMMON_ID_CANNOT_NULL);
        }
        return  sliceDao.getSliceByFileId(fileId, status);
    }

    @Override
    public void saveSlice(SliceEntity sliceEntity) {

    }

    @Override
    public void initiateFileSlice(String fileId, int chunkCount) {
        List<SliceEntity> sliceEntityList = new ArrayList<>();
        SliceEntity sliceEntity;
        for (int i = 0; i < chunkCount; i++) {
            sliceEntity = new SliceEntity();
            sliceEntity.setSliceId(StringUtil.idGenerate());
            sliceEntity.setFileId(fileId);
            sliceEntity.setChunkNumber(i + 1);
            sliceEntityList.add(sliceEntity);
        }
        sliceDao.batchSaveSlice(sliceEntityList);
    }

    @Override
    public void deleteSliceByFileId(String fileId) {
        if (!StringUtils.hasText(fileId)) {
            throw new ServiceException(ResultStatusEnums.COMMON_ID_CANNOT_NULL);
        }
        sliceDao.deleteSliceByFileId(fileId);
    }

}
