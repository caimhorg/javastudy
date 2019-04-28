package com.javastudy.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.javastudy.system.BaseEntity;

/**
 * 角色菜单关联
 * @author cmh
 *
 */
@Entity
@Table(name = "system_role_menu")
public class RoleMenu extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5849600906470874720L;

	/**
     * 主键
     */
    @ Id
    @ GenericGenerator(name = "hibernate-uuid", strategy = "uuid.hex")
    @ GeneratedValue(generator = "hibernate-uuid")
    @ Column(nullable = false)
    private String id;

	/**
	 * 角色id
	 */
	@Column(name = "role_id")
	private String roleId;
	
	/**
	 * 菜单id
	 */
	@Column(name = "menu_id")
	private String menuId;
	

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public String getMenuId() {
		return menuId;
	}

	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}
}
