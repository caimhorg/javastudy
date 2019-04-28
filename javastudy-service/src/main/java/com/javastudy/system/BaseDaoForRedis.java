package com.javastudy.system;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class BaseDaoForRedis {

//	@Autowired
//	private StringRedisTemplate redis;
	
	public void setForString(String key, String value) {
//		redis.opsForValue().set(key, value);
	}
	
	public String getForString(String key) {
//		return redis.opsForValue().get(key);
		return null;
	}
	
	public void setForHash(String key, String hashKey, String hashvalue) {
//		redis.opsForHash().put(key, hashKey, hashvalue);
	}
	
	
}
