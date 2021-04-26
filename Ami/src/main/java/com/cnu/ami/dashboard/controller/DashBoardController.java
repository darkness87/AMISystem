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
import com.cnu.ami.common.PropertyData;
import com.cnu.ami.common.ResponseArrayVO;
import com.cnu.ami.common.ResponseListVO;
import com.cnu.ami.common.ResponseVO;
import com.cnu.ami.common.SystemException;
import com.cnu.ami.dashboard.models.DashBoardMapVO;
import com.cnu.ami.dashboard.models.DeviceRegVO;
import com.cnu.ami.dashboard.models.FailureAllVO;
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

	@Autowired
	PropertyData propertyData;

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
			}).log();
		}

	}

	@RequestMapping(value = "/readingrateAll/dayOne", method = RequestMethod.GET, produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	@ResponseStatus(value = HttpStatus.OK)
	@Description(value = "현황판 : 검침률")
	public Flux<ResponseVO<RateVO>> getElectricMeterReadingRateDayAll(HttpServletRequest request,
			@RequestParam(required = false, defaultValue = "0") int duration) throws Exception {
		if (duration == 0) { // 0일 경우 1회 전달
			return Flux.just(new ResponseVO<RateVO>(request, dashBoardService.getElectricMeterReadingRateDayAll()));
		} else {
			return Flux.interval(Duration.ofSeconds(duration)).map(response -> {
				try {
					return new ResponseVO<RateVO>(request, dashBoardService.getElectricMeterReadingRateDayAll());
				} catch (Exception e) {
					throw new SystemException(HttpStatus.UNAUTHORIZED, ExceptionConst.FAIL, "" + e);
				}
			}).log();
		}

	}

	@RequestMapping(value = "/failureAll/dayhour", method = RequestMethod.GET, produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	@ResponseStatus(value = HttpStatus.OK)
	@Description(value = "현황판 : 금일 상태(장애) 정보")
	public Flux<ResponseVO<FailureAllVO>> getElectricFailureDayHourAll(HttpServletRequest request,
			@RequestParam(required = false, defaultValue = "0") int duration) throws Exception {
//		FailureAllVO data = dashBoardService.getElectricFailureDayHourAll();
//		return Mono.just(new ResponseVO<FailureAllVO>(request, data));

		if (duration == 0) { // 0일 경우 1회 전달
			return Flux.just(new ResponseVO<FailureAllVO>(request, dashBoardService.getElectricFailureDayHourAll()));
		} else {
			return Flux.interval(Duration.ofSeconds(duration)).map(response -> {
				try {
					return new ResponseVO<FailureAllVO>(request, dashBoardService.getElectricFailureDayHourAll());
				} catch (Exception e) {
					throw new SystemException(HttpStatus.UNAUTHORIZED, ExceptionConst.FAIL, "" + e);
				}
			}).log();
		}

	}

	@RequestMapping(value = "/weather/realtime", method = RequestMethod.GET, produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	@ResponseStatus(value = HttpStatus.OK)
	@Description(value = "현황판 : 오늘의 날씨")
	public Flux<ResponseVO<WeatherVO>> getWeatherRealtimeAll(HttpServletRequest request,
			@RequestParam(required = false, defaultValue = "0") int duration) throws Exception {
//		WeatherVO data = dashBoardService.getWeatherRealtimeAll();
//		return Mono.just(new ResponseVO<WeatherVO>(request, data));

		if (duration == 0) { // 0일 경우 1회 전달
			return Flux.just(new ResponseVO<WeatherVO>(request, dashBoardService.getWeatherRealtimeAll()));
		} else {
			return Flux.interval(Duration.ofSeconds(duration)).map(response -> {
				try {
					return new ResponseVO<WeatherVO>(request, dashBoardService.getWeatherRealtimeAll());
				} catch (Exception e) {
					throw new SystemException(HttpStatus.UNAUTHORIZED, ExceptionConst.FAIL, "" + e);
				}
			}).log();
		}
	}

	@RequestMapping(value = "/weather/data", method = RequestMethod.GET, produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	@ResponseStatus(value = HttpStatus.OK)
	@Description(value = "현황판 : 데이터날씨")
	public Flux<ResponseVO<WeatherDataVO>> getWeatherDataWeatherAll(HttpServletRequest request,
			@RequestParam(required = false, defaultValue = "0") int duration) throws Exception {
		if (duration == 0) { // 0일 경우 1회 전달
			return Flux.just(new ResponseVO<WeatherDataVO>(request, dashBoardService.getWeatherDataWeatherAll()));
		} else {
			return Flux.interval(Duration.ofSeconds(duration)).map(response -> {
				try {
					return new ResponseVO<WeatherDataVO>(request, dashBoardService.getWeatherDataWeatherAll());
				} catch (Exception e) {
					throw new SystemException(HttpStatus.UNAUTHORIZED, ExceptionConst.FAIL, "" + e);
				}
			}).log();
		}
	}

	@RequestMapping(value = "/location/failure/mapinfo", method = RequestMethod.GET, produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	@ResponseStatus(value = HttpStatus.OK)
	@Description(value = "현황판 : 지도 정보")
	public Flux<ResponseListVO<DashBoardMapVO>> getLocationFailureMapInfo(HttpServletRequest request,
			@RequestParam(required = false, defaultValue = "0") int duration) throws Exception {
//		List<DashBoardMapVO> data = dashBoardService.getLocationFailureMapInfo();
//		return Mono.just(new ResponseListVO<DashBoardMapVO>(request, data));

		if (duration == 0) { // 0일 경우 1회 전달
			return Flux.just(new ResponseListVO<DashBoardMapVO>(request, dashBoardService.getLocationFailureMapInfo()));
		} else {
			return Flux.interval(Duration.ofSeconds(duration)).map(response -> {
				try {
					return new ResponseListVO<DashBoardMapVO>(request, dashBoardService.getLocationFailureMapInfo());
				} catch (Exception e) {
					throw new SystemException(HttpStatus.UNAUTHORIZED, ExceptionConst.FAIL, "" + e);
				}
			}).log();
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
//		List<DeviceRegVO> data = dashBoardService.getElectricRegistrationDevice();
//		return Mono.just(new ResponseListVO<DeviceRegVO>(request, data));

		if (duration == 0) { // 0일 경우 1회 전달
			return Flux
					.just(new ResponseListVO<DeviceRegVO>(request, dashBoardService.getElectricRegistrationDevice()));
		} else {
			return Flux.interval(Duration.ofSeconds(duration)).map(response -> {
				try {
					return new ResponseListVO<DeviceRegVO>(request, dashBoardService.getElectricRegistrationDevice());
				} catch (Exception e) {
					throw new SystemException(HttpStatus.UNAUTHORIZED, ExceptionConst.FAIL, "" + e);
				}
			}).log();
		}
	}

	@RequestMapping(value = "/location/use/list", method = RequestMethod.GET, produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	@ResponseStatus(value = HttpStatus.OK)
	@Description(value = "현황판 : 지역별사용량")
	public Flux<ResponseListVO<UseLocationVO>> getLocationUseList(HttpServletRequest request,
			@RequestParam(required = false, defaultValue = "0") int duration) throws Exception {

		if (duration == 0) { // 0일 경우 1회 전달
			return Flux.just(new ResponseListVO<UseLocationVO>(request, dashBoardService.getLocationUseList()));
		} else {
			return Flux.interval(Duration.ofSeconds(duration)).map(response -> {
				try {
					return new ResponseListVO<UseLocationVO>(request, dashBoardService.getLocationUseList());
				} catch (Exception e) {
					throw new SystemException(HttpStatus.UNAUTHORIZED, ExceptionConst.FAIL, "" + e);
				}
			}).log();
		}
	}

	@RequestMapping(value = "/all/data", method = RequestMethod.GET, produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	@ResponseStatus(value = HttpStatus.OK)
	@Description(value = "현황판 : 전체 데이터 API")
	public Flux<ResponseArrayVO> getDashBoardAllData(HttpServletRequest request,
			@RequestParam(required = false, defaultValue = "0") int duration) throws Exception {

		ResponseArrayVO responseArrayVO = new ResponseArrayVO(request);

		responseArrayVO.setUseData(dashBoardService.getElectricUseDayHourAll());
		responseArrayVO.setRate(dashBoardService.getElectricMeterReadingRateDayAll());
		responseArrayVO.setFailureStatus(dashBoardService.getElectricFailureDayHourAll());
		responseArrayVO.setWeather(dashBoardService.getWeatherRealtimeAll());
		responseArrayVO.setWeatherData(dashBoardService.getWeatherDataWeatherAll());
//		responseArrayVO.setMap(dashBoardService.getLocationFailureMapInfo());
//		responseArrayVO.setServer(dashBoardService.getServerManagementInfo());
		responseArrayVO.setDevice(dashBoardService.getElectricRegistrationDevice());
		responseArrayVO.setRegionData(dashBoardService.getLocationUseList());

		if (duration == 0) { // 0일 경우 1회 전달
			return Flux.just(responseArrayVO);
		} else {
			return Flux.interval(Duration.ofSeconds(duration)).map(response -> {
				try {
					return responseArrayVO;
				} catch (Exception e) {
					throw new SystemException(HttpStatus.UNAUTHORIZED, ExceptionConst.FAIL, "" + e);
				}
			}).log();
		}

	}
	
	@RequestMapping(value = "/all/firstdata", method = RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	@Description(value = "현황판 : 전체 데이터 처음 전달 API")
	public Mono<ResponseArrayVO> getDashBoardAllDataFirst(HttpServletRequest request) throws Exception {

		ResponseArrayVO responseArrayVO = new ResponseArrayVO(request);

		responseArrayVO.setUseData(dashBoardService.getElectricUseDayHourAll());
		responseArrayVO.setRate(dashBoardService.getElectricMeterReadingRateDayAll());
		responseArrayVO.setFailureStatus(dashBoardService.getElectricFailureDayHourAll());
		responseArrayVO.setWeather(dashBoardService.getWeatherRealtimeAll());
		responseArrayVO.setWeatherData(dashBoardService.getWeatherDataWeatherAll());
//		responseArrayVO.setMap(dashBoardService.getLocationFailureMapInfo());
//		responseArrayVO.setServer(dashBoardService.getServerManagementInfo());
		responseArrayVO.setDevice(dashBoardService.getElectricRegistrationDevice());
		responseArrayVO.setRegionData(dashBoardService.getLocationUseList());

		return Mono.just(responseArrayVO);

	}
	
//	@RequestMapping(value = "/test/flux", method = RequestMethod.GET, produces = MediaType.TEXT_EVENT_STREAM_VALUE)
//	@ResponseStatus(value = HttpStatus.OK)
//	@Description(value = "현황판 : 테스트 flux event count")
//	public Flux<Object> getTestFluxEvent(HttpServletRequest request,
//			@RequestParam(required = false, defaultValue = "0") int duration) throws Exception {
//
//		// https://luvstudy.tistory.com/100
//
//		WeatherVO weatherVO = new WeatherVO();
//		weatherVO.setDate(new Date());
//		WeatherDataVO weatherDataVO = new WeatherDataVO();
//		weatherDataVO.setDate(new Date());
//
//		if (duration == 0) { // 0일 경우 1회 전달
//			return Flux.just(weatherVO, weatherDataVO);
//		} else {
//			try {
//				return Flux.interval(Duration.ofSeconds(duration))
//						.flatMap(response -> Flux.just(weatherVO, weatherDataVO).log());
//			} catch (Exception e) {
//				throw new SystemException(HttpStatus.UNAUTHORIZED, ExceptionConst.FAIL, "" + e);
//			}
//		}
//
//	}
//	
//	@RequestMapping(value = "/test/flux2", method = RequestMethod.GET, produces = MediaType.TEXT_EVENT_STREAM_VALUE)
//	@ResponseStatus(value = HttpStatus.OK)
//	@Description(value = "현황판 : 테스트 flux event count")
//	public Flux<Object> getTestFluxEvent2(HttpServletRequest request,
//			@RequestParam(required = false, defaultValue = "0") int duration) throws Exception {
//
//		// https://luvstudy.tistory.com/100
//
//		WeatherVO weatherVO = new WeatherVO();
//		weatherVO.setDate(new Date());
//		WeatherDataVO weatherDataVO = new WeatherDataVO();
//		weatherDataVO.setDate(new Date());
//
//		Flux<Object> just = Flux.just(weatherVO, weatherDataVO);
//		Flux<Object> interval = Flux.interval(Duration.ofSeconds(duration)).flatMap(response -> Flux.just(weatherVO, weatherDataVO).log());
//		
//		if (duration == 0) { // 0일 경우 1회 전달
//			return just;
//		} else {
//			try {
//				return Flux.zip(just, interval).flatMap(response -> Flux.just(weatherVO, weatherDataVO).log());
//			} catch (Exception e) {
//				throw new SystemException(HttpStatus.UNAUTHORIZED, ExceptionConst.FAIL, "" + e);
//			}
//		}
//
//	}
//
//	@RequestMapping(value = "/test/flux/data", method = RequestMethod.GET, produces = MediaType.TEXT_EVENT_STREAM_VALUE)
//	@ResponseStatus(value = HttpStatus.OK)
//	@Description(value = "현황판 : 테스트 flux event count")
//	public Flux<Object> getTestFluxEventData(HttpServletRequest request,
//			@RequestParam(required = false, defaultValue = "0") int duration) throws Exception {
//
//		if (duration == 0) { // 0일 경우 1회 전달
//			return Flux.just(dashBoardService.getElectricUseDayHourAll(),
//					dashBoardService.getElectricMeterReadingRateDayAll(),
//					dashBoardService.getElectricFailureDayHourAll(), dashBoardService.getWeatherRealtimeAll(),
//					dashBoardService.getWeatherDataWeatherAll(), dashBoardService.getLocationFailureMapInfo(),
//					dashBoardService.getServerManagementInfo(), dashBoardService.getElectricRegistrationDevice(),
//					dashBoardService.getLocationUseList());
//		} else {
//			return Flux.interval(Duration.ofSeconds(duration)).flatMap(response -> {
//				try {
//					return Flux.just(dashBoardService.getElectricUseDayHourAll(),
//							dashBoardService.getElectricMeterReadingRateDayAll(),
//							dashBoardService.getElectricFailureDayHourAll(), dashBoardService.getWeatherRealtimeAll(),
//							dashBoardService.getWeatherDataWeatherAll(), dashBoardService.getLocationFailureMapInfo(),
//							dashBoardService.getServerManagementInfo(),
//							dashBoardService.getElectricRegistrationDevice(), dashBoardService.getLocationUseList());
//				} catch (Exception e) {
//					throw new SystemException(HttpStatus.UNAUTHORIZED, ExceptionConst.FAIL, "" + e);
//				}
//			}).log();
//		}
//
//	}

}
