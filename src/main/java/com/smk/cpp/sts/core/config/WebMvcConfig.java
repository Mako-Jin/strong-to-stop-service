/***********************************************************************/
/**         Copyright (C) 2020-2030 西安三码客软件科技有限公司             */
/**                      All rights reserved                           */
/***********************************************************************/

package com.smk.cpp.sts.core.config;

import com.smk.cpp.sts.core.filter.ExceptionFilter;
import com.smk.cpp.sts.core.resolver.SmkLocaleResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 功能描述：
 *
 * @ClassName: WebMvcConfig
 * @Author: Mr.Jin-晋
 * @Date: 2021-02-10 22:59
 * @Version: V-0.0.1
 * @Description: TODO
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    private static final Logger logger = LoggerFactory.getLogger(WebMvcConfig.class);

    /**
     * 静态资源映射
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
    }

    /**
     * 配置自己的国际化语言解析器
     * @return SmkLocaleResolver
     */
    @Bean
    public LocaleResolver localeResolver() {
        logger.debug("i18n language setting ===>>> LocaleResolver init success!");
        return new SmkLocaleResolver();
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOriginPatterns("*")
                .allowedMethods("*")
                .allowCredentials(true)
                .maxAge(3600)
                .allowedHeaders("*");
    }

    // @Bean
    // public CorsFilter corsFilter() {
    //     final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    //     final CorsConfiguration config = new CorsConfiguration();
    //     config.setAllowCredentials(true); // 允许cookies跨域
    //     config.addAllowedOrigin("*");// #允许向该服务器提交请求的URI，*表示全部允许
    //     config.addAllowedHeader("*");// #允许访问的头信息,*表示全部
    //     config.setMaxAge(18000L);// 预检请求的缓存时间（秒），即在这个时间段里，对于相同的跨域请求不会再预检了
    //     config.addAllowedMethod("*");// 允许提交请求的方法，*表示全部允许
    //     source.registerCorsConfiguration("/**", config);
    //     return new CorsFilter(source);
    // }

}
