package com.smk.cpp.sts.business.file.service.impl;

import com.smk.cpp.sts.base.constant.FileConstants;
import com.smk.cpp.sts.base.enums.ResultStatusEnums;
import com.smk.cpp.sts.base.exception.ServiceException;
import com.smk.cpp.sts.business.file.dto.UploadDto;
import com.smk.cpp.sts.business.file.entity.CatalogEntity;
import com.smk.cpp.sts.business.file.entity.FileEntity;
import com.smk.cpp.sts.business.file.entity.SliceEntity;
import com.smk.cpp.sts.business.file.service.CatalogMgrService;
import com.smk.cpp.sts.business.file.service.FileMgrService;
import com.smk.cpp.sts.business.file.service.SliceService;
import com.smk.cpp.sts.business.file.service.StorageService;
import com.smk.cpp.sts.business.file.service.TransferService;
import com.smk.cpp.sts.business.file.vo.UploadVo;
import com.smk.cpp.sts.common.util.FileUtils;
import com.smk.cpp.sts.core.file.download.DownLoadStrategy;
import com.smk.cpp.sts.core.file.download.LocalDownloadStrategy;
import com.smk.cpp.sts.core.file.upload.FileUploadCallable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * TODO
 *
 * @version 1.0
 * @Author: Jin-LiangBo
 * @Date: 2022年03月15日 11:10
 * @Description:
 */
@Service
public class TransferServiceImpl implements TransferService {

    private static final Logger logger = LoggerFactory.getLogger(TransferServiceImpl.class);

    private static AtomicInteger atomicInteger = new AtomicInteger(0);

    private static final ExecutorService executorService = new ThreadPoolExecutor(
        10, 20, 60000, TimeUnit.MILLISECONDS, 
        new LinkedBlockingDeque<>(200), 
        (r)->{
            String threadName = "thread-" + atomicInteger.getAndIncrement();
            Thread thread = new Thread(r);
            thread.setName(threadName);
            return thread;
        }
    );

    private final CompletionService<UploadVo> completionService = 
            new ExecutorCompletionService<>(executorService);

    @Autowired
    private StorageService storageService;

    @Autowired
    private FileMgrService fileMgrService;
    
    @Autowired
    private SliceService sliceService;

    @Autowired
    private CatalogMgrService catalogMgrService;
    
    @Override
    public UploadVo uploadFile(MultipartFile files, UploadDto uploadDto) {
        if (files == null) {
            logger.info("file list length is 0");
            throw new ServiceException(ResultStatusEnums.UPLOAD_FILE_IS_NULL);
        }
        FileEntity file = fileMgrService.getFileByCatalogId(uploadDto.getCatalogId());
        uploadDto.setFileId(file.getFileId());
        uploadDto.setFilename(file.getFilename());
        try {
            UploadVo uploadVo = doUpload(files, uploadDto);
            sliceService.updateSliceStatus(uploadDto);
            double process = Math.ceil(
                    (uploadDto.getChunkCounts() - uploadVo.getMissChunks().size()) 
                            / uploadDto.getChunkCounts() * 100);
            fileMgrService.updateProgress(file.getFileId(), process);
            return uploadVo;
        } catch (InterruptedException | ExecutionException e) {
            logger.error("file upload error ==> {}", e.getMessage(), e);
            throw new ServiceException(ResultStatusEnums.UPLOAD_FILE_ERROR);
        }
    }
    
    private UploadVo doUpload (MultipartFile file, UploadDto uploadDto) 
            throws InterruptedException, ExecutionException {
        List<SliceEntity> sliceList = sliceService.
                getSliceByFileId(uploadDto.getFileId(), FileConstants.FILE_UPLOAD_STATUS_CREATED);
        Set<Integer> missChunks = sliceList.stream().map((f) -> f.getChunkNumber())
                .collect(Collectors.toSet());
        if (missChunks != null && !missChunks.contains(uploadDto.getCurrentChunk())) {
            completionService.submit(new FileUploadCallable(file, uploadDto));
            return completionService.take().get();
        }
        return new UploadVo(missChunks);
    }
    
    @Override
    public UploadVo checkIdentifier(UploadDto uploadDto) {
        // 检查是否满足秒传条件
        FileEntity fileEntity = fileMgrService
                .getFileByIdentifier(uploadDto.getIdentifier());
        if (fileEntity == null) {
            // 不满足秒传条件
            return null;
        }
        // 检查仓库容量
        // boolean isSufficient = storageService.checkStorage(uploadDto.getTotalSize());
        // if(!isSufficient) {
        //     throw new ServiceException(ResultStatusEnums.STORAGE_NOT_SUFFICIENT);
        // }
        return checkIdentifier(fileEntity, uploadDto);
    }

