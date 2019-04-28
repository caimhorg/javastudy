package com.javastudy.serviceImpl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSONObject;
import com.javastudy.dto.UserDto;
import com.javastudy.entity.User;
import com.javastudy.entity.UserRole;
import com.javastudy.hibernate.dao.UserDao;
import com.javastudy.hibernate.dao.UserRoleDao;
import com.javastudy.service.IUserService;
import com.javastudy.utils.DataPage;
import com.javastudy.utils.DateUtils;
import com.javastudy.utils.MessageDigestEncrypt;
import com.javastudy.utils.UUIDGenerator;

/**
 * 用户业务类
 * @author cmh
 *
 */
@Service
@Transactional
public class UserServiceImpl implements IUserService {
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private UserRoleDao userRoleDao;
	
	/* 
	 * 分页用户
	 * (non-Javadoc)
	 * @see com.books.user.service.IUserService#findPageUsers(java.lang.Integer, java.lang.Integer)
	 */
	@Override
	public DataPage<User> findPageUsers(Integer currentPage, Integer pageSize, String userName, String idCard) throws Exception {

		DataPage<User> dataPage = new DataPage<User>(pageSize, currentPage);
		Map<String, Object> params = new HashMap<String, Object>();
		String hql = "from User u where 1=1 and u.userStatus <> 2";
		String countHql = "select count(*) from User u where 1=1 and u.userStatus <> 2";
		if (StringUtils.hasText(userName)) {
			hql += " and u.userName like :userName";
			countHql += " and u.userName like :userName";
			params.put("userName", "%"+ userName +"%");
		}
		if (StringUtils.hasText(idCard)) {
			hql += " and u.idCard like :idCard";
			countHql += " and u.idCard like :idCard";
			params.put("idCard", "%"+ idCard +"%");
		} 
		hql += " order by u.createTime desc";
		List<User> userList = userDao.findPageNameParam(hql, (dataPage.getCurrentPage() - 1) * dataPage.getPageSize(), dataPage.getPageSize(), params);
		long count = userDao.getCountNameParam(countHql, params);
		dataPage.setDatas(userList);
		dataPage.setTotalRecords(count);
		return dataPage;
	}
	
	/* 
	 * 判断信息是否存在
	 * (non-Javadoc)
	 * @see com.books.user.service.IUserService#checkSame(com.books.user.dto.UserDto)
	 */
	@Override
	public JSONObject checkSame(UserDto userdto) throws Exception {
		JSONObject jo = new JSONObject();
		jo.put("result", true);
		if (StringUtils.hasText(userdto.getUserName())) {
			List<User> userList = userDao.findByProperty(User.class, "userName", userdto.getUserName());
			if (!CollectionUtils.isEmpty(userList)) {
				jo.put("result", false);
				jo.put("msg", "用户名已存在");
				return jo;
			}
		}
		if (StringUtils.hasText(userdto.getIdCard())) {
			List<User> userList = userDao.findByProperty(User.class, "idCard", userdto.getIdCard());
			if (!CollectionUtils.isEmpty(userList)) {
				jo.put("result", false);
				jo.put("msg", "身份证号已存在");
				return jo;
			}
		}
		if (StringUtils.hasText(userdto.getEmail())) {
			List<User> userList = userDao.findByProperty(User.class, "email", userdto.getEmail());
			if (!CollectionUtils.isEmpty(userList)) {
				jo.put("result", false);
				jo.put("msg", "邮箱已存在");
				return jo;
			}
		}
		if (StringUtils.hasText(userdto.getPhone())) {
			List<User> userList = userDao.findByProperty(User.class, "phone", userdto.getPhone());
			if (!CollectionUtils.isEmpty(userList)) {
				jo.put("result", false);
				jo.put("msg", "手机号已存在");
				return jo;
			}
		}
		return jo;
	}
	
	/* 
	 * 注册
	 * (non-Javadoc)
	 * @see com.books.user.service.IUserService#userRegister(com.books.user.dto.UserDto)
	 */
	@Override
	public boolean userRegister(UserDto userdto) throws Exception {
		boolean success = true;
		User user = new User();
		user.setId(UUIDGenerator.getUUID());
		user.setUserName(userdto.getUserName());
		user.setPassword(MessageDigestEncrypt.getStringMD5Code(userdto.getPassword()));
		user.setEmail(userdto.getEmail());
		user.setIdCard(userdto.getIdCard());
		user.setPhone(userdto.getPhone());
		user.setCreateTime(DateUtils.TimestampToStr(new Date()));
		userDao.save(user);
		//注册成功的用户自动获得普通角色
		UserRole ur = new UserRole();
		ur.setId(UUIDGenerator.getUUID());
		ur.setUserId(user.getId());
		ur.setRoleId("2");
		userRoleDao.save(ur);
		return success;
	}
	
	/* 
	 * 登录前的判断
	 * (non-Javadoc)
	 * @see com.books.user.service.IUserService#loginBefore(java.lang.String, java.lang.String)
	 */
	@Override
	public JSONObject loginBefore(String userName, String password)
			throws Exception {
		JSONObject jo = new JSONObject();
		jo.put("result", true);
		User user = findLoginUser(userName, password);
		if (user == null) {
			jo.put("result", false);
			jo.put("msg", "用户不存在");
			return jo;
		} else {
			if (user.getUserStatus() != 0) {
				jo.put("result", false);
				jo.put("msg", "该用户已被冻结");
				return jo;
			}
		}
		return jo;
	}

	/* 
	 * 获取登录时的用户
	 * (non-Javadoc)
	 * @see com.books.user.service.IUserService#findLoginUser(java.lang.String, java.lang.String)
	 */
	@Override
	public User findLoginUser(String userName, String password)
			throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		String hql = "select u from User u where 1=1 and u.userName = :userName and u.password = :password";
		params.put("userName", userName);
		params.put("password", MessageDigestEncrypt.getStringMD5Code(password));
		return userDao.findOneObjectBySqlNameParam(hql, params);
	}
	
	/* 
	 * 冻结/解冻用户
	 * (non-Javadoc)
	 * @see com.books.user.service.IUserService#freeze(java.lang.String, java.lang.Integer)
	 */
	@Override
	public void freeze(String userId, Integer userStatus) throws Exception {
		User user = userDao.findByKey(User.class, userId);
		if (user != null) {
			if (userStatus == 0) {
				user.setUserStatus(1);
			} else if (userStatus == 1) {
				user.setUserStatus(0);
			}
			userDao.saveOrUpdate(user);
		}
	}
	
	@Override
	public void userDelete(String[] ids) throws Exception {
		
		for (String id : ids) {
			User user = userDao.findByKey(User.class, id);
			if (user != null) {
				user.setUserStatus(2);
				userDao.saveOrUpdate(user);
			}
		}
	}
	
	@Override
	public User getUserInfo(String userId) throws Exception {
		
		return userDao.findByKey(User.class, userId);
	}
	
	@Override
	public void testAop() {
		System.out.println("test测试AOP");
		
	}
	
}
