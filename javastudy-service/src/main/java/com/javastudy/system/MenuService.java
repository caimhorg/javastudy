package com.javastudy.system;

import java.util.List;

import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.javastudy.contants.CommonContants;
import com.javastudy.entity.Menu;
import com.javastudy.service.IRoleService;
import com.javastudy.utils.SpringUtils;

public class MenuService {

	/**
	 * 根据用户权限获取首页菜单
	 * @param userId
	 * @return
	 */
	@Deprecated
	public static String initMenus(String userId) {
		
		StringBuffer sb = new StringBuffer();
		sb.append("<li class=\"layui-nav-item\">");
		sb.append("<a class=\"\" href=\"javascript:;\">书籍信息</a>");
		sb.append("<dl class=\"layui-nav-child\">");
		sb.append("<dd><a href=\"javascript:doAction('category/index.do');\">类别管理</a></dd>");
		sb.append("<dd><a href=\"javascript:doAction('books/index.do');\">书籍管理</a></dd>");
		sb.append("</dl>");
		sb.append("</li>");
		sb.append("<li class=\"layui-nav-item\">");
		sb.append("<a href=\"javascript:;\">用户权限</a>");
		sb.append("<dl class=\"layui-nav-child\">");
		sb.append("<dd><a href=\"javascript:doAction('user/index.do');\">用户管理</a></dd>");
		sb.append("<dd><a href=\"javascript:doAction('role/index.do');\">角色管理</a></dd>");
		sb.append("<dd><a href=\"javascript:doAction('role/authIndex.do');\">权限管理</a></dd>");
		sb.append("</dl>");
		sb.append("</li>");
		sb.append("<li class=\"layui-nav-item\"><a>借阅审批</a></li>");
		return sb.toString();
	}
	
	/**
	 * 获取用户菜单
	 * @param userId
	 * @param userMenuIds
	 * @return
	 */
	public static String initMenusNew(String userId, List<String> userMenuIds) {
		StringBuffer sb = new StringBuffer();
		try {
			//获取系统菜单
			Menu rootMenu = SpringUtils.getBean(IRoleService.class).getMenuTree("0", new Menu());
			if (!CollectionUtils.isEmpty(rootMenu.getChildren())) {
				sb = createMenu(rootMenu, userMenuIds, sb);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sb.toString();
	}

	/**
	 * 拼接字符串
	 * @param rootMenu
	 * @param userMenuIds
	 * @param sb
	 * @return
	 */
	private static StringBuffer createMenu(Menu rootMenu, List<String> userMenuIds, StringBuffer sb) {
		for (int i = 0; i < rootMenu.getChildren().size(); i++) {
			Menu childMenu = rootMenu.getChildren().get(i);
			if (!userMenuIds.contains(childMenu.getId())) {
				continue;
			}
			if (CommonContants.LEVEL_ONE.equals(childMenu.getLevel())) {
				sb.append("<li class=\"layui-nav-item\">");
				if (StringUtils.hasText(childMenu.getUrl())) {
					sb.append("<a class=\"\" href=\"javascript:doAction('"+ childMenu.getUrl() +"');\">"+ childMenu.getMenuName() +"</a>");
				} else {
					sb.append("<a class=\"\" href=\"javascript:;\">"+ childMenu.getMenuName() +"</a>");
				}
				if (!CollectionUtils.isEmpty(childMenu.getChildren())) {
					sb = createMenu(childMenu, userMenuIds, sb);
				}
				sb.append("</li>");
			}
			if (CommonContants.LEVEL_TWO.equals(childMenu.getLevel())) {
				sb.append("<dl class=\"layui-nav-child\">");
				sb.append("<dd><a href=\"javascript:doAction('"+ childMenu.getUrl() +"');\">"+ childMenu.getMenuName() +"</a>");
				if (!CollectionUtils.isEmpty(childMenu.getChildren())) {
					sb = createMenu(childMenu, userMenuIds, sb);
				}
				sb.append("</dd>");
				sb.append("</dl>");
			}
		}
		return sb;
	}
}
