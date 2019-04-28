package com.javastudy.hibernate.dao;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.javastudy.entity.Role;
import com.javastudy.system.BaseDaoImpl;
import com.javastudy.utils.DataPage;

@Repository
public class RoleDao extends BaseDaoImpl<Role> {

	public DataPage<Map<String, Object>> findRoleByUserId(Integer currentPage,
			Integer pageSize, String userId) {
		DataPage<Map<String, Object>> dataPage = new DataPage<Map<String, Object>>(pageSize, currentPage);
		Map<String, Object> params = new HashMap<String, Object>();
		String sql = "select r.id, r.role_name as roleName, r.creator_name as creatorName from system_role r left join system_user_role ur on r.id=ur.role_id where 1=1 ";
		String countSql = "select count(*) from system_role r left join system_user_role ur on r.id=ur.role_id where 1=1 ";
		if (StringUtils.hasText(userId)) {
			sql += " and ur.user_id = :userId";
			countSql += " and ur.user_id = :userId";
			params.put("userId", userId);
		}
		sql += " order by r.create_time desc";
		List<Map<String, Object>> data = findPageBySql(sql, (dataPage.getCurrentPage() - 1) * dataPage.getPageSize(), dataPage.getPageSize(), params);
		BigInteger count = getCountBySql(countSql, params);
		dataPage.setDatas(data);
		dataPage.setTotalRecords(count.longValue());
		return dataPage;
		
	}

	public DataPage<Map<String, Object>> findPageOtherRoles(
			Integer currentPage, Integer pageSize, String userId) throws Exception {
		DataPage<Map<String, Object>> dataPage = new DataPage<Map<String, Object>>(pageSize, currentPage);
		Map<String, Object> params = new HashMap<String, Object>();
		String sql = "select r.id, r.role_name as roleName, r.creator_name as creatorName from system_role r left join system_user_role ur on r.id=ur.role_id where 1=1 ";
		String countSql = "select count(*) from system_role r left join system_user_role ur on r.id=ur.role_id where 1=1 ";
		if (StringUtils.hasText(userId)) {
			sql += " and ur.user_id <> :userId";
			countSql += " and ur.user_id <> :userId";
			params.put("userId", userId);
		}
		sql += " order by r.create_time desc";
		List<Map<String, Object>> data = findPageBySql(sql, (dataPage.getCurrentPage() - 1) * dataPage.getPageSize(), dataPage.getPageSize(), params);
		BigInteger count = getCountBySql(countSql, params);
		dataPage.setDatas(data);
		dataPage.setTotalRecords(count.longValue());
		return dataPage;
	}

	
}
