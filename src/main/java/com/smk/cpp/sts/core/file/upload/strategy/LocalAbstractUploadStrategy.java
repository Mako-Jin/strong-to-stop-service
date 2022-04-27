package com.smk.cpp.sts.core.file.upload.strategy;

import com.smk.cpp.sts.base.enums.ResultStatusEnums;
import com.smk.cpp.sts.base.exception.ServiceException;
import com.smk.cpp.sts.business.file.dto.UploadDto;
import com.smk.cpp.sts.business.file.entity.FileEntity;
import com.smk.cpp.sts.business.file.vo.UploadVo;
import com.smk.cpp.sts.common.properties.FileProperties;
import com.smk.cpp.sts.common.util.FileUtils;
import com.smk.cpp.sts.common.util.StringUtil;
import com.smk.cpp.sts.core.file.upload.template.AbstractUploadTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * TODO
 *
 * @version 1.0
 * @Author: Jin-LiangBo
 * @Date: 2022年03月24日 19:12
 * @Description:
 */
public class LocalAbstractUploadStrategy extends AbstractUploadTemplate {

    private static final Logger logger = LoggerFactory.getLogger(LocalAbstractUploadStrategy.class);

    @Override
    public UploadVo sliceUpload(MultipartFile files, UploadDto uploadDto) {
        RandomAccessFile randomAccessFile = null;
        try {
            String fileUploadPath = String.join(File.separator, 
                    FileUtils.getUploadPath());
            File tempFile = FileUtils.createFile(fileUploadPath,
                    uploadDto.getFilename() + ".temp");
            randomAccessFile = new RandomAccessFile(tempFile, "rw");
            long chunkSize = Objects.isNull(uploadDto.getChunkSize()) 
                    ? FileProperties.getUpload().getDefaultChunkSize() * 1024 * 1024
                    : uploadDto.getChunkSize();
            long offset = (uploadDto.getCurrentChunk() - 1) * chunkSize;
            randomAccessFile.seek(offset);
            randomAccessFile.write(files.getBytes());
            return checkAndSetUploadProgress(uploadDto, fileUploadPath);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            FileUtils.close(randomAccessFile);
        }
        return null;
    }

    @Override
    public FileEntity ordinaryUpload(MultipartFile files, UploadDto uploadDto) {
        FileEntity fileEntity = newFileEntity(files);
        final Path path = Paths.get(FileUtils.getUploadPath(), fileEntity.getFilename());
        final File dir = path.getParent().toFile();
        try {
            FileUtils.createDirectory(dir);
            files.transferTo(path);
        } catch (IOException e) {
            logger.error("file transfer error,file name is " + fileEntity.getFilename());
            throw new ServiceException(ResultStatusEnums.UPLOAD_FILE_ERROR);
        }
        return fileEntity;
    }
    
    private FileEntity newFileEntity(MultipartFile file) {
        String originalFilename = file.getOriginalFilename();
        String type = originalFilename.substring(originalFilename.indexOf('.')+1);
        FileEntity fileEntity = new FileEntity();
        fileEntity.setFilename(StringUtil.idGenerate());
        fileEntity.setFileType(type);
        fileEntity.setFileSize(file.getSize());
        return fileEntity;
    }

    /**
     * 检查并修改文件上传进度
     */
    @Override
    public UploadVo checkAndSetUploadProgress(UploadDto uploadDto, String uploadDirPath) {
        File confFile = new File(uploadDirPath, uploadDto.getFilename() + ".conf");
        Set<Integer> unCompleteList = new HashSet<>();
        RandomAccessFile accessConfFile = null;
        try {
            accessConfFile = new RandomAccessFile(confFile, "rw");
            //创建conf文件文件长度为总分片数，每上传一个分块即向conf文件中写入一个127，
            // 那么没上传的位置就是默认0,已上传的就是Byte.MAX_VALUE 127
            accessConfFile.setLength(uploadDto.getChunkCounts());
            accessConfFile.seek(uploadDto.getCurrentChunk() - 1);
            accessConfFile.write(Byte.MAX_VALUE);
            //completeList 检查是否全部完成,如果数组里是否全部都是127(全部分片都成功上传)
            byte[] confFileList = FileUtils.readFileToByteArray(confFile);
            for (int i = 0; i < confFileList.length; i++) {
                if (confFileList[i] != Byte.MAX_VALUE) {
                    unCompleteList.add(i);
                }
            }
        } catch (IOException e) {
            logger.error("upload error {}", e.getMessage());
            throw new ServiceException(ResultStatusEnums.UPLOAD_FILE_ERROR);
        } finally {
            FileUtils.close(accessConfFile);
        }
        if (unCompleteList.size() == 0) {
            confFile.delete();
        }
        return new UploadVo(uploadDto.getCatalogId(), unCompleteList);
    }

}
