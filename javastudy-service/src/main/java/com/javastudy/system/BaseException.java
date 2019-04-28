package com.javastudy.system;

public class BaseException extends RuntimeException {

	private static final long serialVersionUID = -3261414260112601422L;
	private String exceptionCode;
	private Object[] params;
	private String resource;
	private BaseException parent;

	public BaseException(String expCode, Object[] objects) {
		super(expCode);
		this.exceptionCode = expCode;
		this.params = objects;
	}

	public BaseException(String expCode, Throwable ex, Object[] objects) {
		super(expCode, ex);
		this.exceptionCode = expCode;
		this.params = objects;
	}

	public BaseException(BaseException parent, String expCode, Throwable ex,
			Object[] objects) {
		super(expCode, ex);
		this.exceptionCode = expCode;
		this.params = objects;
		this.parent = parent;
	}

	public BaseException(String expCode, String resource) {
		this.exceptionCode = expCode;
		this.resource = resource;
	}

	public BaseException(String resource) {
		this.resource = resource;
	}

	public String getExceptionCode() {
		return this.exceptionCode;
	}

	public Object[] getParams() {
		return this.params;
	}

	public String getResource() {
		return this.resource;
	}

	public BaseException getParent() {
		return this.parent;
	}

	public void setResource(String resource) {
		this.resource = resource;
	}

	public void setParent(BaseException parent) {
		this.parent = parent;
	}


}
