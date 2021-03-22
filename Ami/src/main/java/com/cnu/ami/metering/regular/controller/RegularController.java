package com.cnu.ami.metering.regular.controller;

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
import com.cnu.ami.common.ResponseVO;
import com.cnu.ami.metering.regular.models.RegularMonthVO;
import com.cnu.ami.metering.regular.service.RegularService;

import reactor.core.publisher.Mono;

/**
 * 
 * @author skchae@cnuglobal.com
 * @apiNote regular api (월 검침)
 *
 */

@RestController
@RequestMapping(value = "/api/metering/regular")
public class RegularController {

	@Autowired
	RegularService regularService;

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	@Description(value = "월검침 : 목록")
	public Mono<ResponseListVO<RegularMonthVO>> getMonthListData(HttpServletRequest request,
			@RequestParam int estateSeq, @RequestParam int yearMonth) throws Exception {

		List<RegularMonthVO> data = regularService.getMonthRegularData(estateSeq);

		return Mono.just(new ResponseListVO<RegularMonthVO>(request, data));
	}

	@RequestMapping(value = "/info", method = RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	@Description(value = "월검침 : 상세")
	public Mono<ResponseVO<Object>> getMonthData(HttpServletRequest request, @RequestParam String meterId)
			throws Exception {

		return Mono.just(new ResponseVO<Object>(request, null));
	}

}
