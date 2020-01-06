package com.cn.niecl.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cn.niecl.mail.MailService;

@RestController
@RequestMapping("/mail")
public class MailController {
	
	@Autowired
	private MailService mailServiceImpl;

	@RequestMapping("/send")
	public String send() {
		String to = "*****@qq.com";
		String subject = "helloWorld";
		String content = "helloWorld";
		mailServiceImpl.send(to, subject, content);
		return "成功";
	}
}
