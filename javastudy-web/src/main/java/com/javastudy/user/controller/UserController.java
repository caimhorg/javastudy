package com.javastudy.user.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.javastudy.dto.UserDto;
import com.javastudy.entity.User;
import com.javastudy.hibernate.dao.StudentDao;
import com.javastudy.service.IUserService;
import com.javastudy.system.BaseDaoForRedis;
import com.javastudy.system.InteractiveWithAjax;
import com.javastudy.utils.AjaxResult;
import com.javastudy.utils.DataPage;

/**
 * 用户控制器
 * 
 * @author cmh
 *
 */
@Controller
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private IUserService userService;
	
	@Autowired
	private StudentDao studentDao;
	
//	@Autowired
//	private BaseDaoForRedis redis;
	
	/**
	 * 用户首页
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/index")
	public String index(HttpServletRequest request, HttpServletResponse response) {
		
//		Student stu = new Student("1", "蔡敏浩", 27, "男");
		//mongodb
//		studentDao.save(stu);
		//redis
//		redis.setForString("myname", "caimh");
//		redis.setForHash("myhash", "123", "789");
		//fastjson
//		String jsonstr = JSON.toJSONString(stu);
//		redis.setForString("myname", jsonstr);
//		String str = redis.getForString("myname");
//		Student s = JSON.parseObject(str, Student.class);
		//ES
//		Properties prop = PropertiesUtils.getProperties("system.properties");
//		String dir = prop.getProperty("lucene_index_address");
//		IndexWriter indexWriter = null;
//		try {
//			File indexDir = new File(dir);
//			if (!indexDir.exists()) {
//				indexDir.mkdir();
//			}
//			// 索引位置
//			Directory directory = FSDirectory.open(indexDir.toPath());
//			// 分词器,将文档内容切词
//			Analyzer analyzer = new StandardAnalyzer();
//			// 创建索引的配置信息
//			IndexWriterConfig indexWriterConfig = new IndexWriterConfig(analyzer);
//			// 创建写索引核心对象
//			indexWriter = new IndexWriter(directory, indexWriterConfig);
//			// 创建文档
//			Document document = new Document();
//			// 标题
//			document.add(new TextField("title", "lucene简介", Store.YES));
//			Reader reader = new FileReader(new File("G:/abc.txt"));
//			// 内容
//			document.add(new TextField("content", reader));
//			indexWriter.addDocument(document);
//			indexWriter.commit();
//		} catch (IOException e) {
//			e.printStackTrace();
//		} finally {
//			if (indexWriter != null) {
//				try {
//					indexWriter.close();
//				} catch (IOException e) {
//					e.printStackTrace();
//				}
//			}
//		}
		return "/user/userManage";
	}
	
	/**
	 * 用户注册页面
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/userRegisterIndex")
	public String userRegisterIndex(HttpServletRequest request, HttpServletResponse response) {
		
		return "/user/userRegister";
	}
	
	/**
	 * 分页用户
	 * @param currentPage
	 * @param pageSize
	 * @param request
	 * @return
	 */
	@RequestMapping("/findPageUsers")
	public void findPageUsers(Integer currentPage, Integer pageSize, String userName, String idCard,
			HttpServletRequest request, HttpServletResponse response) {
		
		JSONObject jo = new JSONObject();
		try {
			DataPage<User> dataPage = userService.findPageUsers(currentPage, pageSize, userName, idCard);
			jo.put("data", dataPage.getDatas());
			jo.put("code", 0);
			jo.put("msg", "获取成功");
			jo.put("count", dataPage.getTotalRecords());
		} catch (Exception e) {
			e.printStackTrace();
			jo.put("msg", "内部异常");
		}
		InteractiveWithAjax.printWriter(jo, response);
	}
	
	/**
	 * 用户注册
	 * @param request
	 * @param response
	 */
	@RequestMapping("/userRegister")
	public void userRegister(UserDto userdto, HttpServletRequest request, HttpServletResponse response) {
		
		JSONObject jo = new JSONObject();
		try {
			//判断信息是否存在
			jo = userService.checkSame(userdto);
			if (jo.getBoolean("result")) {
				//注册
				if (userService.userRegister(userdto)) {
					jo.put("msg", "注册成功");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			jo.put("result", false);
			jo.put("msg", "内部异常");
		}
		InteractiveWithAjax.printWriter(jo, response);
	}
	
	/**
	 * 冻结/解冻用户
	 * @param userId
	 * @param userStatus 用户原始状态
	 * @param request
	 * @param response
	 */
	@RequestMapping("/{userId}/{userStatus}/freeze")
	@ResponseBody
	public AjaxResult freeze(@PathVariable String userId, @PathVariable Integer userStatus, HttpServletRequest request, HttpServletResponse response) {
		AjaxResult ajaxResult = new AjaxResult(true);
		try {
			userService.freeze(userId, userStatus);
			if (userStatus == 0) {
				ajaxResult.setMsg("用户已冻结");
			} else if (userStatus == 1) {
				ajaxResult.setMsg("用户已解冻");
			}
		} catch (Exception e) {
			ajaxResult.setSuccess(false);
			ajaxResult.setMsg("内部异常");
		}
		return ajaxResult;
	}
	
	/**
	 * 用户删除
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/userDelete")
	@ResponseBody
	public AjaxResult userDelete(HttpServletRequest request, HttpServletResponse response) {
		AjaxResult ajaxResult = new AjaxResult(true);
		try {
			String[] ids = request.getParameterValues("ids[]");
			userService.userDelete(ids);
			ajaxResult.setMsg("删除成功");
		} catch (Exception e) {
			ajaxResult.setSuccess(false);
			ajaxResult.setMsg("内部异常");
		}
		return ajaxResult;
	}
	
	/**
	 * 用户详细页面
	 * @param userId
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/{userId}/userInfoIndex")
	@ResponseBody
	public ModelAndView userInfoIndex(@PathVariable String userId, HttpServletRequest request, HttpServletResponse response) {
		JSONObject jo = new JSONObject();
		try {
			User user = userService.getUserInfo(userId);
			jo.put("user", user);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ModelAndView("/user/userInfo", jo);
	}

}
