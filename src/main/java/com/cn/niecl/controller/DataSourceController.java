package com.cn.niecl.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.cn.niecl.dto.DataSourceProperties;
import com.cn.niecl.util.SpringContextUtil;

@RestController
public class DataSourceController {

	@Autowired
	DataSourceProperties dataSourceProperties;

	@Resource(name = "myDataSource")
	private DataSource myDataSource;

	@RequestMapping("/data")
	public String hello() {
		return this.contextLoads();
	}

	public String contextLoads() {
		// 方式1：
		// 获取配置的数据源
		DataSource dataSource = SpringContextUtil.getBean(DataSource.class);
		// 查看配置数据源信息
		System.out.println(dataSource);
		System.out.println(dataSource.getClass().getName());
		System.out.println(dataSourceProperties);
		// 执行SQL,输出查到的数据
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		List<?> resultList = jdbcTemplate.queryForList("select * from CS_SBF_SB_YWGZJY");
		System.out.println("===>>>>>>>>>>>" + JSON.toJSONString(resultList));
		// 方式2：
		// 执行SQL,输出查到的数据
		JdbcTemplate jdbcTemplate1 = new JdbcTemplate(myDataSource);
		List<?> resultList1 = jdbcTemplate1.queryForList("select * from CS_SBF_SB_YWGZJY");
		System.out.println("===>>>>>>>>>>>" + JSON.toJSONString(resultList1));
		return "成功";
	}
}
