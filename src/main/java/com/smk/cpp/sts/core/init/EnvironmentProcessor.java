/***********************************************************************/
/**         Copyright (C) 2020-2030 西安三码客软件科技有限公司             */
/**                      All rights reserved                           */
/***********************************************************************/

package com.smk.cpp.sts.core.init;

import com.smk.cpp.sts.base.constant.FileConstants;
import com.smk.cpp.sts.common.util.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.PropertiesPropertySource;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.List;
import java.util.Properties;

/**
 * 功能描述：自定义环境处理，在运行SpringApplication之前加载任意配置文件到Environment环境中
 *
 * @ClassName: EnvironmentProcessor
 * @Author: Mr.Jin-晋
 * @Date: 2021-03-08 0:39
 * @Version: V-0.0.1
 * @Description: TODO
 */
public class EnvironmentProcessor implements EnvironmentPostProcessor {

    private static final Logger logger = LoggerFactory.getLogger(EnvironmentProcessor.class);

    /**
     * Properties对象
     */
    private final Properties properties = new Properties();

    @Override
    public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
        try {
            String configDirectory = ResourceUtils.getURL(
                    ResourceUtils.CLASSPATH_URL_PREFIX 
                            + FileConstants.SETTINGS_CONFIG_PATH)
                        .getPath();
            List<String> profiles = FileUtils.getTargetDirectoryAllFileName(configDirectory);
            for (String profile : profiles) {
                File file = new File(profile);
                Reader reader = new FileReader(file);
                properties.load(reader);
                //加载成PropertySource对象，并添加到Environment环境中
                PropertiesPropertySource propertiesPropertySource = 
                        new PropertiesPropertySource(file.getName(), properties);
                environment.getPropertySources().addLast(propertiesPropertySource);
            }
        }catch (IOException ex) {
            logger.error("load {} config file failed!", ex.getMessage());
        }
    }

}
