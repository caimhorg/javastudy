package com.javastudy.service;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;


/**
 * 
 * @author cmh
 *
 */
@WebService
public interface IWebService {
	
	@WebMethod
	public void sayHello(@WebParam String name);
}
