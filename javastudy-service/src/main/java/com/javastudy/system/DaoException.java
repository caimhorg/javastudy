package com.javastudy.system;

public class DaoException extends Exception {

	private static final long serialVersionUID = 3882771394768323709L;
	private String errorMessage;

	public DaoException() {
	}

	public DaoException(String errorMessage) {
		super(errorMessage);
		this.errorMessage = errorMessage;
	}

	public DaoException(String errorMessage, Throwable rootException) {
		super(errorMessage, rootException);
		this.errorMessage = errorMessage;
	}

	public DaoException(String errorMessage, Exception e) {
		super(errorMessage, e);
		this.errorMessage = errorMessage;
	}

	public String getMessage() {
		return super.getMessage() + " Error Message[" + this.errorMessage + "]";
	}
}
