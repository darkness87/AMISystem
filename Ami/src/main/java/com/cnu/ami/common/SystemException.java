package com.cnu.ami.common;

import org.springframework.http.HttpStatus;

/**
 * Exception 처리를 위한
 * @author sookwon
 *
 */
public class SystemException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	private HttpStatus status;
	private int code;
	private String message;

	public SystemException(HttpStatus status, int code, String message) {
		this.status = status;
		this.code = code;
		this.message = message;
	}

	public HttpStatus getStatus() {
		return status;
	}

	public void setStatus(HttpStatus status) {
		this.status = status;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
}
