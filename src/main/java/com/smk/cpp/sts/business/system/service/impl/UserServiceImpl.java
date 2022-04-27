package com.smk.cpp.sts.business.system.service.impl;

import com.smk.cpp.sts.base.constant.IConstants;
import com.smk.cpp.sts.base.enums.ResultStatusEnums;
import com.smk.cpp.sts.base.exception.ServiceException;
import com.smk.cpp.sts.base.model.AuthorityModel;
import com.smk.cpp.sts.base.model.PageModel;
import com.smk.cpp.sts.base.model.UserDetailsModel;
import com.smk.cpp.sts.base.model.UserInfoModel;
import com.smk.cpp.sts.business.system.dao.RoleDao;
import com.smk.cpp.sts.business.system.dao.UserDao;
import com.smk.cpp.sts.business.system.entity.UserEntity;
import com.smk.cpp.sts.business.system.service.UserService;
import com.smk.cpp.sts.business.system.vo.UserVo;
import com.smk.cpp.sts.common.util.PageUtils;
import com.smk.cpp.sts.common.util.StringUtil;
import com.smk.cpp.sts.core.crypto.PBKDF2PasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Set;

/**
 * @Description:
 * @Author: Jin-LiangBo
 * @Date: 2022/4/10 16:33
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private RoleDao roleDao;

    @Override
    public UserDetailsModel loadUserByUsername(String username) throws UsernameNotFoundException {
        if (!StringUtils.hasText(username)) {
            throw new UsernameNotFoundException(ResultStatusEnums.COMMON_PARAM_CHECK_ERROR.getMessage());
        }
        UserEntity user = userDao.getUserByUserName(username);
        if ( null == user ) {
            throw new UsernameNotFoundException(ResultStatusEnums.USER_IS_NOT_EXISTED.getMessage());
        }
        Set<AuthorityModel> roleList = roleDao.getRoleByUserId(user.getUserId());
        return new UserDetailsModel(user.getUserId(), username, user.getUserPwd(), roleList);
    }

    @Override
    public UserDetailsModel loadUserByPhone(String username) throws UsernameNotFoundException {
        UserDetailsModel userDetailsModel = new UserDetailsModel();
        userDetailsModel.setUsername("13259720691");
        userDetailsModel.setPassword("515322");
        return userDetailsModel;
    }

    @Override
    public PageModel<UserVo> getUserPageList(int pageSize, int currentPageNo, UserEntity user) {
        int count = userDao.getUserTotalCount(user);
        if (count < 1) {
            return new PageModel<>();
        }
        currentPageNo = PageUtils.getPageNum(currentPageNo, pageSize, count);
        int pageStartIndex = PageUtils.getPageStartIndex(currentPageNo, pageSize);
        List<UserVo> userList = userDao.getUserPageList(user, pageStartIndex, pageSize);
        userList.stream().forEach(userVo ->
            userVo.setPhoneNum(encryptPhone(userVo.getPhoneNum()))
        );
        return new PageModel<>(count, pageSize, currentPageNo, userList);
    }

    @Override
    public void saveUser(UserEntity user) {
        user.setUserId(StringUtil.idGenerate());
        // 账号不能重复校验
        int usernameCount = userDao.getUserTotalCount(user);
        if (usernameCount > 0) {
            throw new ServiceException(ResultStatusEnums.USERNAME_PHONE_DUPLICATION);
        }
        PBKDF2PasswordEncoder encoder = new PBKDF2PasswordEncoder();
        // 生成密码
        String userPwd = encoder.encode(IConstants.DEFAULT_PASSWORD);
        user.setUserPwd(userPwd);
        int resultCount = userDao.saveUser(user);
        if (resultCount < 1) {
            throw new ServiceException(ResultStatusEnums.COMMON_DATA_SAVE_FAILED);
        }
    }

    @Override
    public void updateUser(UserEntity user) {
        if (!StringUtils.hasText(user.getUserId())) {
            throw new ServiceException(ResultStatusEnums.COMMON_ID_CANNOT_NULL);
        }
        int resultCount = userDao.updateUserByUserId(user);
        if (resultCount < 1) {
            throw new ServiceException(ResultStatusEnums.COMMON_DATA_UPDATE_FAILED);
        }
    }

    @Override
    public void deleteUserByUserId(String userId) {
        if (!StringUtils.hasText(userId)) {
            throw new ServiceException(ResultStatusEnums.COMMON_ID_CANNOT_NULL);
        }
        int resultCount = userDao.deleteUserByUserId(userId);
        if (resultCount < 1) {
            throw new ServiceException(ResultStatusEnums.COMMON_DATA_DELETE_FAILED);
        }
        userDao.deleteRelationRoleUserByUserId(userId);
    }

    @Override
    public UserInfoModel getUserInfoByUserId(String userId) {
        UserInfoModel userInfo = userDao.getUserInfoByUserId(userId);
        Set<AuthorityModel> roles = roleDao.getRoleByUserId(userId);
        userInfo.setRoles(roles);
        return userInfo;
    }

    @Override
    public UserDetailsModel updatePassword(UserDetails user, String newPassword) {
        return null;
    }
    
    private String encryptPhone(String phoneNum) {
        if (!StringUtils.hasText(phoneNum)) {
            return phoneNum;
        }
        return phoneNum.replaceAll("(\\d{3})\\d{4}(\\d{4})","$1****$2");
    }
}
