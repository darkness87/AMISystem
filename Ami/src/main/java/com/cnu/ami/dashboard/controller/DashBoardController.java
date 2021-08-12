package com.cnu.ami.dashboard.controller;

import java.time.Duration;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Description;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.cnu.ami.common.ExceptionConst;
import com.cnu.ami.common.ResponseArrayVO;
import com.cnu.ami.common.ResponseListVO;
import com.cnu.ami.common.ResponseVO;
import com.cnu.ami.common.SystemException;
import com.cnu.ami.dashboard.models.DashBoardMapVO;
import com.cnu.ami.dashboard.models.DeviceRegVO;
import com.cnu.ami.dashboard.models.FailureAllVO;
import com.cnu.ami.dashboard.models.RateRealVO;
import com.cnu.ami.dashboard.models.RateVO;
import com.cnu.ami.dashboard.models.ServerManagementVO;
import com.cnu.ami.dashboard.models.UseDayHourAllVO;
import com.cnu.ami.dashboard.models.UseLocationVO;
import com.cnu.ami.dashboard.models.WeatherDataVO;
import com.cnu.ami.dashboard.models.WeatherVO;
import com.cnu.ami.dashboard.service.DashBoardService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * 메인현황판 (Flux 적용)
 * 
 * @author skchae@cnuglobal.com
 * @apiNote Main Dashboard Api 전국전력사용량,검침률,금일장애,오늘날씨,데이터날씨,서버운영정보,등록장비,지역별사용량
 */

@RestController
@RequestMapping(value = "/api/dashboard")
public class DashBoardController {

	@Autowired
	DashBoardService dashBoardService;

