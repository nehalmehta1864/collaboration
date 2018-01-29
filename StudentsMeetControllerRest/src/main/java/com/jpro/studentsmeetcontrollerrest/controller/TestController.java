package com.jpro.studentsmeetcontrollerrest.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

	@RequestMapping("/test")
public String getMesseget(){
		return "hi restcontroller";
		
	}
}
