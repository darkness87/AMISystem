package com.cnu.ami.metering.info.controller;

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
import com.cnu.ami.metering.info.models.CollectDcuVO;
import com.cnu.ami.metering.info.models.CollectMeterVO;
import com.cnu.ami.metering.info.models.RealTimeVO;
import com.cnu.ami.metering.info.service.InfoService;

import reactor.core.publisher.Mono;

/**
 * 
 * @author skchae@cnuglobal.com
 * @apiNote info api (검침 정보)
 *
 */

@RestController
@RequestMapping(value = "/api/metering/info")
public class InfoController {

	@Autowired
	InfoService infoService;

	@RequestMapping(value = "/realtime", method = RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	@Description(value = "실시간 검침 : 단지별")
	public Mono<ResponseListVO<RealTimeVO>> getRealTimeData(HttpServletRequest request, @RequestParam int estateSeq)
			throws Exception {

		List<RealTimeVO> data = infoService.getRealTimeData(estateSeq);

		return Mono.just(new ResponseListVO<RealTimeVO>(request, data));
	}

	@RequestMapping(value = "/collection/dcu", method = RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	@Description(value = "수집정보 : 단지별 DCU")
	public Mono<ResponseListVO<CollectDcuVO>> getDcuListData(HttpServletRequest request, @RequestParam int estateSeq)
			throws Exception {

		List<CollectDcuVO> data = infoService.getDcuData(estateSeq);

		return Mono.just(new ResponseListVO<CollectDcuVO>(request, data));
	}

	@RequestMapping(value = "/collection/meterGeneal", method = RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	@Description(value = "수집정보 : 단지별 METER")
	public Mono<ResponseListVO<CollectMeterVO>> getMeterListData(HttpServletRequest request, @RequestParam int estateSeq, @RequestParam String day,
			@RequestParam String dcuId) throws Exception {

		List<CollectMeterVO> data = infoService.getMeterData(estateSeq, day, dcuId);

		return Mono.just(new ResponseListVO<CollectMeterVO>(request, data));
	}
	
	@RequestMapping(value = "/collection/meter", method = RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	@Description(value = "수집정보 : 단지별 METER")
	public Mono<ResponseListVO<CollectMeterVO>> getMeterListAggrData(HttpServletRequest request, @RequestParam int estateSeq, @RequestParam String day,
			@RequestParam String dcuId) throws Exception {

		List<CollectMeterVO> data = infoService.getMeterAggrData(estateSeq, day, dcuId);

		return Mono.just(new ResponseListVO<CollectMeterVO>(request, data));
	}

}
