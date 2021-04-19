package com.cnu.ami.dashboard.controller;

import java.time.Duration;
import java.util.List;

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
import com.cnu.ami.common.ResponseListVO;
import com.cnu.ami.common.ResponseVO;
import com.cnu.ami.common.SystemException;
import com.cnu.ami.dashboard.models.DashBoardMapVO;
import com.cnu.ami.dashboard.models.DeviceRegVO;
import com.cnu.ami.dashboard.models.FailureAllVO;
import com.cnu.ami.dashboard.models.RateVO;
import com.cnu.ami.dashboard.models.ServerManagementVO;
import com.cnu.ami.dashboard.models.UseDayHourAllVO;
import com.cnu.ami.dashboard.models.WeatherVO;
import com.cnu.ami.dashboard.service.DashBoardService;

import reactor.core.publisher.Flux;
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

	@RequestMapping(value = "/useAll/dayhour", method = RequestMethod.GET, produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	@ResponseStatus(value = HttpStatus.OK)
	@Description(value = "현황판 : 전국전력사용량")
	public Flux<ResponseVO<UseDayHourAllVO>> getElectricUseDayHourAll(HttpServletRequest request,
			@RequestParam int duration) throws Exception {

		if (duration == 0) { // 0일 경우 1회 전달
			return Flux.just(dashBoardService.getElectricUseDayHourAll(request));
		} else {

			return Flux.interval(Duration.ofSeconds(duration)).map(response -> {
				try {
					return dashBoardService.getElectricUseDayHourAll(request);
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
			@RequestParam int duration) throws Exception {
		if (duration == 0) { // 0일 경우 1회 전달
			return Flux.just(dashBoardService.getElectricMeterReadingRateDayAll(request));
		} else {
			return Flux.interval(Duration.ofSeconds(duration)).map(response -> {
				try {
					return dashBoardService.getElectricMeterReadingRateDayAll(request);
				} catch (Exception e) {
					throw new SystemException(HttpStatus.UNAUTHORIZED, ExceptionConst.FAIL, "" + e);
				}
			}).log();
		}

	}

	@RequestMapping(value = "/failureAll/dayhour", method = RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	@Description(value = "현황판 : 금일 장애")
	public Mono<ResponseVO<FailureAllVO>> getElectricFailureDayHourAll(HttpServletRequest request) throws Exception {
		FailureAllVO data = dashBoardService.getElectricFailureDayHourAll();

		return Mono.just(new ResponseVO<FailureAllVO>(request, data));
	}

	@RequestMapping(value = "/weather/realtime", method = RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	@Description(value = "현황판 : 오늘의 날씨")
	public Mono<ResponseVO<WeatherVO>> getWeatherRealtimeAll(HttpServletRequest request) throws Exception {
		WeatherVO data = dashBoardService.getWeatherRealtimeAll();

		return Mono.just(new ResponseVO<WeatherVO>(request, data));
	}

	@RequestMapping(value = "/weather/data", method = RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	@Description(value = "현황판 : 데이터날씨")
	public Mono<ResponseVO<WeatherVO>> getWeatherDataWeatherAll(HttpServletRequest request) throws Exception {
		WeatherVO data = dashBoardService.getWeatherDataWeatherAll();

		return Mono.just(new ResponseVO<WeatherVO>(request, data));
	}

	@RequestMapping(value = "/location/failure/mapinfo", method = RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	@Description(value = "현황판 : 지도 정보")
	public Mono<ResponseListVO<DashBoardMapVO>> getLocationFailureMapInfo(HttpServletRequest request) throws Exception {
		List<DashBoardMapVO> data = dashBoardService.getLocationFailureMapInfo();

		return Mono.just(new ResponseListVO<DashBoardMapVO>(request, data));
	}

	@RequestMapping(value = "/server/management/info", method = RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	@Description(value = "현황판 : 서버운영정보")
	public Flux<ResponseVO<ServerManagementVO>> getServerManagementInfo(HttpServletRequest request,
			@RequestParam int duration) throws Exception {

		if (duration == 0) { // 0일 경우 디폴트 15초
			return Flux.just(dashBoardService.getServerManagementInfo(request));
		} else {

			return Flux.interval(Duration.ofSeconds(duration)).map(response -> {
				try {
					return dashBoardService.getServerManagementInfo(request);
				} catch (Exception e) {
					throw new SystemException(HttpStatus.UNAUTHORIZED, ExceptionConst.FAIL, "" + e);
				}
			});

		}

	}

	@RequestMapping(value = "/registration/device", method = RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	@Description(value = "현황판 : 등록설비")
	public Mono<ResponseListVO<DeviceRegVO>> getElectricRegistrationDevice(HttpServletRequest request)
			throws Exception {
		List<DeviceRegVO> data = dashBoardService.getElectricRegistrationDevice();

		return Mono.just(new ResponseListVO<DeviceRegVO>(request, data));
	}

	@RequestMapping(value = "/location/use/list", method = RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	@Description(value = "현황판 : 지역별사용량")
	public Mono<Object> getLocationUseList(HttpServletRequest request, @RequestParam String day) throws Exception {
		Object data = dashBoardService.getLocationUseList();

		return Mono.just(new ResponseVO<Object>(request, data));
	}

//	@RequestMapping(value = "/sse/test/test", method = RequestMethod.GET, produces = MediaType.TEXT_EVENT_STREAM_VALUE)
//	@ResponseStatus(value = HttpStatus.OK)
//	@Description(value = "SSE : SSE 테스트")
//	public Flux<RateVO> getSSETest(HttpServletRequest request) throws Exception {
//
//		RateVO data = dashBoardService.getElectricMeterReadingRateDayAll();
//
//		return Flux.interval(Duration.ofSeconds(15)).map(response -> data).log();
//
//	}
//
//	@RequestMapping(value = "/sse/test/test2", method = RequestMethod.GET, produces = MediaType.TEXT_EVENT_STREAM_VALUE)
//	@ResponseStatus(value = HttpStatus.OK)
//	@Description(value = "SSE : SSE 테스트2")
//	public Flux<RateVO> getSSETest2(HttpServletRequest request) throws Exception {
//
//		return Flux.interval(Duration.ofSeconds(15)).map(response -> {
//			try {
//				return dashBoardService.getElectricMeterReadingRateDayAll();
//			} catch (Exception e) {
//				throw new SystemException(HttpStatus.UNAUTHORIZED, ExceptionConst.FAIL, "" + e);
//			}
//		}).log();
//
//	}
//
//	@RequestMapping(value = "/sse/test/test3", method = RequestMethod.GET, produces = MediaType.TEXT_EVENT_STREAM_VALUE)
//	@ResponseStatus(value = HttpStatus.OK)
//	@Description(value = "SSE : SSE 테스트3")
//	public Flux<ResponseVO<RateVO>> getSSETest3(HttpServletRequest request) throws Exception {
//
//		return Flux.interval(Duration.ofSeconds(15)).map(response -> {
//			try {
//				return dashBoardService.getFluxElectricMeterReadingRateDayAll(request);
//			} catch (Exception e) {
//				throw new SystemException(HttpStatus.UNAUTHORIZED, ExceptionConst.FAIL, "" + e);
//			}
//		}).log();
//
//	}

}
