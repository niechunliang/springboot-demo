package com.cn.niecl.controller;

import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cn.niecl.cmp.SbYwgzJyCMP;
import com.cn.niecl.repository.SbYwgzRepository;

@RestController
public class DataJpaController {

	@Autowired
	private SbYwgzRepository sbYwgzRepository;
//  http://localhost:8080/dataJpa/1
	@RequestMapping("/dataJpa/{id}")
	public String hello(@PathVariable("id") String id) {
		SbYwgzJyCMP cmp = sbYwgzRepository.findOne(id);
		return cmp.getGzDm();
	}
	
//	http://localhost:8080/dataJpa?id=1
	@RequestMapping("/dataJpa")
	public String hello1(@PathParam(value="id") String id) {
		SbYwgzJyCMP cmp = sbYwgzRepository.findOne(id);
		return cmp.getGzDm();
	}
}
