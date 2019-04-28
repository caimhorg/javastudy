package com.javastudy.hibernate.dao;

import java.util.List;
import org.springframework.stereotype.Repository;
import com.javastudy.entity.UserRole;
import com.javastudy.system.BaseDaoImpl;
import com.javastudy.utils.UUIDGenerator;

@Repository
public class UserRoleDao extends BaseDaoImpl<UserRole> {

	public void distributeRole(String userId, List<String> addRoleList) throws Exception {
		for (String roleId : addRoleList) {
			UserRole ur = new UserRole();
			ur.setId(UUIDGenerator.getUUID());
			ur.setRoleId(roleId);
			ur.setUserId(userId);
			save(ur);
		}
	}

	
}
