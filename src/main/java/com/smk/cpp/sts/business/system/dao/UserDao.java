package com.smk.cpp.sts.business.system.dao;

import com.smk.cpp.sts.base.model.UserInfoModel;
import com.smk.cpp.sts.business.system.entity.UserEntity;
import com.smk.cpp.sts.business.system.vo.UserVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Description:
 * @Author: Jin-LiangBo
 * @Date: 2022/4/8 18:38
 */
@Mapper
public interface UserDao {

    /**
     * 通过用户名获取用户
     * @param username 用户账号
     * @return com.smk.cpp.sts.business.system.entity.UserEntity
     */
    UserEntity getUserByUserName(String username);
    
    /**
     * 获取满足条件的用户数量
     * @param user UserEntity
     * @return int
     */
    int getUserTotalCount(@Param("user") UserEntity user);
    
    /**
     * 获取用户分页数据
     * @param user UserEntity
     * @param startIndex 开始索引
     * @param offset 偏移量
     * @return java.util.List<com.smk.cpp.sts.business.system.vo.UserVo>
     */
    List<UserVo> getUserPageList(@Param("user") UserEntity user,
         int startIndex, int offset);
    
    /**
     * 新增用户
     * @param user UserEntity
     * @return int
     */
    int saveUser(UserEntity user);
    
    /**
     * 根据用户id更新用户
     * @param user UserEntity
     * @return int
     */
    int updateUserByUserId(UserEntity user);
    
    /**
     * 根据用户id删除用户
     * @param userId String
     * @return int
     */
    int deleteUserByUserId(String userId);

    /**
     * 根据用户id删除关联的角色信息
     * @param userId String
     */
    void deleteRelationRoleUserByUserId(String userId);
    
    /**
     * 根据用户id查询用户信息，不带敏感信息的.
     * @param userId String
     * @return com.smk.cpp.sts.base.model.UserInfoModel
     */
    UserInfoModel getUserInfoByUserId(String userId);

}
