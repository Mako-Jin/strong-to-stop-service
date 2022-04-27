package com.smk.cpp.sts.core.file.upload.template;

import com.smk.cpp.sts.business.file.dto.UploadDto;
import com.smk.cpp.sts.business.file.entity.FileEntity;
import com.smk.cpp.sts.business.file.vo.UploadVo;
import com.smk.cpp.sts.common.util.FileUtils;
import com.smk.cpp.sts.core.file.upload.UploadStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

/**
 * TODO
 *
 * @version 1.0
 * @Author: Jin-LiangBo
 * @Date: 2022年03月24日 11:09
 * @Description:
 */
public abstract class AbstractUploadTemplate implements UploadStrategy {
    
    private static final Logger logger = LoggerFactory.getLogger(AbstractUploadTemplate.class);

    /**
     * 切片上传
     * @param files 文件
     * @param uploadDto 文件信息
     * @return UploadVo
     */
    public abstract UploadVo sliceUpload(MultipartFile files, UploadDto uploadDto);

    /**
     * 普通上传
     * @param files 文件
     * @param uploadDto 文件信息
     * @return FileEntity
     */
    public abstract FileEntity ordinaryUpload(MultipartFile files, UploadDto uploadDto);

    @Override
    public UploadVo upload(MultipartFile files, UploadDto uploadDto) {
        UploadVo uploadVo = this.sliceUpload(files, uploadDto);
        if (uploadVo.getMissChunks().size() == 0) {
            this.renameFile(uploadDto, uploadDto.getFilename());
        }
        return uploadVo;
    }

    /** 
     * 文件重命名
     */
    private void renameFile(UploadDto uploadDto, String newFileName) {
        String fileUploadPath = FileUtils.getUploadPath();
        File tempFile = FileUtils.createFile(fileUploadPath, 
                uploadDto.getFilename() + ".temp");
        // 检查要重命名的文件是否存在，是否是文件
        if (!tempFile.exists() || tempFile.isDirectory()) {
            logger.error("File does not exist: {}", tempFile.getName());
        }
        String filePath = fileUploadPath + File.separator + newFileName;
        File newFile = new File(filePath);
        tempFile.renameTo(newFile);
    }

    /**
     * 检查文件状态
     * @param uploadDto 文件信息
     * @param uploadDirPath 上传目录
     * @return com.smk.cpp.sts.business.file.vo.UploadVo
     */
    public abstract UploadVo checkAndSetUploadProgress(UploadDto uploadDto, String uploadDirPath);

}
