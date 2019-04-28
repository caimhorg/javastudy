package com.javastudy.serviceImpl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.javastudy.entity.Menu;
import com.javastudy.entity.Role;
import com.javastudy.entity.RoleMenu;
import com.javastudy.entity.User;
import com.javastudy.entity.UserRole;
import com.javastudy.hibernate.dao.MenuDao;
import com.javastudy.hibernate.dao.RoleDao;
import com.javastudy.hibernate.dao.RoleMenuDao;
import com.javastudy.hibernate.dao.UserRoleDao;
import com.javastudy.service.IRoleService;
import com.javastudy.utils.AjaxResult;
import com.javastudy.utils.DataPage;
import com.javastudy.utils.DateUtils;
import com.javastudy.utils.UUIDGenerator;


/**
 * 角色service
 * @author cmh
 *
 */
@Service
@Transactional
public class RoleServiceImpl implements IRoleService {
	
	@Autowired
	private RoleDao roleDao;
	
	@Autowired
	private MenuDao menuDao;
	
	@Autowired
	private RoleMenuDao roleMenuDao;
	
	@Autowired
	private UserRoleDao userRoleDao;
	
	/* 
	 * 分页角色
	 * (non-Javadoc)
	 * @see com.books.user.service.IRoleService#findPageRoles(java.lang.Integer, java.lang.Integer, java.lang.String)
	 */
	@Override
	public DataPage<Role> findPageRoles(Integer currentPage, Integer pageSize, String roleName, String creatorName) throws Exception {

		DataPage<Role> dataPage = new DataPage<Role>(pageSize, currentPage);
		Map<String, Object> params = new HashMap<String, Object>();
		String hql = "from Role r where 1=1 ";
		String countHql = "select count(*) from Role r where 1=1";
		if (StringUtils.hasText(roleName)) {
			hql += " and r.roleName like :roleName";
			countHql += " and r.roleName like :roleName";
			params.put("roleName", "%"+ roleName +"%");
		}
		if (StringUtils.hasText(creatorName)) {
			hql += " and r.creatorName like :creatorName";
			countHql += " and r.creatorName like :creatorName";
			params.put("creatorName", "%"+ creatorName +"%");
		}
		hql += " order by r.createTime desc";
		List<Role> roleList = roleDao.findPageNameParam(hql, (dataPage.getCurrentPage() - 1) * dataPage.getPageSize(), dataPage.getPageSize(), params);
		long count = roleDao.getCountNameParam(countHql, params);
		dataPage.setDatas(roleList);
		dataPage.setTotalRecords(count);
		return dataPage;
	}
	
	/* 
	 * 获取菜单树
	 * (non-Javadoc)
	 * @see com.books.user.service.IRoleService#getMenuTree(java.lang.String)
	 */
	@Override
	public Menu getMenuTree(String parentId, Menu parentMenu) throws Exception {
		List<Menu> list = new ArrayList<Menu>();
		if (StringUtils.hasText(parentId)) {
			list = menuDao.findByProperty(Menu.class, "parentId", parentId);
			if (!CollectionUtils.isEmpty(list)) {
				parentMenu.setChildren(list);
				for (Menu menu : list) {
					getMenuTree(menu.getId(), menu);
				}
			}
		}
		return parentMenu;
	}
	
	/* 获取用户拥有的菜单ids
	 * (non-Javadoc)
	 * @see com.javastudy.user.service.IRoleService#getUserMenuIds(java.lang.String)
	 */
	@Override
	public List<String> getUserMenuIds(String userId) throws Exception {
		List<String> allMenuIds = new ArrayList<String>();
		//获取角色
		List<UserRole> roleList = userRoleDao.findByProperty(UserRole.class, "userId", userId);
		for (UserRole ur : roleList) {
			List<RoleMenu> rmList = roleMenuDao.findByProperty(RoleMenu.class, "roleId", ur.getRoleId());
			allMenuIds.addAll(rmList.stream().map(s -> s.getMenuId()).collect(Collectors.toList()));
		}
		return allMenuIds.stream().distinct().collect(Collectors.toList());
	}
	
	/* 
	 * 获取所有菜单
	 * (non-Javadoc)
	 * @see com.javastudy.user.service.IRoleService#getAllMenu()
	 */
	@Override
	public List<Menu> getAllMenu() throws Exception {
		
		return menuDao.findPage(Menu.class, -1, -1);
	}
	
	/* 
	 * 获取某个角色拥有的菜单
	 * (non-Javadoc)
	 * @see com.javastudy.user.service.IRoleService#getExistMenu(java.lang.String)
	 */
	@Override
	public List<RoleMenu> getExistMenu(String roleId) throws Exception {
		if (!StringUtils.hasText(roleId)) {
			return null;
		}
		return roleMenuDao.findByProperty(RoleMenu.class, "roleId", roleId);
	}
	
	@Override
	public Role findByKey(String roleId) throws Exception {
		return roleDao.findByKey(Role.class, roleId);
	}
	
