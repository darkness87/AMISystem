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
	public Mono<ResponseListVO<RealTimeVO>> getMonthListData(HttpServletRequest request, @RequestParam int estateSeq)
			throws Exception {

		List<RealTimeVO> data = infoService.getRealTimeData(estateSeq);

		return Mono.just(new ResponseListVO<RealTimeVO>(request, data));
	}

}
