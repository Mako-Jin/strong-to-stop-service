package com.smk.cpp.sts.business.system.controller;

import com.smk.cpp.sts.business.system.service.impl.MenuServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * TODO
 *
 * @version 1.0
 * @Author: Jin-LiangBo
 * @Date: 2022年02月08日 15:33
 * @Description:
 */
@DisplayName("菜单管理控制层测试")
public class MenuControllerTest {
    
    private static final Logger logger = LoggerFactory.getLogger(MenuControllerTest.class);

    private MockMvc mockMvc;

    @Mock  // 要mock被测类中依赖的对象使用@Mock注解
    private MenuServiceImpl menuService;

    @InjectMocks  // 被测类本身使用@InjectMocks注解
    private MenuController menuController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        // 构建mvc环境
        mockMvc = MockMvcBuilders.standaloneSetup(menuController).build();
    }

    @Test
    @DisplayName("新增单个菜单 - 正确调用")
    public void testSaveMenu() throws Exception {
        String testParam = "{\n" +
                "    \"menuName\": \"测试菜单\",\n" +
                "    \"menuUrl\": \"/test/v1/getTest\",\n" +
                "    \"menuDesc\": \"测试地址\",\n" +
                "    \"sort\": 1,\n" +
                "    \"isSecret\": 2,\n" +
                "    \"menuType\": 3,\n" +
                "    \"menuLevel\": 1\n" +
                "}";
        // 正常调用
        // when(menuService.saveMenu(any(MenuEntity.class)));
        MvcResult mvcResult = mockMvc.perform(post("/menuMgr/v1/saveMenu")
                .accept(MediaType.APPLICATION_JSON)
                //传参,因为后端是@RequestBody所以这里直接传json字符串
                .content(testParam)
                // 请求type : json
                .contentType(MediaType.APPLICATION_JSON))
                // 期望的结果状态 200
                .andExpect(status().isOk())
                .andExpect(content().json(
                    "{\"code\":32000,\"msg\":\"请求成功。\"," 
                        + "\"data\":null,\"success\":true}")
                )
                .andDo(print())
                .andReturn();//返回结果
        logger.info("success call result is {}", mvcResult);
    }
    
}
