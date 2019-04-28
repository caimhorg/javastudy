package com.javastudy.user.controller;

import java.io.BufferedInputStream;
import java.io.FilterInputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Timer;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import javax.jws.WebService;


public class Demo {
	
	public static void main(String[] args) throws InterruptedException {
		
//		JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
//		factory.setServiceClass(IWebService.class);
//		factory.setAddress("http://localhost:8080/javastudy-web/services/webservice");
//		IWebService service = (IWebService)factory.create();
//		service.sayHello("");
		ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
		MyThread myThread = new MyThread();
		Lock lock = new ReentrantLock();
		ReadWriteLock rwlock = new ReentrantReadWriteLock();
		Lock readLock = rwlock.readLock();
		Lock writeLock = rwlock.writeLock();
		try {
			lock.lock();
			System.out.println("1111");
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			lock.unlock();
		}
		
		
	}
	
	

}




class MyThread implements Runnable, Cloneable {
	
	volatile AtomicInteger a = new AtomicInteger(0);
	
	@Override
	public void run() {
		
		for (int i = 0; i < 10; i++) {
			a.getAndIncrement();//a++
			System.out.println(a);
		}
	}
	
	@Override
	protected Object clone() throws CloneNotSupportedException {
		// TODO Auto-generated method stub
		return super.clone();
	}

	
	
}

class ImproveList<T> {
	
	private final List<T> list;
	
	public ImproveList(List<T> list) {
		this.list = list;
	}
	
	public synchronized boolean putIfAbsent(T t) {
		boolean flag = list.contains(t);
		if (flag) {
			list.add(t);
		}
		return !flag;
	}
}








