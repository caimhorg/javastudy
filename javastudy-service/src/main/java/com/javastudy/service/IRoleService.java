package com.javastudy.service;

import java.util.List;
import java.util.Map;

import com.javastudy.entity.Menu;
import com.javastudy.entity.Role;
import com.javastudy.entity.RoleMenu;
import com.javastudy.entity.User;
import com.javastudy.utils.AjaxResult;
import com.javastudy.utils.DataPage;

/**
 * 角色接口
 * @author cmh
 *
 */
public interface IRoleService {

	/**
	 * 分页角色
	 * @param currentPage
	 * @param pageSize
	 * @param roleName
	 * @param creatorName 
	 * @return
	 * @throws Exception
	 */
	DataPage<Role> findPageRoles(Integer currentPage, Integer pageSize, String roleName, String creatorName) throws Exception;

	/**
	 * 获取菜单树
	 * @return
	 * @throws Exception
	 */
	Menu getMenuTree(String parentId, Menu parentMenu) throws Exception;

	/**
	 * 获取所有菜单
	 * @return
	 */
	List<Menu> getAllMenu() throws Exception;

	/**
	 * 新增或修改角色权限
	 * @param roleId
	 * @param roleName 
	 * @param menuIds
	 * @param user 
	 */
	void createOrUpdateRole(String roleId, String roleName, String[] menuIds, User user) throws Exception;

	/**
	 * 获取某个角色拥有的菜单
	 * @param roleId
	 * @return
	 * @throws Exception
	 */
	List<RoleMenu> getExistMenu(String roleId) throws Exception;

	/**
	 * id查找
	 * @param roleId
	 * @return
	 */
	Role findByKey(String roleId) throws Exception;

	/**
	 * 删除角色
	 * @param ids
	 * @return 
	 * @throws Exception
	 */
	AjaxResult roleDelete(String[] ids) throws Exception;

	/**
	 * 根据userId查找role
	 * @param currentPage
	 * @param pageSize
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	DataPage<Map<String, Object>> findRoleByUserId(Integer currentPage, Integer pageSize,
			String userId) throws Exception;

	/**
	 * 查询某用户未拥有的角色 
	 * @param currentPage
	 * @param pageSize
	 * @param userId
	 * @return
	 */
	DataPage<Map<String, Object>> findPageOtherRoles(Integer currentPage,
			Integer pageSize, String userId) throws Exception;

	/**
	 * 分配角色
	 * @param userId
	 * @param roleIds
	 * @throws Exception
	 */
	void distributeRole(String userId, String[] roleIds) throws Exception;

	/**
	 * 删除用户角色
	 * @param userId
	 * @param roleId
	 * @throws Exception
	 */
	void delUserRole(String userId, String roleId) throws Exception;

	/**
	 * 获取用户拥有的菜单ids
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	List<String> getUserMenuIds(String userId) throws Exception;
	

}
