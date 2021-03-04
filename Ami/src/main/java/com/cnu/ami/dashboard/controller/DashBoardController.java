package com.cnu.ami.dashboard.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Description;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.cnu.ami.common.PropertyData;
import com.cnu.ami.common.ResponseListVO;
import com.cnu.ami.common.ResponseVO;
import com.cnu.ami.dashboard.models.DashBoardMapVO;
import com.cnu.ami.dashboard.models.DeviceRegVO;
import com.cnu.ami.dashboard.models.FailureAllVO;
import com.cnu.ami.dashboard.models.RateVO;
import com.cnu.ami.dashboard.models.ServerManagementVO;
import com.cnu.ami.dashboard.models.UseDayHourAllVO;
import com.cnu.ami.dashboard.models.WeatherVO;
import com.cnu.ami.dashboard.service.DashBoardService;

import reactor.core.publisher.Mono;

/**
 * 메인현황판
 * 
 * @author skchae@cnuglobal.com
 * @apiNote Main Dashboard Api 전국전력사용량,검침률,금일장애,오늘날씨,데이터날씨,서버운영정보,등록장비,지역별사용량
 */

@RestController
@RequestMapping(value = "/api/dashboard")
public class DashBoardController {

	@Autowired
	DashBoardService dashBoardService;

	@Autowired
	PropertyData propertyData;

	@RequestMapping(value = "/electric/use/dayhour/all", method = RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	@Description(value = "현황판 : 전국전력사용량")
	public Mono<ResponseVO<UseDayHourAllVO>> getElectricUseDayHourAll(HttpServletRequest request, @RequestParam String day) throws Exception {
		UseDayHourAllVO data = dashBoardService.getElectricUseDayHourAll();

		return Mono.just(new ResponseVO<UseDayHourAllVO>(request, data));
	}

	@RequestMapping(value = "/electric/meterreadingrate/day/all", method = RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	@Description(value = "현황판 : 검침률")
	public Mono<ResponseVO<RateVO>> getElectricMeterReadingRateDayAll(HttpServletRequest request, @RequestParam String day) throws Exception {
		RateVO data = dashBoardService.getElectricMeterReadingRateDayAll();

		return Mono.just(new ResponseVO<RateVO>(request, data));
	}

	@RequestMapping(value = "/electric/failure/dayhour/all", method = RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	@Description(value = "현황판 : 금일 장애")
	public Mono<ResponseVO<FailureAllVO>> getElectricFailureDayHourAll(HttpServletRequest request, @RequestParam String day) throws Exception {
		FailureAllVO data = dashBoardService.getElectricFailureDayHourAll();

		return Mono.just(new ResponseVO<FailureAllVO>(request, data));
	}

	@RequestMapping(value = "/weather/realtime/all", method = RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	@Description(value = "현황판 : 오늘의 날씨")
	public Mono<ResponseVO<WeatherVO>> getWeatherRealtimeAll(HttpServletRequest request) throws Exception {
		WeatherVO data = dashBoardService.getWeatherRealtimeAll();

		return Mono.just(new ResponseVO<WeatherVO>(request, data));
	}

	@RequestMapping(value = "/weather/dataweather/all", method = RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	@Description(value = "현황판 : 데이터날씨")
	public Mono<ResponseVO<WeatherVO>> getWeatherDataWeatherAll(HttpServletRequest request) throws Exception {
		WeatherVO data = dashBoardService.getWeatherDataWeatherAll();

		return Mono.just(new ResponseVO<WeatherVO>(request, data));
	}

	@RequestMapping(value = "/electric/location/failure/mapinfo", method = RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	@Description(value = "현황판 : 지도 정보")
	public Mono<ResponseListVO<DashBoardMapVO>> getLocationFailureMapInfo(HttpServletRequest request) throws Exception {
		List<DashBoardMapVO> data = dashBoardService.getLocationFailureMapInfo();

		return Mono.just(new ResponseListVO<DashBoardMapVO>(request, data));
	}

	@RequestMapping(value = "/server/management/info", method = RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	@Description(value = "현황판 : 서버운영정보")
	public Mono<ResponseVO<ServerManagementVO>> getServerManagementInfo(HttpServletRequest request) throws Exception {
		ServerManagementVO data = dashBoardService.getServerManagementInfo();

		return Mono.just(new ResponseVO<ServerManagementVO>(request, data));
	}

	@RequestMapping(value = "/electric/registration/device", method = RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	@Description(value = "현황판 : 등록장비")
	public Mono<ResponseListVO<DeviceRegVO>> getElectricRegistrationDevice(HttpServletRequest request) throws Exception {
		List<DeviceRegVO> data = dashBoardService.getElectricRegistrationDevice();

		return Mono.just(new ResponseListVO<DeviceRegVO>(request, data));
	}

	@RequestMapping(value = "/electric/location/use/list", method = RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	@Description(value = "현황판 : 지역별사용량")
	public Mono<Object> getLocationUseList(HttpServletRequest request, @RequestParam String day) throws Exception {
		Object data = dashBoardService.getLocationUseList();

		return Mono.just(new ResponseVO<Object>(request, data));
	}

}
