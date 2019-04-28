package com.javastudy.system;

import java.util.List;

/**
 * mongodb Dao层接口
 * @author cmh
 *
 */
public interface IBaseDaoForMongo<T> {

	void  save(T entity);
	
	List<T> find(Class<T> entityClass);
	
}
