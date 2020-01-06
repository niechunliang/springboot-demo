package com.cn.niecl.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cn.niecl.common.redis.JedisUtil;
import com.cn.niecl.dto.UserConfig;
import com.cn.niecl.dto.UserConfig1;
import com.cn.niecl.util.SpringContextUtil;

@RestController
@RequestMapping("/properties")
public class PropertiesController {
//	@value 两种使用
//  ${key}
	@Value("${redis.hostname}")
	private String hostname;
//  #{redisProperties['key']}
	@Value("#{redisProperties['redis.port']}")
	private String port;
//@ConfigurationProperties 将配置文件的信息，读取并自动封装成实体类
	@Autowired
	private UserConfig userConfig;
//@ConfigurationProperties直接定义在@bean的注解上,参考Application启动类
	@Autowired
	private UserConfig1 userConfig1;
	
	@Autowired
	private JedisUtil.Strings stringsUtil;
	
	@RequestMapping("/redis")
	public String redis() {
		return hostname +":" + port;
	}
	
	@RequestMapping("/redis/1")
	public String getValue() {
		stringsUtil.set("1", "1");
		return stringsUtil.get("1");
	}
	
	@RequestMapping("/user")
	public String getName() {
		return userConfig.getName();
	}
	
	@RequestMapping("/user1")
	public String getUser1() {
		return userConfig1.getName();
	}
	
	@RequestMapping("/user2")
	public String getUser2() {
		UserConfig1 user1 = SpringContextUtil.getBean(UserConfig1.class);
		return user1.getName();
	}
}
