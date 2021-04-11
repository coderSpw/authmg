package com.spw.authmg.configuration.datasource;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @Description druid属性配置
 * @Author spw
 * @Date 2021/03/28
 */
@Data
@ConfigurationProperties(prefix = "spring.datasource.druid")
public class DruidDataSourceProperties {
	private int initialSize;
	private int maxActive = 30;
	private int minIdle;
	private long maxWait;
	private long timeBetweenEvictionRunsMillis;
	private long minEvictableIdleTimeMillis;
	private String validationQuery;
}