package com.cnu.ami.metering.mboard.controller;

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
import com.cnu.ami.common.ResponseListVO;
import com.cnu.ami.common.ResponseReadingArrayVO;
import com.cnu.ami.common.ResponseVO;
import com.cnu.ami.common.SystemException;
import com.cnu.ami.metering.mboard.models.DashReadingMapVO;
import com.cnu.ami.metering.mboard.models.LpCountVO;
import com.cnu.ami.metering.mboard.models.MeterRateVO;
import com.cnu.ami.metering.mboard.models.ReadingRegionAggrVO;
import com.cnu.ami.metering.mboard.service.MBoardService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * 
 * @author skchae@cnuglobal.com
 * @apiNote mboard api
 *
 */

@RestController
@RequestMapping(value = "/api/metering/mboard")
public class MBoardController {

	@Autowired
	MBoardService mBoardService;

	@Autowired
	PropertyData propertyData;

	@RequestMapping(value = "/lp/count", method = RequestMethod.GET, produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	@ResponseStatus(value = HttpStatus.OK)
	@Description(value = "검침현황판 : 금일 검침 데이터 수집수")
	public Flux<ResponseListVO<LpCountVO>> getElectricFailureDayHourAll(HttpServletRequest request,
			@RequestParam(required = false, defaultValue = "0") int duration) throws Exception {

		if (duration == 0) { // 0일 경우 1회 전달
			return Flux.just(new ResponseListVO<LpCountVO>(request, mBoardService.getElectricLPDataCount()));
		} else {
			return Flux.interval(Duration.ofSeconds(duration)).map(response -> {
				try {
					return new ResponseListVO<LpCountVO>(request, mBoardService.getElectricLPDataCount());
				} catch (Exception e) {
					throw new SystemException(HttpStatus.UNAUTHORIZED, ExceptionConst.FAIL, "" + e);
				}
			}).log("검침현황판 : 데이터수집수");
		}
	}

	@RequestMapping(value = "/location/mapinfo", method = RequestMethod.GET, produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	@ResponseStatus(value = HttpStatus.OK)
	@Description(value = "검침현황판 : 지도 정보")
	public Flux<ResponseListVO<DashReadingMapVO>> getLocationFailureMapInfo(HttpServletRequest request,
			@RequestParam(required = false, defaultValue = "0") int duration) throws Exception {

		if (duration == 0) { // 0일 경우 1회 전달
			return Flux.just(new ResponseListVO<DashReadingMapVO>(request, mBoardService.getLocationMapInfo()));
		} else {
			return Flux.interval(Duration.ofSeconds(duration)).map(response -> {
				try {
					return new ResponseListVO<DashReadingMapVO>(request, mBoardService.getLocationMapInfo());
				} catch (Exception e) {
					throw new SystemException(HttpStatus.UNAUTHORIZED, ExceptionConst.FAIL, "" + e);
				}
			}).log("검침현황판 : 지도정보");
		}
	}

	@RequestMapping(value = "/readingrateAll/dayOne", method = RequestMethod.GET, produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	@ResponseStatus(value = HttpStatus.OK)
	@Description(value = "검침현황판 : 검침률")
	public Flux<ResponseVO<MeterRateVO>> getElectricMeterReadingRateDayAll(HttpServletRequest request,
			@RequestParam(required = false, defaultValue = "0") int duration) throws Exception {
		if (duration == 0) { // 0일 경우 1회 전달
			return Flux.just(new ResponseVO<MeterRateVO>(request, mBoardService.getElectricMeterReadingRateDayAll()));
		} else {
			return Flux.interval(Duration.ofSeconds(duration)).map(response -> {
				try {
					return new ResponseVO<MeterRateVO>(request, mBoardService.getElectricMeterReadingRateDayAll());
				} catch (Exception e) {
					throw new SystemException(HttpStatus.UNAUTHORIZED, ExceptionConst.FAIL, "" + e);
				}
			}).log("검침현황판 : 검침율");
		}

	}

	@RequestMapping(value = "/region/aggregations", method = RequestMethod.GET, produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	@ResponseStatus(value = HttpStatus.OK)
	@Description(value = "검침현황판 : 지역별 집계")
	public Flux<ResponseListVO<ReadingRegionAggrVO>> getRegionAggr(HttpServletRequest request,
			@RequestParam(required = false, defaultValue = "0") int duration) throws Exception {

		if (duration == 0) { // 0일 경우 1회 전달
			return Flux.just(new ResponseListVO<ReadingRegionAggrVO>(request, mBoardService.getReadingRegionAggr()));
		} else {
			return Flux.interval(Duration.ofSeconds(duration)).map(response -> {
				try {
					return new ResponseListVO<ReadingRegionAggrVO>(request, mBoardService.getReadingRegionAggr());
				} catch (Exception e) {
					throw new SystemException(HttpStatus.UNAUTHORIZED, ExceptionConst.FAIL, "" + e);
				}
			}).log("검침현황판 : 지역별집계");
		}
	}

	@RequestMapping(value = "/all/firstdata", method = RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	@Description(value = "검침현황판 : 전체 데이터 처음 전달 API")
	public Mono<ResponseReadingArrayVO> getDashBoardAllDataFirst(HttpServletRequest request) throws Exception {

		ResponseReadingArrayVO responseReadingArrayVO = new ResponseReadingArrayVO(request);
		
		// TODO 데이터 수집 수 수정 필요
		responseReadingArrayVO.setRate(mBoardService.getElectricMeterReadingRateDayAll());
		responseReadingArrayVO.setRegion(mBoardService.getReadingRegionAggr());
		responseReadingArrayVO.setLp(mBoardService.getElectricLPDataCount());
		responseReadingArrayVO.setMap(mBoardService.getLocationMapInfo());
		
		return Mono.just(responseReadingArrayVO).log("검침현황판 : First 데이터");

	}
	
	@RequestMapping(value = "/lp/firstlp", method = RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	@Description(value = "검침현황판 : LP 데이터 처음 전달 API")
	public Mono<ResponseReadingArrayVO> getDashBoardLpDataFirst(HttpServletRequest request) throws Exception {

		ResponseReadingArrayVO responseReadingArrayVO = new ResponseReadingArrayVO(request);
		
		// TODO 데이터 수집 수 수정 필요
		responseReadingArrayVO.setLp(mBoardService.getElectricLPDataCount());

		return Mono.just(responseReadingArrayVO).log("검침현황판 : LP First 데이터");

	}
	
	@RequestMapping(value = "/rate/firstrate", method = RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	@Description(value = "검침현황판 : Rate 데이터 처음 전달 API")
	public Mono<ResponseReadingArrayVO> getDashBoardRateDataFirst(HttpServletRequest request) throws Exception {

		ResponseReadingArrayVO responseReadingArrayVO = new ResponseReadingArrayVO(request);
		
		responseReadingArrayVO.setRate(mBoardService.getElectricMeterReadingRateDayAll());

		return Mono.just(responseReadingArrayVO).log("검침현황판 : Rate First 데이터");

	}
	
	@RequestMapping(value = "/map/firstmap", method = RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	@Description(value = "검침현황판 : Map 데이터 처음 전달 API")
	public Mono<ResponseReadingArrayVO> getDashBoardMapDataFirst(HttpServletRequest request) throws Exception {

		ResponseReadingArrayVO responseReadingArrayVO = new ResponseReadingArrayVO(request);
		
		responseReadingArrayVO.setMap(mBoardService.getLocationMapInfo());

		return Mono.just(responseReadingArrayVO).log("검침현황판 : Map First 데이터");

	}
	
	@RequestMapping(value = "/region/firstregion", method = RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	@Description(value = "검침현황판 : 전체 데이터 처음 전달 API")
	public Mono<ResponseReadingArrayVO> getDashBoardRegionDataFirst(HttpServletRequest request) throws Exception {

		ResponseReadingArrayVO responseReadingArrayVO = new ResponseReadingArrayVO(request);
		
		responseReadingArrayVO.setRegion(mBoardService.getReadingRegionAggr());

		return Mono.just(responseReadingArrayVO).log("검침현황판 : Region First 데이터");

	}

}
