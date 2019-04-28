package com.javastudy.common.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSONObject;
import com.javastudy.contants.CommonContants;
import com.javastudy.entity.User;
import com.javastudy.service.IRoleService;
import com.javastudy.service.IUserService;
import com.javastudy.system.InteractiveWithAjax;

/**
 * 登录相关接口类
 * 
 * @author cmh
 *
 */
@Controller
@RequestMapping("/login")
public class LoginController {
	
	@Autowired
	private IUserService userService;
	
	@Autowired
	private IRoleService roleService;
	
	@RequestMapping("/index")
	public String indexLogin(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		return "/login/index";
	}
	
	/**
	 * 登录前的验证操作
	 * @param userName
	 * @param password
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/loginBefore")
	public void loginBefore(String userName, String password, 
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		JSONObject jo = new JSONObject();
		jo.put("result", true);
		try {
			jo = userService.loginBefore(userName, password);
		} catch (Exception e) {
			e.printStackTrace();
			jo.put("result", false);
			jo.put("msg", "内部异常");
		}
		InteractiveWithAjax.printWriter(jo, response);
	}
	
	/**
	 * 登录接口
	 * @param userName
	 * @param password
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/login")
	public String login(String userName, String password, 
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			User user = userService.findLoginUser(userName, password);
			request.getSession().setAttribute(CommonContants.USER, user);
			request.getSession().setAttribute(CommonContants.USER_ID, user.getId());
			//获取用户的menuIds
			List<String> userMenuIds = roleService.getUserMenuIds(user.getId());
			request.getSession().setAttribute(CommonContants.USER_MENU_IDS, userMenuIds);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "/login/manage";
	}
	

}