	@RequestMapping(value = "/useAll/dayhour", method = RequestMethod.GET, produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	@ResponseStatus(value = HttpStatus.OK)
	@Description(value = "현황판 : 전국전력사용량")
	public Flux<ResponseVO<UseDayHourAllVO>> getElectricUseDayHourAll(HttpServletRequest request,
			@RequestParam(required = false, defaultValue = "0") int duration) throws Exception {

		if (duration == 0) { // 0일 경우 1회 전달
			return Flux.just(new ResponseVO<UseDayHourAllVO>(request, dashBoardService.getElectricUseDayHourAll()));
		} else {

			return Flux.interval(Duration.ofSeconds(duration)).map(response -> {
				try {
					return new ResponseVO<UseDayHourAllVO>(request, dashBoardService.getElectricUseDayHourAll());
				} catch (Exception e) {
					throw new SystemException(HttpStatus.UNAUTHORIZED, ExceptionConst.FAIL, "" + e);
				}
			}).log("메인현황판 : 전국전력사용량");
		}

	}

	@RequestMapping(value = "/readingrateAll/dayOne", method = RequestMethod.GET, produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	@ResponseStatus(value = HttpStatus.OK)
	@Description(value = "현황판 : 검침률")
	public Flux<ResponseVO<RateVO>> getElectricMeterReadingRateDayAll(HttpServletRequest request,
			@RequestParam(required = false, defaultValue = "0") int duration) throws Exception {
		
		if (duration == 0) {
			return Flux.just(new ResponseVO<RateVO>(request, dashBoardService.getElectricMeterReadingRateDayAll()));
		} else {
			return Flux.interval(Duration.ofSeconds(duration)).map(response -> {
				try {
					return new ResponseVO<RateVO>(request, dashBoardService.getElectricMeterReadingRateDayAll());
				} catch (Exception e) {
					throw new SystemException(HttpStatus.UNAUTHORIZED, ExceptionConst.FAIL, "" + e);
				}
			}).log("메인현황판 : 검침률");
		}

	}

	@RequestMapping(value = "/failureAll/dayhour", method = RequestMethod.GET, produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	@ResponseStatus(value = HttpStatus.OK)
	@Description(value = "현황판 : 금일 상태(장애) 정보")
	public Flux<ResponseVO<FailureAllVO>> getElectricFailureDayHourAll(HttpServletRequest request,
			@RequestParam(required = false, defaultValue = "0") int duration) throws Exception {

		if (duration == 0) {
			return Flux.just(new ResponseVO<FailureAllVO>(request, dashBoardService.getElectricFailureDayHourAll()));
		} else {
			return Flux.interval(Duration.ofSeconds(duration)).map(response -> {
				try {
					return new ResponseVO<FailureAllVO>(request, dashBoardService.getElectricFailureDayHourAll());
				} catch (Exception e) {
					throw new SystemException(HttpStatus.UNAUTHORIZED, ExceptionConst.FAIL, "" + e);
				}
			}).log("메인현황판 : 상태(장애)정보");
		}

	}

	@RequestMapping(value = "/weather/realtime", method = RequestMethod.GET, produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	@ResponseStatus(value = HttpStatus.OK)
	@Description(value = "현황판 : 오늘의 날씨")
	public Flux<ResponseVO<WeatherVO>> getWeatherRealtimeAll(HttpServletRequest request,
			@RequestParam(required = false, defaultValue = "0") int duration) throws Exception {

		if (duration == 0) {
			return Flux.just(new ResponseVO<WeatherVO>(request, dashBoardService.getWeatherRealtimeAll()));
		} else {
			return Flux.interval(Duration.ofSeconds(duration)).map(response -> {
				try {
					return new ResponseVO<WeatherVO>(request, dashBoardService.getWeatherRealtimeAll());
				} catch (Exception e) {
					throw new SystemException(HttpStatus.UNAUTHORIZED, ExceptionConst.FAIL, "" + e);
				}
			}).log("메인현황판 : 오늘의날씨");
		}

	}

	@RequestMapping(value = "/weather/data", method = RequestMethod.GET, produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	@ResponseStatus(value = HttpStatus.OK)
	@Description(value = "현황판 : 데이터날씨")
	public Flux<ResponseVO<WeatherDataVO>> getWeatherDataWeatherAll(HttpServletRequest request,
			@RequestParam(required = false, defaultValue = "0") int duration) throws Exception {
		
		if (duration == 0) {
			return Flux.just(new ResponseVO<WeatherDataVO>(request, dashBoardService.getWeatherDataWeatherAll()));
		} else {
			return Flux.interval(Duration.ofSeconds(duration)).map(response -> {
				try {
					return new ResponseVO<WeatherDataVO>(request, dashBoardService.getWeatherDataWeatherAll());
				} catch (Exception e) {
					throw new SystemException(HttpStatus.UNAUTHORIZED, ExceptionConst.FAIL, "" + e);
				}
			}).log("메인현황판 : 데이터날씨");
		}

	}

	@RequestMapping(value = "/location/rate/mapinfo", method = RequestMethod.GET, produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	@ResponseStatus(value = HttpStatus.OK)
	@Description(value = "현황판 : 지도 정보")
	public Flux<ResponseListVO<DashBoardMapVO>> getLocationRateMapInfo(HttpServletRequest request,
			@RequestParam(required = false, defaultValue = "0") int duration) throws Exception {

		if (duration == 0) {
			return Flux.just(new ResponseListVO<DashBoardMapVO>(request, dashBoardService.getLocationRateMapInfo()));
		} else {
			return Flux.interval(Duration.ofSeconds(duration)).map(response -> {
				try {
					return new ResponseListVO<DashBoardMapVO>(request, dashBoardService.getLocationRateMapInfo());
				} catch (Exception e) {
					throw new SystemException(HttpStatus.UNAUTHORIZED, ExceptionConst.FAIL, "" + e);
				}
			}).log("메인현황판 : 지도정보");
		}
	}

	@RequestMapping(value = "/server/management/info", method = RequestMethod.GET, produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	@ResponseStatus(value = HttpStatus.OK)
	@Description(value = "현황판 : 서버운영정보")
	public Flux<ResponseVO<ServerManagementVO>> getServerManagementInfo(HttpServletRequest request,
			@RequestParam(required = false, defaultValue = "0") int duration) throws Exception {

		if (duration == 0) { // 0일 경우 디폴트 15초
			return Flux.just(new ResponseVO<ServerManagementVO>(request, dashBoardService.getServerManagementInfo()));
		} else {
			return Flux.interval(Duration.ofSeconds(duration)).map(response -> {
				try {
					return new ResponseVO<ServerManagementVO>(request, dashBoardService.getServerManagementInfo());
				} catch (Exception e) {
					throw new SystemException(HttpStatus.UNAUTHORIZED, ExceptionConst.FAIL, "" + e);
				}
			});
		}

	}

	@RequestMapping(value = "/registration/device", method = RequestMethod.GET, produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	@ResponseStatus(value = HttpStatus.OK)
	@Description(value = "현황판 : 등록설비")
	public Flux<ResponseListVO<DeviceRegVO>> getElectricRegistrationDevice(HttpServletRequest request,
			@RequestParam(required = false, defaultValue = "0") int duration) throws Exception {

		if (duration == 0) {
			return Flux.just(new ResponseListVO<DeviceRegVO>(request, dashBoardService.getElectricRegistrationDevice()));
		} else {
			return Flux.interval(Duration.ofSeconds(duration)).map(response -> {
				try {
					return new ResponseListVO<DeviceRegVO>(request, dashBoardService.getElectricRegistrationDevice());
				} catch (Exception e) {
					throw new SystemException(HttpStatus.UNAUTHORIZED, ExceptionConst.FAIL, "" + e);
				}
			}).log("메인현황판 : 등록설비");
		}

	}

	@RequestMapping(value = "/location/use/list", method = RequestMethod.GET, produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	@ResponseStatus(value = HttpStatus.OK)
	@Description(value = "현황판 : 지역별사용량")
	public Flux<ResponseListVO<UseLocationVO>> getLocationUseList(HttpServletRequest request,
			@RequestParam(required = false, defaultValue = "0") int duration) throws Exception {

		if (duration == 0) {
			return Flux.just(new ResponseListVO<UseLocationVO>(request, dashBoardService.getLocationUseList()));
		} else {
			return Flux.interval(Duration.ofSeconds(duration)).map(response -> {
				try {
					return new ResponseListVO<UseLocationVO>(request, dashBoardService.getLocationUseList());
				} catch (Exception e) {
					throw new SystemException(HttpStatus.UNAUTHORIZED, ExceptionConst.FAIL, "" + e);
				}
			}).log("메인현황판 : 지역별사용량");
		}

	}

	@RequestMapping(value = "/all/data", method = RequestMethod.GET, produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	@ResponseStatus(value = HttpStatus.OK)
	@Description(value = "현황판 : 전체 데이터 API")
	public Flux<ResponseArrayVO> getDashBoardAllData(HttpServletRequest request,
			@RequestParam(required = false, defaultValue = "0") int duration) throws Exception {

		if (duration == 0) {
			ResponseArrayVO responseArrayVO = new ResponseArrayVO(request);
			
			responseArrayVO.setUseData(dashBoardService.getElectricUseDayHourAll());
			responseArrayVO.setWeather(dashBoardService.getWeatherRealtimeAll());
			responseArrayVO.setWeatherData(dashBoardService.getWeatherDataWeatherAll());
			responseArrayVO.setDeviceRegErrorCount(dashBoardService.getDeviceErrorCount());
			responseArrayVO.setReadingDayInfo(dashBoardService.getReadingDayInfo());
			
			return Flux.just(responseArrayVO);
		} else {
			return Flux.interval(Duration.ofSeconds(duration)).map(response -> {
				try {
					ResponseArrayVO responseArray = new ResponseArrayVO(request);
					
					responseArray.setUseData(dashBoardService.getElectricUseDayHourAll());
					responseArray.setWeather(dashBoardService.getWeatherRealtimeAll());
					responseArray.setWeatherData(dashBoardService.getWeatherDataWeatherAll());
					responseArray.setDeviceRegErrorCount(dashBoardService.getDeviceErrorCount());
					responseArray.setReadingDayInfo(dashBoardService.getReadingDayInfo());
					
					return responseArray;
				} catch (Exception e) {
					throw new SystemException(HttpStatus.UNAUTHORIZED, ExceptionConst.FAIL, "" + e);
				}
			}).log("메인현황판 : 전체데이터 API");
		}

	}
	
	@RequestMapping(value = "/all/firstdata", method = RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	@Description(value = "현황판 : 전체 데이터 처음 전달 API")
	public Mono<ResponseArrayVO> getDashBoardAllDataFirst(HttpServletRequest request) throws Exception {

		ResponseArrayVO responseArrayVO = new ResponseArrayVO(request);

		responseArrayVO.setUseData(dashBoardService.getElectricUseDayHourAll());
		responseArrayVO.setWeather(dashBoardService.getWeatherRealtimeAll());
		responseArrayVO.setWeatherData(dashBoardService.getWeatherDataWeatherAll());
		responseArrayVO.setDeviceRegErrorCount(dashBoardService.getDeviceErrorCount());
		responseArrayVO.setReadingDayInfo(dashBoardService.getReadingDayInfo());

		return Mono.just(responseArrayVO).log("메인현황판 : First 데이터");
	}
	
	@RequestMapping(value = "/rate/firstrate", method = RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	@Description(value = "현황판 : 시간별검침율 데이터 처음 전달 API")
	public Mono<ResponseArrayVO> getDashBoardAllDataFirstRate(HttpServletRequest request) throws Exception {

		ResponseArrayVO responseArrayVO = new ResponseArrayVO(request);
		responseArrayVO.setRate(dashBoardService.getReadingRateDayPeriod());

		return Mono.just(responseArrayVO).log("메인현황판 : First Rate 데이터");
	}
	
	@RequestMapping(value = "/map/firstmap", method = RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	@Description(value = "현황판 : 맵 데이터 처음 전달 API")
	public Mono<ResponseArrayVO> getDashBoardAllDataFirstMap(HttpServletRequest request) throws Exception {

		ResponseArrayVO responseArrayVO = new ResponseArrayVO(request);
		responseArrayVO.setMap(dashBoardService.getLocationRateMapInfo());

		return Mono.just(responseArrayVO).log("메인현황판 : First Map 데이터");
	}
	
	
	@RequestMapping(value = "/readingrateAll/dayHour", method = RequestMethod.GET, produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	@ResponseStatus(value = HttpStatus.OK)
	@Description(value = "현황판 : 시간별 검침률")
	public Flux<ResponseVO<RateRealVO>> getReadingRateDayHourAll(HttpServletRequest request,
			@RequestParam(required = false, defaultValue = "0") int duration) throws Exception {

		if (duration == 0) {
			return Flux.just(new ResponseVO<RateRealVO>(request, dashBoardService.getReadingRateDayPeriod()));
		} else {
			return Flux.interval(Duration.ofSeconds(duration)).map(response -> {
				try {
					return new ResponseVO<RateRealVO>(request, dashBoardService.getReadingRateDayPeriod());
				} catch (Exception e) {
					throw new SystemException(HttpStatus.UNAUTHORIZED, ExceptionConst.FAIL, "" + e);
				}
			}).log("메인현황판 : 시간별 검침률");
		}

	}

}
