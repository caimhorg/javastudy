package com.javastudy.common.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {

	@RequestMapping("/index")
	public String indexLogin(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		return "/login/login";
	}
}
