package com.cn.niecl.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.cn.niecl.dto.SbYwgzJyDTO;
import com.cn.niecl.service.RedisService;

@RestController
public class RedisController {

	@Autowired
	private RedisService redisService;

	/**
	 * 队列推消息
	 */
	@RequestMapping("/saveQueue")
	public String saveQueue() {
		SbYwgzJyDTO msgBody = new SbYwgzJyDTO();
		msgBody.setSwjgDm("helloWorld");
		msgBody.setQqcs("描述");
		redisService.saveQueue("A", JSONObject.toJSONString(msgBody));
		return "success";
	}
}
