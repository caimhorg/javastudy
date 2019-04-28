package com.javastudy.user.controller;

import java.io.UnsupportedEncodingException;

public class Demo4 {

	public static void main(String[] args) throws UnsupportedEncodingException {
		// TODO Auto-generated method stub
		String str = "12345蔡敏浩";
		byte[] b = str.getBytes();
		System.out.println(str.getBytes().length);
		System.out.println(str.length());
		System.out.println(str.toCharArray().length);
	}

}
