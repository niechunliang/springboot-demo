package com.cn.niecl.controller;

import com.cn.niecl.aspect.SysLog;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class IndexController {

	@RequestMapping(value="/index", method=RequestMethod.GET)
	@SysLog
	public String hello() {
		return "index";
	}
}
