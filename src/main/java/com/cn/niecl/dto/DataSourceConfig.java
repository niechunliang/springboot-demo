package com.cn.niecl.dto;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataSourceConfig {

	@Bean(name = "myDataSource")
	@Qualifier("myDataSource")
	@ConfigurationProperties(prefix = "datasource")
	public DataSource getMyDataSource() {
		return DataSourceBuilder.create().build();
	}

}
