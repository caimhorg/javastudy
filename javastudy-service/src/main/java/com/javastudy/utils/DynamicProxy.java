package com.javastudy.utils;

import java.lang.reflect.Proxy;

/**
 * 动态代理通用类,用于获取代理类(可以自定义子类指定代理目标)
 * 1.代理目标必须实现一个接口,代理类与代理目标都是此接口的实例
 * 2.需要有Handler来执行代理目标的方法。(实现InvocationHandler)
 * 3.需要有自定义代理生成类,就是此类。
 * 
 * @author cmh
 *
 */
public class DynamicProxy<T extends R, R> {

	private T target;
	
	public DynamicProxy(T target) {
		this.target = target;
	}
	
	/**
	 * 获取代理对象
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public R getDynamicProxyInstance() {
//		return (R)Proxy.newProxyInstance(target.getClass().getClassLoader(), 
//										 target.getClass().getInterfaces(), 
//										 new InvocationHandler() {
//											 
//											@Override
//											public Object invoke(Object proxy, Method method, Object[] args)
//													throws Throwable {
//												
//												return method.invoke(target, args);
//											}
//										});
		return (R)Proxy.newProxyInstance(target.getClass().getClassLoader(), 
									     target.getClass().getInterfaces(), 
									     (proxy, method, args) -> method.invoke(target, args));
	}
}
