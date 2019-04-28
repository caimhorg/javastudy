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
@Table(name = "system_user")
public class User extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5325416997354989788L;
	
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
	@Column(name = "user_name")
	private String userName;
	
	/**
	 * 密码
	 */
	@Column(name = "password_")
	private String password;
	
	/**
	 * 手机
	 */
	@Column(name = "phone_")
	private String phone;
	
	/**
	 * 邮箱
	 */
	@Column(name = "email_")
	private String email;
	
	/**
	 * 身份证
	 */
	@Column(name = "id_card")
	private String idCard;
	
	/**
	 * 用户状态0：正常  1:冻结  2:删除
	 */
	@Column(name = "user_status")
	private Integer userStatus;
	
	/**
	 * 注册时间
	 */
	@Column(name = "create_time")
	private String createTime;
	
	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Integer getUserStatus() {
		return userStatus;
	}

	public void setUserStatus(Integer userStatus) {
		this.userStatus = userStatus;
	}

	public String getIdCard() {
		return idCard;
	}

	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}

}
