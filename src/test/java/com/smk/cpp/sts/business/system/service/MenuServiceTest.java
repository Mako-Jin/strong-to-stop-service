package com.smk.cpp.sts.business.system.service;

import com.alibaba.fastjson.JSONObject;
import com.smk.cpp.sts.base.exception.ServiceException;
import com.smk.cpp.sts.business.system.dao.MenuDao;
import com.smk.cpp.sts.business.system.entity.MenuEntity;
import com.smk.cpp.sts.business.system.service.impl.MenuServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

/**
 * TODO
 *
 * @version 1.0
 * @Author: Jin-LiangBo
 * @Date: 2022年02月08日 18:04
 * @Description:
 */
@DisplayName("菜单管理服务层测试")
public class MenuServiceTest {
    
    private static final Logger logger = LoggerFactory.getLogger(MenuServiceTest.class);

    @InjectMocks
    private MenuServiceImpl menuService;
    
    @Mock
    private MenuDao menuDao;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    
    @Test
    @DisplayName("新增菜单")
    public void testSaveMenu() {
        String testParam = "{\n" +
                "    \"menuName\": \"测试菜单\",\n" +
                "    \"menuUrl\": \"/test/v1/getTest\",\n" +
                "    \"menuDesc\": \"测试地址\",\n" +
                "    \"sort\": 1,\n" +
                "    \"isSecret\": 2,\n" +
                "    \"menuType\": 3,\n" +
                "    \"menuLevel\": 1\n" +
                "}";
        MenuEntity menuEntity = JSONObject.parseObject(testParam, MenuEntity.class);

        Mockito.when(menuDao.saveMenu(menuEntity)).thenReturn(1);
        menuService.saveMenu(menuEntity);
        Mockito.verify(menuDao).saveMenu(menuEntity);

        // 保存失败后抛出serviceException
        Mockito.when(menuDao.saveMenu(menuEntity)).thenReturn(0);
        // 异常后断言  
        try {
            menuService.saveMenu(menuEntity);
        } catch (ServiceException serviceException) {
            assertThat(serviceException.getMsg(), equalTo("data.save.failed"));
        }

        Mockito.verify(menuDao).saveMenu(menuEntity);
        
    }
    
}