	/* 
	 * 新增或修改角色权限
	 * (non-Javadoc)
	 * @see com.javastudy.user.service.IRoleService#createOrUpdateRole(java.lang.String, java.lang.String[])
	 */
	@Override
	public void createOrUpdateRole(String roleId, String roleName, String[] menuIds, User user)
			throws Exception {
		Optional<User> opUser = Optional.ofNullable(user);
		if (!opUser.isPresent()) {
			opUser = Optional.of(new User());
		}
		List<String> menuIdsList = Arrays.asList(menuIds);
		List<RoleMenu> rmList = new ArrayList<RoleMenu>();
		if (!StringUtils.hasText(roleId)) {
			Role newRole = new Role();
			newRole.setId(UUIDGenerator.getUUID());
			newRole.setRoleName(roleName);
			newRole.setCreateTime(DateUtils.TimestampToStr(new Date()));
			newRole.setCreatorId(opUser.map(s -> s.getId()).orElse(""));
			newRole.setCreatorName(opUser.map(s -> s.getUserName()).orElse(""));
			roleDao.save(newRole);
			menuIdsList.forEach(s -> saveRoleMenu(newRole.getId(), s, rmList));
			roleMenuDao.saveAll(rmList);
		} else {
			Role role = roleDao.findByKey(Role.class, roleId);
			role.setRoleName(roleName);
			roleDao.saveOrUpdate(role);
			List<RoleMenu> rms = getExistMenu(roleId);
			roleMenuDao.deleteAll(rms);
			menuIdsList.forEach(s -> saveRoleMenu(roleId, s, rmList));
			roleMenuDao.saveAll(rmList);
		}
	}

	private void saveRoleMenu(String roleId, String menuId, List<RoleMenu> rmList) {
		RoleMenu rm = new RoleMenu();
		rm.setId(UUIDGenerator.getUUID());
		rm.setRoleId(roleId);
		rm.setMenuId(menuId);
		rmList.add(rm);
	}
	
	/* 
	 * 角色删除
	 * (non-Javadoc)
	 * @see com.javastudy.user.service.IRoleService#roleDelete(java.lang.String[])
	 */
	@Override
	public AjaxResult roleDelete(String[] ids) throws Exception {
		
		AjaxResult ajaxResult = new AjaxResult(true);
		for (String roleId : ids) {
			List<UserRole> userRoles = userRoleDao.findByProperty(UserRole.class, "roleId", roleId);
			if (!CollectionUtils.isEmpty(userRoles)) {
				ajaxResult.setSuccess(false);
				ajaxResult.setMsg("角色正在使用,无法删除");
				return ajaxResult;
			}
			List<RoleMenu> rms = getExistMenu(roleId);
			roleMenuDao.deleteAll(rms);
			Role role = roleDao.findByKey(Role.class, roleId);
			if (role != null) {
				roleDao.delete(role);
			}
		}
		return ajaxResult;
	}
	
	/* 
	 * 根据userId查找role
	 * (non-Javadoc)
	 * @see com.javastudy.user.service.IRoleService#findRoleByUserId(java.lang.Integer, java.lang.Integer, java.lang.String)
	 */
	@Override
	public DataPage<Map<String, Object>> findRoleByUserId(Integer currentPage, Integer pageSize, String userId) throws Exception {
		
		return roleDao.findRoleByUserId(currentPage, pageSize, userId);
	}
	
	/* 
	 * 查询某用户未拥有的角色 
	 * (non-Javadoc)
	 * @see com.javastudy.user.service.IRoleService#findPageOtherRoles(java.lang.Integer, java.lang.Integer, java.lang.String)
	 */
	@Override
	public DataPage<Map<String, Object>> findPageOtherRoles(
			Integer currentPage, Integer pageSize, String userId) throws Exception {
		return roleDao.findPageOtherRoles(currentPage, pageSize, userId);
	}
	
	/* 分配角色
	 * (non-Javadoc)
	 * @see com.javastudy.user.service.IRoleService#distributeRole(java.lang.String, java.lang.String[])
	 */
	@Override
	public void distributeRole(String userId, String[] roleIds)
			throws Exception {
		List<String> addRoleList = new ArrayList<String>();
		DataPage<Map<String, Object>> dp = findRoleByUserId(0, 0, userId);
		List<Map<String, Object>> roleObjects = dp.getDatas();
		if (!CollectionUtils.isEmpty(roleObjects)) {
			List<String> existRoleIds = roleObjects.stream()
												   .map(s -> s.get("id").toString())
												   .collect(Collectors.toList());
			for (String roleId : roleIds) {
				if (existRoleIds.contains(roleId)) {
					continue;
				}
				addRoleList.add(roleId);
			}
		} else {
			addRoleList.addAll(Arrays.asList(roleIds));
		}
		userRoleDao.distributeRole(userId, addRoleList);
	}
	
	@Override
	public void delUserRole(String userId, String roleId) throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		String hql = "from UserRole ur where 1=1 and ur.userId = ? and ur.roleId = ?";
		List<UserRole> urList = userRoleDao.findPage(hql, 0, 0, userId, roleId);
		if (!CollectionUtils.isEmpty(urList)) {
			UserRole ur = urList.get(0);
			userRoleDao.delete(ur);
		}
		
	}
	
	
}
