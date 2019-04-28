package com.javastudy.utils;

import java.io.Serializable;

/**
 * Ajax返回结果类
 * 
 * @author cmh
 *
 */
public class AjaxResult implements Serializable {

	private static final long serialVersionUID = 1960980973782194921L;
	private boolean success;
	private String msg;
	private Object data;
	private String token;

	public AjaxResult() {
	}

	public AjaxResult(boolean success) {
		this(success, "", null);
	}

	public AjaxResult(boolean success, String msg) {
		this(success, msg, null);
	}

	public AjaxResult(boolean success, String msg, String token) {
		this.success = success;
		this.msg = msg;
		this.token = token;
	}

	public AjaxResult(boolean success, String msg, Object data) {
		this.success = success;
		this.msg = msg;
		this.data = data;
	}

	public AjaxResult(boolean success, String msg, Object data, String token) {
		this.success = success;
		this.msg = msg;
		this.data = data;
		this.token = token;
	}

	public static AjaxResult error() {
		return error("");
	}

	public static AjaxResult error(String msg) {
		return new AjaxResult(false, msg);
	}

	public static AjaxResult error(String msg, String token) {
		return new AjaxResult(false, msg, token);
	}

	public static AjaxResult success() {
		return success("");
	}

	public static AjaxResult success(String msg) {
		return success(msg, null);
	}

	public static AjaxResult success(String msg, String token) {
		return new AjaxResult(true, msg, token);
	}

	public static AjaxResult success(Object data) {
		return success("", data);
	}

	public static final AjaxResult success(String msg, Object data) {
		return new AjaxResult(true, msg, data);
	}

	public boolean isSuccess() {
		return this.success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getMsg() {
		return this.msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Object getData() {
		return this.data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public String getToken() {
		return this.token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof AjaxResult)) {
			return false;
		}

		AjaxResult result = (AjaxResult) o;

		if (this.success != result.success) {
			return false;
		}
		if (this.data != null)
			if (this.data.equals(result.data))
				return true;
			else if (result.data == null)
				return false;

		if (this.msg != null)
			if (this.msg.equals(result.msg))
				return true;
			else if (result.msg == null)
				return false;

		return true;
	}

	public int hashCode() {
		int result = (this.success) ? 1 : 0;
		result = 31 * result + ((this.msg != null) ? this.msg.hashCode() : 0);
		result = 31 * result + ((this.data != null) ? this.data.hashCode() : 0);
		return result;
	}

}
