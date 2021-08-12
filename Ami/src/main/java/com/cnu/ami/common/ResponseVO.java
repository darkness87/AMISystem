package com.cnu.ami.common;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Common Response Object return
 * 
 * @author sookwon
 */
@Getter
@Setter
public class ResponseVO<T> {

	private Api api;
	private T response;

	@Getter
	@Setter
	@NoArgsConstructor(access = AccessLevel.PROTECTED)
	public class Api {
		private String path;
		private String method;
		private Date timestamp;
		private int status;

		public Api(HttpServletRequest request) {
			this.path = request.getRequestURI();
			this.method = request.getMethod();
			this.timestamp = new Date();
			this.status = 200;
		}

		public Api(int status, HttpServletRequest request) {
			this.path = request.getRequestURI();
			this.method = request.getMethod();
			this.timestamp = new Date();
			this.status = status;
		}

		public String getPath() {
			return path;
		}

		public void setPath(HttpServletRequest request) {
			this.path = request.getRequestURI();
		}

	}

	public ResponseVO(HttpServletRequest request) {
		this.api = new Api(request);
	}

	public ResponseVO(int status, HttpServletRequest request) {
		this.api = new Api(status, request);
	}

	public ResponseVO(HttpServletRequest request, T object) {
		this.api = new Api(request);
		this.response = object;
	}

	public ResponseVO(int status, HttpServletRequest request, T object) {
		this.api = new Api(status, request);
		this.response = object;
	}

}
