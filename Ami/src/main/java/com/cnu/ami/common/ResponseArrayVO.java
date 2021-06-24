package com.cnu.ami.common;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.cnu.ami.dashboard.models.DashBoardMapVO;
import com.cnu.ami.dashboard.models.DeviceErrorCountVO;
import com.cnu.ami.dashboard.models.RateVO;
import com.cnu.ami.dashboard.models.ReadingDayInfoVO;
import com.cnu.ami.dashboard.models.UseDayHourAllVO;
import com.cnu.ami.dashboard.models.WeatherDataVO;
import com.cnu.ami.dashboard.models.WeatherVO;

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
public class ResponseArrayVO {

	private Api api;
	private UseDayHourAllVO useData;
	private RateVO rate;
	private WeatherVO weather;
	private WeatherDataVO weatherData;
	private List<DashBoardMapVO> map;
	private DeviceErrorCountVO deviceMapErrorCount;
	private ReadingDayInfoVO readingDayInfo;

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

	public ResponseArrayVO(HttpServletRequest request) {
		this.api = new Api(request);
	}

	public ResponseArrayVO(int status, HttpServletRequest request) {
		this.api = new Api(status, request);
	}

}
