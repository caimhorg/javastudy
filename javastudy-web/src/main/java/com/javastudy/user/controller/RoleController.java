package com.javastudy.user.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.javastudy.common.controller.BaseController;
import com.javastudy.entity.Menu;
import com.javastudy.entity.Role;
import com.javastudy.entity.RoleMenu;
import com.javastudy.entity.User;
import com.javastudy.service.IRoleService;
import com.javastudy.system.InteractiveWithAjax;
import com.javastudy.utils.AjaxResult;
import com.javastudy.utils.DataPage;

/**
 * 角色控制器
 * 
 * @author cmh
 * 
 *
 */
@Controller
@RequestMapping("/role")
public class RoleController extends BaseController {
	
	@Autowired
	private IRoleService roleService;
	
	/**
	 * 角色首页
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/index")
	public String index(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
//		Properties prop = PropertiesUtils.getProperties("system.properties");
//		String dir = prop.getProperty("lucene_index_address");
//		IndexReader indexReader = null;
//		String queryName = "简介";
//		try {
//			File indexDir = new File(dir);
//			if (!indexDir.exists()) {
//				indexDir.mkdir();
//			}
//			// 索引位置
//			Directory directory = FSDirectory.open(indexDir.toPath());
//			indexReader = DirectoryReader.open(directory);
//			IndexSearcher indexSearcher = new IndexSearcher(indexReader);
//			// 分词器
//			Analyzer analyzer = new StandardAnalyzer();
//			// 指定对哪个字段检索并且指定使用哪个分词器
//			QueryParser queryParser = new QueryParser("title", analyzer);
//			Query query = queryParser.parse(queryName);
//			TopDocs topDocs = indexSearcher.search(query, 10000);
//			// 得分数组(匹配度越高，得分越高)
//			ScoreDoc[] scoreDocs = topDocs.scoreDocs;
//			for (ScoreDoc scoreDoc : scoreDocs) {
//				Document document = indexSearcher.doc(scoreDoc.doc);
//				System.out.println(document.get("title"));
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		} finally {
//			if (indexReader != null) {
//				indexReader.close();
//			}
//		}
		
		return "/role/roleManage";
	}
	
	/**
	 * 权限首页
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/authIndex")
	public String authIndex(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		return "/role/authManage";
	}
	
	/**
	 * 分页角色
	 * @param currentPage
	 * @param pageSize
	 * @param roleName
	 * @param creatorName
	 * @param request
	 * @param response
	 */
	@RequestMapping("/findPageRoles")
	public void findPageUsers(Integer currentPage, Integer pageSize, String roleName, String creatorName,
			HttpServletRequest request, HttpServletResponse response) {
		
		JSONObject jo = new JSONObject();
		try {
			DataPage<Role> dataPage = roleService.findPageRoles(currentPage, pageSize, roleName, creatorName);
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
	 * 获取菜单树
	 * @param request
	 * @param response
	 */
	@RequestMapping("/getMenuTree")
	public void getMenuTree(HttpServletRequest request, HttpServletResponse response) {
		
		try {
			Menu menu = roleService.getMenuTree("0", new Menu());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 获取后台菜单
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/getAllMenu")
	@ResponseBody
	public AjaxResult getAllMenu(HttpServletRequest request, HttpServletResponse response) {
		AjaxResult ajaxResult = new AjaxResult(true);
		try {
			List<Menu> menuList = roleService.getAllMenu();
			ajaxResult.setData(menuList);
		} catch (Exception e) {
			e.printStackTrace();
			ajaxResult.setMsg("内部异常");
			ajaxResult.setSuccess(false);
		}
		return ajaxResult;
	}
	
	/**
	 * 获取某个角色拥有的菜单
	 * @param roleId
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/getExistMenu")
	@ResponseBody
	public void getExistMenu(String roleId, HttpServletRequest request, HttpServletResponse response) {
		JSONObject jo = new JSONObject();
		List<String> existMenuIds = new ArrayList<String>();
		try {
			if (StringUtils.hasText(roleId)) {
				Role role = roleService.findByKey(roleId);
				jo.put("role", role);
				List<RoleMenu> existMenus = roleService.getExistMenu(roleId);
				if (!CollectionUtils.isEmpty(existMenus)) {
					existMenuIds = existMenus.stream().map(rm -> rm.getMenuId()).collect(Collectors.toList());
				}
			}
			List<Menu> allmenus = roleService.getAllMenu();
			jo.put("result", true);
			jo.put("allmenus", allmenus);
			jo.put("existMenuIds", existMenuIds);
		} catch (Exception e) {
			e.printStackTrace();
			jo.put("result", false);
			jo.put("msg", "内部异常");
		}
		InteractiveWithAjax.printWriter(jo, response);
	}
	
	/**
	 * 角色新建修改
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/roleNewIndex")
	public ModelAndView roleNewIndex(String roleId, String layEvent, HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		return new ModelAndView("/role/roleAdd").addObject("roleId", roleId)
												.addObject("layEvent", layEvent);
	}
	
	/**
	 * 新增或修改角色权限
	 * @param roleId
	 * @param menuIds
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/createOrUpdateRole")
	@ResponseBody
	public AjaxResult createOrUpdateRole(String roleId, @RequestParam(value = "menuIds[]")String[] menuIds, 
			String roleName, HttpServletRequest request, HttpServletResponse response) throws Exception {
		AjaxResult ajaxResult = new AjaxResult(true);
		User user = getCurrentUser(request);
		try {
			roleService.createOrUpdateRole(roleId, roleName, menuIds, user);
			ajaxResult.setMsg("保存成功");
		} catch (Exception e) {
			e.printStackTrace();
			ajaxResult.setSuccess(false);
			ajaxResult.setMsg("内部异常");
		}
		return ajaxResult;
	}
	
	/**
	 * 删除角色
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/roleDelete")
	@ResponseBody
	public AjaxResult roleDelete(HttpServletRequest request, HttpServletResponse response) {
		AjaxResult ajaxResult = new AjaxResult();
		try {
			String[] ids = request.getParameterValues("ids[]");
			ajaxResult = roleService.roleDelete(ids);
			ajaxResult.setMsg("删除成功");
		} catch (Exception e) {
			ajaxResult.setSuccess(false);
			ajaxResult.setMsg("内部异常");
		}
		return ajaxResult;
	}
	
	/**
	 * 查看、分配权限页面
	 * @param layEvent
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/{userId}/{layEvent}/authInfoIndex")
	public ModelAndView authInfoIndex(@PathVariable String layEvent, @PathVariable String userId, HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		return new ModelAndView("/role/authInfo").addObject("layEvent", layEvent)
												 .addObject("userId", userId);
	}
	
	/**
	 * 根据userId查找role
	 * @param currentPage
	 * @param pageSize
	 * @param userId
	 * @param request
	 * @param response
	 */
	@RequestMapping("/{userId}/findRoleByUserId")
	public void findPageUsers(Integer currentPage, Integer pageSize, @PathVariable String userId,
			HttpServletRequest request, HttpServletResponse response) {
		
		JSONObject jo = new JSONObject();
		try {
			DataPage<Map<String, Object>> dataPage = roleService.findRoleByUserId(currentPage, pageSize, userId);
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
	 * 查询某用户未拥有的角色 
	 * @param currentPage
	 * @param pageSize
	 * @param userId
	 * @param request
	 * @param response
	 */
	@Deprecated
	@RequestMapping("/{userId}/findPageOtherRoles")
	public void findPageOtherRoles(Integer currentPage, Integer pageSize, @PathVariable String userId,
			HttpServletRequest request, HttpServletResponse response) {
		
		JSONObject jo = new JSONObject();
		try {
			DataPage<Map<String, Object>> dataPage = roleService.findPageOtherRoles(currentPage, pageSize, userId);
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
	 * 分配角色
	 * @param userId
	 * @param request
	 * @param response
	 */
	@RequestMapping("/{userId}/distributeRole")
	@ResponseBody
	public AjaxResult distributeRole(@PathVariable String userId, HttpServletRequest request, HttpServletResponse response) {
		AjaxResult aj = new AjaxResult(true);
		try {
			String[] roleIds = request.getParameterValues("roleIds[]");
			roleService.distributeRole(userId, roleIds);
			aj.setMsg("保存成功");
		} catch (Exception e) {
			e.printStackTrace();
			aj.setSuccess(false);
			aj.setMsg("内部异常");
		}
		return aj;
	}
	
	/**
	 * 删除用户角色
	 * @param userId
	 * @param roleId
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/{userId}/{roleId}/delUserRole")
	@ResponseBody
	public AjaxResult delUserRole(@PathVariable String userId, @PathVariable String roleId, HttpServletRequest request, HttpServletResponse response) {
		AjaxResult aj = new AjaxResult(true);
		try {
			roleService.delUserRole(userId, roleId);
			aj.setMsg("删除成功");
		} catch (Exception e) {
			e.printStackTrace();
			aj.setSuccess(false);
			aj.setMsg("内部异常");
		}
		return aj;
	}

}
