package com.smk.cpp.sts.business.system.service;

import com.smk.cpp.sts.base.model.PageModel;
import com.smk.cpp.sts.base.model.UserDetailsModel;
import com.smk.cpp.sts.base.model.UserInfoModel;
import com.smk.cpp.sts.business.system.entity.UserEntity;
import com.smk.cpp.sts.business.system.vo.UserVo;
import org.springframework.security.core.userdetails.UserDetailsPasswordService;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * @Description:
 * @Author: Jin-LiangBo
 * @Date: 2022/4/10 16:34
 */
public interface UserService extends UserDetailsService, UserDetailsPasswordService {
    
    /**
     * 根据手机号获取用户信息
     * @param phone 手机号
     * @return com.smk.cpp.sts.base.model.UserDetailsModel
     * @throws UsernameNotFoundException
     */
    UserDetailsModel loadUserByPhone(String phone) throws UsernameNotFoundException;
    
    /**
     * 分页获取用户信息
     * @param pageSize 页数大小
     * @param currentPageNo 页数
     * @param user 筛选条件
     * @return com.smk.cpp.sts.base.model.PageModel<com.smk.cpp.sts.business.system.vo.UserVo>
     */
    PageModel<UserVo> getUserPageList(int pageSize, int currentPageNo, UserEntity user);
    
    /**
     * 新增用户
     * @param user 用户信息
     */
    void saveUser(UserEntity user);
    
    /**
     * 更新用户信息
     * @param user 用户信息
     */
    void updateUser(UserEntity user);
    
    /**
     * 删除用户
     * @param userId 用户id
     */
    void deleteUserByUserId(String userId);
    
    /**
     * 通过用户id获取用户信息
     * @param userId 用户id
     * @return com.smk.cpp.sts.base.model.UserInfoModel
     */
    UserInfoModel getUserInfoByUserId(String userId);
    
}
