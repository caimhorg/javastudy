package com.javastudy.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

import com.javastudy.system.BaseEntity;

/**
 * 菜单类
 * 暂分为一级菜单和二级菜单
 * 一级菜单父id默认为0
 * 
 * @author cmh
 *
 */
@Entity
@Table(name = "system_menu")
public class Menu extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4170648489734071226L;

	/**
     * 主键
     */
    @ Id
    @ GenericGenerator(name = "hibernate-uuid", strategy = "uuid.hex")
    @ GeneratedValue(generator = "hibernate-uuid")
    @ Column(nullable = false)
    private String id;

	/**
	 * 菜单名
	 */
	@Column(name = "menu_name")
	private String menuName;
	
	/**
	 * 路劲
	 */
	@Column(name = "url_")
	private String url;
	
	/**
	 * 父id
	 */
	@Column(name = "parent_id")
	private String parentId;
	
	/**
	 * 菜单等级(1级、2级)
	 */
	@Column(name = "level_")
	private String level;
	
	/**
	 * 子菜单
	 */
	@Transient
	private List<Menu> children;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getMenuName() {
		return menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public List<Menu> getChildren() {
		return children;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public void setChildren(List<Menu> children) {
		this.children = children;
	}
	
}
