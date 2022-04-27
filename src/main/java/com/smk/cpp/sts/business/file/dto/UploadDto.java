package com.smk.cpp.sts.business.file.dto;

import org.springframework.web.multipart.MultipartFile;

/**
 * TODO
 *
 * @version 1.0
 * @Author: Jin-LiangBo
 * @Date: 2022年03月21日 23:38
 * @Description:
 */
public class UploadDto {
    /**
     * 目录id，fileId
     */
    private String catalogId;
    /**
     * 目录id，fileId
     */
    private String fileId;
    /**
     * 目标文件夹路径，
     */
    private String parentId;
    /**
     * 原始文件名
     */
    private String originalFilename;
    /**
     * 存储文件名
     */
    private String filename;
    /**
     * 文件类型
     */
    private String fileType;
    /**
     * 总分片数量
     */
    private int chunkCounts;
    /**
     * 当前为第几块分片
     */
    private int currentChunk;
    /**
     * 块大小
     */
    private Long chunkSize;
    /**
     * 标识符，md5码
     */
    private String identifier;
    /**
     * 当前分片大小
     */
    private Long currentChunkSize;
    /**
     * 文件总大小
     */
    private long totalSize;
    
    /**
     * 
     */
    private int dbType;

    public String getCatalogId() {
        return catalogId;
    }

    public void setCatalogId(String catalogId) {
        this.catalogId = catalogId;
    }

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getOriginalFilename() {
        return originalFilename;
    }

    public void setOriginalFilename(String originalFilename) {
        this.originalFilename = originalFilename;
    }

    public int getChunkCounts() {
        return chunkCounts;
    }

    public void setChunkCounts(int chunkCounts) {
        this.chunkCounts = chunkCounts;
    }

    public int getCurrentChunk() {
        return currentChunk;
    }

    public void setCurrentChunk(int currentChunk) {
        this.currentChunk = currentChunk;
    }

    public Long getChunkSize() {
        return chunkSize;
    }

    public void setChunkSize(Long chunkSize) {
        this.chunkSize = chunkSize;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public Long getCurrentChunkSize() {
        return currentChunkSize;
    }

    public void setCurrentChunkSize(Long currentChunkSize) {
        this.currentChunkSize = currentChunkSize;
    }

    public long getTotalSize() {
        return totalSize;
    }

    public void setTotalSize(long totalSize) {
        this.totalSize = totalSize;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public int getDbType() {
        return dbType;
    }

    public void setDbType(int dbType) {
        this.dbType = dbType;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }
}
