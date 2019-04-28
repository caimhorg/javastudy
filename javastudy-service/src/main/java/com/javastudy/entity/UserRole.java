package com.javastudy.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.javastudy.system.BaseEntity;

/**
 * 用户类
 * @author cmh
 *
 */
@Entity
@Table(name = "system_user_role")
public class UserRole extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3794426370377572090L;

	/**
     * 主键
     */
    @ Id
    @ GenericGenerator(name = "hibernate-uuid", strategy = "uuid.hex")
    @ GeneratedValue(generator = "hibernate-uuid")
    @ Column(nullable = false)
    private String id;

	/**
	 * 用户名
	 */
    @Column(name = "user_id")
	private String userId;
	
	/**
	 * 角色名
	 */
    @Column(name = "role_id")
	private String roleId;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

}
