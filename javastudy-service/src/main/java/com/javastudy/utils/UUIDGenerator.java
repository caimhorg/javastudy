package com.javastudy.utils;

import java.util.UUID;

/**
 * @author cmh
 *
 */
public class UUIDGenerator {

	public static String getUUID() {
		
		return UUID.randomUUID().toString().replace("-", "");
	}
}
