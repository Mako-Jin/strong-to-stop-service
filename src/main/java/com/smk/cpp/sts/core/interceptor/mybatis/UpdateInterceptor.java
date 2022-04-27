package com.smk.cpp.sts.core.interceptor.mybatis;

import com.smk.cpp.sts.common.util.SecurityUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;

/**
 * TODO
 *
 * @version 1.0
 * @Author: Jin-LiangBo
 * @Date: 2022年02月07日 11:25
 * @Description:
 */
@Intercepts({
    @Signature(
        type = Executor.class, method = "update",
        args = {MappedStatement.class, Object.class}
    )
})
@Component
public class UpdateInterceptor implements Interceptor {
    
    private static final Logger logger = LoggerFactory.getLogger(UpdateInterceptor.class);
    
    private static final String CREATE_TIME = "createTime";
    private static final String UPDATE_TIME = "updateTime";
    private static final String CREATE_USER = "createUser";
    private static final String UPDATE_USER = "updateUser";
    
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        MappedStatement mappedStatement = (MappedStatement) invocation.getArgs()[0];
        Object parameter = invocation.getArgs()[1];
        String currentUserId = SecurityUtils.getCurrentUserId();
        SqlCommandType sqlCommandType = mappedStatement.getSqlCommandType();
        if (parameter instanceof HashMap) {
            Map<String, Object> map = (HashMap)parameter;
            if (map.containsKey("list")) {
                List<Object> list = (ArrayList<Object>)map.get("list");
                for (int i = 0; i < list.size(); i++) {
                    fillUserAndTime(sqlCommandType, list.get(i), currentUserId);
                }
            }
        } else {
            fillUserAndTime(sqlCommandType, parameter, currentUserId);
        }
        return invocation.proceed();
    }

    private void fillUserAndTime(SqlCommandType sqlCommandType, Object parameter, String userId) 
            throws NoSuchFieldException, IllegalAccessException {
        Field[] fields = parameter.getClass().getDeclaredFields();
        if(Objects.nonNull(parameter.getClass().getSuperclass())){
            Field[] superFields = parameter.getClass().getSuperclass().getDeclaredFields();
            fields = ArrayUtils.addAll(fields,superFields);
        }
        if (sqlCommandType == SqlCommandType.INSERT) {
            for (Field field : fields) {
                switch (field.getName()) {
                    case CREATE_USER:
                        setCreateUser(parameter, userId);
                        break;
                    case CREATE_TIME:
                        setCreateTime(parameter);
                        break;
                    case UPDATE_USER:
                        setUpdateUser(parameter, userId);
                        break;
                    case UPDATE_TIME:
                        setUpdateTime(parameter);
                        break;
                    default:
                        break;
                }
            }
        } else if (sqlCommandType == SqlCommandType.UPDATE) {
            for (Field field : fields) {
                if (UPDATE_USER.equals(field.getName())) {
                    setUpdateUser(parameter, userId);
                } else if (UPDATE_TIME.equals(field.getName())) {
                    setUpdateTime(parameter);
                }
            }
        }
    }

    private void setCreateUser(Object parameter, String currentUserId)
            throws IllegalAccessException, NoSuchFieldException {
        Field fieldCreateUser = parameter.getClass().getDeclaredField(CREATE_USER);
        fieldCreateUser.setAccessible(true);
        fieldCreateUser.set(parameter, currentUserId);
    }

    private void setCreateTime(Object parameter)
            throws IllegalAccessException, NoSuchFieldException {
        Field fieldCreateTime = parameter.getClass().getDeclaredField(CREATE_TIME);
        fieldCreateTime.setAccessible(true);
        fieldCreateTime.set(parameter, new Date());
    }

    private void setUpdateUser(Object parameter, String currentUserId)
            throws NoSuchFieldException, IllegalAccessException {
        Field fieldUpdateUser = parameter.getClass().getDeclaredField(UPDATE_USER);
        fieldUpdateUser.setAccessible(true);
        fieldUpdateUser.set(parameter, currentUserId);
    }

    private void setUpdateTime(Object parameter)
            throws NoSuchFieldException, IllegalAccessException {
        Field fieldUpdateTime = parameter.getClass().getDeclaredField(UPDATE_TIME);
        fieldUpdateTime.setAccessible(true);
        fieldUpdateTime.set(parameter, new Date());
    }
    
    @Override
    public Object plugin(Object target) {
        return Interceptor.super.plugin(target);
    }

    @Override
    public void setProperties(Properties properties) {
        Interceptor.super.setProperties(properties);
    }
    
}
