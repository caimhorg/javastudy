package com.javastudy.common.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.javastudy.contants.CommonContants;
import com.javastudy.entity.User;

@Controller
@RequestMapping("/base")
public class BaseController {

	public User getCurrentUser(HttpServletRequest request) {
		
		return (User)request.getSession().getAttribute(CommonContants.USER);
	}
}
