package com.smk.cpp.sts.business.file.entity;

import java.util.Date;

/**
 * TODO
 *
 * @version 1.0
 * @Author: Jin-LiangBo
 * @Date: 2022年03月21日 17:34
 * @Description:
 */
public class StorageEntity {
    
    private String storageId;
    
    private String userId;

    /**
     * 剩下的空间
     */
    private Long remainStorageSize;
    
    /**
     * 已经使用的空间
     */
    private Long usedStorageSize;

    /**
     * 总空间大小
     */
    private Long totalStorageSize;

    /**
     * 升级的空间大小
     */
    private Long upgradeStorageSize;
    
    /**
     * 是否已释放 1：是；2：否
     */
    private int isDelete;

    private String createUser;

    private Date createTime;

    private String updateUser;

    private Date updateTime;

    public String getStorageId() {
        return storageId;
    }

    public void setStorageId(String storageId) {
        this.storageId = storageId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Long getRemainStorageSize() {
        return remainStorageSize;
    }

    public void setRemainStorageSize(Long remainStorageSize) {
        this.remainStorageSize = remainStorageSize;
    }

    public Long getUsedStorageSize() {
        return usedStorageSize;
    }

    public void setUsedStorageSize(Long usedStorageSize) {
        this.usedStorageSize = usedStorageSize;
    }

    public Long getTotalStorageSize() {
        return totalStorageSize;
    }

    public void setTotalStorageSize(Long totalStorageSize) {
        this.totalStorageSize = totalStorageSize;
    }

    public Long getUpgradeStorageSize() {
        return upgradeStorageSize;
    }

    public void setUpgradeStorageSize(Long upgradeStorageSize) {
        this.upgradeStorageSize = upgradeStorageSize;
    }

    public int getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(int isDelete) {
        this.isDelete = isDelete;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
