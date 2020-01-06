package com.cn.niecl.dto;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

@Component
@Data
@ConfigurationProperties(prefix="user11")
public class UserConfig {
	
	private String name;
	
	private String sex;
	
	private long age;

}
