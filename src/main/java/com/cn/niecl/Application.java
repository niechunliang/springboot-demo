package com.cn.niecl;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.PropertySource;

import com.cn.niecl.dto.UserConfig1;
import com.cn.niecl.util.YamlPropertySourceFactory;

@SpringBootApplication
// 读取bean配置文件
@ImportResource(locations = { "classpath:/beans-context.xml" })
// 读取自定义properties配置文件
@PropertySource(value = { "classpath:redis/redis.properties", "classpath:user.properties","classpath:data/datasource.properties",
		"classpath:data/jpa.properties","classpath:mail.properties" }, encoding = "utf-8")
// @PropertySource 不支持yml文件的对象转换,默认实现PropertySourceFactory
// 读取自定义yml配置文件
//@PropertySource(value = { "classpath:data/datasource.yml",
//		"classpath:data/jpa.yml" }, factory = YamlPropertySourceFactory.class, encoding = "utf-8")
public class Application {
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Bean
	@ConfigurationProperties(prefix = "user11")
	public UserConfig1 getUserConfig() {
		System.out.println();
		return new UserConfig1();
	}
}
