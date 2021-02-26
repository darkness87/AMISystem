package com.cnu.ami.dashboard.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Description;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.cnu.ami.common.PropertyData;
import com.cnu.ami.dashboard.models.DashBoardMapVO;
import com.cnu.ami.dashboard.models.DeviceRegVO;
import com.cnu.ami.dashboard.models.FailureAllVO;
import com.cnu.ami.dashboard.models.RateVO;
import com.cnu.ami.dashboard.models.ServerManagementVO;
import com.cnu.ami.dashboard.models.UseDayHourAllVO;
import com.cnu.ami.dashboard.models.WeatherVO;
import com.cnu.ami.dashboard.service.DashBoardService;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

/**
 * 메인현황판
 * 
 * @author skchae@cnuglobal.com
 * @apiNote Main Dashboard Api 전국전력사용량,검침률,금일장애,오늘날씨,데이터날씨,서버운영정보,등록장비,지역별사용량
 */

@Slf4j
@RestController
@RequestMapping(value = "/api/dashboard")
public class DashBoardController {

	@Autowired
	DashBoardService dashBoardService;

	@Autowired
	PropertyData propertyData;

//	@RequestMapping(value = "/test", method = RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	public Object getTestData1() throws Exception {
		log.info("=== Comm Controller ===");
		Object data = dashBoardService.testReadData();
		return data;
	}

	@RequestMapping(value = "/electric/use/dayhour/all", method = RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	@Description(value = "현황판 : 전국전력사용량")
	public Mono<UseDayHourAllVO> getElectricUseDayHourAll(String day) throws Exception {
		log.info("=== DashBoard Api : /api/dashboard/electric/use/dayhour/all ===");
		log.info("day (yyyyMMdd) : {}", day);

		UseDayHourAllVO data = dashBoardService.getElectricUseDayHourAll();

		return Mono.just(data);
	}

	@RequestMapping(value = "/electric/meterreadingrate/day/all", method = RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	@Description(value = "현황판 : 검침률")
	public Mono<RateVO> getElectricMeterReadingRateDayAll(String day) throws Exception {
		log.info("=== DashBoard Api : /api/dashboard/electric/meterreadingrate/day/all ===");
		log.info("day (yyyyMMdd) : {}", day);

		RateVO data = dashBoardService.getElectricMeterReadingRateDayAll();

		return Mono.just(data);
	}

	@RequestMapping(value = "/electric/failure/dayhour/all", method = RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	@Description(value = "현황판 : 금일 장애")
	public Mono<FailureAllVO> getElectricFailureDayHourAll(String day) throws Exception {
		log.info("=== DashBoard Api : /api/dashboard/electric/failure/dayhour/all ===");
		log.info("day (yyyyMMdd) : {}", day);

		FailureAllVO data = dashBoardService.getElectricFailureDayHourAll();

		return Mono.just(data);
	}

	@RequestMapping(value = "/weather/realtime/all", method = RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	@Description(value = "현황판 : 오늘의 날씨")
	public Mono<WeatherVO> getWeatherRealtimeAll() throws Exception {
		log.info("=== DashBoard Api : /api/dashboard/weather/realtime/all ===");

		WeatherVO data = dashBoardService.getWeatherRealtimeAll();

		return Mono.just(data);
	}

	@RequestMapping(value = "/weather/dataweather/all", method = RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	@Description(value = "현황판 : 데이터날씨")
	public Mono<WeatherVO> getWeatherDataWeatherAll() throws Exception {
		log.info("=== DashBoard Api : /api/dashboard/weather/dataweather/all ===");

		WeatherVO data = dashBoardService.getWeatherDataWeatherAll();

		return Mono.just(data);
	}

	@RequestMapping(value = "/electric/location/failure/mapinfo", method = RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	@Description(value = "현황판 : 지도 정보")
	public Mono<List<DashBoardMapVO>> getLocationFailureMapInfo() throws Exception {
		log.info("=== DashBoard Api : /api/dashboard/electric/location/failure/mapinfo ===");

		List<DashBoardMapVO> data = dashBoardService.getLocationFailureMapInfo();

		return Mono.just(data);
	}

	@RequestMapping(value = "/server/management/info", method = RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	@Description(value = "현황판 : 서버운영정보")
	public Mono<ServerManagementVO> getServerManagementInfo() throws Exception {
		log.info("=== DashBoard Api : /api/dashboard/server/management/info ===");

		ServerManagementVO data = dashBoardService.getServerManagementInfo();

		return Mono.just(data);
	}

	@RequestMapping(value = "/electric/registration/device", method = RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	@Description(value = "현황판 : 등록장비")
	public Mono<List<DeviceRegVO>> getElectricRegistrationDevice() throws Exception {
		log.info("=== DashBoard Api : /api/dashboard/electric/registration/device ===");

		List<DeviceRegVO> data = dashBoardService.getElectricRegistrationDevice();

		return Mono.just(data);
	}

	@RequestMapping(value = "/electric/location/use/list", method = RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	@Description(value = "현황판 : 지역별사용량")
	public Mono<Object> getLocationUseList(String day) throws Exception {
		log.info("=== DashBoard Api : /api/dashboard/electric/location/use/list ===");
		log.info("day (yyyyMMdd) : {}", day);

		Object data = dashBoardService.getLocationUseList();

		return Mono.just(data);
	}

}
