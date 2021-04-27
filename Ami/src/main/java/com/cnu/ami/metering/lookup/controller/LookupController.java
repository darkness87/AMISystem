package com.cnu.ami.metering.lookup.controller;

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

import com.cnu.ami.common.ResponseListVO;
import com.cnu.ami.metering.lookup.dao.document.RawLpCycleTemp;
import com.cnu.ami.metering.lookup.models.RawLpCycleVO;
import com.cnu.ami.metering.lookup.service.LookupService;

import reactor.core.publisher.Mono;

/**
 * 
 * @author skchae@cnuglobal.com
 * @apiNote lookup api (검침조회)
 *
 */

@RestController
@RequestMapping(value = "/api/metering/lookup")
public class LookupController {

	@Autowired
	LookupService lookupService;

	@RequestMapping(value = "/lp/cycle", method = RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	@Description(value = "검침조회 : 검침 주기별 데이터")
	public Mono<ResponseListVO<RawLpCycleVO>> getMeteringCycleData(HttpServletRequest request,
			@RequestParam(required = false, defaultValue = "0") int estateSeq,
			@RequestParam(required = false, defaultValue = "0") int buildingSeq,
			@RequestParam(required = false, defaultValue = "") String dcuId,
			@RequestParam(required = false, defaultValue = "") String day) throws Exception {

		List<RawLpCycleVO> data = lookupService.getLpCycle(estateSeq, buildingSeq, dcuId, day);

		return Mono.just(new ResponseListVO<RawLpCycleVO>(request, data));
	}

	@RequestMapping(value = "/test/lp/hour", method = RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	@Description(value = "검침조회 : 1시간 데이터")
	public Mono<ResponseListVO<RawLpCycleTemp>> getMeteringHourData(HttpServletRequest request,
			@RequestParam(required = false, defaultValue = "0") int estateSeq,
			@RequestParam(required = false, defaultValue = "0") int buildingSeq,
			@RequestParam(required = false, defaultValue = "") String dcuId,
			@RequestParam(required = false, defaultValue = "") String day) throws Exception {

		List<RawLpCycleTemp> data = lookupService.getLpHour(estateSeq, buildingSeq, dcuId, day);

		return Mono.just(new ResponseListVO<RawLpCycleTemp>(request, data));
	}

	@RequestMapping(value = "/test/lp/duration", method = RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	@Description(value = "검침조회 : 검침 주기별 데이터")
	public Mono<ResponseListVO<RawLpCycleTemp>> getMeteringDurationData(HttpServletRequest request,
			@RequestParam(required = false, defaultValue = "0") int estateSeq,
			@RequestParam(required = false, defaultValue = "0") int buildingSeq,
			@RequestParam(required = false, defaultValue = "") String dcuId,
			@RequestParam(required = false, defaultValue = "") String day) throws Exception {

		List<RawLpCycleTemp> data = lookupService.getLpDuration(estateSeq, buildingSeq, dcuId, day);

		return Mono.just(new ResponseListVO<RawLpCycleTemp>(request, data));
	}

}
