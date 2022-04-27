package com.smk.cpp.sts.core.file.upload;

import com.smk.cpp.sts.business.file.dto.UploadDto;
import com.smk.cpp.sts.business.file.vo.UploadVo;
import org.springframework.web.multipart.MultipartFile;

/**
 * TODO
 *
 * @version 1.0
 * @Author: Jin-LiangBo
 * @Date: 2022年03月23日 10:40
 * @Description:
 */
public interface UploadStrategy {

    /**
     * 上传文件
     * @param files 文件列表
     * @param uploadDto 文件信息
     * @return com.smk.cpp.sts.business.file.vo.UploadVo
     */
    UploadVo upload(MultipartFile files, UploadDto uploadDto);
    
}
