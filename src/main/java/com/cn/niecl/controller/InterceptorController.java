package com.cn.niecl.controller;

import com.cn.niecl.aspect.SysLog;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class InterceptorController {

	@RequestMapping(value="/interceptor", method=RequestMethod.POST)
	@SysLog
	public String hello() {
		return "index";
	}
}
