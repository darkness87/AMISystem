package com.cnu.ami.common;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.cnu.ami.dashboard.models.DashBoardMapVO;
import com.cnu.ami.dashboard.models.FailureAllVO;
import com.cnu.ami.failure.fboard.models.FailureCompareVO;
import com.cnu.ami.failure.fboard.models.FailureRegionAggrVO;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Response Object return
 * 
 * @author sookwon
 */
@Getter
@Setter
public class ResponseFailureArrayVO {

	private Api api;
	private FailureAllVO failureStatus;
	private List<DashBoardMapVO> map;
	private FailureCompareVO failureCompare;
	private List<FailureRegionAggrVO> failureRegion;

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

	public ResponseFailureArrayVO(HttpServletRequest request) {
		this.api = new Api(request);
	}

	public ResponseFailureArrayVO(int status, HttpServletRequest request) {
		this.api = new Api(status, request);
	}

}
