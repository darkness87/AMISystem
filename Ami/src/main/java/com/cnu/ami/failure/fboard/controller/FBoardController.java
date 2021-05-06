package com.cnu.ami.failure.fboard.controller;

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
import com.cnu.ami.common.ResponseFailureArrayVO;
import com.cnu.ami.common.ResponseListVO;
import com.cnu.ami.common.ResponseVO;
import com.cnu.ami.common.SystemException;
import com.cnu.ami.dashboard.models.DashBoardMapVO;
import com.cnu.ami.dashboard.models.FailureAllVO;
import com.cnu.ami.failure.fboard.models.FailureCompareVO;
import com.cnu.ami.failure.fboard.models.FailureRegionAggrVO;
import com.cnu.ami.failure.fboard.service.FBoardService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * 
 * @author skchae@cnuglobal.com
 * @apiNote FBoard api
 *
 */

@RestController
@RequestMapping(value = "/api/failure/fboard")
public class FBoardController {

	@Autowired
	FBoardService fBoardService;

	@Autowired
	PropertyData propertyData;

	@RequestMapping(value = "/all/dayhour", method = RequestMethod.GET, produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	@ResponseStatus(value = HttpStatus.OK)
	@Description(value = "장애현황판 : 금일 상태(장애) 정보")
	public Flux<ResponseVO<FailureAllVO>> getElectricFailureDayHourAll(HttpServletRequest request,
			@RequestParam(required = false, defaultValue = "0") int duration) throws Exception {

		if (duration == 0) { // 0일 경우 1회 전달
			return Flux.just(new ResponseVO<FailureAllVO>(request, fBoardService.getElectricFailureDayHourAll()));
		} else {
			return Flux.interval(Duration.ofSeconds(duration)).map(response -> {
				try {
					return new ResponseVO<FailureAllVO>(request, fBoardService.getElectricFailureDayHourAll());
				} catch (Exception e) {
					throw new SystemException(HttpStatus.UNAUTHORIZED, ExceptionConst.FAIL, "" + e);
				}
			}).log();
		}
	}

	@RequestMapping(value = "/location/mapinfo", method = RequestMethod.GET, produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	@ResponseStatus(value = HttpStatus.OK)
	@Description(value = "장애현황판 : 지도 정보")
	public Flux<ResponseListVO<DashBoardMapVO>> getLocationFailureMapInfo(HttpServletRequest request,
			@RequestParam(required = false, defaultValue = "0") int duration) throws Exception {

		if (duration == 0) { // 0일 경우 1회 전달
			return Flux.just(new ResponseListVO<DashBoardMapVO>(request, fBoardService.getLocationFailureMapInfo()));
		} else {
			return Flux.interval(Duration.ofSeconds(duration)).map(response -> {
				try {
					return new ResponseListVO<DashBoardMapVO>(request, fBoardService.getLocationFailureMapInfo());
				} catch (Exception e) {
					throw new SystemException(HttpStatus.UNAUTHORIZED, ExceptionConst.FAIL, "" + e);
				}
			}).log();
		}
	}

	@RequestMapping(value = "/compare/rate", method = RequestMethod.GET, produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	@ResponseStatus(value = HttpStatus.OK)
	@Description(value = "장애현황판 : 상태코드 비교율")
	public Flux<ResponseVO<FailureCompareVO>> getFailureCompareRate(HttpServletRequest request,
			@RequestParam(required = false, defaultValue = "0") int duration) throws Exception {

		if (duration == 0) { // 0일 경우 1회 전달
			return Flux.just(new ResponseVO<FailureCompareVO>(request, fBoardService.getFailureCompare()));
		} else {
			return Flux.interval(Duration.ofSeconds(duration)).map(response -> {
				try {
					return new ResponseVO<FailureCompareVO>(request, fBoardService.getFailureCompare());
				} catch (Exception e) {
					throw new SystemException(HttpStatus.UNAUTHORIZED, ExceptionConst.FAIL, "" + e);
				}
			}).log();
		}
	}

	@RequestMapping(value = "/region/aggregations", method = RequestMethod.GET, produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	@ResponseStatus(value = HttpStatus.OK)
	@Description(value = "장애현황판 : 지역별 집계")
	public Flux<ResponseListVO<FailureRegionAggrVO>> getRegionAggr(HttpServletRequest request,
			@RequestParam(required = false, defaultValue = "0") int duration) throws Exception {

		if (duration == 0) { // 0일 경우 1회 전달
			return Flux.just(new ResponseListVO<FailureRegionAggrVO>(request, fBoardService.getFailureRegionAggr()));
		} else {
			return Flux.interval(Duration.ofSeconds(duration)).map(response -> {
				try {
					return new ResponseListVO<FailureRegionAggrVO>(request, fBoardService.getFailureRegionAggr());
				} catch (Exception e) {
					throw new SystemException(HttpStatus.UNAUTHORIZED, ExceptionConst.FAIL, "" + e);
				}
			}).log();
		}
	}

	@RequestMapping(value = "/all/firstdata", method = RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	@Description(value = "장애현황판 : 전체 데이터 처음 전달 API")
	public Mono<ResponseFailureArrayVO> getDashBoardAllDataFirst(HttpServletRequest request) throws Exception {

		ResponseFailureArrayVO responseFailureArrayVO = new ResponseFailureArrayVO(request);

		responseFailureArrayVO.setFailureStatus(fBoardService.getElectricFailureDayHourAll());
		responseFailureArrayVO.setMap(fBoardService.getLocationFailureMapInfo());
		responseFailureArrayVO.setFailureCompare(fBoardService.getFailureCompare());
		responseFailureArrayVO.setFailureRegion(fBoardService.getFailureRegionAggr());

		return Mono.just(responseFailureArrayVO).log();

	}

}
