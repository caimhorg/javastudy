package com.javastudy.devlog.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 开发日志控制器
 * 
 * @author cmh
 *
 */
@Controller
@RequestMapping("/devlog")
public class DevLogController {
	
	
	/**
	 * 首页
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/index")
	public String index(HttpServletRequest request, HttpServletResponse response) {
		
		return "/devlog/devlogManage";
	}
	
	

}
