package com.cnu.ami.dashboard.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.cnu.ami.common.ResponseVO;
import com.cnu.ami.dashboard.models.DashBoardMapVO;
import com.cnu.ami.dashboard.models.DeviceRegVO;
import com.cnu.ami.dashboard.models.FailureAllVO;
import com.cnu.ami.dashboard.models.RateVO;
import com.cnu.ami.dashboard.models.ServerManagementVO;
import com.cnu.ami.dashboard.models.UseDayHourAllVO;
import com.cnu.ami.dashboard.models.WeatherDataVO;
import com.cnu.ami.dashboard.models.WeatherVO;

public interface DashBoardService {

	// DashBoard
	public ResponseVO<UseDayHourAllVO> getElectricUseDayHourAll(HttpServletRequest request) throws Exception;

	public ResponseVO<RateVO> getElectricMeterReadingRateDayAll(HttpServletRequest request) throws Exception;

	public FailureAllVO getElectricFailureDayHourAll() throws Exception;

	public WeatherVO getWeatherRealtimeAll() throws Exception;

	public WeatherDataVO getWeatherDataWeatherAll() throws Exception;

	public List<DashBoardMapVO> getLocationFailureMapInfo() throws Exception;

	public ResponseVO<ServerManagementVO> getServerManagementInfo(HttpServletRequest request) throws Exception;

	public List<DeviceRegVO> getElectricRegistrationDevice() throws Exception;

	public List<Object> getLocationUseList() throws Exception;

}
