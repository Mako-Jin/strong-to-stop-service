package com.smk.cpp.sts.core.file.upload;

import com.smk.cpp.sts.business.file.dto.UploadDto;
import com.smk.cpp.sts.business.file.vo.UploadVo;
import com.smk.cpp.sts.core.file.upload.strategy.LocalAbstractUploadStrategy;
import org.springframework.web.multipart.MultipartFile;

import java.util.concurrent.Callable;

/**
 * TODO
 *
 * @version 1.0
 * @Author: Jin-LiangBo
 * @Date: 2022年03月25日 11:20
 * @Description:
 */
public class FileUploadCallable implements Callable<UploadVo> {

    private UploadDto uploadDto;
    
    private UploadStrategy strategy;
    
    private MultipartFile file;

    public FileUploadCallable(MultipartFile file, UploadDto uploadDto) {
        this.file = file;
        this.uploadDto = uploadDto;
        this.strategy = new LocalAbstractUploadStrategy();
    }

    public FileUploadCallable(MultipartFile file, UploadDto uploadDto, 
            UploadStrategy uploadStrategy) {
        this.file = file;
        this.uploadDto = uploadDto;
        this.strategy = uploadStrategy;
    }

    @Override
    public UploadVo call() throws Exception {
        //
        return strategy.upload(file, uploadDto);
    }
}
