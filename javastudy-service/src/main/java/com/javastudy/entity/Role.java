package com.javastudy.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.javastudy.system.BaseEntity;

/**
 * 角色类
 * @author cmh
 *
 */
@Entity
@Table(name = "system_role")
public class Role extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1510163476434040290L;
	
	public Role() {
		// TODO Auto-generated constructor stub
	}

	public Role(String id, String roleName) {
		this.id = id;
		this.roleName = roleName;
	}
	
	/**
     * 主键
     */
    @ Id
    @ GenericGenerator(name = "hibernate-uuid", strategy = "uuid.hex")
    @ GeneratedValue(generator = "hibernate-uuid")
    @ Column(nullable = false)
    private String id;

	/**
	 * 角色名
	 */
	@Column(name = "role_name")
	private String roleName;
	
	/**
	 * 创建者id
	 */
	@Column(name = "creator_id")
	private String creatorId;
	
	/**
	 * 创建者名
	 */
	@Column(name = "creator_name")
	private String creatorName;
	
	/**
	 * 创建时间
	 */
	@Column(name = "create_time")
	private String createTime;
	
	public String getCreatorName() {
		return creatorName;
	}

	public void setCreatorName(String creatorName) {
		this.creatorName = creatorName;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getCreatorId() {
		return creatorId;
	}

	public void setCreatorId(String creatorId) {
		this.creatorId = creatorId;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	
}
