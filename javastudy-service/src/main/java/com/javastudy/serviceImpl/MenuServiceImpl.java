package com.javastudy.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.javastudy.hibernate.dao.RoleDao;
import com.javastudy.service.IMenuService;


/**
 * 菜单service
 * @author cmh
 *
 */
@Service
@Transactional
public class MenuServiceImpl implements IMenuService {
	
	@Autowired
	private RoleDao roleDao;
	
	
}
