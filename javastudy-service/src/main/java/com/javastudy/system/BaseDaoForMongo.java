package com.javastudy.system;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;

public class BaseDaoForMongo<T> implements IBaseDaoForMongo<T> {

	@Override
	public void save(T entity) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<T> find(Class<T> entityClass) {
		// TODO Auto-generated method stub
		return null;
	}

//	@Autowired
//	private MongoTemplate mongo;
//	
//	@Override
//	public void save(T entity) {
//		mongo.save(entity);
//		
//	}
//	
//	@Override
//	public List<T> find(Class<T> entityClass) {
//		return mongo.findAll(entityClass);
//	}
	
	
	
	
}
