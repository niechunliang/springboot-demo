package com.cn.niecl.dto;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

@Component
@Data
@ConfigurationProperties(prefix="datasource")
public class DataSourceProperties {
	
	private String url;
	
	private String username;
	
	private String password;
	
	private String driverClassName;
	
	private String initialSize;

	private String minIdle;

	private String maxActive;

	private String maxWait;

	private String timeBetweenEvictionRunsMillis;

	private String minEvictableIdleTimeMillis;

	private String validationQuery;

	private String testWhileIdle;

	private String testOnBorrow;

	private String testOnReturn;

	private String poolPreparedStatements;

	private String maxPoolPreparedStatementPerConnectionSize;

	private String filters;

	private String connectionProperties;


}
