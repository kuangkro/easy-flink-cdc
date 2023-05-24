package com.esflink.starter.config.parser;

import com.esflink.starter.config.FlinkListenerProperties;
import org.springframework.core.io.Resource;

import java.util.List;

/**
 * 获取配置文件
 *
 * @author zhouhongyin
 * @since 2023/5/23 16:48
 */
public interface FlinkListenerPropertiesParser {

    List<FlinkListenerProperties> getProperties(Resource resource);

    String getName();
}