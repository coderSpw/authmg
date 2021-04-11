package com.spw.authmg.backup.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @Description 备份数据源
 * @Author spw
 * @Date 2021/4/5
 */
@Data
@Component
@ConfigurationProperties(prefix = "authmg.backup.datasource")
public class DataSourceProperties {
    private String host;
    private String username;
    private String password;
}
