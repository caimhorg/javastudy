package com.javastudy.service;

import com.alibaba.fastjson.JSONObject;
import com.javastudy.dto.UserDto;
import com.javastudy.entity.User;
import com.javastudy.utils.DataPage;

/**
 * 用户接口
 * @author cmh
 *
 */
public interface IUserService {

	/**
	 * 分页用户
	 * @param currentPage
	 * @param pageSize
	 * @param idCard 
	 * @param userName 
	 * @return
	 */
	DataPage<User> findPageUsers(Integer currentPage, Integer pageSize, String userName, String idCard) throws Exception;

	/**
	 * 判断信息是否存在
	 * @param userdto
	 * @return
	 * @throws Exception
	 */
	JSONObject checkSame(UserDto userdto) throws Exception;

	/**
	 * 注册
	 * @param userdto
	 * @return
	 * @throws Exception
	 */
	boolean userRegister(UserDto userdto) throws Exception;

	/**
	 * 登录前的判断
	 * @param userName
	 * @param password
	 * @return
	 * @throws Exception
	 */
	JSONObject loginBefore(String userName, String password) throws Exception;

	/**
	 * 获取登录的用户
	 * @param userName
	 * @param password
	 * @return
	 * @throws Exception
	 */
	User findLoginUser(String userName, String password) throws Exception;

	/**
	 * 冻结/解冻用户
	 * @param userId
	 * @param userStatus
	 * @throws Exception
	 */
	void freeze(String userId, Integer userStatus) throws Exception;

	/**
	 * 用户删除
	 * @param ids
	 * @throws Exception
	 */
	void userDelete(String[] ids) throws Exception;

	/**
	 * 用户信息
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	User getUserInfo(String userId) throws Exception;
	
	void testAop();

}
