package com.javastudy.serviceImpl;

import javax.jws.WebService;

import com.javastudy.service.IWebService;


@WebService
public class WebserviceImpl implements IWebService {

	@Override
	public void sayHello(String name) {
		
		System.out.println("Hello你好");

	}

}
