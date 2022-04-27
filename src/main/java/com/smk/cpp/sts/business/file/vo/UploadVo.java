package com.smk.cpp.sts.business.file.vo;

import java.util.Set;

/**
 * TODO
 *
 * @version 1.0
 * @Author: Jin-LiangBo
 * @Date: 2022年03月21日 16:07
 * @Description:
 */
public class UploadVo {

    private String catalogId;
    
    private boolean uploadComplete;

    /**
     * 已经上传的分片
     */
    private Set<Integer> missChunks;

    public UploadVo(Set<Integer> missChunks) {
        this.missChunks = missChunks;
        this.uploadComplete = missChunks != null && missChunks.size() == 0;
    }

    public UploadVo() {}

    public UploadVo(String catalogId, Set<Integer> missChunks) {
        this.catalogId = catalogId;
        this.missChunks = missChunks;
        this.uploadComplete = missChunks.size() == 0;
    }

    public String getCatalogId() {
        return catalogId;
    }

    public void setCatalogId(String catalogId) {
        this.catalogId = catalogId;
    }

    public boolean isUploadComplete() {
        return uploadComplete;
    }

    public void setUploadComplete(boolean uploadComplete) {
        this.uploadComplete = uploadComplete;
    }

    public Set<Integer> getMissChunks() {
        return missChunks;
    }

    public void setMissChunks(Set<Integer> missChunks) {
        this.missChunks = missChunks;
    }
}
