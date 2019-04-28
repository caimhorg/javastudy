package com.javastudy.books.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 书籍信息控制器
 * 
 * @author cmh
 *
 */
@Controller
@RequestMapping("/books")
public class BooksController {
	
	/**
	 * 书籍首页
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/index")
	public String index(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		return "/books/booksManage";
	}
	
	

}
