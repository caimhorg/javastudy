package com.javastudy.entity;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class UserAspect {

	@Before("execution(* com.javastudy.serviceImpl.UserServiceImpl.*(..))")
	public void before() {
		System.out.println("before增强");
	}
	
	@After("execution(* com.javastudy.serviceImpl.UserServiceImpl.*(..))")
	public void after() {
		System.out.println("after增强");
	}
}
