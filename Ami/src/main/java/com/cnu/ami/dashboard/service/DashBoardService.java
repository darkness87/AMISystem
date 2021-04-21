package com.cnu.ami.dashboard.service;

import java.util.List;

import com.cnu.ami.dashboard.models.DashBoardMapVO;
import com.cnu.ami.dashboard.models.DeviceRegVO;
import com.cnu.ami.dashboard.models.FailureAllVO;
import com.cnu.ami.dashboard.models.RateVO;
import com.cnu.ami.dashboard.models.ServerManagementVO;
import com.cnu.ami.dashboard.models.UseDayHourAllVO;
import com.cnu.ami.dashboard.models.UseLocationVO;
import com.cnu.ami.dashboard.models.WeatherDataVO;
import com.cnu.ami.dashboard.models.WeatherVO;

public interface DashBoardService {

	// DashBoard
	public UseDayHourAllVO getElectricUseDayHourAll() throws Exception;

	public RateVO getElectricMeterReadingRateDayAll() throws Exception;

	public FailureAllVO getElectricFailureDayHourAll() throws Exception;

	public WeatherVO getWeatherRealtimeAll() throws Exception;

	public WeatherDataVO getWeatherDataWeatherAll() throws Exception;

	public List<DashBoardMapVO> getLocationFailureMapInfo() throws Exception;

	public ServerManagementVO getServerManagementInfo() throws Exception;

	public List<DeviceRegVO> getElectricRegistrationDevice() throws Exception;

	public List<UseLocationVO> getLocationUseList() throws Exception;

}
