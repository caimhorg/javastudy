package com.javastudy.utils;

import java.security.MessageDigest;

/**
 * md5
 * @author cmh
 *
 */
public class MessageDigestEncrypt {

	private static char[] hexChar = { '0', '1', '2', '3', '4', '5', '6', '7',
			'8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
	
	/**
	 * 获取字符的md5摘要，该字符以系统默认编码方式表示。
	 * @param value
	 * @return
	 */
	public static String getStringMD5Code(String value)
	{
	    try
	    {
    	    MessageDigest md5 = MessageDigest.getInstance("MD5");
    	    md5.update(value.getBytes());
            return toHexString(md5.digest());
	    }
	    catch(Exception e)
	    {
	        e.printStackTrace();
            return "";
	    }
	}

	/**
	 * 把MD5的byte值转换为16进制的字符串，该字符串中abcdef为小写字符
	 * @param b md5的值
	 * @return md5的16进制字符串
	 */
	private static String toHexString(byte[] b) {
		StringBuilder sb = new StringBuilder(b.length * 2);
		for (int i = 0; i < b.length; i++)
		{
			sb.append(hexChar[(b[i] & 0xf0) >>> 4]);
			sb.append(hexChar[b[i] & 0x0f]);
		}
		return sb.toString();
	}
}