    private UploadVo checkIdentifier (FileEntity fileEntity, UploadDto uploadDto) {
        List<SliceEntity> unCompleteSliceList = sliceService
                .getSliceByFileId(fileEntity.getFileId(), FileConstants.FILE_UPLOAD_STATUS_CREATED);
        if (unCompleteSliceList.size() == 0) {
            String catalogId = catalogMgrService.createCatalog(uploadDto, fileEntity.getFileId());
            fileMgrService.updateProgress(fileEntity.getFileId(), 100);
            return new UploadVo(catalogId, new HashSet<>());
        }
        String confFilePath = FileUtils.getUploadPath(fileEntity.getFilename() + ".conf");
        File confFile = new File(confFilePath);
        try {
            if (!confFile.exists()) {
                confFile = recoveryConfFile(confFilePath, fileEntity);
            }
            byte[] completeList = FileUtils.readFileToByteArray(confFile);
            Set<Integer> missChunkList = new HashSet<>();
            for (int i = 0; i < completeList.length; i++) {
                if (completeList[i] != Byte.MAX_VALUE) {
                    missChunkList.add(i);
                }
            }
            return new UploadVo(missChunkList);
        } catch (IOException e) {
            logger.error("check identifier read conf file error ==>", e);
            fileMgrService.deleteFileByFileId(fileEntity.getFileId());
            throw new ServiceException(ResultStatusEnums.CHECK_IDENTIFIER_ERROR);
        }
    }
    
    /**
     * 恢复上传状态文件
     */
    private File recoveryConfFile(String confFilePath, FileEntity fileEntity) throws IOException {
        File confFile = new File(confFilePath);
        List<SliceEntity> sliceList = sliceService.getSliceByFileId(fileEntity.getFileId());
        RandomAccessFile accessConfFile = null;
        accessConfFile = new RandomAccessFile(confFile, "rw");
        accessConfFile.setLength(sliceList.size());
        for (int i = 0; i < sliceList.size(); i++) {
            if (sliceList.get(i).getStatus() == FileConstants.FILE_UPLOAD_STATUS_DONE) {
                accessConfFile.seek(sliceList.get(i).getChunkNumber() - 1);
                accessConfFile.write(Byte.MAX_VALUE);
            }
        }
        accessConfFile.close();
        return confFile;
    }

    @Override
    public void preview(HttpServletRequest request, HttpServletResponse response,
                        String catalogId) {
        CatalogEntity catalog = catalogMgrService.getCatalogByCatalogId(catalogId);
        if (catalog.getIsDirectory() == 1) {
            throw new ServiceException("文件夹不支持预览");
        }
        FileEntity fileEntity = fileMgrService.getFileByFileId(catalog.getFileId());
        if (fileEntity.getProgress() != 100.0) {
            throw new ServiceException("文件还未上传完，不可以预览");
        }
        String headerValue = String.format("attachment; filename=\"%s\"", catalog.getOriginalFilename());
        response.addHeader(HttpHeaders.CONTENT_DISPOSITION, headerValue);
        response.addHeader(HttpHeaders.ACCEPT_RANGES, "bytes");
        download(request, response, fileEntity);
    }
    
    private void download(HttpServletRequest request, HttpServletResponse response, FileEntity fileEntity) {
        String downloadRange = request.getHeader("Range");
        long downloadSize = fileEntity.getFileSize();
        long startPosition = 0;
        if (StringUtils.hasLength(downloadRange)) {
            logger.debug("range:{}", downloadRange);
            response.setStatus(HttpStatus.PARTIAL_CONTENT.value());
            String[] range = downloadRange.substring(downloadRange.indexOf("=")).split("-");
            startPosition = Long.parseLong(range[0]);
            downloadSize = (int) (downloadSize - startPosition);
            if (range.length == 2 && startPosition > Long.parseLong(range[1])) {
                downloadSize = (int) (Long.parseLong(range[1]) - startPosition);
            }
        }
        response.addHeader(HttpHeaders.CONTENT_LENGTH, String.valueOf(downloadSize));
        DownLoadStrategy strategy = new LocalDownloadStrategy();
        try {
            strategy.downloadFile(response, fileEntity.getFilename(), downloadSize, startPosition);
        } catch (FileNotFoundException e) {
            logger.error("file is not exist ==> {}", e.getMessage(), e);
            throw new ServiceException(ResultStatusEnums.FILE_IS_NOT_EXIST);
        }
    }
    
}
